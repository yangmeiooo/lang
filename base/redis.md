# redis缓存

## 缓存一般是如何使用的？？ 使用不当会存在哪些问题？？

- 高性能
- 高并发

使用不当，会导致以下问题：

- 缓存穿透
- 缓存雪崩
- 缓存与数据库双写不一致

怎么解决呢， 看底下分解。



**缓存使用 pattern**

读：服务先读缓存，如果缓存存在直接返回，如果缓存不存在，去数据库查询，再放到缓存中，再返回。

写：一般使用 先更新数据库，再删除缓存。  但是这种顺序容易在。 写后立即读 发生脏读问题，所以要使用双删策略。



### 什么是缓存穿透？怎么防止缓存穿透？？

黑客，故意请求很多个不存在缓存的数据，那么数据全部会到达数据库。

可能打挂数据库。

怎么办？？

布隆过滤器， **原理**：。



### 什么是缓存雪崩？ 怎么防止缓存雪崩？？

是指redis 很多热点数据，同一段时间内过期，大量请求打到数据库上，

导致数据库挂了。  重启动机器，redis 又被打挂了。

怎么办？？

事前： redis 主从+ 哨兵， 防止redis 全盘崩溃。

事中： 核心程序，进行  限流。 减少请求进入程序后到达数据库。

事后：redis 持久化， 重起快速恢复。



### 什么是缓存击穿？？

缓存击穿，是指 高并发请求的时候，大量热点数据请求缓存，缓存失效的时候，很多请求打数据库上，

相当于开了一个口子。

怎么办？

- 缓存数据不更新，直接设置，用不过期。
- 缓存数据更新不频繁，设置少量线程能到达数据库，其余线程在释放后，再访问新缓存。
- 缓存数据更新频繁或者更新时间过长， 定时任务提前构建缓存，或者延长缓存过期时间。



### <u>什么是缓存与数据库双写不一致？怎么防止？？？</u>

缓存和数据库 双写，就一定会遇到  数据一致性问题，怎么解决缓存和数据库双写一致性问题呢？？

- 先更新mysql
- 再删除缓存

或者双删策略，先删除缓存，再更新mysql，再删除缓存。

这个也只能应对并发不高的场景。

**2021/12/12 高可用培训更新。**

缓存和数据库的双写不一致问题，是处于 mysql 主从同步的过程中

使用缓存。缓存会存在数据不一致问题。

怎么处理是使用双删策略来解决。



#### 为啥要先更新数据库呢？？不能后更新数据库吗？？

是因为，如果先操作缓存。

可能存在一种情况，线程A，删除缓存，这个线程B 查询，查不到，去db 将老数据存到缓存。

这个时候，线程A更新db。这样就导致缓存与数据库数据不一致。

#### 为啥要删除缓存？？ 而不是更新缓存呢？？

是因为，每次操作都2个步骤，操作数据库，操作缓存。而不保证

先后顺序。

所以会出现问题，比如 线程A 更新数据库为1。线程B更新数据库为2.然后缓存更新为2.

线程A最后更新缓存，导致缓存与数据库不一致。



### Redis 并发竞争问题是什么？ 怎么解决这个问题？ 了解Redis 事务的CAS 方案吗？？

这个问题指的是，多个客户端，同时去操作同一个key。

怎么解决这个问题？

zookeeper 实现分布式锁，谁拿到锁就能去操作mysql。

操作mysql 写到缓存的时候，通过mysql 时间戳来判断数据的新旧。

如果当前数据是旧的，就不能更新。



## Redis 基本知识

### 什么是redis?

Redis,是一个内存 key-value 数据库

由于读写数据都是单线程模式在内存，所以一般常用来做缓存，能够扛住10万并发

### redis 有什么优点，缺点？？



### <u>redis 基本数据类型</u>？？

- Strings
- Hash
- List
- Set
- SortedSet

还有 Bitmaps，Hyloglog，Streams。

基本用法：

1. String ，   set key value

2. Hash， 类似Map，设置一个对象进去。   hset Person name。

3. List， 有序列表。  

   lrange mylist 0 -1  实现高性能分页。

   lpush mylist 1

   rpop mylist

4. Set 无序集合，自动去重

   sadd

