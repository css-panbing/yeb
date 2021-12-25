package com.cssnj.server.controller;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 *
 * @author panbing
 * @date 2021/12/16 17:25
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录并返回Token")
    @PostMapping("/login")
    public ResponseData login(@RequestBody AdminLogin adminLogin, HttpServletRequest request){
        ResponseData responseData = adminService.login(adminLogin, request);
        return responseData;
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(principal == null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        return admin;
    }

    /**
     * 这里后端不做处理，前端调用/logout后端返回调用成功的状态码，前端在请求头中把token删除即实现退出登录
     * @return
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseData logout(){
        return ResponseData.success("注销成功！");
    }

}
