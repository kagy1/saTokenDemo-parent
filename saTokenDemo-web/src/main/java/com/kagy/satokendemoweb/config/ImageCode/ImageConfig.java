package com.kagy.satokendemoweb.config.ImageCode;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ImageConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        // 设置验证码图片是否有边框，yes表示有边框，no表示无边框
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");

        // 设置验证码图片边框的颜色，RGB值格式：红,绿,蓝
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");

        // 设置验证码文字的颜色，可以使用颜色名称或RGB值
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");

        // 设置验证码图片的宽度，单位像素
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "110");

        // 设置验证码图片的高度，单位像素
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");

        // 设置验证码文字的字体大小，单位像素
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");

        // 设置验证码在session中存储的key名称
        properties.setProperty(Constants.KAPTCHA_SESSION_CONFIG_KEY, "code");

        // 设置验证码的字符长度，即生成几位验证码
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");

        // 设置验证码使用的字体名称，多个字体用逗号分隔，随机选择
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");

        // 设置验证码字符的取值范围，从这些字符中随机选择生成验证码
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // 设置验证码干扰线的颜色，用于增加破解难度
        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, "white");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
