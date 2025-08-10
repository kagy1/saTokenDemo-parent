package com.kagy.satokendemoweb.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MakeMenuTree {
    // 组装树工具
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        // 存放组装的树数据
        List<SysMenu> list = new ArrayList<>();
        if (menuList == null || menuList.size() == 0) {
            return list;
        }
        // 组装树
        menuList.stream()
                // 过滤顶级菜单
                // 只保留父ID等于传入参数pid的菜单项
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item, menu);  // 复制属性
                    menu.setLabel(item.getTitle());  // 设置显示名称
                    menu.setValue(item.getMenuId());  // 设置值
                    // 查找下级，递归调用
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
