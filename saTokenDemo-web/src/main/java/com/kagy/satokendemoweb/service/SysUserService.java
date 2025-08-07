package com.kagy.satokendemoweb.service;

import com.kagy.satokendemoweb.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2025-08-05
 */
public interface SysUserService extends IService<SysUser> {
    void saveUser(SysUser sysUser);

    void editUser(SysUser sysUser);

    void deleteUser(Long userId);
}
