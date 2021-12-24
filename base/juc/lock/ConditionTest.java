package com.test.jucTest.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    static int d = 1;

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10;i ++) {

                    try{
                        lock.lock();
                        //System.out.println("a get lock");
                        while(ConditionTest.d != 1) {
                            a.await();
                        }

                        System.out.print(Thread.currentThread().getName());
                        ConditionTest.d = 2;
                        b.signal();


                    }catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }
        };

        Runnable runnableb = new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10;i ++) {
                    try{

                        lock.lock();
                        //System.out.println("b get lock");

                        while(ConditionTest.d != 2) {
                            b.await();
                        }

                        System.out.print(Thread.currentThread().getName());
                        ConditionTest.d =3;
                        c.signal();



                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        };

        Runnable runnablec = new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10;i ++) {
                    try{

                        lock.lock();
                        //System.out.println("c get lock");

                        while(ConditionTest.d != 3) {
                            c.await();
                        }

                        System.out.print(Thread.currentThread().getName());
                        d = 1;
                        a.signal();


                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        };

        Thread threadA = new Thread(runnable, "A");
        Thread threadB = new Thread(runnableb, "B");
        Thread threadC = new Thread(runnablec, "C");

        threadA.start();
        threadB.start();
        threadC.start();


        threadC.join();
        System.out.println("finish");
    }
}
