package com.qijiabin.curatorDemo;


import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * ========================================================
 * 日 期：2016年4月11日 下午1:58:47
 * 作 者：jiabin.qi
 * 版 本：1.0.0
 * 类说明：测试三
 * 创建节点及权限
 * ========================================================
 * 修订日期     修订人    描述
 */
public class CreateNodeAuth {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		CuratorFramework zkClient = CuratorFrameworkFactory
				.builder()
				.connectString("192.168.1.66:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000)
				.retryPolicy(retryPolicy)
				.authorization("digest", "node1:123456".getBytes())//授权
				.build();
		zkClient.start();
		
		
		//ACL认证
		List<ACL> acls = new ArrayList<ACL>();
		ACL aclIp = new ACL(Perms.READ, new Id("ip", "192.168.1.87"));
		ACL aclDigest = new ACL(Perms.READ|Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("node1:123456")));
		acls.add(aclIp);
		acls.add(aclDigest);
		
		String path = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.withACL(acls).forPath("/node1/data3", "hello".getBytes());
		System.out.println(path);
		
		byte[] data = zkClient.getData().forPath("/node1/data3");
		System.out.println(new String(data));
	}
	
}
