package com.kagy.satokendemoweb.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kagy.satokendemoweb.entity.SysUser;
import com.kagy.satokendemoweb.entity.UserParam;
import com.kagy.satokendemoweb.service.SysUserService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-08-05
 */
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    public Result add(@RequestBody SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUserService.saveUser(sysUser);
        return Result.success("新增成功");
    }

    @PutMapping
    public Result edit(@RequestBody SysUser sysUser) {
        sysUser.setUpdateTime(LocalDateTime.now());
        sysUserService.editUser(sysUser);
        return Result.success("编辑成功");
    }

    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable("userId") Long userId) {
        sysUserService.deleteUser(userId);
        return Result.success("删除成功");
    }

    @GetMapping("/list")
    public Result list(UserParam userParam) {
        // 构造分页对象
        IPage<SysUser> sysUserIPage = new Page<>(userParam.getCurrentPage(), userParam.getPageSize());
        // 构造查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .like(StrUtil.isNotBlank(userParam.getUsername()), SysUser::getUsername, userParam.getUsername())
                .like(StrUtil.isNotBlank(userParam.getNickName()), SysUser::getNickName, userParam.getNickName())
                .like(StrUtil.isNotBlank(userParam.getEmail()), SysUser::getEmail, userParam.getEmail())
                .like(StrUtil.isNotBlank(userParam.getPhone()), SysUser::getPhone, userParam.getPhone());
        queryWrapper.orderByAsc(SysUser::getCreateTime);

        // 使用带角色信息的查询方法
        IPage<SysUser> sysUserIPageResult = sysUserService.getUserListWithRoles(sysUserIPage, queryWrapper);
        return Result.success(sysUserIPageResult);
    }

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody SysUser sysUser) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, sysUser.getUserId()).set(SysUser::getPassword, "123456");
        if (sysUserService.update(updateWrapper)) {
            return Result.success("重置密码成功");
        }
        return Result.error("重置密码失败");
    }

}



