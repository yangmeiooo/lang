package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:59:44
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试十
 * 删除节点
 * ========================================================
 * 修订日期     修订人    描述
 */
public class DeleteNode {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		zkClient.delete().guaranteed().deletingChildrenIfNeeded().forPath("/node1/data1");
	}
	
}