5. Sorted Set 有序集合，也去重。写进去的时候给一个分数，自动根据分数 排序。

   zadd board 86 zhangsan

   

   zrevrange board 0 3

   zrank board zhaoliu





### redis 的过期策略都有哪些？？ 内存淘汰机制有哪些？？手写一下LRU 代码实现？？

redis 写入的值，一定会过期吗？？

由过期策略来决定： 定期删除+ 惰性删除

定期删除：   redis 每到规定时间，就随机检查一批 key，如果过期，就删除。

惰性删除： 是指哪些过期了，但是还没被检查到，就没法被删除，如果这个时候使用到了

就检查是否过期，过期就删除了。  返回空



如果很多key 过期了，但是也没被使用，很多key 占用了大量内存，这个时候会走

内存淘汰机制：  主要的机制是 删除最近最少使用的key。

1. 内存空间不够时，直接报错

allkey -lru: 在所有key中， 选择最近最少使用的key中删除

Alley-random: 所有key中，随机删除。

Volatile-lru:  过期key中，选择最近最少使用的key 删除

Volatile-random:过期key，随机

end： 在设置过期时间的key空间中， 选择更早过期的key 来删除。



#### 怎么设置一个永久不删除的key？？？

设置过期时间为-1， 且开启 持久化。



## 和其他缓存工具有啥不同，<u>单线程模式</u>是啥？？？

