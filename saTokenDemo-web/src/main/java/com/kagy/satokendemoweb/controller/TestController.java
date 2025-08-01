package com.kagy.satokendemoweb.controller;

import com.kagy.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/t1")
    public Result t1() {
        return Result.error("t1 error");
    }

    @PostMapping("/t2")
    public Result t2() {
        return Result.success("t2 success");
    }
}
