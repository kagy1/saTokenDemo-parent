package com.kagy.satokendemoweb.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Integer userId;
    private String name;
    private Object[] permissons;
}
