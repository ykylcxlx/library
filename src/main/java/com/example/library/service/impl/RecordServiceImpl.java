package com.example.library.service.impl;

import com.example.library.common.service.impl.BaseServiceImpl;
import com.example.library.etc.ServiceException;
import com.example.library.mapper.BookMapper;
import com.example.library.pojo.entity.Book;
import com.example.library.pojo.entity.Record;
import com.example.library.mapper.RecordMapper;

import com.example.library.service.BookService;
import com.example.library.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

@Service
public class RecordServiceImpl extends BaseServiceImpl<Record, RecordMapper> implements RecordService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookService bookService;
    @Resource
    private RecordMapper recordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Record entity) {
        Book book = bookMapper.selectBookByName(entity.getBookName());
        if (book == null) {
            throw new ServiceException("目标书籍不存在");
        }else{
            if(book.getBorrowed()){
                throw new ServiceException("目标书籍已经被借出");
            }
        }
        Date now = new Date(System.currentTimeMillis());
        // 创建 Calendar 对象，并设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        // 添加一个月
        calendar.add(Calendar.MONTH, 1);

        // 获取添加一个月后的时间
        Date nextMonthDate = (Date) calendar.getTime();
        entity.setBorrow_time(now);
        entity.setReturn_time(nextMonthDate);
        if (!super.save(entity)) {
            return false;
        }
        book.setBorrowed(true);
        bookService.updateById(book);
        //persistHandle(entity, true,true);
        return true;
    }
    //续借
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Record entity) {//更新续借时间
        Record record = recordMapper.selectRecordById(entity.getId());
        Date date1 = record.getReturn_time();
        Date date2 = entity.getReturn_time();
        if(date1 != null && date2 != null && date1.before(date2)){
            record.setReturn_time(date2);
        }else{
            throw new ServiceException("输入时间不合法");
        }
        if (!super.updateById(record)) {
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Record getRecordByUserName(String name) {
        Record record = recordMapper.selectRecordByUserName(name);
        if (record == null ) {
            throw new ServiceException("无该用户记录");
        }
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
//        Record record = super.getById(id);
        Record record = recordMapper.selectRecordById(id);
        if (!super.removeById(id)) {
            return false;
        }

        Book book = bookMapper.selectBookByName(record.getBookName());
        System.out.println("book_name"+record.getBookName());
        book.setBorrowed(false);
        bookService.updateById(book);
        return true;
    }







}