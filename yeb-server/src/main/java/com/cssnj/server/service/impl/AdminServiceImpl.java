package com.cssnj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.config.security.JwtTokenUtil;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.mapper.AdminMapper;
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
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public ResponseData login(String username, String password, HttpServletRequest request) {
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())){
            return ResponseData.error("用户名或者密码不正确");
        }
        if(!userDetails.isEnabled()){
            return ResponseData.error("该用户被禁用，请联系管理员");
        }
        //更新Security中登录用户信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseData.success("登录成功", tokenMap);
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
