package com.kagy.satokendemoweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kagy.satokendemoweb.entity.SaveMenyParm;
import com.kagy.satokendemoweb.entity.SysRoleMenu;
import com.kagy.satokendemoweb.mapper.SysRoleMenuMapper;
import com.kagy.satokendemoweb.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色与菜单对应关系表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-08-12
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void saveRoleMenu(SaveMenyParm saveMenyParm) {
        // 先删除
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, saveMenyParm.getRoleId());
        this.remove(queryWrapper);
        sysRoleMenuMapper.saveRoleMenu(saveMenyParm.getRoleId(), saveMenyParm.getList());
    }
}
