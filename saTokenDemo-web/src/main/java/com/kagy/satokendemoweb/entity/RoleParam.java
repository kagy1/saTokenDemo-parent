package com.kagy.satokendemoweb.entity;

import lombok.Data;

@Data
public class RoleParam {
    // 当前是第几页
    private Long currentPage;
    // 每页显示多少条数据
    private Long pageSize;
    // 角色名称
    private String roleName;
}
