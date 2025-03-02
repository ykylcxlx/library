package com.example.library.service;

import com.example.library.common.service.BaseService;
import com.example.library.pojo.entity.Role;

/**
 * @author WangYi
 * @create 2024/7/30
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 按角色码查询
     * @param code
     * @return
     */
    Role getByCode(String code);
}
