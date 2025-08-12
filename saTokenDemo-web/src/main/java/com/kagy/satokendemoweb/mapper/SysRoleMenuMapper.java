package com.kagy.satokendemoweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kagy.satokendemoweb.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-08-12
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    // 保存角色菜单
    boolean saveRoleMenu(@Param("roleId") Integer roleId, @Param("menuIds") List<Integer> menuIds);
}
