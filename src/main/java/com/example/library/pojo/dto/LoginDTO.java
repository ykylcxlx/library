package com.example.library.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author WangYi
 * @create 2024/7/31
 */
@Data
public class LoginDTO {
    @NotBlank(message = "账户不能为空")
    String account;

    @NotBlank(message = "密码不能为空")
    String password;
}
