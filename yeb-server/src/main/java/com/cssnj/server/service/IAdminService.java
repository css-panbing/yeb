package com.cssnj.server.service;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
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

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRolesWithAdminId(Integer adminId);
}
