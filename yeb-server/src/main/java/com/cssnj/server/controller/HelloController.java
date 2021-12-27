package com.cssnj.server.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author panbing
 * @date 2021/12/16 21:55
 */
@Api(tags = "HelloController")
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/employee/basic/hello")
    public String basicHello(){
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String advancedHello(){
        return "/employee/advanced/hello";
    }

}
