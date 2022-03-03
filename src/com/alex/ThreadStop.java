package com.alex;

/**
 * 线程终止
 * @author alex
 * @date 2022/3/3
 */
public class ThreadStop{
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
        stopT.interrupt();
        while (stopT.isAlive()){
            //确保线程已经终止

        }
        //输出结果
        stopT.print();

    }
}
