package com.example.library.config;

import com.example.library.etc.Result;
import com.example.library.etc.ResultGenerator;
import com.example.library.etc.ServiceException;
import com.example.library.etc.VerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author wangyi
 * @create 2024/7/30
 * 全局异常捕获配置类
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.library.controller")
public class GlobalExceptionConf {
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> serviceExceptionHandle(ServiceException ex) {
        return ResultGenerator.genFail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = VerificationException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> serviceExceptionHandle(VerificationException ex) {
        return ResultGenerator.genFail(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> bindExceptionHandle(BindException ex) {
        return ResultGenerator.genFail(HttpStatus.BAD_REQUEST.value(), ex.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> noHandlerFoundHandle() {
        return ResultGenerator.genFail(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> paramMissionHandle() {
        return ResultGenerator.genFail(HttpStatus.BAD_REQUEST.value(), "请求参数缺失或错误");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> argumentExceptionHandle(MethodArgumentNotValidException ex) {
        return ResultGenerator.genFail(HttpStatus.BAD_REQUEST.value(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> methodNotSupportHandle() {
        return ResultGenerator.genFail(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> securityAccessDeniedHandle() {
        return ResultGenerator.genFail(HttpStatus.FORBIDDEN.value(), "用户无权限");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> securityAuthenticationExceptionHandle() {
        return ResultGenerator.genFail(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public Result<?> exceptionHandle(Exception ex) {
        log.error("exception occur", ex);
        return ResultGenerator.genFail(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
