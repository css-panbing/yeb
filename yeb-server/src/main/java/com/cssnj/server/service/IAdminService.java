package com.cssnj.server.service;

import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.pojo.Role;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

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
    RespData login(AdminLogin adminLogin, HttpServletRequest request);

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
    RespData updateAdminRoles(Integer adminId, String[] roleIds);

    /**
     * 更新用户密码
     * @param oldPassword
     * @param newPassword
     * @param adminId
     * @return
     */
    RespData updateAdminPassword(String oldPassword, String newPassword, Integer adminId);

    /**
     * 更新用户头像
     * @param file
     * @param adminId
     * @param authentication
     * @return
     */
    RespData updateAdminUserFace(MultipartFile file, Integer adminId, Authentication authentication);
}
