package com.kagy.satokendemoweb.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * 签发者
     */
    private String issuer;

    /**
     * 密钥
     */
    private String secret;

    /**
     * Token 过期时间（单位：分钟）
     */
    private Integer expiration;
}
