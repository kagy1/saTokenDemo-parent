package com.kagy.satokendemoweb.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kagy.satokendemoweb.Vo.RouterVo;
import com.kagy.satokendemoweb.entity.SysMenu;
import com.kagy.satokendemoweb.entity.SysUser;
import com.kagy.satokendemoweb.service.SysMenuService;
import com.kagy.satokendemoweb.service.SysUserService;
import com.kagy.satokendemoweb.utils.MakeMenuTree;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-08-08
 */
@RestController
@RequestMapping("/api/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserService sysUserService;

    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody SysMenu sysMenu) {
        sysMenu.setCreateTime(LocalDateTime.now());
        if (sysMenuService.save(sysMenu)) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

    // 编辑
    @PutMapping
    public Result edit(@RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateTime(LocalDateTime.now());
        if (sysMenuService.updateById(sysMenu)) {
            return Result.success("编辑成功");
        } else {
            return Result.error("编辑失败");
        }
    }

    // 删除
    @DeleteMapping("/{menuId}")
    public Result delete(@PathVariable("menuId") Long menuId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, menuId);
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        if (list.size() > 0) {
            return Result.error("存在子菜单，不能删除");
        }
        if (sysMenuService.removeById(menuId)) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    // 列表
    @GetMapping("/list")
    public Result getList() {
        // 查询所有菜单
        List<SysMenu> list = sysMenuService.list();
        // 组装树结构
        List<SysMenu> sysMenus = MakeMenuTree.makeTree(list, 0L);
        return Result.success("查询成功", sysMenus);
    }

    // 上级菜单
    @GetMapping("/getParent")
    public Result getParent() {
        List<SysMenu> parent = sysMenuService.getParent();
        return Result.success("查询成功", parent);
    }

    // 获取菜单
    @GetMapping("/getMenuList")
    public Result getMenuList(Integer userId) {
        // 获取用户的信息
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 菜单数据
        List<SysMenu> menuList = null;
        // 判断是否为超级管理员
        if (StrUtil.isNotBlank(user.getIsAdmin()) && user.getIsAdmin().equals("1")) {
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        List<SysMenu> collect = null;
        // 过滤菜单数据，去掉按钮
        if (menuList != null && menuList.size() > 0) {
            collect = menuList.stream().filter(item -> StrUtil.isNotBlank(item.getType()) && !item.getType().equals("2")).collect(Collectors.toList());
        }
        // 组装路由数据
        List<RouterVo> routerVos = MakeMenuTree.makeRouter(collect, 0L);
        return Result.success("查询成功", routerVos);
    }


}
