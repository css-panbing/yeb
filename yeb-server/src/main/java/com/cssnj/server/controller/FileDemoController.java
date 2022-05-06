package com.cssnj.server.controller;

import cn.hutool.core.lang.UUID;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传测试
 * @author panbing
 * @date 2022/5/6 10:25
 */
@RestController
@Api(tags = "Minio文件上传")
@RequestMapping("/file")
public class FileDemoController {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.url}")
    private String url;
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws Exception {
        //上传
        String path = UUID.randomUUID().toString();//文件名称，使用UUID随机
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)//存储桶
                .object(path)//文件名称
                .stream(file.getInputStream(), file.getSize(), -1)//文件内容
                .contentType(file.getContentType())//文件类型
                .build());
        return String.format("%s/%s/%s", url, bucketName, path);
    }

    /**
     * 删除文件
     * @param path
     * @throws Exception
     */
    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public void delete(@RequestParam("path") String path) throws Exception{
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)//存储桶
                .object(path)//文件名称
                .build());
    }

}
