package com.alex;

/**
 * 演示同步代码块中 使用 suspend 和resume 出现死锁现象
 * @author alex
 * @date 2022/3/4
 */
public class ConsumerThreadTest {

    public static Object baozidian = null;

    public static void main(String[] args) throws InterruptedException {
        /**
         * 模拟生产者和消费者线程  suspend和resume 出现死锁的问题
         * suspend 挂起线程，不会释放锁。容易出现死锁问题
         */
        Thread consumerThread = new Thread(()->{
            if(baozidian==null){
                System.out.println("1、没有包子，进入等待");
                synchronized (ConsumerThreadTest.class) {
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2、买到包子，回家");
        });

        consumerThread.start();

        //3秒后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (ConsumerThreadTest.class) {
            consumerThread.resume();
        }
        System.out.println("3、通知消费者");
    }


}
