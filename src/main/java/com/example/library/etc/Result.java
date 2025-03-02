package com.example.library.etc;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangyi
 * @create 2024/7/30
 * 统一API响应结果封装
 */
@Getter
@Setter
public class Result<T> {
    Integer status;
    String msg;
    T data;
}

