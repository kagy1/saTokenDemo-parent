package com.kagy.satokendemoweb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author author
 * @since 2025-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "登录账户")
    private String username;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "是否是超级管理员 1: 是 0: 否")
    private String isAdmin;

    @ApiModelProperty(value = "账户是否过期(1 未过期，0已过期)")
    private Integer isAccountNonExpired;

    @ApiModelProperty(value = "账户是否被锁定(1 未锁定，0已锁定)")
    private Integer isAccountNonLocked;

    @ApiModelProperty(value = "凭证是否过期(1 未过期，0已过期)")
    private Integer isCredentialsNonExpired;

    @ApiModelProperty(value = "账户是否可用(1 可用，0 禁用用户)")
    private Integer isEnabled;

    @ApiModelProperty(value = "昵名")
    private String nickName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
