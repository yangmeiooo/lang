package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:10:53
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试四
 * 更新节点数据
 * ========================================================
 * 修订日期     修订人    描述
 */
public class UpdateNodeData {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		Stat stat = new Stat();
		zkClient.getData().storingStatIn(stat).forPath("/node1");
		zkClient.setData().withVersion(stat.getVersion()).forPath("/node1", "jack".getBytes());
	}
	
}
