# JUC



## 33.聊聊 ThreadLocal 吧

- ThreadLocal其实就是**「线程本地变量」**，他会在每个线程都创建一个副本，那么在线程之间访问内部副本变量就行了，做到了线程之间互相隔离。![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9CNyynpMcQ1GXfhNArzkicJJ3Ruicz3YmoLuibicMVuja0Er1v34ibjQzVGQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
- ThreadLocal 有一个**「静态内部类 ThreadLocalMap」**，ThreadLocalMap 又包含了一个 Entry 数组，**「Entry 本身是一个弱引用」**，他的 key 是指向 ThreadLocal 的弱引用，**「弱引用的目的是为了防止内存泄露」**,如果是强引用那么除非线程结束,否则无法终止,可能会有内存泄漏的风险。
- 但是这样还是会存在内存泄露的问题，假如 key 和 ThreadLocal 对象被回收之后，entry 中就存在 key 为 null ，但是 value 有值的 entry 对象，但是永远没办法被访问到，同样除非线程结束运行。**「解决方法就是调用 remove 方法删除 entry 对象」**。

## 15.说说进程和线程的区别？

**「进程是系统资源分配和调度的基本单位」**，它能并发执行较高系统资源的利用率.

**「线程」**是**「比进程更小」**的能独立运行的基本单位,创建、销毁、切换成本要小于进程,可以减少程序并发执行时的时间和空间开销，使得操作系统具有更好的并发性。



## 16.volatile 有什么作用？

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9ofZqBibS1bgd6J2qTUibIBY4HNuXibMvh3v0GGVbZVHHzc1JG6EY03DXQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **「1.保证内存可见性」**

- - 可见性是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。也就是一个线程修改的结果，另一个线程马上就能看到。

- **「2.禁止指令重排序」**

- - cpu 是和缓存做交互的，但是由于 cpu 运行效率太高，所以会不等待当前命令返回结果从而继续执行下一个命令，就会有乱序执行的情况发生





## 19.JMM 是什么？



![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9HKMTYbRics9xmeSWEuIsKRvWwSmkORMFtKm5G9icQZux8lHojXZTL2JA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

JMM 就是 **「Java内存模型」**(java memory model)。因为在不同的硬件生产商和不同的操作系统下，内存的访问有一定的差异，所以会造成相同的代码运行在不同的系统上会出现各种问题。所以java内存模型(JMM)**「屏蔽掉各种硬件和操作系统的内存访问差异，以实现让java程序在各种平台下都能达到一致的并发效果」**。

Java内存模型规定所有的变量都存储在主内存中，包括实例变量，静态变量，但是不包括局部变量和方法参数。每个线程都有自己的工作内存，线程的工作内存保存了该线程用到的变量和主内存的副本拷贝，线程对变量的操作都在工作内存中进行。**「线程不能直接读写主内存中的变量」**。

每个线程的工作内存都是独立的，**「线程操作数据只能在工作内存中进行，然后刷回到主存」**。这是 Java 内存模型定义的线程基本工作方式。



## 20.创建对象有哪些方式

有**「五种创建对象的方式」**

- 1、new关键字

```
Person p1 = new Person();
```

- 2.Class.newInstance

```
Person p1 = Person.class.newInstance();
```

- 3.Constructor.newInstance

```
Constructor<Person> constructor = Person.class.getConstructor();
Person p1 = constructor.newInstance();
```

- 4.clone

```
Person p1 = new Person();
Person p2 = p1.clone();
```

- 5.反序列化

```
Person p1 = new Person();
byte[] bytes = SerializationUtils.serialize(p1);
Person p2 = (Person)SerializationUtils.deserialize(bytes);
```





## 21.讲讲单例模式懒汉式吧

直接贴代码

```
// 懒汉式
public class Singleton {
// 延迟加载保证多线程安全
    Private volatile static Singleton singleton;
    private Singleton(){}
    public static Singleton getInstance(){
        if(singleton == null){
            synchronized(Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

- 使用 volatile 是**「防止指令重排序，保证对象可见」**，防止读到半初始化状态的对象
- 第一层if(singleton == null) 是为了防止有多个线程同时创建
- synchronized 是加锁防止多个线程同时进入该方法创建对象
- 第二层if(singleton == null) 是防止有多个线程同时等待锁，一个执行完了后面一个又继续执行的情况

## 22.volatile 有什么作用

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9QicoP5HPENZEAGMrvHudzfRGzFaFOVKeUnR8jmzzkLQPR5ljXBWDzSw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.**「保证内存可见性」**

- - 当一个被volatile关键字修饰的变量被一个线程修改的时候，其他线程可以立刻得到修改之后的结果。当一个线程向被volatile关键字修饰的变量**「写入数据」**的时候，虚拟机会**「强制它被值刷新到主内存中」**。当一个线程**「读取」**被volatile关键字修饰的值的时候，虚拟机会**「强制要求它从主内存中读取」**。

- 2.**「禁止指令重排序」**

- - 指令重排序是编译器和处理器为了高效对程序进行优化的手段，cpu  是与内存交互的，而 cpu 的效率想比内存高很多，所以 cpu 会在不影响最终结果的情况下，不等待返回结果直接进行后续的指令操作，而 volatile 就是给相应代码加了**「内存屏障」**，在屏障内的代码禁止指令重排序。

## 23.怎么保证线程安全？

- 1.synchronized关键字

- - 可以用于代码块，方法（静态方法，同步锁是当前字节码对象；实例方法，同步锁是实例对象）

- 2.lock锁机制

```
Lock lock = new ReentrantLock();
lock. lock();
try {
    System. out. println("获得锁");
} catch (Exception e) {
   
} finally {
    System. out. println("释放锁");
    lock. unlock();
}
```



## 24.synchronized 锁升级的过程

在 Java1.6 之前的版本中，synchronized 属于重量级锁，效率低下，**「锁是」** cpu 一个**「总量级的资源」**，每次获取锁都要和 cpu 申请，非常消耗性能。

在 **「jdk1.6 之后」** Java 官方对从 JVM 层面对 synchronized 较大优化，所以现在的 synchronized 锁效率也优化得很不错了，Jdk1.6 之后，为了减少获得锁和释放锁所带来的性能消耗，引入了偏向锁和轻量级锁，**「增加了锁升级的过程」**，由无锁->偏向锁->自旋锁->重量级锁![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9EWJeVc4eM8kIgVbJ7vwsQ7QGQShIPeaY2d3wqSGEaXKicxzuRpQfW4Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

增加锁升级的过程主要是**「减少用户态到核心态的切换，提高锁的效率，从 jvm 层面优化锁」**





## 25.cas 是什么？

cas 叫做 CompareAndSwap，**「比较并交换」**，很多地方使用到了它，比如锁升级中自旋锁就有用到，主要是**「通过处理器的指令来保证操作的原子性」**，它主要包含三个变量：

- **「1.变量内存地址」**
- **「2.旧的预期值 A」**
- **「3.准备设置的新值 B」**

当一个线程需要修改一个共享变量的值，完成这个操作需要先取出共享变量的值，赋给 A，基于 A 进行计算，得到新值 B，在用预期原值 A 和内存中的共享变量值进行比较，**「如果相同就认为其他线程没有进行修改」**，而将新值写入内存

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9WVvquWODJ4KOeYlB1ibiaeYvSpR5anG3Xldqnaw888YbFJ6hlKNzP02w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**「CAS的缺点」**

- **「CPU开销比较大」**：在并发量比较高的情况下，如果许多线程反复尝试更新某一个变量，却又一直更新不成功，又因为自旋的时候会一直占用CPU，如果CAS一直更新不成功就会一直占用，造成CPU的浪费。
- **「ABA 问题」**：比如线程 A 去修改 1 这个值，修改成功了，但是中间 线程 B 也修改了这个值，但是修改后的结果还是 1，所以不影响 A 的操作，这就会有问题。可以用**「版本号」**来解决这个问题。
- **「只能保证一个共享变量的原子性」**



## 26.聊聊 ReentrantLock 吧

ReentrantLock 意为**「可重入锁」**，说起 ReentrantLock 就不得不说 AQS ，因为其底层就是**「使用 AQS 去实现」**的。

ReentrantLock有两种模式，一种是公平锁，一种是非公平锁。

- 公平模式下等待线程入队列后会严格按照队列顺序去执行
- 非公平模式下等待线程入队列后有可能会出现插队情况

**「公平锁」**

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9IhIR5CHwj6TlXwDMGNudxcCYkRDicYXHCM2nGz3nNkx1L1brl8b1Ieg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 第一步：**「获取状态的 state 的值」**

- - 如果 state=0 即代表锁没有被其它线程占用，执行第二步。
  - 如果 state!=0 则代表锁正在被其它线程占用，执行第三步。

- 第二步：**「判断队列中是否有线程在排队等待」**

- - 如果不存在则直接将锁的所有者设置成当前线程，且更新状态 state 。
  - 如果存在就入队。

- 第三步：**「判断锁的所有者是不是当前线程」**

- - 如果是则更新状态 state 的值。
  - 如果不是，线程进入队列排队等待。

**「非公平锁」**

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc97CxMlQ9UicpKPmpJ7Zj3nibGbPlicbHQFibbUKAfqSxbeEvPicC1BiceI3Tw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 获取状态的 state 的值

- - 如果 state=0 即代表锁没有被其它线程占用，则设置当前锁的持有者为当前线程，该操作用 CAS 完成。
  - 如果不为0或者设置失败，代表锁被占用进行下一步。

- 此时**「获取 state 的值」**

- - 如果是，则给state+1，获取锁
  - 如果不是，则进入队列等待
  - 如果是0，代表刚好线程释放了锁，此时将锁的持有者设为自己
  - 如果不是0，则查看线程持有者是不是自己

## 27.多线程的创建方式有哪些？

- 1、**「继承Thread类」**，重写run()方法

```
public class Demo extends Thread{
    //重写父类Thread的run()
    public void run() {
    }
    public static void main(String[] args) {
        Demo d1 = new Demo();
        Demo d2 = new Demo();
        d1.start();
        d2.start();
    }
}
```

- 2.**「实现Runnable接口」**，重写run()

```
public class Demo2 implements Runnable{

    //重写Runnable接口的run()
    public void run() {
    }
    
    public static void main(String[] args) {
        Thread t1 = new Thread(new Demo2());
        Thread t2 = new Thread(new Demo2());
        t1.start();
        t2.start();
    }

}
```

- 3.**「实现 Callable 接口」**

```
public class Demo implements Callable<String>{

    public String call() throws Exception {
        System.out.println("正在执行新建线程任务");
        Thread.sleep(2000);
        return "结果";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Demo d = new Demo();
        FutureTask<String> task = new FutureTask<>(d);
        Thread t = new Thread(task);
        t.start();
        //获取任务执行后返回的结果
        String result = task.get();
    }
    
}
```

- 4.**「使用线程池创建」**

```
public class Demo {
    public static void main(String[] args) {
        Executor threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0 ;i < 10 ; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    //todo
                }
            });
        }
        
    }
}
```

## 28.线程池有哪些参数？

- **「1.corePoolSize」**：**「核心线程数」**，线程池中始终存活的线程数。
- **「2.maximumPoolSize」**: **「最大线程数」**，线程池中允许的最大线程数。
- **「3.keepAliveTime」**: **「存活时间」**，线程没有任务执行时最多保持多久时间会终止。
- **「4.unit」**: **「单位」**，参数keepAliveTime的时间单位，7种可选。
- **「5.workQueue」**: 一个**「阻塞队列」**，用来存储等待执行的任务，均为线程安全，7种可选。
- **「6.threadFactory」**: **「线程工厂」**，主要用来创建线程，默及正常优先级、非守护线程。
- **「7.handler」**：**「拒绝策略」**，拒绝处理任务时的策略，4种可选，默认为AbortPolicy。



## 29.线程池的执行流程？

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9ohBWuW2WwlZlrPQPialgcibXgsJGDRwk3pSE3t8u6MMiaoVxFiasVL1RnA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 判断线程池中的线程数**「是否大于设置的核心线程数」**

- - 如果**「没有满」**，则**「放入队列」**，等待线程空闲时执行任务
  - 如果队列已经**「满了」**，则判断**「是否达到了线程池设置的最大线程数」**

- - 如果**「没有达到」**，就**「创建新线程」**来执行任务
  - 如果已经**「达到了」**最大线程数，则**「执行指定的拒绝策略」**

- - 如果**「小于」**，就**「创建」**一个核心线程来执行任务
  - 如果**「大于」**，就会**「判断缓冲队列是否满了」**

## 30.线程池的拒绝策略有哪些？

- **「AbortPolicy」**：直接丢弃任务，抛出异常，这是默认策略
- **「CallerRunsPolicy」**：只用调用者所在的线程来处理任务
- **「DiscardOldestPolicy」**：丢弃等待队列中最旧的任务，并执行当前任务
- **「DiscardPolicy」**：直接丢弃任务，也不抛出异常





## LockSupport.  

- park
- Unpack(Thread t1)

> 特点：

1. 与 wait 相比 不需要先去获取 锁。

2. park & unpack 是以线程为单位的 阻塞 和唤醒，而notify 只能随机唤醒一个线程，notifyAll 则是唤醒所有等待线程。

3. 先调用 unpark ,再调用 park 马上唤醒



## park 原理

![截屏2021-07-16 下午10.51.46](/Users/lizhenning/Desktop/截屏2021-07-16 下午10.51.46.png)



## AQS 原理

- 什么是 AQS，其实就是抽象队列同步器
- 拿来给 juc 包下的工具，提供维护 state 和 线程阻塞后进入队列同步和唤醒的一个的机制。

![image-20211121201817058](/Users/lizhenning/Library/Application Support/typora-user-images/image-20211121201817058.png)

| 枚举      | 含义                                           |
| :-------- | :--------------------------------------------- |
| 0         | 当一个Node被初始化的时候的默认值               |
| CANCELLED | 为1，表示线程获取锁的请求已经取消了            |
| CONDITION | 为-2，表示节点在等待队列中，节点线程等待唤醒   |
| PROPAGATE | 为-3，当前线程处在SHARED情况下，该字段才会使用 |
| SIGNAL    | 为-1，表示线程已经准备好了，就等资源释放了     |



```
node.prev = t;
if (compareAndSetTail(t, node)) {
    t.next = node;   // 为啥是 t.next 呢，t已经指向末尾了啊？
    return t;
}
```





1. 线程加入等待队列的时机

当执行Acquire(1)时，会通过tryAcquire获取锁。在这种情况下，如果获取锁失败，就会调用addWaiter加入到等待队列中去。

2. 如何加入等待队列

获取锁失败后，会执行addWaiter(Node.EXCLUSIVE)加入等待队列，具体实现方法如下：

3. shouldParkAfterFailedAcquire中取消节点是怎么生成的呢？什么时候会把一个节点的waitStatus设置为-1？

4. 是在什么时间释放节点通知到被挂起的线程呢？

   release 方法中，会找虚拟头节点后，拿它的next节点， LockSupport.unpark(s.thread); 唤醒。

5. 为啥unparkSuccessor 执行前是 ，从后往前找的呢？

​     因为，在添加节点入队的时候，新node 节点的， node.pre = pre; 后续pre.next = t; 不是原子操作。肯定会存在node.next = null的情况。





附上美团技术文章：  https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html

## 根据AQS 实现的自定义锁

`

