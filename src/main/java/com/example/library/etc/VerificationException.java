package com.example.library.etc;

/**
 * @author wangyi
 * @create 2024/7/30
 * 自定义校验异常
 */
public class VerificationException extends RuntimeException {
    public VerificationException() {
    }

    public VerificationException(String message) {
        super(message);
    }

    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
