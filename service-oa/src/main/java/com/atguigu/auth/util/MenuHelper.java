package com.atguigu.auth.util;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuHelper
 *
 * @author Jia Yaoyi
 * @date 2023/07/18
 */
public class MenuHelper {

    public static List<SysMenu> buildTreeMenu(List<SysMenu> sysMeneList) {
        //递归方式构建菜单
        //创建list集合用于最终数据
        List<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单
        for (SysMenu sysMenu : sysMeneList) {
            //获取当前菜单的父id
            Long pid = sysMenu.getParentId().longValue();
            //判断是否为一级菜单
            if (pid == 0) {
                //为一级菜单，将其添加到tree集合
                trees.add(getChildren(sysMenu, sysMeneList));

            }
        }
        return trees;
    }

    public static SysMenu getChildren(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<>());
        //遍历所有菜单，判断是否为当前菜单的子菜单
        for (SysMenu menu : sysMenuList) {
            //获取当前菜单的id
            Long id = sysMenu.getId().longValue();
            //获取当前菜单的父id
            Long pid = menu.getParentId().longValue();
            //判断是否为当前菜单的子菜单
            if (id.equals(pid)) {
                //为当前菜单的子菜单，递归调用
                sysMenu.getChildren().add(getChildren(menu, sysMenuList));
            }
        }
        return sysMenu;
    }
}
