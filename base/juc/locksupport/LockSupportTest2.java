package com.test.jucTest.locksupport;

import java.util.concurrent.locks.LockSupport;

/*
    LockSupport  park 之后的状态
 */
public class LockSupportTest2 {

    public static void test() throws InterruptedException {
        //park不限时
        Thread thread = new Thread(() -> LockSupport.park());
        //park限时
        Thread thread2 = new Thread(() -> LockSupport.parkNanos(3000000000l));
        thread.start();
        thread2.start();
        //主线睡眠一秒,让子线程充分运行
        Thread.sleep(1000);
        //获取处于park的子线程状态
        System.out.println(thread.getState());
        System.out.println(thread2.getState());

    }

    public static void main(String[] args) throws InterruptedException {

        test();

    }
}
