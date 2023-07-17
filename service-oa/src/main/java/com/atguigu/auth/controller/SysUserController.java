package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Jiayaoyi
 * @since 2023-07-17
 */
@RestController
@RequestMapping("/admin/system/sysUser")
@Api(tags = "用户管理接口")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("查询所有用户")
    @GetMapping("findAll")
    public Result<List<SysUser>> findAll(){
        List<SysUser> sysUserList = sysUserService.list();
        return Result.ok(sysUserList);
    }

    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryUser(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(page,limit);

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        String username = sysUserQueryVo.getKeyword();
        if(!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername,username);
        }

        IPage<SysUser> pageModel = sysUserService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result<String> save(@RequestBody  SysUser sysUser){
        boolean isSuccess = sysUserService.save(sysUser);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("添加失败");
        }
    }

    @ApiOperation("获取用户")
    @GetMapping("get/{id}")
    public Result<SysUser> get (@PathVariable Long id ){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation("修改用户")
    @PutMapping("/update")
    public Result<String> update(@RequestBody SysUser sysUser){
        boolean isSuccess = sysUserService.updateById(sysUser);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("更新失败");
        }
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result<String> delete(@PathVariable Long id){
        boolean isSuccess = sysUserService.removeById(id);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("批量删除用户")
    @DeleteMapping("/batchRemove")
    public Result<String> batchRemove(@RequestBody List<Long> idList){
        boolean isSuccess = sysUserService.removeByIds(idList);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }
}
