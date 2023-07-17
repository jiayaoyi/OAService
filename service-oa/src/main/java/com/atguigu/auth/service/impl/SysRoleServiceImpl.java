package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * SysRoleServiceImpl
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Map<String, Object> findRoleDataByUserId(Long userId) {
        List<SysRole> sysRoleList = baseMapper.selectList(null);
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> existRoleList = sysUserRoleService.list(queryWrapper);
        List<Long> existRoleIdList = existRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<SysRole> assignRoleList = new ArrayList<>();
        for (SysRole sysRole : sysRoleList){
            if (existRoleIdList.contains(sysRole.getId())){
                assignRoleList.add(sysRole);
            }
        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoleList",assignRoleList);
        roleMap.put("allRolesList",sysRoleList);
        return roleMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, assginRoleVo.getUserId());
        sysUserRoleService.remove(queryWrapper);

        List<Long> roleIdList = assginRoleVo.getRoleIdList();

        List<SysUserRole> sysUserRoleList = roleIdList.stream()
                .filter(Objects::nonNull)
                .map(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(assginRoleVo.getUserId());
                    sysUserRole.setRoleId(roleId);
                    return sysUserRole;
                })
                .collect(Collectors.toList());

        sysUserRoleService.saveBatch(sysUserRoleList);
    }

}
