package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:49:08
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试八
 * 监听节点数据变化
 * ========================================================
 * 修订日期     修订人    描述
 */
public class ListenNodeChange {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		final NodeCache nodeCache = new NodeCache(zkClient, "/node1");
		nodeCache.start();
		
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			
			public void nodeChanged() throws Exception {
				byte[] data = nodeCache.getCurrentData().getData();
				System.out.println("new data: " + new String(data));
			}
		});
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
