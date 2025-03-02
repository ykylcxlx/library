package com.example.library.pojo.entity;

import com.example.library.common.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends BaseEntity {
    Long roleId;

    Long permissionId;
}
