螺旋下降曲线

螺旋上升曲线

1.异步非阻塞，基于事件驱动，高性能，高可靠性，高可定制性

怎么做到？

基于 jdk 的nio.  屏蔽底层的api 复杂性。

nio 三大概念：

- buffer

- channel

- selector

  

-----------------------

bio 带来的弊端：
1. server 单线程模式， 会阻塞2次，一个监听连接时，阻塞一次，另外一次是，监听接受数据是否准备好了，发送端不发，服务端会一直阻塞。
2. 为了解决1 ，服务端，可以用监听到一个连接后，new Thread 模式，去处理一个socket 一个线程处理的模式，这样就变成一个阻塞。
但是，适用于连接数少，连接数一多，对服务器很大压力。

nio 异步非阻塞：
能解决bio 2个阻塞的问题。
怎么解决的？
1.服务端监听socket 请求，任旧是一个线程。一个线程是怎么做到bio 都做不好的事呢？？
2.服务端 每一个socket 收不到数据的时候，怎么不阻塞当前线程呢？？

在单线程服务器阻塞接受数据时，让服务器端不阻塞就好了。通过nio 设置非阻塞模式。
但是容易丢失客户端socket 连接。所以List缓存就好了。但是每次去轮询效率极低。

真实的Nio  解决方案怎么做？
放到操作系统层面去 感知数据得到来。
epoll
io 复用模型

网络线程模型
reactor 模型= reactor + handlers 

reactor ：  类似 电话接线员， 接收电话再分配给不同得实际处理员
handlers： 实际得处理程序

根据 Reactor 的数量和处理资源池线程的数量不同，有 3 种典型的实现：
1）单 Reactor 单线程； redis.     
存在问题，单线程，无法发挥多核CPU 性能
2）单 Reactor 多线程;  单reactor 容易成为性能瓶颈。
3）主从 Reactor 多线程。 





2.什么是Selectors? 本质是一直监视，那个通道可读，可写的 监视选择器。

它是给程序猿，来注册各种感兴趣的io 事件的地方，当哪些事件发生时，它告诉你（会主动通知你）。



Java NIO的选择器允许单个线程监视多个输入通道。你可以使用选择器注册多个通道，然后使用单个线程“选择”具有可用于处理的输入的通道，或者选择准备写入的通道。这种选择器机制使单个线程可以轻松管理多个通道
3.什么是Channel 通道？？？



4.buffer ，缓冲区, 有很不多不同种类的buffer


其实就是 数组，只不过拥有记录数据读写的进度，和重置读写标志的功能。

buffer你还需要检查缓冲区是否包含完整处理所需的所有数据?
并且，你需要确保在将更多数据读入缓冲区时，不要覆盖尚未处理的缓冲区中的数据?
=======
4.1你还需要检查缓冲区是否包含完整处理所需的所有数据?
4.2并且，你需要确保在将更多数据读入缓冲区时，不要覆盖尚未处理的缓冲区中的数据?



Netty 的核心类库：

1. **ByteBuf 和相关辅助类**

2. **Channel 和 Unsafe**

3. **ChannelPipeline 和 ChannelHandler**

4. **EventLoop**

5. **Future 和 Promise**

   

   





0.NioEventLoop  是干啥得
NioEventLoop 中维护了一个线程和任务队列，支持异步提交执行任务，线程启动时会调用 NioEventLoop 的 run 方法，执行 I/O 任务和非 I/O 任务：

1.EventLoopGroup  是干啥的？
其实就是线程池，维护 多个线程（eventloop），每个loop处理多个channel上的事件，
而一个channel只对应一个线程。

2.ServerBootstrap  是干啥的？
服务端启动引导类，串联起各个组件


3.1 ChannelHandler 是干啥的？ChannelHandlerContext？
channelhandler 是接口用来处理IO，使用子类，ChannelInboundHandler 用于处理入站 I/O 事件。
ChannelOutboundHandler 用于处理出站 I/O 操作。


3.2 pipeline 是干啥的？ChannelPipline 又是啥？

4. serverHandler  解耦  线程池和  处理逻辑。

5. ServerBootstrap .bind().sync();  
ChannelFuture.channel().closeFuture().sync()
为了啥??
netty 所有操作都是异步得，需要这个进行回调通知


6. Selector
Netty 对nio selector  实现了一个IO 多路复用机制，通过Selector 一个线程可以监听多个连接得channel

https://www.bilibili.com/video/BV1NZ4y1T7Y1/?spm_id_from=333.788.recommend_more_video.-1

https://zhuanlan.zhihu.com/p/159346800
