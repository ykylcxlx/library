package com.example.library.etc;

import org.springframework.http.HttpStatus;

/**
 * @author wangyi
 * @create 2024/7/30
 * HTTP请求响应体处理类
 */
public class ResultGenerator {
    private static <T> Result<T> genResult(Integer status, String message, T data) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public static Result<?> genSuccess() {
        return genResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    public static <T> Result<T> genSuccess(T data) {
        return genResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static Result<?> genFail(Integer status, String message) {
        return genResult(status, message, null);
    }

    public static Result<?> genFail(HttpStatus httpStatus) {
        return genFail(httpStatus.value(), httpStatus.getReasonPhrase());
    }
}
