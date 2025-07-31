package com.kagy.satokendemoweb;

import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaTokenDemoWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        System.out.println("test");
        String token = UUID.randomUUID().toString();
        System.out.println(token);
    }

}
