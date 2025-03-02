package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.library.pojo.entity.Book;
import com.example.library.pojo.entity.User;
import org.apache.ibatis.annotations.Param;


public interface BookMapper extends BaseMapper<Book> {
    /**
     * 按账号名查询用户信息
     * @param name
     * @return
     */
    Book selectBookByName(@Param("name") String name);


}
