package com.firecode.mongodbtest.async;

import java.util.Arrays;

import org.bson.Document;
import org.junit.Test;

import com.firecode.mongodbtest.async.util.SubscriberHelpers.PrintSubscriber;
import com.firecode.mongodbtest.async.util.TestAsyncConnection;
import com.mongodb.reactivestreams.client.Success;

/**
 * Reactive异步数据插入简单使用
 * @author JIANG
 *
 */
public class TestReactiveInsert extends TestAsyncConnection {

	/**
	 * 插入单条数据
	 * @throws Throwable
	 */
	@Test
	public void insertOne() throws Throwable {
		// 回调实现
		PrintSubscriber<Success> successSubscriber = new PrintSubscriber<Success>("Database Names: %s");
		Document doc = new Document().append("_id", "1")
				                     .append("name", "maomao")
				                     .append("type", "database")
				                     .append("count", 1)
				                     .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
				                     .append("info", new Document("x", 203)
							         .append("y", 102));
	    this.mapCollection.insertOne(doc).subscribe(successSubscriber);
	    // 等待回调完成
	    successSubscriber.await();
	}
	
	/**
	 * 插多单条数据（注意：这种写入，是只会写入正确的文档，错误的文档将会失败）
	 * @throws Throwable
	 */
	@Test
	public void insertMany() throws Throwable {
		// 回调实现
		PrintSubscriber<Success> successSubscriber = new PrintSubscriber<Success>("Database Names: %s");
		Document doc1 = new Document().append("_id", "2")
				                     .append("name", "tiantian")
				                     .append("type", "database");
		
		Document doc2 = new Document().append("_id", "3")
                                      .append("name", "jiangjian")
                                      .append("type", "dsfsdfdsf");
		
		Document doc3 = new Document().append("_id", "3")
                                      .append("name", "asda")
                                      .append("type", "asda");
		this.mapCollection.insertMany(Arrays.asList(doc1,doc2,doc3)).subscribe(successSubscriber);	
		// 等待回调完成
	    successSubscriber.await();
	}

}
