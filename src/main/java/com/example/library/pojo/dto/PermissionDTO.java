package com.example.library.pojo.dto;

import com.example.library.common.pojo.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionDTO extends BaseDTO {
    @NotBlank(message = "权限名称不能为空")
    String permissionName;

    @NotBlank(message = "权限值不能为空")
    String permissionValue;

//    String requestUrl;
}
