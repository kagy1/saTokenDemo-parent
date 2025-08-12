package com.kagy.satokendemoweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kagy.satokendemoweb.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-08-08
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getMenuByUserId(@Param("userId") Integer userId);

    List<SysMenu> getMenuByRoleId(@Param("roleId") Integer roleId);
}
