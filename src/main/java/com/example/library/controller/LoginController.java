package com.example.library.controller;

import com.example.library.pojo.dto.LoginDTO;
import com.example.library.pojo.vo.LoginVO;
import com.example.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYi
 * @create 2024/7/31
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "登陆接口", description = "登陆接口")
public class LoginController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public LoginVO login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}
