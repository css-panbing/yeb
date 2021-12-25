package com.cssnj.server.service;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssnj.server.pojo.AdminLogin;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录并返回Token
     * @param adminLogin
     * @param request
     * @return
     */
    ResponseData login(AdminLogin adminLogin, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);
}