```java
package lzn.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/*
    自定义 实现锁， 独占，不可重入锁
 */
public class MyLock implements Lock {

    private final MySync sync;

    class MySync extends AbstractQueuedSynchronizer {
        // 尝试一次 去获取锁，获取失败就会被入等待队列
        protected boolean tryAcquire(int arg) {

           if (compareAndSetState(0, 1)) {
               setExclusiveOwnerThread(Thread.currentThread());
               return true;
           }
            return false;
        }
        // 尝试一次释放锁
        protected boolean tryRelease(int arg) {

            if (compareAndSetState(1, 0)) {
                setExclusiveOwnerThread(null);
                return true;
            }
            return false;
        }
        // 当前线程是否  占领锁， 怎么重写
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }
    }

    public MyLock() {
        this.sync = new MySync();
    }


    // 尝试获取锁
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

    @Override
    public Condition newCondition() {
        return newCondition();
    }

    public static void main(String[] args) {

        MyLock lock = new MyLock();

        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "locked ..");
            try{
                System.out.println(Thread.currentThread().getName() + "locking");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "locked ..");
            try{
                System.out.println(Thread.currentThread().getName() + "locking");
            } finally {
                lock.unlock();
            }
        }, "t2").start();

    }
}
```

`

## 读写锁

### 读写锁， 测试demo

`

