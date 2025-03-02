package com.example.library.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@UniqCheck
public class Role extends BaseEntity {
    String roleName;

    @UniqColumn(message = "已存在同名角色码")
    String roleCode;

    @TableField(exist = false)
    List<Permission> permissionList;
}
