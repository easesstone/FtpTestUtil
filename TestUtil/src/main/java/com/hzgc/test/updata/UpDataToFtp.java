package com.hzgc.test.updata;

import com.hzgc.test.util.UpDataToFtpProperHelper;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UpDataToFtp {
    public static void main(String[] args) {
        UpDataToFtpProperHelper upDataToFtpProperHelper = new UpDataToFtpProperHelper();
        int threadNum = upDataToFtpProperHelper.getThreadNum();

        int loopNum = upDataToFtpProperHelper.getLoopNum(); //发送图片循环次数
        String ipcId = "DS-2DE72XYZIW-ABCVS20160823CCCH641752612"; //ipcId
        String picFromPath = ClassLoader.getSystemResource("picFrom").getPath();
        File file = new File(picFromPath);

        long startTime = System.currentTimeMillis();
        // 开启threadNum个线程池来向ftp发送图片
        ThreadPoolExecutor pool = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < threadNum; i++) {
            pool.execute(new UpDataThread(file, loopNum, ipcId));
        }
        pool.shutdown();

        // 判断线程池中的所有线程都执行完成
        boolean allThreadIsDone = pool.getTaskCount() == pool.getCompletedTaskCount();
        while (!allThreadIsDone){
            allThreadIsDone = pool.getTaskCount() == pool.getCompletedTaskCount();
            if (allThreadIsDone){
                long endTime = System.currentTimeMillis();
                System.out.println("The send start time is: " + startTime);
                System.out.println("The send end time is: " + endTime);
                System.out.println("********************************");
                System.out.println("The Throughput is: " + (endTime - startTime));
                System.out.println("********************************");
            }
        }

        // 总共发送到ftp的图片数量
        System.out.println("The total pic group count send to FTP is: " + threadNum * loopNum);
        System.out.println("End");
    }
}
