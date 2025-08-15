package com.kagy.satokendemoweb.utils;

import com.kagy.satokendemoweb.Vo.RouterVo;
import com.kagy.satokendemoweb.entity.SysMenu;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //构造路由数据
    public static List<RouterVo> makeRouter(List<SysMenu> menuList, Long pid) {
        List<RouterVo> list = new ArrayList<>();

        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    RouterVo router = new RouterVo();
                    router.setName(item.getName());
                    router.setPath(item.getPath());

                    // 设置Meta信息
                    router.setMeta(new RouterVo().new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getVisible() != null ? item.getVisible() : true,
                            item.getKeepTab() != null ? item.getKeepTab() : true
                    ));

                    if (item.getParentId() == 0L && item.getType().equals("1")) {
                        // 一级菜单需要重定向到第一个子菜单
                        List<RouterVo> children = makeRouter(menuList, item.getMenuId());
                        router.setChildren(children);

                        // 设置重定向到第一个子路由
                        if (!children.isEmpty()) {
                            router.setRedirect(children.get(0).getPath());
                        }
                    } else {
                        // 叶子节点或二级菜单
                        router.setComponent(item.getUrl());

                        // 检查是否有子菜单
                        List<RouterVo> children = makeRouter(menuList, item.getMenuId());
                        if (!children.isEmpty()) {
                            router.setChildren(children);
                        }
                    }
                    list.add(router);
                });
        return list;
    }
}
