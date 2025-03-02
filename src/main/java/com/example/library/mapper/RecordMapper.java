package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.library.pojo.entity.Book;
import com.example.library.pojo.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface RecordMapper extends BaseMapper<Record> {
    Record selectRecordByName(@Param("name") String name);
    Record selectRecordByUserName(@Param("name") String name);
    Record selectRecordById(@Param("id") Serializable id);


}
