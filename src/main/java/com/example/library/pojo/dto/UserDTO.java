package com.example.library.pojo.dto;

import com.example.library.common.pojo.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    @Schema(description = "用户账号名")
    @NotBlank(message = "用户账号名不能为空")
    String account;

    @Schema(description = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    String password;

    @Schema(description = "用户姓名")
    String name;

    @Schema(description = "用户角色主键集合")
    List<Long> roleIds;
}
