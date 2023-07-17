package com.atguigu.auth.service;


import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-17
 */
public interface SysUserService extends IService<SysUser> {
	public void updateStatus (Long id, Integer status);
}
