package com.example.library.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.pojo.entity.BaseEntity;
import com.example.library.constant.CommonConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@UniqCheck
public class User extends BaseEntity implements UserDetails {
    @UniqColumn(message = "已存在同名用户")
    String account;

    @JsonIgnore
    String password;

    String name;

    @TableField(exist = false)
    List<Role> roleList;

    @TableField(exist = false)
    List<Permission> permissionList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(roleList) && CollectionUtils.isEmpty(permissionList)) {
            return authorityList;
        }
        if (!CollectionUtils.isEmpty(roleList)) {
            List<GrantedAuthority> roleAuthorities = roleList.stream().map(item ->
                    new SimpleGrantedAuthority(CommonConstant.SPRING_SECURITY_ROLE_PREFIX.concat(item.getRoleCode())))
                    .collect(Collectors.toList());
            authorityList.addAll(roleAuthorities);
        }
        if (!CollectionUtils.isEmpty(permissionList)) {
            List<GrantedAuthority> permissionAuthorities = permissionList.stream().map(item -> new SimpleGrantedAuthority(item.getPermissionValue())).collect(Collectors.toList());
            authorityList.addAll(permissionAuthorities);
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }
}
