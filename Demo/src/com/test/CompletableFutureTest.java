package com.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureTest {

    public static void main1(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("=============>异步线程开始...");
                System.out.println("=============>异步线程为：" + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("=============>异步线程结束...");
                return "supplierResult";
            }
        });
        // 阻塞获取结果
        System.out.println("异步结果是:" + completableFuture.get());
        System.out.println("main结束");
    }


    public static void  main2(String[] args) throws InterruptedException, ExecutionException {
        // 提交一个任务，返回CompletableFuture（注意，并不是把CompletableFuture提交到线程池，它没有实现Runnable）
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("=============>异步线程开始...");
                System.out.println("=============>异步线程为：" + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("=============>异步线程结束...");
                return "supplierResult";
            }
        });

        // 异步回调：上面的Supplier#get()返回结果后，异步线程会回调BiConsumer#accept()
        completableFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println("=============>异步任务结束回调...");
                System.out.println("=============>回调线程为：" + Thread.currentThread().getName());
                System.out.println("result: " + s);
            }
        });

        // CompletableFuture的异步线程是守护线程，一旦main结束就没了，为了看到打印结果，需要让main休眠一会儿
        System.out.println("main结束");
        TimeUnit.SECONDS.sleep(15);
    }



    public static void main(String[] args) throws InterruptedException {
            // 任务一：把第一个任务推进去，顺便开启异步线程
            CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    System.out.println("=============>异步线程开始...");
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("=============>completableFuture1任务结束...");
                    System.out.println("=============>执行completableFuture1的线程为：" + Thread.currentThread().getName());
                    return "supplierResult";
                }
            });
            System.out.println("completableFuture1:" + completableFuture1);

            // 任务二：把第二个任务推进去，等待异步回调
            CompletableFuture<String> completableFuture2 = completableFuture1.thenApply(new Function<String, String>() {
                @Override
                public String apply(String s) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("=============>completableFuture2任务结束 result=" + s);
                    System.out.println("=============>执行completableFuture2的线程为：" + Thread.currentThread().getName());
                    return s;
                }
            });
            System.out.println("completableFuture2:" + completableFuture2);

            // 任务三：把第三个任务推进去，等待异步回调
            CompletableFuture<String> completableFuture3 = completableFuture2.thenApply(new Function<String, String>() {
                @Override
                public String apply(String s) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("=============>completableFuture3任务结束 result=" + s);
                    System.out.println("=============>执行completableFuture3的线程为：" + Thread.currentThread().getName());
                    return s;
                }
            });
            System.out.println("completableFuture3:" + completableFuture3);

            System.out.println("主线程结束");
            TimeUnit.SECONDS.sleep(100);
        }

}
