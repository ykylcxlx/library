package com.example.library.authorize;

import com.example.library.etc.ResultGenerator;
import com.example.library.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @author WY
 **/
public class AuthEntryHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if ((authException instanceof BadCredentialsException) || (authException instanceof UsernameNotFoundException)) {
            ResponseUtil.response(response, ResultGenerator.genFail(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误"));
            return;
        }

        ResponseUtil.response(response, ResultGenerator.genFail(HttpStatus.UNAUTHORIZED));
    }
}
