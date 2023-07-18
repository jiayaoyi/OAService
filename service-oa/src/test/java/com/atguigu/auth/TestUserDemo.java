package com.atguigu.auth;

import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.system.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * TestUserDemo
 *
 * @author Jia Yaoyi
 * @date 2023/07/17
 */

@SpringBootTest
public class TestUserDemo {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Test
    public void getAll(){
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        sysUsers.forEach(System.out::println);
    }

    @Test
    public void add(){
        SysUser user = new SysUser();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setName("Test User");
        user.setPhone("1234567890");
        user.setHeadUrl("http://test.com/avatar.jpg");
        user.setDeptId(1L);
        user.setPostId(1L);
        user.setDescription("This is a test user.");
        user.setOpenId("testOpenId");
        user.setStatus(1);
        // set all required fields according to your actual user model
        int rows = sysUserMapper.insert(user);
        System.out.println("Inserted: " + rows);
    }

    @Test
    public void update(){
        SysUser user = sysUserMapper.selectById(1); // replace 1 with the actual id
        user.setUsername("newTestUser");
        user.setPassword("newTestPassword");
        int rows = sysUserMapper.updateById(user);
        System.out.println("Updated: " + rows);
    }

    @Test
    public void delete(){
        int rows = sysUserMapper.deleteById(1); // replace 1 with the actual id
        System.out.println("Deleted: " + rows);
    }

    @Test
    public void addMultipleUsers() {
        for (int i = 1; i <= 1000; i++) {
            SysUser user = new SysUser();
            // Format the username as "test" followed by a three-digit number
            String username = String.format("%09d", i);
            user.setUsername(username);
            user.setPassword("testPassword");
            user.setName("TestUser " + i);
            user.setPhone("1234567890");
            user.setHeadUrl("http://test.com/avatar.jpg");
            user.setDeptId(1L);
            user.setPostId(1L);
            user.setDescription("This is test user number " + i);
            user.setOpenId("testOpenId" + i);
            user.setStatus(1);
            // set all required fields according to your actual user model
            int rows = sysUserMapper.insert(user);
            System.out.println("Inserted: " + rows + " Username: " + username);
        }
    }
}
