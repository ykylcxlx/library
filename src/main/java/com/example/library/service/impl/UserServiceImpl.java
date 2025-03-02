package com.example.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.library.common.service.impl.BaseServiceImpl;
import com.example.library.constant.CommonConstant;
import com.example.library.etc.ServiceException;
import com.example.library.mapper.UserMapper;
import com.example.library.pojo.dto.LoginDTO;
import com.example.library.pojo.entity.User;
import com.example.library.pojo.entity.UserRole;
import com.example.library.pojo.vo.LoginVO;
import com.example.library.service.RoleService;
import com.example.library.service.UserRoleService;
import com.example.library.service.UserService;
import com.example.library.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author WangYi
 * @create 2024/7/30
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService, UserDetailsService {
    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    @Lazy
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User entity) {
        if (!StringUtils.hasText(entity.getName())) {
            entity.setName(entity.getAccount());
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (!super.save(entity)) {
            return false;
        }
        persistHandle(entity, true);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(User entity) {
        if (!StringUtils.hasText(entity.getName())) {
            entity.setName(entity.getAccount());
        }
        if (StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        if (!super.updateById(entity)) {
            return false;
        }
        persistHandle(entity, false);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectUserByAccount(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return user;
    }

    public void persistHandle(User entity, boolean isSave) {
        List<UserRole> userRoleList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(entity.getRoleList())) {
            userRoleList = entity.getRoleList().stream().map(item -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(item.getId());
                return userRole;
            }).collect(Collectors.toList());
        } else {
            UserRole userRole = new UserRole();
            userRole.setUserId(entity.getId());
            userRole.setRoleId(roleService.getByCode(CommonConstant.ROLE_USER).getId());
            userRoleList.add(userRole);
        }
        if (!isSave) {
            LambdaUpdateWrapper<UserRole> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(UserRole::getUserId, entity.getId());
            if (!userRoleService.remove(wrapper)) {
                throw new ServiceException("清除用户旧数据发生异常");
            }
        }
        if (!userRoleService.saveBatch(userRoleList)) {
            throw new ServiceException("保存用户信息发生异常");
        }
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getAccount(), loginDTO.getPassword());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new ServiceException("用户名或密码错误");
        }
        if (authentication == null) {
            throw new ServiceException("用户名或密码错误");
        }

        User targetUser = (User) authentication.getPrincipal();
        LoginVO loginVO = new LoginVO();
        loginVO.setId(targetUser.getId());
        loginVO.setAccount(targetUser.getAccount());
        loginVO.setToken(jwtTokenUtil.generateUserToken(targetUser));
        return loginVO;
    }
}
