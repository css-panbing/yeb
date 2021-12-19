package com.cssnj.server.service;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 登录之后返回Token
     * @param username
     * @param password
     * @param request
     * @return
     */
    ResponseData login(String username, String password, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);
}
