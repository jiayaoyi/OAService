package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysRoleController
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */
@RestController
@Slf4j
@RequestMapping("/admin/system/sysRole")
@Api(tags = "角色管理接口")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAll(){
        List<SysRole> sysRoleList = sysRoleService.list();
        return Result.ok(sysRoleList);
    }

    /**
     * 分页查询角色
     * @param page 页码
     * @param limit 页面大小
     * @param sysRoleQueryVo 用户角色查询条件
     * @return
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo) {
        //调用service的方法实现
        //1 创建Page对象，传递分页相关参数
        //page 当前页  limit 每页显示记录数
        Page<SysRole> pageParam = new Page<>(page,limit);

        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)) {
            //封装 like模糊查询
            wrapper.like(SysRole::getRoleName,roleName);
        }

        //3 调用方法实现
        IPage<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }
    @ApiOperation("添加角色")
    @PostMapping("/save")
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
    public Result<SysRole> get (@PathVariable Long id ){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation("修改角色")
    @PutMapping("/update")
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
    public Result<String> batchRemove(@RequestBody List<Long> idList){
        boolean isSuccess = sysRoleService.removeByIds(idList);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }
}
