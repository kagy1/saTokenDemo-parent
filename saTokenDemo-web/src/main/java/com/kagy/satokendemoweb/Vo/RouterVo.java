package com.kagy.satokendemoweb.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)  // JSON序列化时忽略空值和null值
public class RouterVo {
    private String path;
    private String component;
    private String name;
    private String redirect;
    private Meta meta;


    @Data
    @AllArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private boolean visible;
        private boolean keepTab;
    }

    private List<RouterVo> children = new ArrayList<>();
}
