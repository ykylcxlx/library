package com.example.library.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.library.common.controller.BaseExtController;
import com.example.library.constant.AuthorizeConstant;
import com.example.library.pojo.dto.RoleDTO;
import com.example.library.pojo.entity.Role;
import com.example.library.service.PermissionService;
import com.example.library.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色信息接口", description = "角色信息接口")
@PreAuthorize(AuthorizeConstant.HAS_ROLE_ADMIN)
public class RoleController extends BaseExtController<Role, RoleDTO, RoleService> {
    @Resource
    private PermissionService permissionService;

    @Override
    @PostMapping("/save")
    @Operation(summary = "新增", description = "传入新增对象")
    public Boolean save(@Valid @RequestBody RoleDTO dto) {
        Role role = rolePropertySet(dto);
        return service.save(role);
    }

    @Override
    @PostMapping("/update")
    @Operation(summary = "修改", description = "传入修改对象")
    public Boolean update(@Valid @RequestBody RoleDTO dto) {
        Role role = rolePropertySet(dto);
        return service.updateById(role);
    }





    private Role rolePropertySet(RoleDTO dto) {
        Role role = new Role();
        BeanUtil.copyProperties(dto, role);
        if (!CollectionUtils.isEmpty(dto.getPermissionIds())) {
            role.setPermissionList(permissionService.listByIds(dto.getPermissionIds()));
        }
        return role;
    }
}
