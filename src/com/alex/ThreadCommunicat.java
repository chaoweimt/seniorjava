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
    public static Object baozidian = null;
    public static void main(String[] args) throws InterruptedException {


        /**
         * 文件共享
         */
        // 线程1 写数据
        new Thread(() -> {
            try {
                while (true) {
                    Files.write(Paths.get("file.log"), String.valueOf("当前时间:" + System.currentTimeMillis()).getBytes());
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        // 线程2 读数据
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(2000L);
                    byte[] allBytes = Files.readAllBytes(Paths.get("file.log"));
                    System.out.println("allBytes => " + new String(allBytes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        /**
         * 变量共享
         */

        // 线程1 写数据
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000L);
                    content = String.valueOf("当前时间:" + System.currentTimeMillis());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        // 线程2 读数据
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    System.out.println("content => " + content);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();



        /**
         * 模拟生产者和消费者线程  suspend和resume
         */
        Thread consumerThread = new Thread(()->{
            if(baozidian==null){
                System.out.println("1、没有包子，进入等待");
                    Thread.currentThread().suspend();
            }
            System.out.println("2、买到包子，回家");
        });

        consumerThread.start();

        //3秒后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
            consumerThread.resume();
        System.out.println("3、通知消费者");


        /**
         * 模拟生产者和消费者线程  suspend和resume 出现死锁的问题
         * suspend 挂起线程，不会释放锁。容易出现死锁问题
         */
        Thread consumerThreadS = new Thread(()->{
            if(baozidian==null){
                System.out.println("1、没有包子，进入等待");
                synchronized (ThreadCommunicat.class) {
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2、买到包子，回家");
        });

        consumerThreadS.start();

        //3秒后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (ThreadCommunicat.class) {
            consumerThreadS.resume();
        }
        System.out.println("3、通知消费者");

    }
}
