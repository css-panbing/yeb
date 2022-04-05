package com.cssnj.server.controller;

import com.cssnj.server.pojo.Admin;
import com.cssnj.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 在线聊天
 *
 * @author panbing
 * @date 2022/4/5 12:01
 */
@RestController
@Api(tags = "在线聊天")
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取操作员（可通过用户名匹配）")
    @GetMapping("/admin")
    public List<Admin> getAdminsByKey(String keywords){
        return adminService.getAdminsByKeywords(keywords);
    }

}
