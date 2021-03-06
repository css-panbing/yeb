package com.cssnj.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码Controller
 * @author panbing
 * @date 2021/12/25 16:36
 */
@RestController
@Api(tags = "验证码")
public class CaptchaController {

    static final Logger logger = Logger.getLogger(CaptchaController.class);

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "生成验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, mustrevalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");

        //-------------------生成验证码 begin --------------------------
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        logger.info("获取的验证码文本内容："+text);
        // 将验证码文本内容放入session
        request.getSession().setAttribute("captcha", text);
        // 根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("输出验证码图片流异常："+e);
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流异常："+e);
                }
            }
        }
        //-------------------生成验证码 end ----------------------------

    }

}
