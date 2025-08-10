package com.kagy.satokendemoweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kagy.satokendemoweb.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author author
 * @since 2025-08-08
 */
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getParent();
}
