package com.firecode.mongodbtest.async.util;

import java.util.logging.Level;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;

import com.firecode.mongodbtest.domain.User;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

/**
 * Reactive异步驱动，连接测试
 * 
 * @author JIANG
 */
public class TestAsyncConnection {

	protected MongoClient client;
	protected MongoDatabase database;
	protected MongoCollection<Document> mapCollection;
	protected MongoCollection<User> userCollection;

	@Before
	public void init() {
		// 关闭日志
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		this.client = MongoClients.create("mongodb://127.0.0.1");
		//this.client = MongoClients.create(new ConnectionString("mongodb://127.0.0.1"));
		this.database = client.getDatabase("test_test");
		// 创建集合（数据表）
		//this.database.createCollection("test_map").subscribe(new PrintSubscriber<Success>("Database Names: %s"));
		this.mapCollection = database.getCollection("test_map");
		this.userCollection = database.getCollection("test_user", User.class);
	}

	@After
	public void close() {
		client.close();
	}
}
