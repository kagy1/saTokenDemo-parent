package com.kagy.satokendemoweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kagy.satokendemoweb.Vo.AssignTreeVo;
import com.kagy.satokendemoweb.entity.AssignTreeParm;
import com.kagy.satokendemoweb.entity.MakeMenuTree;
import com.kagy.satokendemoweb.entity.SysMenu;
import com.kagy.satokendemoweb.entity.SysUser;
import com.kagy.satokendemoweb.mapper.SysMenuMapper;
import com.kagy.satokendemoweb.service.SysMenuService;
import com.kagy.satokendemoweb.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-08-08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getParent() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getType, "0", "1").orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenus = this.list(queryWrapper);
        // 组装顶级树
        SysMenu menu = new SysMenu();
        menu.setTitle("顶级菜单");
        menu.setLabel("顶级菜单");
        menu.setParentId(-1L);
        menu.setMenuId(0L);
        menu.setValue(0L);
        sysMenus.add(menu);
        // 组装菜单树
        List<SysMenu> tree = MakeMenuTree.makeTree(sysMenus, -1L);
        return tree;
    }

    @Override
    public List<SysMenu> getMenuByUserId(Integer userId) {
        return sysMenuMapper.getMenuByUserId(userId);
    }

    @Override
    public List<SysMenu> getMenuByRoleId(Integer roleId) {
        return sysMenuMapper.getMenuByRoleId(roleId);
    }
}
