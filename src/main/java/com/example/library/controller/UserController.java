package com.example.library.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.library.common.controller.BaseExtController;
import com.example.library.constant.AuthorizeConstant;
import com.example.library.pojo.dto.UserDTO;
import com.example.library.pojo.entity.User;
import com.example.library.service.RoleService;
import com.example.library.service.UserService;
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
@RequestMapping("/user")
@Tag(name = "用户信息接口", description = "用户信息接口")
@PreAuthorize(AuthorizeConstant.HAS_ROLE_ADMIN)
public class UserController extends BaseExtController<User, UserDTO, UserService> {
    @Resource
    private RoleService roleService;

    @Override
    @PostMapping("/save")
    @Operation(summary = "新增", description = "传入新增对象")
    public Boolean save(@Valid @RequestBody UserDTO dto) {
        User user = userPropertySet(dto);
        return service.save(user);
    }

    @Override
    @PostMapping("/update")
    @Operation(summary = "修改", description = "传入修改对象")
    public Boolean update(@Valid @RequestBody UserDTO dto) {
        User user = userPropertySet(dto);
        return service.updateById(user);
    }

    private User userPropertySet(UserDTO dto) {
        User user = new User();
        BeanUtil.copyProperties(dto, user);
        if (!CollectionUtils.isEmpty(dto.getRoleIds())) {
            user.setRoleList(roleService.listByIds(dto.getRoleIds()));
        }
        return user;
    }
}
