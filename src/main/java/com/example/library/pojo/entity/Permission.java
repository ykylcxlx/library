package com.example.library.pojo.entity;

import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@UniqCheck
public class Permission extends BaseEntity {
    String permissionName;

    @UniqColumn(message = "已存在相同权限值")
    String permissionValue;

//    String requestUrl;
}
