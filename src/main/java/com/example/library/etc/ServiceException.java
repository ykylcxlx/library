package com.example.library.etc;

/**
 * @author wangyi
 * @create 2024/7/30
 * 自定义运行时异常
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
