package com.kagy.satokendemoweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kagy.satokendemoweb.entity.SaveMenyParm;
import com.kagy.satokendemoweb.entity.SysRoleMenu;

/**
 * <p>
 * 角色与菜单对应关系表 服务类
 * </p>
 *
 * @author author
 * @since 2025-08-12
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void saveRoleMenu(SaveMenyParm saveMenyParm);
}
