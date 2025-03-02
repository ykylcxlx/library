package com.example.library.authorize;

import com.example.library.etc.ResultGenerator;
import com.example.library.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author WY
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {
        ResponseUtil.response(resp, ResultGenerator.genFail(HttpStatus.FORBIDDEN.value(), "用户无权限"));
    }
}
