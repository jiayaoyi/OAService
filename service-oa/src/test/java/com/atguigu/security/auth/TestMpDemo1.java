package com.atguigu.security.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * TestMpDemo1
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */

@SpringBootTest
public class TestMpDemo1 {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;
    @Test
    public void gettAll(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        System.out.println(sysRoles);
    }

    @Test
    public void add(){
        SysRole role = new SysRole();
        role.setRoleName("1231");
        role.setRoleCode("role");
        role.setDescription("角色管理员2");
        int rows = sysRoleMapper.insert(role);
        System.out.println(rows);
    }

    @Test
    public void update(){
        SysRole role = sysRoleMapper.selectById(10);
        role.setRoleName("sssxxx");
        int rows = sysRoleMapper.updateById(role);
        System.out.println(rows);
    }
}
