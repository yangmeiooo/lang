### netty 

### 什么是netty？

netty 是一个 支持异步的事件驱动的网络程序框架，可用于的高性能可维护网络协议服务器和客户端



### netty 解决了什么问题？？





### Netty 使用的场景？？

连接数目多且连接比较短的架构，聊天服务器，弹幕服务器，服务器间通讯。



### 有什么优点，缺点。带来了什么问题？？

优点：

- api 简单，方便易用
- nio框架最好用的
- dubbo，es 底层都使用到了 netty。
- 内置多种解码编码器，支持多种协议。

缺点：





### 什么是零拷贝？，它怎么实现的？它的原理是什么？？

Java 程序中常见的零拷贝，有mmap（内存映射）和 sendFile。



传统的数据拷贝

![image-20210807160259151](/Users/lizhenning/Library/Application Support/typora-user-images/image-20210807160259151.png)

需要用户态切换到内核态，再内核态度切换到用户态，再切换到内核态。

- 硬盘拷贝到内核缓冲区，内核缓存区拷贝到用户buffer，用户态再修改放到 socket buffer，再拷贝到协议栈
- 所以就是 4次数据拷贝，4次切换状态。



### mmap 优化

通过 内存映射，将文件映射到内核缓冲区，**用户空间可以共享内核缓冲区的内容**。

这样在网络传输的过程中，可以减少从内核到用户态的拷贝。

![image-20210807160907103](/Users/lizhenning/Library/Application Support/typora-user-images/image-20210807160907103.png)



总结：还是4次状态切换，但是数据拷贝次数来到了3次。



### sendFile优化

linux 2.4版本，避免从内核缓存区拷贝到 socket buffer，直接拷贝到协议栈。

![image-20210807161224050](/Users/lizhenning/Library/Application Support/typora-user-images/image-20210807161224050.png)

总结： 上下文切换减少了一次，总共3次，数据拷贝2次。



### mmap 和 sendFile 使用场景

- mmap 用于低数据量拷贝，sendFile 适用于大文件传输。





### netty 原理架构图

![image-20210807173025639](/Users/lizhenning/Library/Application Support/typora-user-images/image-20210807173025639.png)





1. boosgroup 和 workergroup 和reactor 的主从多线程模型怎么对应起来。
2. pipline 每一个都是 handler ，那么入站事件，怎么传递，且都是一个个来处理的吗？处理过程是啥样的？？

![image-20210808162716976](/Users/lizhenning/Library/Application Support/typora-user-images/image-20210808162716976.png)