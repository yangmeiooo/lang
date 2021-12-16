## Mongo

> mongodb



mongodb 是一个文档型数据库
跟mysql 的区别

 mysql           mongodb
1. 数据库         数据库
2. 数据表         collections
3. row            document
4. column         field

mongodb 的优点：
- 高拓展    （mongodb 怎么实现高拓展的？  redis 怎么实现高拓展的？）
- 高性能    （mongodb 高性能体现在那？ redis 高性能是基于内存的K-V数据库）
- 高可用   （体现在那？mongodb 复制到副本集  kafka的高可用体现在那？  redis 的高可用体现在那？哨兵机制，故障转移）

mongodb 常用命令
1. 显示当前数据库<br>
    ``` db ``` <br>
2. 切换数据库 <br>
 ``` use dataname ```<br>
3. 创建集合，创建集合最简单的方式是一条记录（文档）插入到集合中，如果集合不存在，则创建一个新的集合。 <br>
``` db.databaseName.insert({json格式数据}) ``` <br>
4. 插入 一个文档数组到集合中
以一个变量来承接，<br>
``` db.databaseName.insert(p); 即可以json 格式输出 ```<br>
5. 自定义查询条件,  > 2($gt:2) <br>
``` db.databaseName.find({EmployeeName:"Smith"}).forEach(printjson); ```<br>
6. mongodb 查询修饰符 <br>
``` db.lzn.find().limit(2).forEach()  查2个  db.lzn.find().sort({Id:-1})... 降序，值为1 为升序```    <br>
7. mongodb 聚合的概念,count()函数和remove()函数<br>
``` db.lzn.find().count()  and   db.lzn.find().remove() ``` <br>
8. mogodb 更新语句与 multi 多条记录更新<br>
``` db.lzn.update({"Id": 1}, {$set:{"name": "lzn"}}); ``` <br>


mongodb 一些新特点：
- 数据库不存在，集合不存在 .第一次切换到一个不存在的库，且创建一个集合，就会自动创建

- mongodb 不要求集合中的文档 都具有相同的属性，但是从3.2 开始更新和插入可以对集合执行强制文档验证规则
-  mongodb 文档插入的时候，没有指定_Id 的时候，mongo 将自动创建_Id 主键，如果要使用自定义主键，就需要在创建集合时明确定义它。



> mongodb 安全，监控，备份

- 创建用户并添加角色
```
    db.createUser({user: "lzn", pwd:"123",roles:[{role: "userAdminAnyDatabase",db:"admin"}]})
Successfully added user: {
        "user" : "lzn",
        "roles" : [
                {
                        "role" : "userAdminAnyDatabase",
                        "db" : "admin"
                }
        ]
}

```

- 管理单个数据库添加角色
```
    db.createUser(

{

user: "Employeeadmin",

pwd: "password",

roles:[{role: "userAdmin" , db:"Employee"}]})

//  只要指定db 即可
// role:  read,  readwrite  2种
```


- mongodb 副本集
```
    所有的从服务器都执行以下命令，来发现其他 服务器
    mongo –host ServerB –port 27017

    从服务器都添加

    总结: 复制是指 从一个mongodb 服务器复制到 副本集 的过程
```

- mongodb 分片
```
    起因： 如果查询的数量级很大，在单个服务器上，将会导致CPU 过高。

    解决方案: 通过分片技术，将很大数据集查询操作分到集群上，降低单个服务器查询的 负载
```

- mongodb 索引
```
    如何创建索引： createIndex()
    db.Employee.createIndex({Employeeid:1, EmployeeName:1])

    如何查找索引： getindexs()
    如何删除索引:  dropindex()
```

> 面试题
1. 什么是MongoDB?
mongodb 是文档数据库，提供高可用，高性能，高拓展

2. 什么是 mongodb 的命名空间？
mongodb 在集合中存储BSON 对象，集合名称和数据库名称的串联称为名称空间。

3. mongodb 中的分片是什么？？
在多个服务器 存储数据记录的过程叫分片。

4. 什么是副本集？
副本集是 一组相同数据集的mongodb 实例，在副本集中，一个节点是主节点，另外是辅助节点，从主节点到辅助节点，所有数据都会复制。

5. 复制在MongoDb 如何工作？
在多台服务器之间  同步数据的过程叫复制。     从主服务器同步的数据的从服务器叫做副本集， 用来提供数据甬余和数据的可用性，复制单个服务器数据的丢失。


6. 创建集合和删除集合的语句？？
    db.createCollection(name, options)
    db.collection.drop()

7. 说明Profiler 在mongodb 的作用是啥？
分析 慢查询

8. 要进行安全备份，可以使用mongodb 的什么功能？
日志功能

9. mongodb 的objectId 由啥组成？？
时间戳， 客户端进程ID，客户机器ID，字节递增计数器

10. 解释一下Mongodb 的索引，是什么？
索引是 mongodb 特定数据结构， 方便搜索数据，因为它 以易于遍历的形式存储小部分数据集。

11. 什么是mongodb 的GridFS??
为了存储和检索大文件，图像，视频，音频文件，默认情况下，使用2个文件来fs.files 和fs.chunks 来存储文件的元数据和数据块。





## Mysql

### 为啥要分库分表？？ 用过哪些分库分表中间件，怎么对数据库进行垂直拆分和水平拆分？？

一是并发读写高，而是 单库数据量太大，所以才要分库分表。

Sharing-jdbc

**垂直拆分**：

- 垂直拆表，就是 把一个用户表，拆成2个表， 用户表和用户信息表。
- 垂直拆库，就是把一个库，本来是放2个表用户表和订单表，现在 就是单库单表。

**水平拆分**：

- 水平分库分表，指的是 将user 表 分摊到 数据库db0.user_0,db0_user_1. 和 db1_user_0, db1_user_1.  中。

### 怎么让 未分库分表的数据 迁移到分表分表的数据库中？？

双写，写老库的同时，写新库。

再开一个程序，不断去同步老库的数据到新库。

### 如何设计动态扩缩容的分库分表方案？？



### 分库分表之后，id 主键怎么处理？？

分布式主键算法

### 你做过Mysql 的读写分离过吗？如何实现读写分离？Mysql 主从复制原理是啥？？ 

从一个主库读， 分几个从库写。

读写分离是基于 主从架构来实现的。

主从架构指的是 主数据库复制自身库的数据，同步到从库。

主库将变更写入binlog日志， 从库会有一个io 线程去拉取主库的 binlog 日志。

这个时候会引入一个新问题，主从同步延时的问题。

同时从库会起一个sql 线程去执行binlog日志的内容。



**主从同步延时问题？以及。主从同步过程中，主库挂了将导致从库的数据丢失？？。**

半同步复制是用来解决 ，主从同步 主库数据丢失问题。

主库将变更写入到从库的时候，这个时候立即强制将数据刷新到从库，从库写入到relay log日志，后返回一个

ack， 主库收到至少一个从库才认为 同步成功。



并行复制是用来解决，主从同步延时的问题。

其实就是，库级别 的并行复制，多个从库开启线程从主库同步日志，

降低主库的压力。







