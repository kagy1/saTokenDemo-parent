package com.kagy.satokendemoweb.entity;

import lombok.Data;

import java.util.List;

@Data
public class SaveMenyParm {
    private Integer roleId;
    private List<Integer> list;
}
