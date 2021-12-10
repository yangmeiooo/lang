## 消息队列(Kafka)

一些新技术，引入，一定要思考为啥要使用这个

好处坏处， 调研一下，为啥只使用这个。



## Kafka 基本概念

- Broker  一个机器安装了一个Kafka 就算一个Broker,一个集群由一个或多个broker 组成
- Producer  生产者，发送消息到topic
- Consumer 消费者，从topic 中订阅消息
- Consumer Group 由多个消费者组成的消费者组，组统一订阅一个topic,一条消息可以被多个组消费，一个topic 的消息只能被一个消费者组消费一次，可能随机到组内的某个消费者实例消费（组内只有一个消费者能消费这个消息），一个分区只能被一个消费组实例消费，多余的实例将会空闲？触发reblance ,导致消息消费异常。一个topic和多个分区，分区是topic 的全量副本，还是差异化集合？
- Topic  分类，每发送的消息都必须指定一个topic，逻辑上的概念（就是一个消息队列）
- Partition  是物理上的概念，一个topic 是包含多个Partition ，一个Partition ，对应一个文件夹，，文件夹下有index 文件和log 文件，log 文件里含有offset. 存在的意义就是将一个topic（消息队列拆分成多个分区，其实就是多个队列，方便管理和分区并行处理，提升吞吐量）
- Replica  副本， 即Partition 的副本，主副本leader 负责读写，并且同步消息到 所有的follower. 副本数不能超过集群机器数目。
- Leader 副本集里面 的可读可写的主副本
- Follower。副本集里面的 从副本，只能读



**https://developer.51cto.com/art/202003/611798.htm 基本知识讲的很好**

https://developer.51cto.com/art/202003/611798.htm



## 为什么使用消息队列？？

### 优点好处 

换一个问法，其实就是消息队列有啥好处，值得去在某些场景下使用？？

- 解耦
- 异步
- 流量消峰

而在upe 中只是利用kafka 大数据日志能力来处理。，很多模块都依赖生产者的信息

所以， 利用Kafka 系统解耦。

### 缺点

- 增加系统复杂度， 很多系统需要进行维护。  **怎么保证消息没有重复消费？？怎么处理消息丢失的情况？？怎么保证消息传递的顺序性**
- 系统可用性降低，一旦Kafka 挂掉，很多系统都依赖它，可能其他系统也崩溃。  **所以怎么保证消息队列的高可用？？**
- 一致性问题？？  A 系统处理完马上返回了，BC 系统写库成功，D系统写库失败。怎么搞。



## 消息队列之间的比较？？

RabbitMQ,  万级的数据规模  ，  微妙级别。  主从架构

RocketMQ,十万的数据规模 ，   topic 数量几百几千的时候，吞吐量会下降一点。ms 级别， 分布式架构

Kafka,十万，一般用来实时计算，日志收集， topic  数量几百的时候，吞吐量会下降很多。 ms 级别以内。 分布式架构。





## 怎么保证消息队列的高可用？？

RabbitMQ 消息队列的高可用，是镜像集群模式， 每一个queue. 都保存相应的副本

缺点： 1. 每一个机器都需要同步信息，做不到高可用，线性拓展。

 		   2. 性能开销很大。每发一条消息，都必须同步到所有 镜像的机器上



### Kafka 怎么保证高可用性？？？

其实很简单， 就是 每一个partition 都分布在不同的机器上，

并且，每一个partition的 副本集 会被均匀的分布到 集群上，如果刚好partition 的机器挂了，

那么会从它的 副本 中继续读写，如果刚好此机器上的partition是leader ,那么 从副本集合选出一个新的 leader 负责读写。

这就是 Kafka 的高可用性。

https://github.com/doocs/advanced-java/blob/main/docs/high-concurrency/how-to-ensure-high-availability-of-message-queues.md





## 如何保证消息不被重复消费？？

这三者都是引入Kafka 后，系统复杂度提升后需要注意的一些点。

消息重复消费，都是有可能导致的，关键是 怎么处理 消息消费的幂等性？

- 主键查一下，是否存在
- redis 天然的set
- 数据库的唯一索引
- 其他的业务逻辑，设立一个全局的id，判断这个id 是否已经被消费过了。



## 如何保证消息不丢失？？？

其实这个问题很好回答的。

每次从 生产者，消息队列和 消费者的角度出发回答即可。

**生产者**：

生产者发送消息到 消息队列的时候可能会丢失。

