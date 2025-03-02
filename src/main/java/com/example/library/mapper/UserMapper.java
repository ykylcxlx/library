package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.library.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYi
 * @create 2024/7/30
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 按账号名查询用户信息
     * @param account
     * @return
     */
    User selectUserByAccount(@Param("account") String account);
}
