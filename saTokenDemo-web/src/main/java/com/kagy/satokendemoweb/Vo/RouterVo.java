package com.kagy.satokendemoweb.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {
    private String path;
    private String component;
    private String name;
    private String redirect;
    private String meta;
    private String icon;
}
