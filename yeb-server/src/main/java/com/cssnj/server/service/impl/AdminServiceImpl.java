package com.cssnj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.common.utils.AdminUtils;
import com.cssnj.server.config.security.component.JwtTokenUtil;
import com.cssnj.server.mapper.AdminRoleMapper;
import com.cssnj.server.mapper.RoleMapper;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.mapper.AdminMapper;
import com.cssnj.server.pojo.AdminLogin;
import com.cssnj.server.pojo.AdminRole;
import com.cssnj.server.pojo.Role;
import com.cssnj.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作员实现类 AdminServiceImpl
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回Token
     * @param adminLogin
     * @param request
     * @return
     */
    @Override
    public ResponseData login(AdminLogin adminLogin, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        //从session中获取验证码与前端传递过来的验证码进行匹配
        if("".equals(adminLogin.getCode()) || !captcha.equalsIgnoreCase(adminLogin.getCode())){
            return ResponseData.error("验证码错误，请重新输入！");
        }
        //重写UserDetailsService.loadUserByUsername()方法实现登录（通过前端传递的用户名获取用户信息）
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
        if(userDetails == null){
            return ResponseData.error("用户名不存在！");
        }
        if(!passwordEncoder.matches(adminLogin.getPassword(), userDetails.getPassword())){
            return ResponseData.error("密码错误！");
        }
        if(!userDetails.isEnabled()){
            return ResponseData.error("账号被禁用，请联系管理员！");
        }
        //登录成功，更新Security登录用户信息（把登录用户信息放在Security全文中）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);//头部信息，供前端放在请求头中
        return ResponseData.success("登录成功！", tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        //Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
        return admin;
    }

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRolesWithAdminId(Integer adminId) {
        List<Role> roles = roleMapper.getRolesWithAdminId(adminId);
        return roles;
    }

    /**
     * 通过关键字查询操作员信息
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAdminsByKeywords(String keywords) {
        Admin currentAdmin = AdminUtils.getCurrentAdmin();
        return adminMapper.getAdminsByKeywords(currentAdmin.getId(), keywords);
    }

    /**
     * 更新操作员角色信息
     * @param adminId
     * @param roleIds
     * @return
     */
    @Override
    @Transactional
    public ResponseData updateAdminRoles(Integer adminId, String[] roleIds) {
        //1、先删除该操作员关联的角色
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        //2、重新查询角色数据
        if(roleIds == null || roleIds.length == 0){
            return ResponseData.success("更新成功");
        }
        Integer result = adminRoleMapper.insertAdminRoles(adminId, roleIds);
        if(result == roleIds.length){
            return ResponseData.success("更新成功");
        }
        return ResponseData.error("更新失败");
    }

}
