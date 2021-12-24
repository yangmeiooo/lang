package com.test.jucTest.locksupport;

import java.util.concurrent.locks.LockSupport;

/*
    LockSupport 中断
 */
public class LockSupportTest3 {

    public static void test5() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //最开始中断标志位位false
                System.out.println(Thread.currentThread().isInterrupted());
                long currentTimeMillis = System.currentTimeMillis();
                System.out.println("begin park");
                LockSupport.park();
                System.out.println("end park");
                System.out.println(System.currentTimeMillis() - currentTimeMillis);
                //调用interrupt方法之后,中断标志位为true
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });
        thread.start();
        //开放或者注释该行代码,观察end park时间
        Thread.sleep(2000);
        //使用interrupt,也可以中断因为park造成的阻塞,但是该中断不会抛出异常
        thread.interrupt();
    }

    /*

     */
    public static void test6() throws InterruptedException {
        //分别尝试注释这两行代码,运行程序,运行cmd,使用jps  命令,找到该进程对应的pid,然后使用jstack pid   命令,就可以看到线程信息.
        LockSupport.park();
        //LockSupport.park(new LockSupportTest());
    }


    public static void main(String[] args) throws InterruptedException {

       //test5();
        test6();
    }
}