```
package lzn.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readlock = lock.readLock();
    private final Lock writelock = lock.writeLock();

    void read() {
        System.out.println("尝试获取读锁");
        readlock.lock();

        try{
            System.out.println(Thread.currentThread().getName() + "已经获取读锁");
            Thread.sleep(1000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readlock.unlock();
        }
    }

    void write() {
        System.out.println("尝试获取写锁");
        writelock.lock();

        try{
            System.out.println(Thread.currentThread().getName() + "已经获取写锁");
            Thread.sleep(1000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writelock.unlock();
        }
    }


    public static void main(String[] args) {

        ReadWriteLockTest test = new ReadWriteLockTest();

        new Thread(() -> {
           test.read();
        }, "t1").start();

        new Thread(() -> {
            test.write();
        }, "t2").start();

    }
}
```

`

> 注意事项

- 读锁不支持条件变量
- 重入时不支持升级： 即持有读锁的情况下，去获取写锁会导致， 获取写锁永久等待。
- 重入时 支持降级，**即拥有写锁的情况下去获取读锁**。



### 读写锁，原理

本质，state 变量，高16位 读锁，低16位 写锁。





###  stampedlock











## CountDownLatch

倒计时 锁

用来线程同步，等待所有线程完成倒计时

其中构造函数初始化等待倒计时，await 方法等待 倒计时为0， countdown 将计数 减 1



### 自定义demo

`	

