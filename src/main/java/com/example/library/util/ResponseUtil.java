package com.example.library.util;

import com.example.library.etc.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author WY
 **/
public class ResponseUtil {
    public static void response(HttpServletResponse response, Result<?> res) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(res));
        out.flush();
        out.close();
    }
}
