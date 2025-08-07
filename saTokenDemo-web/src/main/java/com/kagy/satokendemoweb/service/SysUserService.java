package com.kagy.satokendemoweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kagy.satokendemoweb.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2025-08-05
 */
public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> getUserListWithRoles(IPage<SysUser> page, LambdaQueryWrapper<SysUser> queryWrapper);

    void saveUser(SysUser sysUser);

    void editUser(SysUser sysUser);

    void deleteUser(Long userId);
}
