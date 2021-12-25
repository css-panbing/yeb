package com.cssnj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.config.security.JwtTokenUtil;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.mapper.AdminMapper;
import com.cssnj.server.pojo.AdminLogin;
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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
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

    @Value("${jwt.tokenHeader}")
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
        if("".equals(adminLogin.getCode()) || !captcha.equalsIgnoreCase(adminLogin.getCode())){
            return ResponseData.error("验证码错误，请重新输入！");
        }
        //重写UserDetailsService.loadUserByUsername()方法实现登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
        if(userDetails == null || !passwordEncoder.matches(adminLogin.getPassword(), userDetails.getPassword())){
            return ResponseData.error("用户名或密码不正确！");
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
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
        return admin;
    }
}
