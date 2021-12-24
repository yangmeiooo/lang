package com.test.jucTest.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock  implements Lock {

      int max = 0;

     public void count(){
         for(int i = 0;i < 10;i ++) {
             max += i;
         }
     }


    class Sync extends AbstractQueuedSynchronizer {


        protected boolean tryAcquire(int arg) {
//           Thread thread = Thread.currentThread();
//           int c = getState();
//           if (c == 0 ){
//               boolean catchs = compareAndSetState(0, 1);
//               if (catchs) {
//                   setExclusiveOwnerThread(thread);
//                   return true;
//               }
//               return false;
//           } else {
//               return false;
//           }

            if(compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        protected boolean tryRelease(int arg) {
            Thread currentThread = Thread.currentThread();
            Thread thread = getExclusiveOwnerThread();
            if (currentThread != thread) {
                throw new RuntimeException("出现错误");
            } else {
//                boolean catchs = compareAndSetState(1, 0);
//                if (catchs) {
//                    setExclusiveOwnerThread(null);
//                    return true;
//                } else {
//                    return false;
//                }

                setState(0);
                setExclusiveOwnerThread(null);
                return true;
            }
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

    }

    private Sync sync ;

    public MyLock() {
        sync = new Sync();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    // 1
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public static void main(String[] args) throws InterruptedException {

        MyLock myLock = new MyLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    myLock.lock();
                    System.out.println(Thread.currentThread().getName());
                    myLock.count();
                    System.out.println(Thread.currentThread().getName() + ": " + myLock.max);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                   myLock.unlock();
                }
            }
        };

        for(int i = 0;i < 10; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(10000);
        System.out.println("result: " + myLock.max);
    }
}
