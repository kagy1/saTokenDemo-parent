package com.kagy.satokendemoweb.entity;

import lombok.Data;

@Data
public class UserParam {
    // 当前是第几页
    private Long currentPage;
    // 每页显示多少条数据
    private Long pageSize;

    private String username;
    private String email;
    private String phone;
    private String nickName;
}
