package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Jiayaoyi
 * @since 2023-07-18
 */
public interface SysMenuService extends IService<SysMenu> {
	List<SysMenu> findNodes();

	void removeMenuById(Long id);

	List<SysMenu> findMenuByRoleId(Long id);

	void doAssign(AssginMenuVo assginMenuVo);

	List<RouterVo> findUserMenuListByUserId(Long userId);

	List<String> findUserPermsByUserId(Long userId);
}
