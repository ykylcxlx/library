package com.example.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.library.common.service.impl.BaseServiceImpl;
import com.example.library.etc.ServiceException;
import com.example.library.mapper.RoleMapper;
import com.example.library.pojo.entity.Role;
import com.example.library.pojo.entity.RolePermission;
import com.example.library.service.RolePermissionService;
import com.example.library.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements RoleService {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Role entity) {
        if (!super.save(entity)) {
            return false;
        }
        persistHandle(entity, true);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Role entity) {
        if (!super.updateById(entity)) {
            return false;
        }
        persistHandle(entity, false);
        return true;
    }

    public void persistHandle(Role entity, boolean isSave) {
        if (CollectionUtils.isEmpty(entity.getPermissionList())) {
            return;
        }
        List<RolePermission> rolePermissionList = entity.getPermissionList().stream().map(item -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(item.getId());
            rolePermission.setPermissionId(entity.getId());
            return rolePermission;
        }).collect(Collectors.toList());
        if (!isSave) {
            LambdaUpdateWrapper<RolePermission> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(RolePermission::getRoleId, entity.getId());
            if (!rolePermissionService.remove(wrapper)) {
                throw new ServiceException("清除角色旧数据发生异常");
            }
        }
        if (!rolePermissionService.saveBatch(rolePermissionList)) {
            throw new ServiceException("保存角色信息发生异常");
        }
    }

    @Override
    public Role getByCode(String code) {
        Role wrapperRole = new Role();
        wrapperRole.setRoleCode(code);
        return getOne(wrapperRole);
    }
}
