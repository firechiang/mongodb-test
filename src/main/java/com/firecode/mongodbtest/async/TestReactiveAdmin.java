package com.firecode.mongodbtest.async;

import org.junit.Test;

import com.firecode.mongodbtest.async.util.SubscriberHelpers.PrintSubscriber;
import com.firecode.mongodbtest.async.util.TestAsyncConnection;

/**
 * Reactive异步获取管理相关信息
 * @author JIANG
 *
 */
public class TestReactiveAdmin extends TestAsyncConnection {

	/**
	 * 获取所有数据库
	 * @throws Throwable
	 */
	@Test
	public void database() throws Throwable {
		// 回调
		PrintSubscriber<String> printSubscriber = new PrintSubscriber<String>("Database Names: %s");
		client.listDatabaseNames().subscribe(printSubscriber);
		// 等待回调完成
	    printSubscriber.await();
	}
	
	/**
	 * 获取所有集合（数据表）
	 * @throws Throwable
	 */
	@Test
	public void collection() throws Throwable {
		// 回调
		PrintSubscriber<String> printSubscriber = new PrintSubscriber<String>("Database Names: %s");
		this.database.listCollectionNames().subscribe(printSubscriber);
		// 等待回调完成
		printSubscriber.await();
	}

}
