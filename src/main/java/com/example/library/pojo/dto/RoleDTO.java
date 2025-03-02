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
public class RoleDTO extends BaseDTO {
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    String roleName;

    @Schema(description = "角色代码")
    @NotBlank(message = "角色代码不能为空")
    String roleCode;

    @Schema(description = "权限主键集合")
    List<Long> permissionIds;
}
