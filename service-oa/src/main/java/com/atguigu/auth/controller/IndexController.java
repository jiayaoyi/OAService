package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.security.common.exception.CustomException;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.security.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IndexController
 *
 * @author Jia Yaoyi
 * @date 2023/07/17
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录方法
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        //获取输入的用户名和密码
		String username = loginVo.getUsername();
        String password = MD5.encrypt(loginVo.getPassword());
        //根据用户名和密码查询数据库
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        //判断用户是否存在
		if (sysUser == null){
            throw new CustomException(201,"用户名不存在");
        }
        //判断密码是否正确
        String passwordDB = sysUser.getPassword();
        if (!password.equals(passwordDB)){
            throw new CustomException(201,"密码错误");
        }
        //判断用户是否被禁用 1(true)启用 0(false)禁用
        if (sysUser.getStatus().intValue() == 0){
            throw new CustomException(201,"用户被禁用");
        }
        //登录成功，生成token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        //返回结果
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        //1 从请求头获取用户信息（获取请求头token字符串）
        String token = request.getHeader("token");

        //2 从token字符串获取用户id 或者 用户名称
        Long userId = JwtHelper.getUserId(token);

        //3 根据用户id查询数据库，把用户信息获取出来
        SysUser sysUser = sysUserService.getById(userId);

        //4 根据用户id获取用户可以操作菜单列表
        //查询数据库动态构建路由结构，进行显示
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);

        //5 根据用户id获取用户可以操作按钮列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        //6 返回相应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        //返回用户可以操作菜单
        map.put("routers",routerList);
        //返回用户可以操作按钮
        map.put("buttons",permsList);
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

}
