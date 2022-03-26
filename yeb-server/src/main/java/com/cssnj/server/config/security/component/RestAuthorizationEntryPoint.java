package com.cssnj.server.config.security.component;

import com.cssnj.server.common.response.RespData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效的时候访问接口时，自定义的返回结果
 *
 * @author panbing
 * @date 2021/12/16 21:19
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespData data = RespData.error("尚未登录，请登录！");
        data.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();//输出流
        out.close();//关闭流
    }

}
