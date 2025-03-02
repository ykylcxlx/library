package com.example.library.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.library.common.service.impl.BaseServiceImpl;
import com.example.library.etc.ServiceException;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.RecordMapper;
import com.example.library.pojo.entity.*;
import com.example.library.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
public class BookServiceImpl extends BaseServiceImpl<Book, BookMapper> implements BookService {


    @Resource
    @Lazy
    private AuthenticationManager authenticationManager;

    @Resource
    @Lazy
    private BookService bookService;
    @Resource
    private RecordMapper recordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Book entity) {
        entity.setBorrowed(false);
        if (!super.save(entity)) {
            return false;
        }
        //persistHandle(entity, true,true);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Book entity) {
        Book book = super.getById(entity.getId());
        if (book != null && book.getBorrowed() && entity.getBorrowed()) {
            throw new ServiceException("书籍借出中，无法修改");
        }
        if (!super.updateById(entity)) {
            return false;
        }

        //persistHandle(entity, false,true);
        return true;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        Book book = super.getById(id);
        if (book != null){System.out.println("book_name"+book.getBookName());}
        if (book != null && book.getBorrowed()) {
            throw new ServiceException("书籍借出中，无法删除");
        }
        if (!super.removeById(id)) {
            return false;
        }

//        Book book = new Book();
//        book.setId((Long) id);
//        persistHandle(book, false,false);
        return true;
    }
//    @Override
//    public UserDetails loadBookByUsername(String boookname) throws UsernameNotFoundException {
//        Book book = baseMapper.selectBookByAccount(bookname);
//        if (book == null) {
//            throw new UsernameNotFoundException("用户名或密码错误");
//        }
//        return book;
//    }
//    public Book getBorrow(Long id){
//        Book book = baseMapper.selectBookByAccount(bookname);
//    }
    public void persistHandle(Book entity, boolean isSave,boolean isUpdate) {

        if (!isSave) {
            LambdaUpdateWrapper<Book> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(Book::getId, entity.getId());
            if (!bookService.remove(wrapper)) {
                throw new ServiceException("清除用户旧数据发生异常");
            }

        }
        if (isUpdate) {
            if (!bookService.save(entity)) {
                throw new ServiceException("保存用户信息发生异常");
            }
        }

    }
}
