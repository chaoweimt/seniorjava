package com.alex;


/**
 * 线程状态 6中状态演示
 * @author alex
 * @date 2022/3/2
 */
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        //第一种线程状态  新建-> 运行 -> 终止
            System.out.println("#### 第1种线程状态 ：新建-> 运行 -> 终止 #########");
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread1 当前状态："+ Thread.currentThread().getState().toString());
                    System.out.println("thread1 执行了");
                }
            });

            System.out.println("没调用start方法，thread1的当前状态："+thread1.getState().toString());
            thread1.start();
            Thread.sleep(2000L);  // 等待thread1执行结束，在看状态
            System.out.println("等待2秒，thread1的当前状态："+thread1.getState().toString());
//            thread1.start();
            // thread1.start(); 注意，线程终止后，在进行调用，会抛出 java.lang.IllegalThreadStateException 异常；

        // 第二种线程状态：新建 -> 运行  -> 等待  -> 终止
        System.out.println("#### 第2种线程状态 ：新建 -> 运行  -> 等待  -> 终止 #########");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 将线程2移到等待状态 ，1500后自动唤醒
                try {
                    Thread.sleep(1500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 当前状态："+ Thread.currentThread().getState().toString());
                System.out.println("thread2 执行了");
            }
        });
        System.out.println("没调用start方法，thread2的当前状态："+thread2.getState().toString());
        thread2.start();
        System.out.println("调用start方法，thread2的当前状态："+thread2.getState().toString());
        Thread.sleep(200L);  // 等待200好眠，在看状态
        System.out.println("等待200毫秒，再看thread2的当前状态："+thread2.getState().toString());
        Thread.sleep(3000L);  // 等待3秒，让thread2执行完毕，再看状态
        System.out.println("等待3秒，再看thread2的当前状态："+thread2.getState().toString());


        // 第三种线程状态：  新建 -> 运行 -> 阻塞 -> 运行 -> 终止
        System.out.println("#### 第3种线程状态 ：新建 -> 运行 -> 阻塞 -> 运行 -> 终止 #########");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(ThreadState.class){
                    System.out.println("thread3 当前状态："+ Thread.currentThread().getState().toString());
                    System.out.println("thread3 执行了");
                }
            }
        });
        synchronized (ThreadState.class){
            System.out.println("没调用start方法，thread3的当前状态："+thread3.getState().toString());
            thread3.start();
            System.out.println("调用start方法，thread3的当前状态："+thread3.getState().toString());
            Thread.sleep(200L);  // 等待200好眠，在看状态
            System.out.println("等待200毫秒，再看thread3的当前状态："+thread3.getState().toString());
        }

        Thread.sleep(3000L);  // 等待3秒，让thread3执行完毕，再看状态
        System.out.println("等待3秒，再看thread3的当前状态："+thread3.getState().toString());

        // 第四种线程状态：新建 -> 运行 ->
    }}
