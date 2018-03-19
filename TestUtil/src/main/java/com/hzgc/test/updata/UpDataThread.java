package com.hzgc.test.updata;

import com.hzgc.test.util.FTPDownloadUtils;
import com.hzgc.test.util.UpDataToFtpProperHelper;

import java.io.File;
import java.net.URL;
import java.util.Random;

/**
 * 对于本地path路径下的所有文件，循环loopNum次，发送到Ftp服务器
 */
class UpDataThread implements Runnable {
    // 从配置文件读取发送图片的端口号
    private UpDataToFtpProperHelper upDataToFtpProperHelper = new UpDataToFtpProperHelper();
    private int port = upDataToFtpProperHelper.getPort();
    private String ip = upDataToFtpProperHelper.getIp();

    private File path;
    private int loopNum; // 循环次数
    private String ipcId; // ipcId
    private int count;

    UpDataThread(File path, int loopNum, String ipcId) {
        this.path = path;
        this.loopNum = loopNum;
        this.ipcId = ipcId;
    }

    @Override
    public void run() {

        File[] tempList = path.listFiles();
        System.out.println(tempList);
        for (int i = 0; i < loopNum; i++) {
            Random random = new Random();
            int randNum = random.nextInt(10000000);
            String randName = String.valueOf(randNum);
            for (int j = 0; j < (tempList != null ? tempList.length : 0); j++) {
                if (tempList[j].isFile()) {
                    String originFilePath = tempList[j].getAbsolutePath();
                    String fileName = randName + tempList[j].getName();
                    StringBuilder filePath = new StringBuilder();
                    // 拼接路径
                    filePath = filePath.append(ipcId).append("/")
                            .append(tempList[j].getName().substring(0, 14).replaceAll("_", "/"));

                    // basePath FTP服务器基础目录
                    // filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。
                    // 文件的路径为 basePath + filePath
                    FTPDownloadUtils.upLoadFromProduction(ip, port, "admin",
                            "123456", "", filePath.toString(), fileName, originFilePath);
                }
            }
            count++;
            System.out.println(Thread.currentThread().getName() + ", count: " + count);
        }
        System.out.println("Thread name is: " + Thread.currentThread().getName() + ", Picture count send to FTP is：" + count);
    }
}