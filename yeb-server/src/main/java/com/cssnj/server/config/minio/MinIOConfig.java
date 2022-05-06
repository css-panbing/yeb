package com.cssnj.server.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置类
 * @author panbing
 * @date 2022/5/6 10:12
 */
@Configuration
public class MinIOConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        //创建 MinioClient 客户端
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

}















