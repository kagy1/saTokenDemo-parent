package com.kagy.satokendemoweb.entity;

import lombok.Data;

@Data
public class UpdatePassWordParm {
    private Integer userId;
    private String oldPassword;
    private String password;
}