```
package lzn.aqs;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch count = new CountDownLatch(3);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始减1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.countDown();
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始减1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.countDown();
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始减1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.countDown();
        }).start();

        System.out.println(Thread.currentThread().getName() + "主线程阻塞");
        count.await();
        System.out.println(Thread.currentThread().getName() + "主线程恢复阻塞");
    }

}
```

`

### 为啥要使用 CountDownLatch ??

而不是用join, join 是底层API，CountDownLatch 是高级Api, 使用更加方便。

为啥，比如配合线程池使用， 更加方便使用。

e g: 开启一个4个任务的线程池， 前3个任务分别是去做一些任务，第4个线程

用CountDownLatch await 方法等待暂停即可，十分方便。





## CyclicBarrier

为了解决， CounDownLatch 不能解决当await 之后计数为0时

重置， 于是出现 循环栅栏。



### 自定义demo

`

```java
package lzn.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        // 当线程池数目多于 栅栏 数目，可能导致出错
        ExecutorService executorService = Executors.newFixedThreadPool(2);  // 线程数目不要超过
                                                                                    // 栅栏数目

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("栅栏运行完成");
        });

        for(int i = 0;i < 3;i ++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程执行任务1");
                try {
                    cyclicBarrier.await(); //会让计数减1，2-1=1  不为0 时会阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程执行任务2");
                try {
                    cyclicBarrier.await(); //会让计数减1，1- 1=0  为0时，已经结束
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();  //必须添加
    }




}
```

