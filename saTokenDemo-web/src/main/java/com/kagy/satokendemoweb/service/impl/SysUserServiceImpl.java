package com.kagy.satokendemoweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kagy.satokendemoweb.Vo.AssignTreeVo;
import com.kagy.satokendemoweb.entity.*;
import com.kagy.satokendemoweb.mapper.SysMenuMapper;
import com.kagy.satokendemoweb.mapper.SysUserMapper;
import com.kagy.satokendemoweb.service.SysMenuService;
import com.kagy.satokendemoweb.service.SysUserRoleService;
import com.kagy.satokendemoweb.service.SysUserService;
import com.kagy.satokendemoweb.utils.MakeMenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-08-05
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public IPage<SysUser> getUserListWithRoles(IPage<SysUser> page, LambdaQueryWrapper<SysUser> queryWrapper) {
        // 先查询用户基本信息
        // page为分页配置，queryWrapper为查询条件
        IPage<SysUser> userPage = this.page(page, queryWrapper);
        if (!userPage.getRecords().isEmpty()) {
            // 提取所有用户ID
            // 例如：查到3个用户，ID分别是 [1, 5, 8]
            List<Integer> collect = userPage.getRecords().stream().map(SysUser::getUserId).collect(Collectors.toList());
            // 构造角色查询条件0
            // 相当于 SQL：WHERE user_id IN (1,5,8)
            LambdaQueryWrapper<SysUserRole> roleQueryWrapper = new LambdaQueryWrapper<>();
            roleQueryWrapper.in(SysUserRole::getUserId, collect);
            // 一次性查询出所有相关的用户角色关系
            List<SysUserRole> userRoles = sysUserRoleService.list(roleQueryWrapper);
            // 按用户ID分组
            Map<Integer, List<Integer>> userRoleMap = userRoles.stream()
                    .collect(Collectors.groupingBy(
                            SysUserRole::getUserId,
                            Collectors.mapping(SysUserRole::getRoleId, Collectors.toList())
                    ));
            // 设置每个用户的角色ID列表
            userPage.getRecords().forEach(user -> {
                List<Integer> roleIds = userRoleMap.get(user.getUserId());
                user.setRoleIds(roleIds != null ? roleIds : new ArrayList<>());
            });
        }
        return userPage;

    }

    @Override
    public void saveUser(SysUser sysUser) {
        int i = this.baseMapper.insert(sysUser);
        if (i > 0 && sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
            List<SysUserRole> roles = new ArrayList<>();
            sysUser.getRoleIds().forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getUserId());
                sysUserRole.setRoleId(roleId);
                roles.add(sysUserRole);
            });
            sysUserRoleService.saveBatch(roles);
        }
    }

    @Override
    @Transactional
    public void editUser(SysUser sysUser) {
        int i = this.baseMapper.updateById(sysUser);
        if (i > 0) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, sysUser.getUserId());
            sysUserRoleService.remove(queryWrapper);
            if (sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
                List<SysUserRole> roles = new ArrayList<>();
                sysUser.getRoleIds().forEach(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getUserId());
                    sysUserRole.setRoleId(Integer.valueOf(roleId));
                    roles.add(sysUserRole);
                });
                // 保存到用户角色关系表
                sysUserRoleService.saveBatch(roles);
            }
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        int i = this.baseMapper.deleteById(userId);
        if (i > 0) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, userId);
            sysUserRoleService.remove(queryWrapper);
        }
    }

    @Override
    public AssignTreeVo getAssignTree(AssignTreeParm assignTreeParm) {
        // 查询用户的信息
        SysUser user = this.getById(assignTreeParm.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        List<SysMenu> menuList = null;
        // 判断是否是超级管理员
        if (StrUtil.isNotBlank(user.getIsAdmin()) && user.getIsAdmin().equals("1")) {
            // 是超级管理员，查询所有菜单
            menuList = sysMenuService.list();
        } else {
            // 不是超级管理员，查询用户有权限的菜单
            menuList = sysMenuService.getMenuByUserId(assignTreeParm.getUserId());
        }
        // 组装树
        List<SysMenu> sysMenus = MakeMenuTree.makeTree(menuList, 0L);
        // 查询用户角色原来的菜单
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(assignTreeParm.getRoleId());
        // 提取菜单ID列表
        List<Long> ids = new ArrayList<>();
        if (roleList != null && roleList.size() > 0) {
            roleList.stream().filter(item -> item != null).forEach(item -> ids.add(item.getMenuId()));
        }
        AssignTreeVo assignTreeVo = new AssignTreeVo();
        assignTreeVo.setMenuList(sysMenus);
        assignTreeVo.setCheckList(ids.toArray());
        return assignTreeVo;
    }
}

