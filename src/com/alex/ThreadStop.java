package com.alex;

/**
 * 线程终止
 * @author alex
 * @date 2022/3/3
 */
public class ThreadStop{
    public  volatile static boolean flag = true;
    static class ThreadStopT extends Thread{

        int i = 0,j = 0;
        @Override
        public void run(){
            synchronized (this){
                //添加同步锁确保线程安全
                ++i;

                try{
                    //休眠10秒，模拟耗时操作
                    Thread.sleep(10000L);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                ++j;
            }
        }

        public void print(){
            System.out.println("i="+i+",j="+j);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ThreadStopT stopT = new ThreadStopT();
        stopT.start();
        //休眠1秒，确保i变量自增成功
        Thread.sleep(1000L);
        // 不正确的终止线程方式
//        stopT.stop();

        // 正确终止线程的方式,会抛出 java.lang.InterruptedException 异常
        /**
         * 如果线程正在调用 Object class 的 wait()、wait(long)或者wait(long,int)方法、join()、join(long，int)或者sleep(long,int)
         * 方法时被阻塞，那么interrupt会生效，该线程的中断状态会被清除，抛出InterruptedException异常。
         *
          */

        stopT.interrupt();

        while (stopT.isAlive()){
            //确保线程已经终止

        }
        //输出结果
        stopT.print();


        /**
         * 线程终止的另一种方式 标记位
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (flag){ // 判断是否运行
                        System.out.println("运行中");

                            Thread.sleep(1000L);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 3秒后，将状态标志改为False，代表不继续运行
        Thread.sleep(3000L);
        flag = false;
        System.out.println("程序运行结束");
        //或者cpu核心
    }
}