`

## seraphore

信号量， 用来限制 同时访问 共享资源的线程上限





## HashMap

1.8以后

数组加链表, Node 数组加链表的形式



### put 操作

底层是putVal 操作， 其实就是找 table,不存在新建， 找到index 索引

找到后，不存在就新建，存在就更新，寻找链表过程中，不相等，同时会判断是否是 链表是否是

TreeNode 红黑树节点， 不是，就普通链表寻找，找到hash,key 相等就更新，否则就尾擦法。



### 为啥1.8 以后 使用尾擦法

之前是使用头擦法，在多线程操作下容易死锁。



### get 操作

get 方法本质是调用，getNode 方法。

寻找 table 桶下标 ， table[(n - 1) & hash]

从 table[i] 开始找，先判断是否是树节点，是树节点就往下找。

不是的话，就从链表开始找。

`

Node e;

do {

​	判断e 是否相等, return true;

} while(e.next != null)

`



### Resize 扩容方法  ***

```
loadFactor   负载因子
```

```
threshold  当前 HashMap 所能容纳键值对数量的最大值,超过这个,则需扩容
```

```
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
```

```
static final int MAXIMUM_CAPACITY = 1 << 30;
```

```
DEFAULT_LOAD_FACTOR = 0.75f
```

```
static final int TREEIFY_THRESHOLD = 8;
```

```
static final int UNTREEIFY_THRESHOLD = 6;
```

```
static final int MIN_TREEIFY_CAPACITY = 64;
```

## FutureTask 介绍

1. 为啥需要这个类

这个类帮助我们，在异步耗时任务，拿到执行结果。

2. 怎么使用的？

   ```java
   public static void main(String[] args) throws ExecutionException, InterruptedException {
   
           ExecutorService executorService = Executors.newSingleThreadExecutor();
           Future future = executorService.submit(new Runnable() {
               @Override
               public void run() {
                   System.out.println("test begin");
   
                   try{
                       Thread.sleep(3000);
                   }catch (Exception e) {
   
                   }
               }
           });
           System.out.println("result: " + future.get());
   
           executorService.shutdown();
       }
   ```

   

   

   3. 这个类设计的原理，内部是什么样的？

      原理其实是，FutureTask 内部有一个callable 变量是承接传入的task。

      随后再去执行， 为啥 任务是可以组赛获取。 其实 就是 LockSupport 组赛中，任务完成再唤醒。

      

   

