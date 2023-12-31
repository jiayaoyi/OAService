package com.atguigu.auth.service;


import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Jiayaoyi
 * @since 2023-07-17
 */
public interface SysUserService extends IService<SysUser> {
	public void updateStatus (Long id, Integer status);

	SysUser getUserByUserName(String userName);
}
