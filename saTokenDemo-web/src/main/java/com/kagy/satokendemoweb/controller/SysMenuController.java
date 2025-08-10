package com.kagy.satokendemoweb.controller;


import com.kagy.satokendemoweb.entity.MakeMenuTree;
import com.kagy.satokendemoweb.entity.SysMenu;
import com.kagy.satokendemoweb.service.SysMenuService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
}
