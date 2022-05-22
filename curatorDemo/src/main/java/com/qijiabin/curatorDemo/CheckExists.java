package com.qijiabin.curatorDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午2:28:17
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试七
 * 校验节点数据
 * ========================================================
 * 修订日期     修订人    描述
 */
public class CheckExists {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryUntilElapsed(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", retryPolicy);
		zkClient.start();
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		zkClient.checkExists().inBackground(new BackgroundCallback() {
			
			public void processResult(CuratorFramework zk, CuratorEvent event) throws Exception {
				Stat stat = event.getStat();
				System.out.println(stat);
				System.out.println(event.getContext());
			}
		}, "tom", service).forPath("/node1");
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