但是开启那个 all 策略，即消息队列收到数据后，不单单主Partition写磁盘后，

所有的follower 都同步消息后，才返回确认。



**消息队列可能也会丢失**：

什么情况下可能会丢失呢？  当broker 挂了以后，消息还没同步到 follower,

但是，follower 被选举成为 主partition后，就相当于丢失了一部分数据。

怎么处理呢？

- 给 topic 设置 `replication.factor` 参数：这个值必须大于 1，要求每个 partition 必须有至少 2 个副本。
- 在 Kafka 服务端设置 `min.insync.replicas` 参数：这个值必须大于 1，这个是要求一个 leader 至少感知到有至少一个 follower 还跟自己保持联系，没掉队，这样才能确保 leader 挂了还有一个 follower 吧。
- 在 producer 端设置 `acks=all` ：这个是要求每条数据，必须是**写入所有 replica 之后，才能认为是写成功了**。
- 在 producer 端设



**消费者可能也会丢失数据**：

什么情况下丢失数据呢？？  消费者收到消息后，突然挂机，导致消息消费自动commit。

怎么处理呢？？

手动提交 offset  。  但是可能会重复消费，什么情况下会重复消费呢？？

手动提交offset过程中，挂了，还是会重复消费，需要消费者这边保证幂等性。







## 如何保证消息的顺序性？？？

什么是消息的顺序？？？ 为啥要保证消息的顺序？？

当指定一个topic 的时候，有3个partition, 将一个订单id 作为key，所有数据都到

同一个partition, 单个消费者消费 顺序不会错乱，这就是顺序。

如果 多个线程来提高消费速度，就会导致消息消费 顺序错乱，可能导致数据出错。



有没有办法保证消息的顺序？？

将消费者 准备n个内存队列，  同一个写到同一个内存队列，其他消费线程消费不到。即可。

![kafka-order-02](https://github.com/doocs/advanced-java/raw/main/docs/high-concurrency/images/kafka-order-02.png)





## Kafka 超高并发设计的核心是什么？

答案是： Reactor 网络设计模式

其实redis， netty 底层本质就是，使用了这个。





## kafka 怎么保证高可用？

kafka 默认一个topic ，一个patition分区， 但是可以指定多个分区

同时可以指定多个副本。  

副本是分 leader 和 从副本集，  当写入一条数据后，必须写入到leader 分区，再同步到从副本集。

才算这个消息提交。



## kafka优秀架构设计总结：

1. 怎么保证 kafka 的高可用，高并发，高性能的：

   高可用：是存在副本机制

   高并发： 通过nio reactor 模型，能处理高并发

   高性能： 体现在

   写数据的时候， 磁盘顺序写，速度非常快。

   读数据的时候，根据稀疏索引，快速定位到数据

   零拷贝机制，减少了应用程序与操作系统上下文的切换



## 难点： Kafka 消费组 消费不到消息？

KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR这个参数就是用来配置__consumer_offsets副本数的。
**一个分区只能被一个消费组实例消费，多余的实例将会空闲？重新调整消费者数量多余分区数目，触发reblance ,导致消息消费异常。待验证** 

## 消费者
一个分区只能被一个消费组实例消费，可是 分区数目多余 消费组的消费者数量，会存在一个消费者消费多个分区，不是矛盾吗？
验证一下。
消费者偏移量管理是怎么做的？
每次消费者消费后，新版的kafka 会提交offset 给kafka的topic。

**消费者 重新选举，消费者是如何实现rebalance的？**


## kafka 的isr是



## kafka 操作命令
1. 查看topic 集合 bin/kafka-topics.sh --zookeeper host12:2181 --list
2. 创建topic  bin/kafka-topics.sh --zookeeper host12:2181 --create --replication-factor 3 --partitions 1 --topic lrxtest0625
3. 删除topic  bin/kafka-topics.sh --zookeeper host12:2181 --delete --topic lrxtest0625
4. 查看topic 详情 bin/kafka-topics.sh --zookeeper host12:2181  --describe --topic lrxtest0625
5. 生产者发送消息 bin/kafka-console-producer.sh --broker-list host12:9092 --topic lrxtest0625
6. 消费者消费消息 bin/kafka-console-consumer.sh  --bootstrap-server host12:9092 --from-beginning --topic lrxtest0625

--from-beginning：会把first主题中以往所有的数据都读取出来。根据业务场景选择是否增加该配置。

 --bootstrap-server：生产消息的服务器

