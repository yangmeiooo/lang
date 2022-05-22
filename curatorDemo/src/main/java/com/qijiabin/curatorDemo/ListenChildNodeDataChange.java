package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:54:34
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试九
 * 监听子节点数据变化
 * ========================================================
 * 修订日期     修订人    描述
 */
public class ListenChildNodeDataChange {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		final PathChildrenCache cache = new PathChildrenCache(zkClient, "/node1", true);
		cache.start();
		
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("添加子节点:"+event.getData());
					break;
				case CHILD_UPDATED:
					System.out.println("更新子节点:"+event.getData());
					break;
				case CHILD_REMOVED:
					System.out.println("删除子节点:"+event.getData());
					break;
				default:
					break;
				}
			}
		});
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
