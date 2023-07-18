package com.atguigu.auth.service.impl;


import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.util.MenuHelper;
import com.atguigu.common.exception.CustomException;
import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单
        List<SysMenu> sysMeneList = baseMapper.selectList(null);
        //递归下级菜单
        List<SysMenu> menuList = MenuHelper.buildTreeMenu(sysMeneList);
        return menuList;
    }

    @Override
    public void removeMenuById(Long id) {
        //判断当前菜单是否有子菜单
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new CustomException(201, "当前菜单有子菜单，不能删除");
        }
        //删除菜单
        baseMapper.deleteById(id);
    }
}
