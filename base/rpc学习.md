# Rpc ，Zookeeper学习

## rpc 是什么？？ 什么是rpc？？

remote process call ,远程过程调用。

![image](https://segmentfault.com/img/remote/1460000018932803)

https://segmentfault.com/a/1190000018932798

## rpc 和rpc 框架的区别？？

Rpc 是一种思想，远程过程调用。**一种点对点调用**

本质就是：  5个角色，客户端，客户端stub, 网络服务，服务端stub，服务端



Rpc 框架类似 Dubbo,  引入了分布式后，机器数量变多，服务必须**负载均衡**，服务的注册和发现，

服务的高可用，服务治理等。



## 为什么使用rpc??

不在同一内存，同一主机，进行调用， A 系统 调用B系统 像本地方法调用那样方便。





## rpc 解决了一个什么样的问题呢？？

解决了远程主机上某个服务的方法调用，不能像本地方法调用一样？？





## 什么是 zookeeper ??

一个分布式协调服务。

http://weekly.dockone.io/article/9028



## 解决了什么问题，有什么优点，缺点？？？

主要解决很多分布式服务的协调问题，



## Zookeeper主要的功能？？

- 存储功能，存储（用户提交的数据，和读取数据）
- 为用户程序提交的数据节点监听服务。  Watcher 机制



Zookeeper 

Znode 节点数据，

![3.jpg](http://dockone.io/uploads/article/20190629/4f4771b38f3fed5ce8380c5e66e338f6.jpg)



## Zookeeper使用场景？？

- 分布式锁
- 分布式协调， A 系统发送信息到 MQ 中，B 消费，通过zookeeper 可以让A 知道信息被消费了。
- 元数据，信息配置
- 系统的可用性注册。