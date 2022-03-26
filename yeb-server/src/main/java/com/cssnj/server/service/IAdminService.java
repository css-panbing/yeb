package com.cssnj.server.service;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作员接口 IAdminService
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

    /**
     * 通过关键字查询操作员
     * @param keywords
     * @return
     */
    List<Admin> getAdminsByKeywords(String keywords);

    /**
     * 更新操作员角色信息
     * @param adminId
     * @param roleIds
     * @return
     */
    ResponseData updateAdminRoles(Integer adminId, String[] roleIds);
}
