## 7.arrayList 和 linkedList 的区别？

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9A9Dg6YoMs7eL4T1fRU8PWwt32YdDUMWnTOM3zhQfU7d7bBL155tUhw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.ArrayList 是实现了基于**「数组」**的，存储空间是连续的。LinkedList 基于**「链表」**的，存储空间是不连续的。（LinkedList 是双向链表）
- 2.对于**「随机访问」** get 和 set ，ArrayList 觉得优于 LinkedList，因为 LinkedList 要移动指针。
- 3.对于**「新增和删除」**操作 add 和 remove ，LinedList 比较占优势，因为 ArrayList 要移动数据。
- 4.同样的数据量 LinkedList 所占用空间可能会更小，因为 ArrayList 需要**「预留空间」**便于后续数据增加，而 LinkedList 增加数据只需要**「增加一个节点」**





## 8.hashMap 1.7 和 hashMap 1.8 的区别？

只记录**「重点」**

| 不同点          |          hashMap 1.7           |                    hashMap 1.8 |
| :-------------- | :----------------------------: | -----------------------------: |
| 数据结构        |           数组+链表            |               数组+链表+红黑树 |
| 插入数据的方式  |             头插法             |                         尾插法 |
| hash 值计算方式 | 9次扰动处理(4次位运算+5次异或) | 2次扰动处理(1次位运算+1次异或) |
| 扩容策略        |           插入前扩容           |                     插入后扩容 |

## 9.hashMap 线程不安全体现在哪里？

在 **「hashMap1.7 中扩容」**的时候，因为采用的是头插法，所以会可能会有循环链表产生，导致数据有问题，在 1.8 版本已修复，改为了尾插法

在任意版本的 hashMap 中，如果在**「插入数据时多个线程命中了同一个槽」**，可能会有数据覆盖的情况发生，导致线程不安全。



## 10.那么 hashMap 线程不安全怎么解决？

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9ibYEOpa4zibmqyEdjzbicMbcYSNY7PWvnRFaVo02ibNLr5PG73rOzy5bLQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 一.给 hashMap **「直接加锁」**,来保证线程安全
- 二.使用 **「hashTable」**,比方法一效率高,其实就是在其方法上加了 synchronized 锁
- 三.使用 **「concurrentHashMap」** , 不管是其 1.7 还是 1.8 版本,本质都是**「减小了锁的粒度,减少线程竞争」**来保证高效.



## 11.concurrentHashMap 1.7 和 1.8 有什么区别

只记录**「重点」**

| 不同点   |    concurrentHashMap 1.7     |              concurrentHashMap 1.8 |
| :------- | :--------------------------: | ---------------------------------: |
| 锁粒度   |         基于segment          |                      基于entry节点 |
| 锁       |        reentrantLock         |                       synchronized |
| 底层结构 | Segment + HashEntry + Unsafe | Synchronized + CAS + Node + Unsafe |





## 12.介绍一下 hashset 吧



![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8a16Uvk8nLR7MJSFpG4Mgc9OibEAD7pPJlwaiak532V1VrOWVhz9E4oLh25uZMM6ts0QsDia3ZYCC5zQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

上图是 set 家族整体的结构，

set 继承于 Collection 接口，是一个**「不允许出现重复元素，并且无序的集合」**.

HashSet 是**「基于 HashMap 实现」**的，底层**「采用 HashMap 来保存元素」**

元素的哈希值是通过元素的 hashcode 方法 来获取的, HashSet 首先判断两个元素的哈希值，如果哈希值一样，接着会比较 equals 方法 如果 equls 结果为 true ，HashSet 就视为同一个元素。如果 equals 为 false 就不是同一个元素。