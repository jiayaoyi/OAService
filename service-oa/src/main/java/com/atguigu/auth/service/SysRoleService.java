package com.atguigu.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * SysRoleService
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */
public interface SysRoleService extends IService<SysRole> {
    Map<String,Object> findRoleDataByUserId(Long userId);

    void doAssign(AssginRoleVo assginRoleVo);
}
