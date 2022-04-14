package com.cssnj.server.controller;

import com.baidu.ueditor.ActionEnter;
import com.cssnj.server.common.fastdfs.FastDFSClient;
import com.cssnj.server.common.response.RespUEditorData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 百度富文本编辑 控制器
 * @author panbing
 * @date 2022/4/8 22:13
 */
@RestController
@RequestMapping("/UEditor")
@CrossOrigin//允许跨域
public class UEditorController {

    private static final Logger logger = LoggerFactory.getLogger(UEditorController.class);
    @Autowired
    private FastDFSClient fastDFSClient;

    @RequestMapping("/config")
    public String config(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "action") String action,
                         @RequestParam(value = "upfile", required = false)MultipartFile file) throws Exception {
        if("config".equals(action)){//获取配置
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            return new ActionEnter(request, rootPath).exec();
        }else if("uploadimage".equals(action)){//上传图片
            String imageName = file.getOriginalFilename();
            long imageSize = file.getSize();
            String suffix = imageName.substring(imageName.lastIndexOf(".") + 1);
            logger.info("图片名称："+imageName+"，图片大小："+imageSize+"，图片类型："+suffix);
            String url = fastDFSClient.uploadFile(file);
            JSONObject jsonObject = new JSONObject(RespUEditorData.success(url,imageName,suffix,imageSize,imageName));
            return jsonObject.toString();
        }else if("uploadfile".equals(action)){//上传文件
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            logger.info("文件名称："+fileName+"，文件大小："+fileSize+"，文件类型："+suffix);
            String url = fastDFSClient.uploadFile(file);
            JSONObject jsonObject = new JSONObject(RespUEditorData.success(url,fileName,suffix,fileSize,fileName));
            return jsonObject.toString();
        }
        return "无效的Action";
    }

}
