package com.cssnj.server.common.fastdfs;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * FastDFS文件上传工具类
 * @author panbing
 * @date 2022/4/10 10:39
 */
@Component
public class FastDFSClient {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private FdfsWebServer fdfsWebServer;//读取.yml配置文件

    /**
     * 上传文件
     * @param multipartFile 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile multipartFile){
        StorePath storePath = null;
        try {
            storePath = storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            return getResAccessUrl(storePath);
        } catch (IOException e) {
            logger.error("上传文件异常：{}", e);
        }
        return null;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(File file){
        FileInputStream inputStream = null;
        StorePath storePath = null;
        try {
            inputStream = new FileInputStream(file);
            storePath = storageClient.uploadFile(inputStream, file.length(),
                    FilenameUtils.getExtension(file.getName()), null);
            return getResAccessUrl(storePath);
        } catch (IOException e) {
            logger.error("上传文件异常：{}", e);
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     */
    public boolean deleteFile(String fileUrl){
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return true;
        }catch (Exception e){
            logger.error("删除服务器文件异常：{}", e);
            return false;
        }
    }

    /**
     * 下载文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public byte[] downloadFile(String fileUrl){
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray byteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, byteArray);
        return bytes;
    }

    /**
     * 获取文件完整URL地址
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath){
        String fileUrl = fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
        return fileUrl;
    }


}
