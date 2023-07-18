package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysUser")
@Api(tags = "用户管理接口")
@EnableCaching
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("查询所有用户")
    @GetMapping("findAll")
    @Cacheable(value = "users", key = "'findAll'")
    public Result<List<SysUser>> findAll(){
        List<SysUser> sysUserList = sysUserService.list();
        return Result.ok(sysUserList);
    }

    @ApiOperation("添加用户")
    @PostMapping("/save")
    @CachePut(value = "users", key = "#sysUser.id")
    public Result<String> save(@RequestBody  SysUser sysUser){
        //MD5加密
        String password = MD5.encrypt(sysUser.getPassword());
        if (!StringUtils.isEmpty(password)){
            sysUser.setPassword(password);
        }
        boolean isSuccess = sysUserService.save(sysUser);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("添加失败");
        }
    }

    @ApiOperation("获取用户")
    @GetMapping("get/{id}")
    @Cacheable(value = "users", key = "#id")
    public Result<SysUser> get (@PathVariable Long id ){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation("修改用户")
    @PutMapping("/update")
    @CachePut(value = "users", key = "#sysUser.id")
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
    @CacheEvict(value = "users", key = "#id")
    public Result<String> delete(@PathVariable Long id){
        boolean isSuccess = sysUserService.removeById(id);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryUser(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(page,limit);

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        String keyword = sysUserQueryVo.getKeyword();
        if (!StringUtils.isEmpty(sysUserQueryVo.getCreateTimeBegin())) {
            wrapper.ge(SysUser::getCreateTime, sysUserQueryVo.getCreateTimeBegin());
        }
        if (!StringUtils.isEmpty(sysUserQueryVo.getCreateTimeEnd())) {
            wrapper.le(SysUser::getCreateTime, sysUserQueryVo.getCreateTimeEnd());
        }
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SysUser::getUsername, keyword)
                    .or()
                    .like(SysUser::getName, keyword)
                    .or()
                    .like(SysUser::getPhone, keyword);
        }

        IPage<SysUser> pageModel = sysUserService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation("批量删除用户")
    @DeleteMapping("/batchRemove")
    @CacheEvict(value = "users", allEntries = true)
    public Result<String> batchRemove(@RequestBody List<Long> idList){
        boolean isSuccess = sysUserService.removeByIds(idList);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("根据用户ID更改用户状态")
    @GetMapping ("/updateStatus/{id}/{status}")
    @CacheEvict(value = "users", key = "#id")
    public Result updateStatus (@PathVariable Long id, @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }
}
