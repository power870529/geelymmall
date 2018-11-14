package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class FTPUtil {

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    public FTPUtil(String ip, int port, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img", fileList);
        log.info("开始连接ftp服务器，结束上传，上传结果：{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        // 连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pass)) {
            try {
                // 更改上传目录
                // 先要判断是否有remotePath这个文件夹，如果没有先创建
                // changeWorkingDirectory(remotePath)相当于cd命令，进入remotePath文件夹下
                boolean isChanged = ftpClient.changeWorkingDirectory(remotePath);

                if (!isChanged) {
                    ftpClient.makeDirectory(remotePath);
                    ftpClient.changeWorkingDirectory(remotePath);
                }
                // 设置缓冲器
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 将文件设置成二进制形式，可以防止乱码问题
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 打开本地被动模式（ftp服务器安装时设置的模式）
                ftpClient.enterLocalPassiveMode();
                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    uploaded = ftpClient.storeFile(file.getName(), fis);
                }
            } catch (IOException e) {
                uploaded = false;
                log.error("上传文件异常", e);
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String username, String password) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();

        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(username, password);
        } catch (IOException e) {
            log.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }

    private String ip;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
