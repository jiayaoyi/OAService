package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
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
import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
@Api(tags = "角色管理接口")
@EnableCaching
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("findAll")
    @Cacheable(value = "roles", key = "'findAll'")
    public Result<List<SysRole>> findAll(){
        List<SysRole> sysRoleList = sysRoleService.list();
        return Result.ok(sysRoleList);
    }

    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page,limit);

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)) {
            wrapper.like(SysRole::getRoleName,roleName);
        }
        IPage<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加角色")
    @PostMapping("/save")
    @CachePut(value = "roles", key = "#sysRole.id")
    public Result<String> save(@RequestBody  SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("添加失败");
        }
    }

    @ApiOperation("获取角色")
    @GetMapping("get/{id}")
    @Cacheable(value = "roles", key = "#id")
    public Result<SysRole> get (@PathVariable Long id ){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation("修改角色")
    @PutMapping("/update")
    @CachePut(value = "roles", key = "#sysRole.id")
    public Result<String> update(@RequestBody  SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("更新失败");
        }
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/remove/{id}")
    @CacheEvict(value = "roles", key = "#id")
    public Result<String> delete(@PathVariable Long id){
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    @CacheEvict(value = "roles", allEntries = true)
    public Result<String> batchRemove(@RequestBody List<Long> idList){
        boolean isSuccess = sysRoleService.removeByIds(idList);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("查询所有角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> map = sysRoleService.findRoleDataByUserId(userId);
        return Result.ok(map);
    }

    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign (@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
}
