package com.test.jucTest.atomic;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Tests {

    private AtomicInteger atomicI = new AtomicInteger(0);
    //使用到计数器实现线程等待,设置初始计数器值为100
    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {

        final Tests cas = new Tests();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int j = 0; j < 100; j++) {
            threadPoolExecutor.submit(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.safeCount();
                    //cas.unsafeCount();
                }
                //上面的任务执行完毕,计数器值减去一
                countDownLatch.countDown();
            });
        }
        threadPoolExecutor.shutdown();
        //main线程将会等待所有任务都执行完，即计数器值变为0时，才继续执行
        countDownLatch.await();
        System.out.println(cas.atomicI.get());
    }

    /**
     * 不安全的更新方式,unsafeCount方法中的代码不是原子性的,有可能造成多个线程重复写同一个变量
     */
    private void unsafeCount() {
        int i = atomicI.get();
        atomicI.set(++i);
    }

    /**
     * 使用CAS实现安全的更新方法
     */
    private void safeCount() {
        //for循环尝试使得共享变量atomicI的值增加1
        for (; ; ) {
            int i = atomicI.get();
            //compareAndSet方法是Java帮我们实现的一个CAS方法，CAS成功之后会返回true，CAS失败则返回false
            //compareAndSet方法的参数含义是：预估原值为i，让后将原值尝试CAS更新为++i；其他所需的参数则是在compareAndSet方法内部帮我们自动获取了。
            //如果是true,说明变量atomicI的值增加成功，跳出循环，如果返回false，说明变量atomicI的值增加失败，重新循环直到成功为止
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * compareAndSet方法内部,变量和字段内存偏移量帮我们获取了
     * @param expect
     * @param update
     * @return
     */
//    public final boolean compareAndSet(int expect, int update) {
//        //this:变量  valueOffset:value值的偏移量,通过此可以定位该字段在JVM内存中的位置  expect:预估原值   update:更新为指定值
//        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//    }

}
