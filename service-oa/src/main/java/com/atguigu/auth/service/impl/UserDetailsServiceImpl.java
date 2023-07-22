package com.atguigu.auth.service.impl;


import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.security.common.exception.CustomException;
import com.atguigu.security.custom.CustomUser;
import com.atguigu.security.custom.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserByUserName(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new CustomException(201,"账号已停用");
        }
        //根据用户ID查询用户操作权限数据
        List<String> userPermsByUserId = sysMenuService.findUserPermsByUserId(sysUser.getId());
        //创建list集合用于封装最终权限数据
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String perms : userPermsByUserId) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(perms.trim()));
        }
        return new CustomUser(sysUser, simpleGrantedAuthorities);
    }
}