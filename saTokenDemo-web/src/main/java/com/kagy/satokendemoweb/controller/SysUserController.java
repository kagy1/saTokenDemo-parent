package com.kagy.satokendemoweb.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kagy.satokendemoweb.Vo.AssignTreeVo;
import com.kagy.satokendemoweb.Vo.LoginVo;
import com.kagy.satokendemoweb.entity.*;
import com.kagy.satokendemoweb.service.SysMenuService;
import com.kagy.satokendemoweb.service.SysUserService;
import com.kagy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private SysMenuService sysMenuService;
//    @Autowired
//    private JwtProperties jwtProperties;

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

    // 图片验证码
    @PostMapping("/getImage")
    public Result imageCode(HttpServletRequest request) {
        // 获取 Session
        HttpSession session = request.getSession();
        // 验证码为空，重新生成
        String imageText = defaultKaptcha.createText();
        // 存放到session中
        session.setAttribute("imageCode", imageText);
        // 生成图片,转换为base64
        BufferedImage image = defaultKaptcha.createImage(imageText);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            String dataUrl = "data:image/jpg;base64," + encode;
            return Result.success("生成成功", dataUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("生成失败");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam param, HttpServletRequest request) {
        // 获取前端传递过来的code
        String code = param.getCode();
        // 获取session里的验证码
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("imageCode");
        if (StrUtil.isBlank(sessionCode)) {
            return Result.error("验证码已过期，请重新获取");
        }
        // 判断验证码是否正确
        if (!sessionCode.equalsIgnoreCase(code)) {
            return Result.error("验证码错误");
        }

        // 验证码正确，立即清除避免重复使用
        session.removeAttribute("imageCode");

        // 验证用户名和密码是否为空
        if (StrUtil.isBlank(param.getUsername()) || StrUtil.isBlank(param.getPassword())) {
            return Result.error("用户名和密码不能为空");
        }

        // 查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, param.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one == null) {
            return Result.error("用户名错误");
        }
        if (!one.getPassword().equals(param.getPassword())) {
            return Result.error("密码错误");
        }
        if (one.getIsEnabled() == 0) {
            return Result.error("用户已被禁用，请联系管理员");
        }

        // 使用 Sa-Token 进行登录
        StpUtil.login(one.getUserId());

        // 返回用户信息和toekn
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(one.getUserId());
        loginVo.setNickName(one.getNickName());

//        Date now = new Date();
//        // 根据配置的分钟数计算过期时间
//        Date expireDate = DateUtil.offsetMinute(now, jwtProperties.getExpiration());

        // 生成JWT token  替换为 Sa-Token
//        String token = JWT.create()
//                .setPayload("userId", one.getUserId())
//                .setPayload("username", one.getUsername())
//                .setIssuer(jwtProperties.getIssuer()) // 设置签发者，对应配置中的 issuer
//                .setIssuedAt(now) // 设置签发时间
//                .setExpiresAt(expireDate) // 设置过期时间
//                .setKey(jwtProperties.getSecret().getBytes()) // 设置密钥，对应配置中的 secret
//                .sign();

        // 将token设置到返回对象中
//        loginVo.setToken(token);
        loginVo.setToken(StpUtil.getTokenValue());
        return Result.success("登录成功", loginVo);

    }

    // 查询菜单树
    @GetMapping("/getAssignTree")
    public Result getAssingTree(AssignTreeParm param) {
        AssignTreeVo assignTreeVo = sysUserService.getAssignTree(param);
        return Result.success("查询成功", assignTreeVo);
    }

    // 更新密码
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePassWordParm param) {
        SysUser user = sysUserService.getById(param.getUserId());
        if (!param.getOldPassword().equals(user.getPassword())) {
            return Result.error("原密码不正确");
        }
        // 更新条件
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword, param.getPassword())
                .eq(SysUser::getUserId, param.getUserId())
                .eq(SysUser::getPassword, param.getOldPassword());

        if (sysUserService.update(updateWrapper)) {
            return Result.success("密码修改成功");
        }
        return Result.error("密码修改失败");

    }

    // 获取用户信息
    @GetMapping("/getUserInfo")
    public Result getUserInfo(Integer userId) {
        // 根据id查询用户信息
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        List<SysMenu> menuList = null;
        if (StrUtil.isNotBlank(user.getIsAdmin()) && user.getIsAdmin().equals("1")) {
            // 超级管理员
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.getMenuByUserId(userId);
        }

        // 处理权限列表
        List<String> permissions = new ArrayList<>();
        if (menuList != null && !menuList.isEmpty()) {
            permissions = menuList.stream()
                    .filter(item -> StrUtil.isNotEmpty(item.getCode()))
                    .map(SysMenu::getCode)
                    .collect(Collectors.toList());
        }

        // 设置返回值（无论权限是否为空都返回用户信息）
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getNickName());
        userInfo.setUserId(user.getUserId());
        userInfo.setPermissions(permissions.toArray(new String[0])); // 修正拼写错误

        return Result.success("查询成功", userInfo);
    }

    @PostMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success("退出成功");
    }


}