![Redis-single-thread-model](https://github.com/doocs/advanced-java/raw/main/docs/high-concurrency/images/redis-single-thread-model.png)

文件事件处理器是单线程的，所以redis 才是单线程模型。

- 服务器 会io 多路复用模式监听多个socket 连接。
- 客户端连接服务器 ，会将产生的socket 事件存入 内存队列，队尾有个事件分发器分发到不同处理器。
- 服务器端的  连接应答处理器来处理这个socket ，将socket 可读事件关联到命令请求处理器上。
- 这个时候，客户端发送 set key value 到socket 中，事件处理器将会读取到信息。
- 找到 命令表 ，查找命令并执行命令， 将可写事件关联到 命令回复处理器。
- 并将完成信息写到 缓冲区中。监听到信息完成后发送给客户端。
- 客户端根据协议 解析成人能看得懂的文字。ok



### 为<u>啥单线程模型还这么快</u>呢？？？

- C语言
- 基于内存
- **非阻塞的 io 多路复用机制**
- **单线程模式 反而避免上下文切换，预防了多线程竞争的原因**



## redis 可用性，拓展性

### 如何保证Redis 的高并发，高可用？？Redis 主从架构是什么？？ 主从复制原理是什么？？

基于主从架构，来实现高并发， 一主写，多从读，达到 每秒几十万的Qps。



**主从架构**

指的是 一个主master 负责读，许多台从服务器负责写， 添加多台服务器，可以支持更多的读。



**主从架构是基于 redis 主从复制的原理。**

什么是主从复制？

![Redis-master-slave-replication](https://github.com/doocs/advanced-java/raw/main/docs/high-concurrency/images/redis-master-slave-replication.png)

**主从复制的核心原理**：

- 从服务器第一次连接服务器，会发送 psync 命令给主服务器。
- 主服务器会启动一个线程生成rdb 文件，同时将现在写入的 命令放在内存中。
- 从服务器 收到rdb文件先存入 磁盘，再从磁盘加载 rdb 文件。
- 再同步 主服务器内存中的命令。

**可能也会存在一些问题**：

主服务挂了，启动 如果没有开启持久化，可能主从复制会导致主服务器是空的， 从服务器同步空数据。



主从复制的 断点复制：

其实就是增量复制，block log 记载了 offset。



无磁盘化复制：

全量复制：

增量复制：

异步复制：



redis 高可用， 利用redis 哨兵机制，实现 主备切换。

### 主从缺点：



### redis哨兵原理是啥？？  各种方案都有优点和缺点？？



**主从架构的缺点**



**哨兵的缺点**



### redis 集群怎么搭建？？ 



### Redis，key怎么寻址的？？分布式寻址都有哪些算法？了解一致性hash算法？？？



### 主从集群和哨兵集群怎么搭建？？？





## Redis 的持久化

### redis 的持久化的使用场景？ 

主从同步的时候要开启 主redis 的持久化。

### redis持久化机制有哪几种方法？？

2种，rdb 和 aof

RDB， 持久化机制，会周期性的同步数据到硬盘

AOF， 持久化机制，就将每条命令执行后，追加到 aof 文件。



如果同时开启RDB，和AOF， 会重做 AOF 文件，因为AOF 文件更完整。



### 不同的持久化机制都有什么优缺点？

RDB 优缺点：

优点： 

1. 适合做冷备

			2. RDB比AOF 回复数据会很快
   			3. RDB 可以让redis 更快，因为原理是 主进程fork 了一个子进程去同步

缺点：

1. 主进程每次fork 子进程去生成RDB文件时，如果RDB 文件特别大，redis 可能会在数ms 不能提供服务。
2. Redis 采用 RDB 防止丢失数据，可能不是特别好， 因为RDB每次生成时，如果redis 挂了会导致 丢失这5分钟甚至更久的数据。



AOF 优缺点：

AOF 更好的保证数据不丢失，一秒执行一次 fsync，最多丢失一秒的数据

AOF 以append-only 模式写入，没有任何寻址开销。写入性能非常高。而且文件不容破损，也容易修复。

AOF 非常适合做灾难性的误删除的紧急恢复。



同一份文件来说，AOF比RDB 要大。

AOF 开启后，会导致RDB 性能大大下降。



### 持久化机制底层怎么实现的？？











- 1.什么是 redis？它能做什么？
- 2.redis 有哪八种数据类型？有哪些应用场景？
- 3.redis为什么这么快？
- 4.听说 redis 6.0之后又使用了多线程，不会有线程安全的问题吗？
- 5.redis 的持久化机制有哪些？优缺点说说
- \6. Redis的过期键的删除策略有哪些？
- \7. Redis的内存满了怎么办？
- 8.Redis 的热 key 问题怎么解决？
- 9.缓存击穿、缓存穿透、缓存雪崩是什么？怎么解决呢？
- 10.Redis 有哪些部署方式？
- 11.哨兵有哪些作用？
- 12.哨兵选举过程是怎么样的？
- 13.cluster集群模式是怎么存放数据的？
- 14.cluster的故障恢复是怎么做的？
- 15.主从同步原理是怎样的？
- 16.无硬盘复制是什么？





## 1.什么是 redis？它能做什么？

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7U3XkjvnJuVJbkxAOfNJFiaHVogy4ZMH9ibFlDsXjVibxv797p7nb1ngpVw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)





redis: redis 即 Remote Dictionary Server，用中文翻译过来可以理解为**远程数据服务**或远程字典服务。其是使用 C 语言的编写的key-value**存储系统**

应用场景:缓存，数据库，消息队列，分布式锁，点赞列表，排行榜等等



## 2.redis 有哪八种数据类型？有哪些应用场景？

redis 总共有**八种数据结构，五种基本数据类型和三种特殊数据类型**。

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7Uoibw9udJ49SafrvibDs8zialsNsgGmwXdQlw3lLtRr0Aqib3Q4AwUguf1g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

五种基本数据类型:

- **1.string**:字符串类型，常被用来存储计数器，粉丝数等，简单的分布式锁也会用到该类型
- **2.hashmap**:key - value 形式的，value 是一个map
- **3.list**:基本的数据类型，列表。在 Redis 中可以把 list 用作栈、队列、阻塞队列。
- **4.set**:集合，不能有重复元素，可以做点赞，收藏等
- **5.zset**:有序集合，不能有重复元素，有序集合中的每个元素都需要指定一个分数，根据分数对元素进行升序排序。可以做排行榜

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UB4EcXwnMHj6ibg8SrxlEYjJLC1paNkXBeUOZEWmsVLoBL9plFfO5RoA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 三种特殊数据类型:
- **1.geospatial**: Redis 在 3.2 推出 Geo 类型，该功能**可以推算出地理位置信息，两地之间的距离**。
- **2.hyperloglog**:基数：数学上集合的元素个数，是不能重复的。这个数据结构**常用于统计网站的 UV**。
- **3.bitmap**: bitmap 就是通过最小的单位 bit 来进行0或者1的设置，表示某个元素对应的值或者状态。一个 bit 的值，或者是0，或者是1；也就是说一个 bit 能存储的最多信息是2。bitmap **常用于统计用户信息比如活跃粉丝和不活跃粉丝、登录和未登录、是否打卡等**。

## 3.redis为什么这么快？



![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7URO6caBteGAtmPa3ZJnq9BSHibMZK9KLtpIdNF54yRzwQZc02o6a7IDA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

官方数据 redis 可以做到每秒近10w的并发，这么快的原因主要总结为以下几点：

- 1:完全基于内存操作
- 2:使用单线程模型来处理客户端的请求，避免了上下文的切换
- 3:IO 多路复用机制
- 4:自身使用 C 语言编写，有很多优化机制，比如动态字符串 sds



## 4.听说 redis 6.0之后又使用了多线程，不会有线程安全的问题吗？

**不会**

其实 redis **还是使用单线程模型来处理客户端的请求**，只是使用多线程来处理数据的读写和协议解析，执行命令还是使用单线程，所以是不会有线程安全的问题。

之所以加入了多线程因为 redis 的性能瓶颈在于网络IO而非CPU，使用多线程能提升IO读写的效率，从而整体提高redis的性能。



## 5.redis 的持久化机制有哪些？优缺点说说



edis 有**两种**持久化的方式，AOF 和 RDB.

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UNCyWVw5nYiak1EVzoAEHcKTcCsWCYb0rHpp0ibHy36WPCysCQBC95onQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**AOF**:

- redis 每次执行一个命令时,都会把这个「命令原本的语句记录到一个.aod的文件当中,然后通过fsync策略,将命令执行后的数据持久化到磁盘中」(不包括读命令)，

AOF的优缺点

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7Uvq8SW1RQE2gRhUldZR05fax2wQWYqakaOk6tZzibglxyibh96iccMqbTw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **AOF 的「优点」**:

- - 1.AOF可以「更好的保护数据不丢失」，一般AOF会以每隔1秒，通过后台的一个线程去执行一次fsync操作，如果redis进程挂掉，**最多丢失1秒的数据**
  - 2.AOF是将命令直接追加在文件末尾的,**「写入性能非常高」**
  - 3.AOF日志文件的命令通过非常可读的方式进行记录，这个非常「**适合做灾难性的误删除紧急恢复」**，如果某人不小心用 flushall 命令清空了所有数据，只要这个时候还没有执行 rewrite，那么就可以将日志文件中的 flushall 删除，进行恢复

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UwXlzE5wJc6M30HNCFuCxYzjaqBjYGCed52mBlpUJ3VNAGzjpPVrQPw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **AOF 的「缺点」**:

- - 1.对于同一份数据源来说,一般情况下**AOF 文件比 RDB 数据快照要大**
  - 2.由于 .aof 的**每次命令都会写入**,那么相对于 RDB 来说「需要消耗的性能也就更多」，当然也会有 **aof 重写**将 aof 文件优化。
  - 3.**「数据恢复比较慢」**，不适合做冷备。

- **RDB**:

- - 把**某个时间点 redis 内存**中的数据以二进制的形式存储的一个.rdb为后缀的文件当中,也就是「**周期性的备份redis中的整个数据**」,这是redis**默认**的持久化方式,也就是我们说的快照(snapshot)，是采用 fork 子进程的方式来写时同步的。

- **RDB的优缺点**

- ![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7U51osQeianOyXXFfsiaKKfQFPSXOV6TMlvHLj2ZiasicESbJ4bvx0oPAFjw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- - RDB的优点:

  - - 1.它是将某一时间点redis内的所有数据保存下来,所以当我们做「大型的数据恢复时,RDB的恢复速度会很快」
    - 2.由于RDB的FROK子进程这种机制,队友给客户端提供读写服务的影响会非常小

- ![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7U9VNHpNotHbibicq9ErcDOvShVVH471MFpibr8HsyhuYaia4QwMUibhnT3CQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- - RDB的缺点:

  - - 举个例子假设我们定时5分钟备份一次,在10:00的时候 redis 备份了数据,但是如果在10:04的时候服务挂了,那么我们就会丢失在10:00到10:04的整个数据
    - 1:「有可能会产生长时间的数据丢失」
    - 2:可能会有长时间停顿:我们前面讲了,fork 子进程这个过程是和 redis 的数据量有很大关系的,**如果「数据量很大,那么很有可能会使redis暂停几秒」**

- ## 6. Redis的过期键的删除策略有哪些？

过期策略通常有以下三种：

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UOsbDNWgygbFGJ2qNCicamdfRl1gRkicIZdyCibd43SF0RuJjSmeH9GVqA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **定时过期**：**每个设置过期时间的key都需要创建一个定时器，到过期时间就会立即清除**。该策略可以立即清除过期的数据，对内存很友好；但是会占用大量的CPU资源去处理过期的数据，从而影响缓存的响应时间和吞吐量。
- **惰性过期**：只有当**访问一个key时，才会判断该key是否已过期**，过期则清除。该策略可以最大化地节省CPU资源，却对内存非常不友好。极端情况可能出现大量的过期key没有再次被访问，从而不会被清除，占用大量内存。
- **定期过期**：**每隔一定的时间，会扫描一定数量的数据库的expires字典中一定数量的key，并清除其中已过期的key**。该策略是前两者的一个折中方案。通过调整定时扫描的时间间隔和每次扫描的限定耗时，可以在不同情况下使得CPU和内存资源达到最优的平衡效果。



## 7. Redis的内存满了怎么办？

实际上Redis**定义了「8种内存淘汰策略」用**来处理redis内存满的情况：

- 1.noeviction：直接返回错误，不淘汰任何已经存在的redis键
- 2.allkeys-lru：所有的键使用lru算法进行淘汰
- 3.volatile-lru：有过期时间的使用lru算法进行淘汰
- 4.allkeys-random：随机删除redis键
- 5.volatile-random：随机删除有过期时间的redis键
- 6.volatile-ttl：删除快过期的redis键
- 7.volatile-lfu：根据lfu算法从有过期时间的键删除
- 8.allkeys-lfu：根据lfu算法从所有键删除



## 8.Redis 的热 key 问题怎么解决？

热 key  就是说，在某一时刻，有非常多的请求访问某个 key，流量过大，导致该 redi 服务器宕机

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UAIavvosPQhxrrMibrOJMhmZA4LKdX0PoEpag8cFOVV7vEiaeIGNwABAQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

解决方案:

- 可以将结果缓存到本地内存中
- 将热 key 分散到不同的服务器中
- 设置永不过期

## 9.缓存击穿、缓存穿透、缓存雪崩是什么？怎么解决呢？



缓存穿透:

- 缓存穿透是指用户请求的数据**在缓存中不存在并且在数据库中也不存在**，导致用户每次请求该数据都要去数据库中查询一遍，然后返回空。

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7U2uxMelHwcKJHLbfZ8ILtqT3oib6ic3uFCn1kcibLwV385vUREofyG2ILw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

解决方案:

- 布隆过滤器
- 返回空对象

缓存击穿：

- 缓存击穿，是指一个 key 非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个 key 在**失效的瞬间，持续的大并发就穿破缓存**，直接请求数据库，就像在一个屏障上凿开了一个洞。

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7Uc2ouhNP6TBNnS7dF701qw4lf6UsiawIA3Cpxj1XK0saULttXCgXtetg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

解决方案:

- 互斥锁
- 永不过期

缓存雪崩：

- 缓存雪崩是指缓存中**不同的数据大批量到过期时间**，而查询数据量巨大，请求直接落到数据库上导致宕机。

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UCqr8n5ibnomOIPoDrqiba0YzFq4VwulW1ZQZkDQsHLDva5xK4TiaU0jcA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

解决方案:

- 均匀过期
- 加互斥锁
- 缓存永不过期
- 双层缓存策略

## 10.Redis 有哪些部署方式？



![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UxD6zTS7hsg4EPcb8kY4zPiaegXYLn7TmUoXwhkuHzhpSQ04YXfB5x2w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 单机模式:这也是最基本的部署方式,只需要一台机器,负责读写,一般只用于开发人员自己测试
- 哨兵模式:哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独立运行。其原理是哨兵通过发送命令，等待Redis服务器响应，从而监控运行的多个Redis实例。它具备**自动故障转移、集群监控、消息通知**等功能。
- cluster集群模式:在redis3.0版本中支持了cluster集群部署的方式，这种集群部署的方式能**自动将数据进行分片**，每个master上放一部分数据，提供了内置的高可用服务，即使某个master挂了，服务还可以正常地提供。
- 主从复制:在主从复制这种集群部署模式中，我们会将数据库分为两类，第一种称为主数据库(master)，另一种称为从数据库(slave)。主数据库会负责我们整个系统中的读写操作，从数据库会负责我们整个数据库中的读操作。其中在职场开发中的真实情况是，我们会让主数据库只负责写操作，让从数据库只负责读操作，就是为了**读写分离**，减轻服务器的压力。



## 11.哨兵有哪些作用？

- 1.监控整个主数据库和从数据库，观察它们是否正常运行
- 2.当主数据库发生异常时，自动的将从数据库升级为主数据库，继续保证整个服务的稳定

## 12.哨兵选举过程是怎么样的？

- 1.第一个发现该master挂了的哨兵，向每个哨兵发送命令，让对方选举自己成为领头哨兵
- 2.其他哨兵如果没有选举过他人，就会将这一票投给第一个发现该master挂了的哨兵
- 3.第一个发现该master挂了的哨兵如果发现由超过一半哨兵投给自己，并且其数量也超过了设定的quoram参数，那么该哨兵就成了领头哨兵
- 4.如果多个哨兵同时参与这个选举，那么就会重复该过程，知道选出一个领头哨兵

选出领头哨兵后，就开始了故障修复，会从选出一个从数据库作为新的master

## 13.cluster集群模式是怎么存放数据的？



一个cluster集群中总共有16384个节点，集群会**将这16384个节点平均分配给每个节点**，当然，我这里的节点指的是每个主节点，就如同下图：

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UYxht5k6HwticicdCEeJBPL7thOWdiay5TzzDcXNK7umbcq721wldAdrLA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 14.cluster的故障恢复是怎么做的？



判断故障的逻辑其实与哨兵模式有点类似，在集群中，每个节点都会**定期的向其他节点发送ping命令**，通过有没有收到回复来判断其他节点是否已经下线。

如果**长时间没有回复，那么发起ping命令的节点就会认为目标节点疑似下线**，也可以和哨兵一样称作主观下线，当然也需要集群中一定数量的节点都认为该节点下线才可以，我们来说说具体过程：

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8bpiaguNVUIpEN7MUxgrSU7UQwwoXOmQy4UV6sPibe1ibuia7GVW5nj8rZSpFTGzzN1HUVvu4emcBVoibA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.当A节点发现目标节点疑似下线，就会向集群中的其他节点散播消息，其他节点就会向目标节点发送命令，判断目标节点是否下线
- 2.如果集群中半数以上的节点都认为目标节点下线，就会对目标节点标记为下线，从而告诉其他节点，让目标节点在整个集群中都下线



## 15.主从同步原理是怎样的？

- 1.当一个从数据库启动时，它会向**主数据库发送一个SYNC命令**，master收到后，在后台保存快照，也就是我们说的RDB持久化，当然保存快照是需要消耗时间的，并且redis是单线程的，在保存快照期间redis受到的命令会缓存起来
- 2.快照完成后会**将缓存的命令以及快照一起打包发给slave节点**，从而保证主从数据库的一致性。
- 3.从数据库接受到快照以及缓存的命令后会将这部分数据**写入到硬盘上的临时文件当中**，写入完成后会用这份文件去替换掉RDB快照文件，当然，这个操作是不会阻塞的，可以继续接收命令执行，具体原因其实就是fork了一个子进程，用子进程去完成了这些功能。

因为不会阻塞，所以，这部分初始化完成后，当主数据库执行了改变数据的命令后，会异步的给slave，这也就是我们说的复制同步阶段，这个阶段会贯穿在整个中从同步的过程中，直到主从同步结束后，复制同步才会终止



## 16.无硬盘复制是什么？

我们刚刚说了主从之间是通过RDB快照来交互的，虽然看来逻辑很简单，但是还是会存在一些问题，但是会存在着一些问题。

- 1.master禁用了RDB快照时，发生了主从同步(复制初始化)操作，也会生成RDB快照，但是之后如果master发成了重启，就会用RDB快照去恢复数据，这份数据可能已经很久了，中间就会丢失数据
- 2.在这种一主多从的结构中，master每次和slave同步数据都要进行一次快照，从而在硬盘中生成RDB文件，会影响性能

为了解决这种问题，redis在后续的更新中也加入了无硬盘复制功能，也就是说**直接通过网络发送给slave**，避免了和硬盘交互，但是也是有io消耗
