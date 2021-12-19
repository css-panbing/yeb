package com.cssnj.server.controller;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author panbing
 * @date 2021/12/16 17:25
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("登录获取Token")
    @PostMapping("/login")
    public ResponseData login(@RequestBody AdminLogin adminLogin, HttpServletRequest request){
        ResponseData responseData = adminService.login(adminLogin.getUsername(), adminLogin.getPassword(), request);
        return responseData;
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(principal == null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseData logout(){
        return ResponseData.success("退出成功");
    }

}
