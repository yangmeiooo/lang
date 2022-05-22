package com.qijiabin.curatorDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午12:21:17
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试一
 * 建立与服务端的连接
 * ========================================================
 * 修订日期     修订人    描述
 */
public class CreateSession {

	public static void main(String[] args) {
//		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
//		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
		
//		CuratorFramework zkClient = CuratorFrameworkFactory.newClient("192.168.1.66:2181", 
//				5000, 5000, retryPolicy);
		CuratorFramework zkClient = CuratorFrameworkFactory
									.builder()
									.connectString("192.168.1.66:2181")
									.sessionTimeoutMs(5000)
									.connectionTimeoutMs(5000)
									.retryPolicy(retryPolicy)
									.build();
		
		zkClient.start();
		System.out.println("connect ok!");
	}
	
}
