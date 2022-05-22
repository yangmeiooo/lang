package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午1:52:28
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试二
 * 创建节点
 * ========================================================
 * 修订日期     修订人    描述
 */
public class CreateNode {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		String path = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/node1/data2", "tom".getBytes());
		System.out.println(path);
	}
	
}
