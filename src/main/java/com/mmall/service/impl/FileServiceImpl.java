package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 对文件重命名，保证上传时文件名的唯一性
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，上传文件的文件名{}，上传的路径{}，新文件名{}：", fileName, path, uploadFileName);

        // 递归创建上传文件在tomcat中的目标文件夹
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setReadable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try {
            // 上传文件到tomcat服务器中
            file.transferTo(targetFile);
            // 文件上传完成

            // 将targetFile上传到FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 文件上传ftp服务器完成

            // 删除tomcat服务器中的targetFile
            targetFile.delete();

        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
