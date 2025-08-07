package com.kagy.satokendemoweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kagy.satokendemoweb.entity.SysUser;
import com.kagy.satokendemoweb.entity.SysUserRole;
import com.kagy.satokendemoweb.mapper.SysUserMapper;
import com.kagy.satokendemoweb.service.SysUserRoleService;
import com.kagy.satokendemoweb.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public void saveUser(SysUser sysUser) {
        int i = this.baseMapper.insert(sysUser);
        if (i > 0) {
            // 设置用户的角色
            String[] split = sysUser.getRoleId().split(",");
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                Arrays.stream(split).forEach(roleId -> {
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
    public void editUser(SysUser sysUser) {
        int i = this.baseMapper.updateById(sysUser);
        if (i > 0) {
            String[] split = sysUser.getRoleId().split(",");
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, sysUser.getUserId());
            sysUserRoleService.remove(queryWrapper);
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                Arrays.stream(split).forEach(roleId -> {
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
}

