package com.example.library.controller;

import com.example.library.common.controller.BaseExtController;
import com.example.library.constant.AuthorizeConstant;
import com.example.library.pojo.dto.PermissionDTO;
import com.example.library.pojo.entity.Permission;
import com.example.library.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "权限信息接口", description = "权限信息接口")
@PreAuthorize(AuthorizeConstant.HAS_ROLE_ADMIN)
public class PermissionController extends BaseExtController<Permission, PermissionDTO, PermissionService> {
}
