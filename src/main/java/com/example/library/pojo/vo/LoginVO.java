package com.example.library.pojo.vo;

import lombok.Data;

/**
 * @author WangYi
 * @create 2024/7/31
 */
@Data//帮助开发者自动生成Java类中的一些标准方法，如toString()、
// equals()、hashCode()和getter/setter方法等，从而简化开发过程。
public class LoginVO {
    Long id;

    String account;

    String token;
}
