package com.example.library.config;

import com.example.library.annotations.EnableOriginalResp;
import com.example.library.etc.Result;
import com.example.library.etc.ResultGenerator;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wangyi
 * @create 2024/7/30
 * 全局Controller响应返回处理切面
 */
@RestControllerAdvice(basePackages = "com.example.library.controller")
public class GlobalResponseConf implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(EnableOriginalResp.class) ||
                methodParameter.hasMethodAnnotation(EnableOriginalResp.class)) {
            return o;
        }
        if (o instanceof Result) {
            return o;
        }
        return ResultGenerator.genSuccess(o);
    }
}
