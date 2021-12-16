[动态规划](#动态规划)<br>
[回溯问题](#回溯算法)<br>
[二分](#二分)<br>
[手撕拓扑排序](#手撕拓扑排序)<br>
[手撕快排](#手撕快排)<br>
[手撕归并排序](#手撕归并排序)<br>
[手撕堆排序](#手撕堆排序)<br>
[手撕LRUCache](#手撕LRU)<br>
[手写消费者模式生产者模式](#消费生产者模式)<br>
[手写多线程交替打印ABC](#手写多线程交替)<br>
[手写打印foobar](#交替打印foobar)<br>
[手写阻塞队列](#手写阻塞队列)<br>
[手写线程池](#手写线程池)<br>

[高频lru](#lru)<br>


1. 先刷二叉树， 是为了培养框架思维
2. 而大部分算法技巧，本质上都是树的遍历问题。

所有二叉树题目遍历框架

`
​	void traverse(TreeNode root){
​        // 前序遍历
​        traverse(root.left);
​        // 中序遍历
​        traverse(root.right);
​        // 后序遍历​	
​    }
`

### [动态规划]
动态规划  暴力解法就是遍历一颗N叉树
回溯算法  就是N叉树的前后序遍历问题

特点： 最优子结构， 重叠子问题，状态转移方程

框架：
`
1. 初始化 base case
dp[0][0][..] = base
2. 进行状态转移
for 状态1 in 状态1的所有取值:
    for 状态2 in 状态2的所有取值：
      for ..
        dp[1][2][3] = 求最值（选择1， 选择2...）
`
动态规划问题，就要思考如何列出正确的状态转移方程？
1、确定 base case，这个很简单，显然目标金额 amount 为 0 时算法返回 0，因为不需要任何硬币就已经凑出目标金额了。

2、确定###「状态」###，也就是原问题和子问题中会变化的变量。由于硬币数量无限，硬币的面额也是题目给定的，只有目标金额会不断地向 base case 靠近，所以唯一的「状态」就是目标金额 amount。

3、确定「选择」，也就是导致「状态」产生变化的行为。目标金额为什么变化呢，因为你在选择硬币，你每选择一枚硬币，就相当于减少了目标金额。所以说所有硬币的面值，就是你的「选择」。

4、明确 dp 函数/数组的定义。我们这里讲的是自顶向下的解法，所以会有一个递归的 dp 函数，一般来说函数的参数就是状态转移中会变化的量，也就是上面说到的「状态」；函数的返回值就是题目要求我们计算的量。就本题来说，状态只有一个，即「目标金额」，题目要求我们计算凑出目标金额所需的最少硬币数量。所以我们可以这样定义 dp 函数：



递归的时间复杂度计算：
递归树的总节点个数乘以 单个节点的时间复杂度



![image-20210525220802978](C:\Users\lizn7\AppData\Roaming\Typora\typora-user-images\image-20210525220802978.png)

递归的进入，和返回很重要，在返回的时候，牵扯状态的回溯。



//1.  0-1 背包, 给一系列物品，每个物品都有体积和价值，尝试放入容量为V 的背包

f[i][j], 以i 个系列物品，放入j 容量的背包的最大价值

            for(int i = 0; i < n; i++)
              for(int j = 1; j < V; j++)
状态转移：          f[i][j] = f[i-1][j]  , j < w[i]
                           = max(f[i-1][j] , f[i-1][j-w[i]] + v[i]）;

空间状态优化：   二维优化成一维数组
            for (int i = 0; i < n; i++) {
              for (int j = V; j >= w[i]; j--){
                   f[j] = max(f[j], f[j-w[i]] + v[i]);  //  j < w[i] 部分呢？ 
                                                        //其实就是执行了f[i][j]=f[i-1][j]
              }
            }


// 2. 完全背包问题, 给定一系列物品，每个物品都有体积和价值，尝试放入容量为V 的背包,
      但是, 每一个物品 都可以放无限次

      解法：  尝试基于每一个 背包容量V， 去将当前物品放很多次.
    
        for(int i = 0; i < n; i++){
          for(int j = 0;j < V; j++){
            f[j] = max(f[j], f[j-w[i]] + v[i]);   // 在0-1背包的一维优化中正序寻找
          }
        }

// 3. 多重背包问题, 给定一系列物品,每个物品都有体积和价值，和个数，
尝试放入背包容量为 V 的背包。

      for(int i = 0;i < n; i++){
        for (int j = M; j >= v[i];j--){
          for (int k = 0; k <= M[i] && k * w[i]<= j; j++){
            f[j] = Math.max(f[j-1], f[j-k * w[i]] + k* v[i])
          }
        }
      }

### 动态规划的一系列经典问题

> 单串问题

``爬楼梯70``
```
    Map<Integer, Integer> map = new HashMap<>();
    // f[n] 代表爬 n 层楼梯的次数
    // base case：f[0] = 0; f[1] = 1; f[2] = 2;
    // 转移方程： f[n] = f[n-1] + f[n-2];
    public int climbStairs(int n) {
        map.put(0,0);
        map.put(1,1);
        map.put(2,2);
        if (n <= 2) return map.get(n);

        for (int i = 3;i <= n; i++ ){
            map.put(i, map.get(i-1) + map.get(i-2));
        }
        return map.get(n);
    }
```

``801 使序列递增的最小交换次数``
```
  
  尝试：  正序， 反序去做，取不到最优的最小交换次数
  尝试1：  [0,4,4,5,9]
          [0,1,6,8,10]   代码运行是 2， 答案是  1
    public int minSwap(int[] nums1, int[] nums2) {
        int n = nums1.length;
        if(n == 1) return 0;
        int min = dfs(nums1, nums2, 1, 1);
        return min;
    }


    int dfs(int[] a,int[] b, int i,int j){
        if (i >= a.length || j >= b.length) return 0;
        int ret = 0;
        if (a[i] <= a[i-1] || b[j] <= b[j-1]){
            int temp = a[i];   
            a[i] =  b[j];
            b[j] = temp;
            ret = 1;
        } 
        int r = dfs(a, b, i + 1, j + 1);
        return r + ret;
    }
  思考：  题目想考核的真正的点是啥，我所不知道的地方，我需要学习的地方
  1.怎么做到  判断当前i == j，的元素交换下，怎么让后续的选择是最优的。
  2. 我当前简单的判断  a[i]<=[i-1] || b[j]<=b[j-1] 这种策略不是最优的
  3. 因为存在  当a[i] > a[i-1] && b[j] > b[j-1] 是保持递增，但是这个时候交换却是最好的选择
  4. 所以就是当，3存在的时候，这个时候拿什么依据来做判断是破题的关键。
  5. 尝试增加判断，还是错误，证明当前  尝试最优的方法是错误的。

  主要还是没有对 题目认证分析到位，对多种情况分析到位。
          a[i] > a[i-1] && b[i] > b[i-1]
          a[i] > b[i-1] && b[i] > a[i-1]
  对某一个位置i, 必须满足 其中一种情况，或者都满足

   只满足 a[i] > a[i-1] && b[i] > b[i-1]
        i 交换     i-1 必须交换
        i 不交换   i-1  不交换
   只满足 a[i] > b[i-1] && b[i] > a[i-1]
        i 交换     i-1 不交换
        i 不交换   i-1 交换

   同时满足 上面两种情况
        i 交换     在i-1 可以选择交换或者不交换，选择最优即可
        i 不交换   在i-1 可以选择交换或者不交换，选择最优即可
    

   class Solution {
    public int minSwap(int[] a, int[] b) {
        int n = a.length;
        if(n == 1) return 0;

        int[] swap = new int[n];   //f[i] =       
        int[] keep = new int[n];   //
        Arrays.fill(swap, Integer.MAX_VALUE);
        Arrays.fill(keep, Integer.MAX_VALUE);

        swap[0] = 1;
        keep[0] = 0;
        for (int i = 1;i < n; i++){
            if (a[i] > a[i-1] && b[i] > b[i-1]
                && a[i] > b[i-1] && b[i] > a[i-1])
                {
                    swap[i] = Math.min(swap[i-1],keep[i-1]) + 1;
                    keep[i] = Math.min(swap[i-1],keep[i-1]);
                    continue;
                }

            if (a[i] > a[i-1] && b[i] > b[i-1]){
                swap[i] = swap[i-1] + 1;
                keep[i] = keep[i-1];
            }

            if (a[i] > b[i-1] && b[i] > a[i-1]){
                swap[i] = keep[i-1] + 1;
                keep[i] = swap[i-1]; 
            }
        }

        return Math.min(swap[n-1] , keep[n-1]);
    }
   
} 

    总结： 主要还是有一个突破口，分析的突破口，基于当前的i，
    分析满足递增的公式，  全部存在的情况，全部不满足的情况。

    基于所有情况， 逐个分析单个的情况，基于单个情况，能做什么操作，
    做了这个操作，代码怎么表示出来，就能想到状态方程。

    总结 出来转移方程2个套路，1是 递归决策树看懂了。2 是所有情况，逐个分析单个情况。
```

`` 746 使用最小花费爬楼梯 ``
```
  这是一个简单题，但是我感觉自己不是做的很好，
  但是， 我感觉我对题目分析的情况比以前更深入了。
  更深入是指， 对题目的一些边边角角的细节，了解和考虑的更仔细了。

   public int minCostClimbingStairs(int[] cost) {
        
        // dp[i]   代表 上到i 台阶的消耗的最小体力花费。
        int n = cost.length;
        int[] dp = new int[n + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i <= n;i++){
            dp[i] = Math.min(dp[i-2] , dp[i-1]) + (i == n? 0: cost[i]);
        } 
        return dp[n];
    }

    总结：  1. 终结条件是，必须超出数组，这样，数组内的某一个台阶，都得算上到i台阶后，还得加上
          自己的花费。
```
``300 最长递增子序列``
```
    最长递增子序列，给一个nums [0,1,0,3,2,3]
    0,1,2,3  长度为4   

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;

        //  dp[i] 以第i个序列结尾的值 递增的子序列长度
        //  dp[i] = dp[j] + 1, 0 < j < i , a[i] > a[j]    
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1;i < n; i++){
            for (int j = 0; j < i; j++){
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);   // 这里是找到比 a[i]小的那个子序列的长度的最大值。
                }
            }
        }
        int res = 0;
        for (int i = 0;i < n; i++){
            res = Math.max(dp[i], res);
        }
        return res;
    }
```

> 单串状态问题

``887 鸡蛋掉落``
```

```


### [回溯算法]

> 剪枝

```
    leetcode 46 题
    全排列

    给定一个不含重复顺序的数字的数组nums, 返回其可能的全排列。
    按照任意顺序返回。

     static boolean[] flag = null;
    static List<List<Integer>> list = null;

    public List<List<Integer>> permute(int[] nums) {
        list = new ArrayList<>();
        flag = new boolean[nums.length];
        permute(nums, new ArrayList<Integer>());
        return list;
    }

    public void permute(int[] nums,List<Integer> temp){

        if (temp.size() == nums.length) {
            list.add(new ArrayList<Integer>(temp));
            return; // 本次调用结束, 主调方也会返回
        }

        for(int i = 0; i < nums.length; i++) {
            if (flag[i]) continue;
            
            temp.add(nums[i]);
            flag[i] = !flag[i];
            permute(nums, temp);
            // 这里需要回溯状态置回一下
            // 因为共用的是同一个 temp , flag boolean 数组
            flag[i] = !flag[i];
            temp.remove(temp.size() -1 );
        }
    }
     总结： 就是简单的回溯， 记得从子节点返回的时候，维持状态

```

```
    leetcode 47
    全排列  ，但是去掉返回的List<List<Integer>> 

    2个解决方案， 一个是 返回的元素，都用Set 来接受。
    但是速度慢

    另外一个方法就是 剪枝

    static List<List<Integer>> res = null;
    static boolean[] falg = null;

    public List<List<Integer>> permuteUnique(int[] nums) {
        int n = nums.length;
        res = new ArrayList<>();
        Arrays.sort(nums);
        falg = new boolean[n];
        permute(new ArrayList<>(), nums);
        return res;
    }

    public void permute(List<Integer> temp,int[] nums){

        if (temp.size() == nums.length){
            res.add(new ArrayList<Integer>(temp));
            return;
        }

        for(int i = 0;i < nums.length; i++){
            // O(1) 时间复杂度判断  nums[i] 是不是在之前的序列存在
            // 剪枝
            if (i > 0 && nums[i] == nums[i-1]
                    && !falg[i-1]) continue;

            if (falg[i]) continue;
            temp.add(nums[i]);
            falg[i] = !falg[i];
            permute(temp, nums);
            temp.remove(temp.size() -1 );
            falg[i] = !falg[i];
        }
    }



    总结： 1. 在不断递归下 ，应该不剪枝   falg[n-1] 没有被访问
          2.  在新一轮递归开始, 应该发现  n > 0 && nums[n] == nums[n-1] 直接剪枝

          但是就满足2的情况下, 会把正确的也剪枝掉，所以多加判断条件去过滤
```


>索引遍历
```
        leetcode 78


```

```
        leetcode 47

```

```
        leetcode 131
    题目： 给你一个字符串s, 请你将s 分割成一些子串，使每个字串，都是回文串。
    返回s 的所有分割方案.

    难点： 1. 怎么  拿到当前结果时，快速判断当前是否是回文串（这里可以维护一个，Map<String,>快速进行判断是否是 回文串。）
          2. 递归过程  怎么去重复，这里我没看懂,题目要求的。
          3. 题目 是寻找给定一个字符串s 的所有子串的 是回文串的情况。答案就是暴力搜索。
          怎么去搜索呢？ 选，不选，但是情况不一样。
          4. 为啥需要 int i呢？？ 维护一个区间吗？？
    
    
    class Solution {
    
    static List<List<String>> ret;
    static String str;

    public List<List<String>> partition(String s) {
        ret = new ArrayList<>();
        str = s;
        permute(0,new ArrayList<>());
        return ret;
    }

    public void permute(int start,List<String> temp){

        if (start >= str.length() ){
            if (temp.size() > 0){  // 这个判断需要加吗??
                ret.add(new ArrayList<>(temp));
            }
            return;
        }

        for (int a = start; a < str.length() ; a++ )
        {

            //System.out.println(p);
            if (IsPrime(start, a)){
                //System.out.println(p);
                temp.add(str.substring(start , a + 1));
                permute(a + 1,temp);
                // 是回文就  递归查找
                // 回溯的时候，需要擦除 temp
                temp.remove(temp.size() - 1);
            }
            // 不是回文 就剪枝
        }
    }

    /*
        给定一个p ,判断是否是回文串
     */
    public static boolean IsPrime(int start , int end ){


        boolean isPrime = true;
        while(start < end){
            if (str.charAt(start) != str.charAt(end)){
                isPrime = false;
                break;
            }
            start ++;
            end --;
        }
        return isPrime;
    }
}



    总结：
        这题是字符串题目，走的是  每一次选择都是，先选一个，选2个。。   
        一般找到所有的方案， 暴力搜索所有的情况即可。
        2021/6/7 : 题目理解错了。 每一次分割后，都保证分割后的子串,每一个单独的字符串都是
        回文串。
        总结：  在字符串分割的情况，往往是区间扫描法。一次扫 [start,i] 区间的字符串，以及[i,end] 就能找到所有的字符串情况。（这是一个技巧）
```

> 资源消耗
```
    leetcode 22 括号生成
    给定一个数，生成所有的括号的对数
    一看就是，求所有的括号的情况，递归回溯
    关键: 就是一种什么样的方式去分

        public List<String> generateParenthesis(int n) {

        List<String> ret = new ArrayList<>();
        permute(ret, new StringBuilder(), n, n);
        return ret;
    }

    public void permute(List<String> ret,StringBuilder sb,int l,int r){

        if (l < 0 || r < 0) return;

        // 结束条件
        if (l == 0 && r == 0){
            ret.add(sb.toString()) ;
            return;
        }
        // 剪枝
        if (r < l){
            return;
        }

        permute(ret, sb.append('('), l-1, r);
        sb.deleteCharAt(sb.length() -1 );

        permute(ret, sb.append(')'), l, r-1);
        sb.deleteCharAt(sb.length() -1);
    }


    总结： 以前做过的题， 以一个左右变量来约束

```


```
    leetcode 93 复原IP 

    第一次失败,证明有地方做的不好
    sb 不该是String, Integer 更好
    其次，List<Integer> 最后加.符号，比递归前加. 要更好

    其次就是，递归的状态没想清楚， 每一层的开始递归，s 应该是截取后的比较好

```



```
    leetcode 17 电话号码

     static String[] strs = {"abc","def", "ghi", "jkl", "mno", "pqrs","tuv","wxyz"};

     static String s;

    public List<String> letterCombinations(String digits) {

        s = digits;
        List<String> list = new ArrayList<>();
        if (digits.isEmpty()) return list;
        StringBuilder sb = new StringBuilder();
        permute(sb, list, 0);
        return list;
    }

    public void permute(StringBuilder sb, List<String> ret, int i){

        if (sb.length() == s.length()){
            ret.add(sb.toString());
            return;
        }
        
        if (i < s.length()) {
            char c =  s.charAt(i);
            String find = strs[c - '2'];
            for (char fc : find.toCharArray()) {
                sb.append(fc);
                permute(sb, ret, i + 1);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

    }


    总结： 正常递归即可

```



docker run -d --name kafka --publish 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=127.0.0.1:2181 -e KAFKA_ADVERTISED_HOST_NAME=127.0.0.1 -e KAFKA_ADVERTISED_PORT=9092 -e KAFKA_LOG_DIRS=/kafka/kafka-logs-1 -v /usr/local/logs:/kafka/kafka-logs-1 wurstmeister/kafka

> 多重限制
``` 
    leetcode 37 解数独




    leetcode 51  解N 皇后


```

> 递归
```
    leetcode 10 正则表达式匹配

    给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。

    想法：  感觉自己能做出来, 其实就是 在p 的多叉树中，能不能将
    字符串s  走完，能走完就能返回true. 匹配。

    尝试: 1. 递归方向感觉是对的, 但是  * 匹配这可能想的有问题
        2. 正则匹配  我理解可能就是错误的
        3. 证明正确， * 只能匹配 *前面一个元素0 次到 多次
        4. 写出第二个版本又错了, 题目意思是  s ,p p必须比较到末尾
        5.  纠结2个小时写的不对

    public boolean isMatch(String s, String p) {
        if(p.isEmpty()) return s.isEmpty();

        boolean match = false;
        // s isEmpty or not
        if(!s.isEmpty())
            match = s.charAt(0) == p.charAt(0) || p.charAt(0) == '.';

        if (p.length() >= 2 && p.charAt(1) == '*')
            // s 完全不匹配 ， 或者匹配一个
            return isMatch(s, p.substring(2)) || (match && isMatch(s.substring(1), p));

        // 走这里是  上面没有 *
        return match && isMatch(s.substring(1), p.substring(1));
    }


    总结： * ,任意匹配0次， 和一次
        思考的混乱的时候，就应该重新想

```

### [手撕拓扑排序]
解决方案：  常用来解决工程问题
什么是拓扑排序？ 
虽然叫排序， 但是实际并不是一种排序算法
而是针对特定的一种图的算法 DAG  有向无环图

有向无环图有啥特点？  
有方向
节点的走向不会构成一个环

结论：
1. 如果这个图 不是DAG ,那么是没有拓扑排序
2. 如果是 DAG, 至少有一个拓扑排序
3. 相反，存在一个拓扑排序， 那么这个图必是DAG

一个DAG ,可能存在多个 拓扑排序

算法步骤：
1. 拿到所有入度为0 的节点。
2. 拿到这些放到一个容器 （队列，先进先出）
3. 出队列 的一个节点， 将这个点出队列，代表从这个节点的出度为0，
同时擦除这个点到其他节点的入度，为0,就再入队。
4. 重复3，直到 队列为空，结束

0. 当发现所有节点入度都不为0， 代表存在环。 即不是DAG


要点:
1. 处理入度 ，数组或者Map 都可。

```
     public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 入度数组
        int flag = 0;
        int[] come = new int[numCourses];
        List<Integer> ret = new ArrayList<>();
        // 预处理入度
        for (int[] fan:  prerequisites){
            // fan[1]  -->  fan[0]
            come[fan[0]] += 1;
            flag += 1;
        }


        Queue<Integer> queue = new LinkedList<>();
        // 入度为0 ,入队列
        // 能不写 for循环就入队列吗？？？？
        for(int i = 0;i < come.length; i++){
            if (come[i] == 0) {
                queue.add(i);
            }
        }
        //System.out.println(queue.element());
        while(!queue.isEmpty()){

            int queueIndex = queue.poll();
            ret.add(queueIndex);
            // 擦除从这个节点的出度，同时 减少其他节点的入度
            // 迅速的从index 找到到其他的点入度 -1
            for(int j = 0; j < prerequisites.length;j ++){
                if (prerequisites[j][1] == queueIndex){
                    // 会不会存在,是重复扫描的原因
                    come[prerequisites[j][0]] -= 1;
                    // 从 a -> b的入度减1 ，证明少了条边
                    flag -= 1;
                    if (come[prerequisites[j][0]] ==  0){
                        // 入度出度节点写错了
                        queue.add(prerequisites[j][0]);
                    }
                }
            }
        }
        // 怎么判断, 当前存在环，存在环返回空
        // 怎么马上判断出  存在环，一个取巧的思路是 计数，当计数没减少到0时，证明存在环
        if (ret.size() == 0 || flag > 0){
            return new int[0];
        }

        return ret.stream().mapToInt(Integer::valueOf).toArray();
    }

    //存在一些问题？？
    1. 每次都要查找入度数组 ，for循环遍历，能在O(1) 时间复杂度找到吗？Map 存储 a->b   提升了  15%的速度
    2. 判断是否存在环？？  有啥方法，在执行过程中就能找到，找到了并且直接返回，省略了后续的代码，加快了时间。


```

### [二分]
```
    // 0. l , r 取超出区间得值, 都会尽量在区间查找这个值
    // 给一个超级大区间不存在得值，找不到这个值，会怎么做呢?? 超过范围，且while(l < r) 且可能l = r 这个不判断
    // 给一个超级小得数，区间不存在，找不到这个值，怎么做??
    // 1. 直接去判断最后得三个数，对于你得二分目前得情况来说，能否满足

```

### [手撕快排]

```
   1. 在不使用快速排序的情况，怎么去排序一个数组
    结论: 冒泡法, 在长度为n 的数组中, 持续n-1 轮
    每轮  交换，一个最大的值放到数组的最后， for(int j = 0;j < nums.length -i -1)

     public static void main(String[] args) {
        Random random = new Random();
        int n = 100000;
        int[] nums = new int[n];
        for (int i = 0;i < n; i++){
            nums[i] = random.nextInt(n);
        }
        long old = System.currentTimeMillis();

        for (int i = 0;i < nums.length -1; i++){
            // nums.length -1 轮交换
            for(int j = 0;j < nums.length - i - 1; j++){
                if (nums[j] > nums[j + 1]){
                    int temp = nums[j];
                    nums[j ] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }

        long newS  = System.currentTimeMillis();
        System.out.println(newS - old);  // 14773
        for (int i: nums){
            System.out.print(i);
            System.out.print(" ");
        }
    } 
    时间复杂度为： O(n^2)
    2. 存在一些问题, 怎么优化?
    能不能遍历一遍的时候，就可以？？
    能不能少交换？？

    普通快速排序
    class Solution {
    public int[] sortArray(int[] nums) {
        
        quickSort(nums, 0, nums.length -1);
        return nums;
    }
    public void quickSort(int[] nums, int l,int r){
        
        if (l > r) return;
        int low  = l;
        int high = r;
        int pivt = nums[l];
        while(low < high){

            while(low < high && nums[high] >= pivt){
                high--;
            }
            nums[low] = nums[high];
            while(low < high && nums[low] < pivt){
                low ++;
            }
            // 特殊情况， low == high ,
            nums[high-- ] = nums[low];
        }
        // 需要将这个位置填进去
        // 不填不行吗???
        // 会不会覆盖其他值呢？？？
        // 左边就都是比这个小吗？？ 右边都是比这个值大吗？？
        nums[low] = pivt;

        // 会不会越界 呢？？
        quickSort(nums, l, low -1);
        // 思考一下特殊情况
        quickSort(nums, low + 1, r);
    }


    快速排序的优化版本：
     public static void quickSort(int[] nums, int low, int high) {

        if (low >= high) return;

        if (high - low + 1 > 10) {
            int l = low;
            int r = high;
            // 中值
            int pivt = nums[l];

            while (l < r) {
                while (l < r && nums[r] >= pivt) {
                    r--;
                }
                nums[l] = nums[r];
                while (l < r && nums[l] < pivt) {
                    l++;
                }
                nums[r--] = nums[l];
            }
            nums[l] = pivt;

            quickSort(nums, low, l - 1);
            quickSort(nums, l + 1, high);
        } else {
            insertSort(nums, low, high);
        }
    }

    // 插入排序, 去优化快速排序
    public static void insertSort(int[] array, int low, int high) {

        int j;
        for (int i = 1; i < (high - low + 1); ++i)
        {
            int key = array[low + i];
            for (j = i; j>0 && array[ low + j - 1] > key;j--)
            {
                array[low + j] = array[low + j - 1];
            }
            array[low + j] = key;
        }
    }

    插入排序在 10以下能优化速度
}

        总结：其实就是一个寻找基准值位置得方法，同时保证，基准值左边都比它小，右边都大
        再递归左右子问题

```

### [手撕归并排序]

```
    1. 什么是归并排序
    归并排序实际就是一种稳定得排序算法，并且最差，最好平均得时间复杂度都为 O(nlogn)
    思想是： 分治思想，分是递归去分，再递归返回得时候，再排序合并得时候是为治

     public int[] sortArray(int[] nums) {
        
       int[] temp = new int[nums.length];
       quickSort(nums, 0, nums.length -1 ,temp);
       return nums; 
    }

    public void quickSort(int[] nums, int l,int r, int[] temp) {

       if (l >= r) return;

       int mid = (l + r )>>> 1;
       quickSort(nums, l , mid, temp);
       quickSort(nums, mid + 1, r, temp);

       mergeArray(nums, l, mid, r, temp); 
    }

    public void mergeArray(int[] nums, int l, int mid, int r, int[] temp) {
        int lf = l;
        int m = mid;
        int rl = mid + 1;
        int index = 0;
        while(lf <= mid && rl <= r) {
            if (nums[lf] >= nums[rl]) {
                temp[index++] = nums[rl++];
            } else {
                temp[index++] = nums[lf++];
            }
        }
        while(lf <= mid ) {
            temp[index++] = nums[lf++];
        }
        while(rl <= r) {
            temp[index++] = nums[rl++];
        }

        for(int i = 0;i < index;i++) {
            nums[ l + i] = temp[i];
        }
    }

    总结：  临时数组，是干啥得？自己不清楚
    其实就是一种，中间数组，方便拷贝，排序完，再拷贝回
    原来数组
```
### [手撕堆排序]

```
    1.什么是堆, 堆是用数组实现得二叉树，同时不需要像树一样存在父节点和子节点得指针。 根据堆属性来排序，
    堆属性决定树中节点得位置。

    2.堆 常用得用途： 构造优先队列, 堆排序, 在数组中快速找出最大值或者最小值。

    3. 堆属性： 最大堆，指的是父节点的值都比子节点的值要大。 最小堆，父节点的值比子节点的值都要小。

    4. 没有指针，怎么找到父节点或者子节点。
    子节点： 2*i+1 , 2*i + 2
    父节点： (i -1)/2
    5. 堆属性被破坏后,需要上浮或者下沉来重新调整堆属性。

    6. 堆排序的原理是啥？？
    每次堆顶都能拿到最大(递增)，或者最小(降序)
    的值和末尾元素交换后，再调整堆
        - 构建堆
        - 交换顺序，调整堆
        - 完成

    堆排序：

    public class HeapSort {

    public static void heapSort(int[] arr) {
        if (arr.length < 2) return;

        // 1.构建堆
        for(int i = arr.length/2 -1;i >= 0;i --) {
            // 从第一个非叶子节点从下至上，从右到左调整顺序
            adjustHeap(arr, i, arr.length);
        }

        // 2.调整堆结构+ 交换堆顶元素与末尾元素
        for(int j = arr.length -1; j >0;j--){
            swap(arr, 0, j); // 堆顶与末尾元素交换
            adjustHeap(arr, 0, j); //重新调整堆
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp=arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
        调整堆这个方法特别重要
     */
    private static void adjustHeap(int[] arr, int i, int length) {
        // 其实就是从i 这个节点交换下去
        int index = i;
        int temp = arr[index];

        for(int j = index * 2 + 1;j < length; j= (index *2) + 1) {

            int k = j;
            if(k + 1 < length && arr[k + 1] > arr[j]) {
                k++;
            }
            // 大顶堆
            if (arr[index] <= arr[k]){
                arr[index] = arr[k];
                index = k;
            } else {
                break;
            }
        }
        arr[index] = temp;
    }

    public static void main(String[] args) {

        int[] nums = {6,2 ,4, 5, 1, 2, 10};

        heapSort(nums);
        for(int a: nums) {
            System.out.print(a);
            System.out.print(" ");
        }
    }


```

### [手撕LRU]
```
    什么是 LRU Cache 策略??
    Least Recently Used 最近最少使用，常用的页面置换算法。
    
    简单的 LRU cache 写法，直接使用LinkedHashMap即可。
    底层是 双向链表

    class LRUCache {

    int capacity;
    LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<Integer, Integer> (capacity, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return cache.size() > capacity;
            }
        };
    }
    
    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        cache.put(key, value);
    }
}
    LinkedHashMap 源码
    afterNodeRemoval 这个在Map删除元素之后，如何优美从双向链接删除某个元素
    1. 保存，当前元素引用
    当前元素父引用，子引用。
    2. 判断父引用是否为空，
    是 head=当前元素子引用，否则，
    当前元素父引用的.after = 当前元素子引用
    3. 尾巴节点判断
    判断尾巴节点是否为空，
    是空，tail = 当前元素的父元素,否则，
     当前元素的子元素.before = 当前的元素的父元素 

    afterNodeInsertion  在节点插入之后,判断需要是否移除一直不用的某些元素
    其实找到双向链表的head， 当head 不为空removeEldestEntry 方法被调用    
    再去hashMap 调用删除 head.key 值

    afterNodeAccess  在节点被访问之后，如何优美将指定元素放到双向链表的尾部
    // 情况分为3种。
    // 1种是 当前元素 是队头
    // 2  当前元素在队中
    // 3  双向队列是空
        <b> - <p>  -<a>
       p.after =  null;

        // 处理队头
       if (b == null) {
           head = a;
       } else {
           b.after = a;
       }
        // 处理队尾
       if (a != null) {
           a.before = b;
       } else {
           last = b; // ***
       }
       if (last = null) {  // 啥意思
           head = p;
       } else {
           p.before = last;
           last.after = p;
       }
       tail = p;

    // 4  当前元素直接就在队尾
        直接在队尾不做任何操作


    另外一种解法：  底层双向链表
    需要解决什么问题： 
        当数组容量不够的时候，需要去除掉，
        最近最少使用的元素。
        怎么样合理的设计数据结构才能完成。
    
    




```

### [消费生产者模式]
```package lc;

import java.util.ArrayList;
import java.util.List;

class Storage {
    private static int MAX_VALUE = 100;
    private List<Object> list = new ArrayList<>();
    public void produce(int num) {
        synchronized (list) {
            while (list.size() + num > MAX_VALUE) {
                System.out.println("暂时不能执行生产任务");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            System.out.println("已生产产品数"+num+" 仓库容量"+list.size());
            list.notifyAll();
        }
    }

    public void consume(int num) {
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("暂时不能执行消费任务");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {
                list.remove(0);
            }
            System.out.println("已消费产品数"+num+" 仓库容量" + list.size());
            list.notifyAll();
        }
    }
}

 class Producer extends Thread {
    private int num;
    private Storage storage;
    public Producer(Storage storage) {
        this.storage = storage;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void run() {
        storage.produce(this.num);
    }
}

 class Customer extends Thread {
    private int num;
    private Storage storage;
    public Customer(Storage storage) {
        this.storage = storage;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void run() {
        storage.consume(this.num);
    }
}



public class Test {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Producer p1 = new Producer(storage);
        Producer p2 = new Producer(storage);
        Producer p3 = new Producer(storage);
        Producer p4 = new Producer(storage);
        Customer c1 = new Customer(storage);
        Customer c2 = new Customer(storage);
        Customer c3 = new Customer(storage);
        p1.setNum(10);
        p2.setNum(20);
        p3.setNum(80);
        c1.setNum(50);
        c2.setNum(20);
        c3.setNum(20);
        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
    }
}


/* 自己写得 */
package lc;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class ThreadTest {

     static int max = 100;
     static Queue<Integer> temp = new LinkedList<>();
     static Object lock = new Object();
     static Random random = new Random();
    /*
        生产者模式
        task: 生产货物
     */
     static class Producer implements Runnable{

        @Override
        public void run() {
            while(true) {
               synchronized (lock) {
                  while(temp.size() > max) {
                      try {
                          lock.wait();
                      }catch (Exception e){

                      }
                  }
                  for(int i = 0;i < max;i++) {
                      System.out.println(Thread.currentThread().getName()+"生产者生产了: " + temp.add(random.nextInt()));
                  }
                  lock.notifyAll();
               }
            }
        }
    }

    /*
        消费者
        task: 消费货物
     */
    static class Consumer implements Runnable {


        @Override
        public void run() {
            while(true) {
                synchronized (lock) {
                    while (temp.size() < max) {
                        try{
                           lock.wait();
                        } catch (Exception e){

                        }
                    }
                    for(int i = 0;i < max;i++ ) {
                        System.out.println(Thread.currentThread().getName() + "消费者 消费了：" + temp.poll());
                    }
                    lock.notifyAll();
                }
            }
        }
    }


    public static void main(String[] args) {
        ThreadTest lc = new ThreadTest();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread produceThread = new Thread(producer);
       // Thread produceThread2 = new Thread(producer);
        //Thread produceThread3 = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        produceThread.start();
        //produceThread2.start();
        //produceThread3.start();
        consumerThread.start();
    }

    总结：
        //  主要是 生产者满了，阻塞，不生产
        //  消费者 小于固定值，阻塞，不消费 
}

```

### [手写多线程交替]

``` 
    题目： 三个线程分别打印 ABC
    可以利用
    条件队列 Condition,每次只会唤醒 
    特定得线程，从而实现  固定顺序的线程的打印工作

    普通的线程, 只会每次固定 线程池阻塞队列，唤醒
    全部的线程，但是 有没有一种方式，能唤醒部分线程，答案就是
    Condition

    package lc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CharPrinter implements Runnable {

    static int printNumbers = 10;
    private final ReentrantLock lock;
    private final Condition thisCondition;
    private final Condition nextCondition;
    private final  char printChar;

    public CharPrinter(ReentrantLock lock, Condition a, Condition b,
                       char c) {
        this.lock = lock;
        this.thisCondition = a;
        this.nextCondition = b;
        this.printChar = c;
    }


    @Override
    public void run() {

        // 为啥在外面
        // for
        lock.lock();

        try{
            for(int i = 0;i < printNumbers; i ++) {

                System.out.print(printChar);
                // 唤醒
                // 且Condition 条件队列只有一个值
                nextCondition.signal();
                // 为啥for 循环在里面
                // 因为这里就释放锁了
                //  thisCondition.await(); 直接会死锁
                //
                if (i < printNumbers - 1) {
                    try {
                        // 本线程让出锁并等待唤醒
                        thisCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                

//                try {
//                    thisCondition.wait();    // 这个for 循环,最后不干的时候，也塞了一个 线程到 condition
//                    System.out.println(e.getMessage());
//                }
            }
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();

        CharPrinter aPrinter = new CharPrinter(lock, a, b, 'A');
        CharPrinter bPrinter = new CharPrinter(lock, b, c, 'B');
        CharPrinter cPrinter = new CharPrinter(lock, c, a, 'C');


        Thread athread = new Thread(aPrinter);
        Thread bthread = new Thread(bPrinter);
        Thread cthread = new Thread(cPrinter);

        athread.start();
        Thread.sleep(100);
        bthread.start();
        Thread.sleep(100);
        cthread.start();
    }

}
```

### [交替打印foobar]

```
    输入一个n 打印次数
    交替打印 foo bar n 次

     

```

### [手写阻塞队列]
```
    什么是阻塞队列？？
    阻塞队列相比普通 的队列
    它的插入和 获取是不同的。
    当一个线程往里塞东西时, 队列满了就阻塞当前线程
    当不满，线程就能继续塞
    当一个线程取东西时，队列空了就阻塞当前线程。
    当不空就能继续取

    

```


### [手写线程池]
```
        手写一个简易的线程池其实不是特别难

        主要是 , 模拟JDK 线程池的实现
        Set<WorkThread>的所有引用,进行管理
        BlockingQueue<Runnable> 队列存储任务
        主要是 还没做阻塞策略
        每一个工作线程，都被阻塞队列所阻塞了。
        任务队列没任务就阻塞不做任务


        package lc.threadpool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPoolTwon implements Executors{

    private static final int WORK_NUM = 5;
    private static final int TASK_NUM = 100;

    private int worknums;
    private int tasknum;

    // 管理所有线程的引用
    private final Set<WorkThread> workThreadSet;
    // 任务队列
    private final BlockingQueue<Runnable> taskQueue;

    public ThreadPoolTwon(){
        this(WORK_NUM, TASK_NUM);
    }

    public ThreadPoolTwon(int worknums,int tasknum) {
        if (worknums < 0) this.worknums = WORK_NUM;
        if (tasknum < 0) this.tasknum = TASK_NUM;

        taskQueue = new ArrayBlockingQueue<>(tasknum);
        this.worknums = worknums;
        this.tasknum = tasknum;

        workThreadSet = new HashSet<>();
        // 启动一定线程数量
        for(int i = 0; i < worknums;i ++) {
          WorkThread workThread = new WorkThread("thread_" + i);
          workThread.start();
          workThreadSet.add(workThread);
        }
    }

    @Override
    public void execute(Runnable task) {
        try{
            taskQueue.put(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void destroy(){
        System.out.println("准备销毁线程池");
        if (workThreadSet == null || workThreadSet.isEmpty()) return;
        for(WorkThread work:workThreadSet) {
            work.stopWork();
            work = null;
        }
        workThreadSet.clear();
    }


    private class WorkThread extends Thread{

        public WorkThread(String str) {
            super();
            setName(str);
        }

        /*
            线程池的实现
         */
        @Override
        public void run() {
            while(!interrupted()) {
                try{
                    Runnable runnable = taskQueue.take();
                    if(runnable != null){
                        System.out.println();
                        runnable.run();
                    }
                    runnable = null;

                } catch (Exception e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
         }

         public void stopWork(){
            interrupt();
         }

    }
}

```

### [lru]

手写Lru cache 算法

什么是lru 算法，Least Recently Use,最近最少使用算法，是一种页面置换算法。

当发生缺页中断时，将最久使用次数不频繁的置换出去。

实现原理：

链表+ Map

`

​	O(1) 时间复杂度完成，插入和get ，所以需要维护一个Map

​	为啥是 双向链表，单链表不行吗？？？？

​	 是因为通过 Map 去删除链表的时候，需要O（1）的时间复杂度去找到删除节点的前驱节点。

​    所以双向链表才是选择

​	为啥 链表存Key? 需要删除Map 里的数据。

​		

**双向链表先放入一个头尾节点，巨他妈方便操作**

**单向链表放头节点，非常方便操作**

​	**双向链表通用删除操作**：

​	  pre = node.pre;

​	  next = node.next;

​	  pre.next = next;

​	  next.pre = pre;

​     不用管当前删除节点的 left，right；

​	

​	**双向链表的头擦法：**

​	通用的从头节点开始擦，其实如果，其他的插入方法，

​	传入，  insert(node, 需要插入节点之前的节点)

​	 next = pre.next;

​	pre.next = node; //  插入的时候4个方向都得维护。

​	next.pre = node;//

   node.pre = pre; //

  node.next = next; //



​	



   class LRUCache {



​    class Node{

​        

​        int k,v;

​        Node left,right;



​        public Node(int k,int v, Node l,Node r) {

​            this.k = k;

​            this.v = v;

​            this.left = l;

​            this.right = r;

​        }



​    }

​    int capacity;

​    Node head;

​    Node tail;

​    Map<Integer,Node> map;



​    public LRUCache(int capacity) {

​        this.capacity = capacity;

​        this.map = new HashMap<>(capacity);

​        this.head = new Node(-1, -1, null, null);

​        this.tail = new Node(-1, -1, null, null);

​        this.head.right = tail;

​        this.tail.left  = this.head;

​    }

​    

​    public int get(int key) {



​        if (map.containsKey(key)) {

​            Node node = map.get(key);

​            //delete(node.left);

​            refresh(node);

​            return node.v;

​        }

​        return -1;

​    }

​    

​    public void put(int key, int value) {

​        // Node node = new Node(key, value, null, null);

​        // if (map.containsKey(key)) {

​            

​        //     map.put(key, node);

​        //     refresh(node);

​        //     return;

​        // }



​        

​        // if (map.size() >= this.capacity) {

​        //     // 删除最近最久没用的

​        //     delete(this.tail.left);

​        //     map.remove(key);



​        //     map.put(key, node);

​        //     refresh(node);

​        // } else {

​        //     // 未满，插入这个节点

​        //     map.put(key, node);

​        //     refresh(node);

​        //     this.capacity++;

​        // }

​        Node node = null;

​        if (map.containsKey(key)) {

​            node = map.get(key);

​            node.v = value;

​        } else {

​            if (map.size() == capacity) {

​                Node del = tail.left;

​                map.remove(del.k);

​                delete(del);

​            }

​            node = new Node(key, value, null, null);

​            map.put(key, node);

​        }

​        refresh(node);



​    }   



​    public void delete(Node node) {



​        // 删除双向链表的节点的前驱节点

​        // 因为这个元素，是最近最久未被使用的需要删除



​        if (node.left != null){

​            Node l = node.left;

​            Node r = node.right;

​            l.right = r;

​            r.left = l;

​        }

​    }



​    public void refresh(Node node) {

​        delete(node);

​        // 插入双向链表的头

​        Node next = this.head.right;

​        this.head.right = node;

​        next.left = node;

​        node.left = this.head;

​        node.right = next;

​    }

}



​	总结：  

- 插入和删除不太熟悉
- 整个算法逻辑，自己不太熟悉
- 其实不是很难。



算法逻辑：

​		插入，  

​		判断  map 存在吗？

​		存在，  更新，且刷新key



​		不存在，要插入，就要判断是否map 已满 ，

​		未满，直接插入，

​		已满，删除，再插入。



​		查找，当map 不存在就返回-1

​		存在，就将node 移动队头

`











w822612726


// 待完成题目，  Number01  递归备忘录写法
// leetcode 300  的二分优化写法
// 启发比较大的是 leetcode 801
// 背包写法的  0-1 和完全背包 ，还没融会贯通呢
// 131 还有动态规划写法 
// leetcode 10 正则表达式和 KMP 的部分匹配表 有相似处
// leetcode 10 还有dp 写法

