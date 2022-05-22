package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:18:12
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试五
 * 获取节点数据
 * ========================================================
 * 修订日期     修订人    描述
 */
public class GetNodeData {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		Stat stat = new Stat();
		byte[] data = zkClient.getData().storingStatIn(stat).forPath("/node1");
		System.out.println(new String(data));
		System.out.println(stat);
	}
	
}
