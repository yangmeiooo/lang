package com.test.jucTest.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    /*
        简单测试  LockSupport park unpark 方法
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                System.out.println("begin park");
                LockSupport.park();
                System.out.println("end park");
                System.out.println(System.currentTimeMillis() - currentTimeMillis);
            }
        });
        thread.start();
        //开放或者注释该行代码,观察end park时间
        //Thread.sleep(2000);
        //使当子线程获取到许可证
        LockSupport.unpark(thread);

    }

}
