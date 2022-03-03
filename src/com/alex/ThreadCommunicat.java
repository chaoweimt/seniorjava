package com.alex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;

/**
 * 线程通信  suspend/resume、wait/notify、park/unpark
 * @author alex
 * @date 2022/3/3
 */
public class ThreadCommunicat {
    public static String content="init value";
    public static void main(String[] args) {


        /**
         * 文件共享
         */
        // 线程1 写数据
        new Thread(()->{
            try {
                while (true){
                    Files.write(Paths.get("file.log"),String.valueOf("当前时间:"+System.currentTimeMillis()).getBytes());
                    Thread.sleep(1000L);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();

        // 线程2 读数据
        new Thread(()->{
            try {
                while (true){
                    Thread.sleep(1000L);
                    byte[] allBytes = Files.readAllBytes(Paths.get("file.log"));
                    System.out.println("allBytes => "+new String(allBytes));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();


        /**
         * 变量共享
         */

        // 线程1 写数据
        new Thread(()->{
            try {
                while (true){
                    Thread.sleep(1000L);
                    content = String.valueOf("当前时间:"+System.currentTimeMillis());

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();

        // 线程2 读数据
        new Thread(()->{
            try {
                while (true){
                    System.out.println("content => "+content);
                    Thread.sleep(1000L);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

}
