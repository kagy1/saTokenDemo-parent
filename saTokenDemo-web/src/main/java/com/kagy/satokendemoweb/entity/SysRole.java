package com.kagy.satokendemoweb.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long roleID;

    private String roleName;

    private String type;

    private String remark;

    private Data createTime;

    private Data updateTime;
}
