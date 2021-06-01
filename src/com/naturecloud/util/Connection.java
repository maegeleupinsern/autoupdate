package com.naturecloud.util;


import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class Connection {
	private static ReadFile read=new ReadFile();
	private static final String HOST=read.readValue("mongo.host");
	private static final String dbName=read.readValue("mongo.dbName");
	private static final String port=read.readValue("mongo.port");
	private static final String COLLECTION=read.readValue("mongo.collection");
	private static final String id=read.readValue("mongo.id");
	private static final String pwd=read.readValue("mongo.pwd");
	private static Mongo mongo;
	private static DB db;
	private static DBCollection collection;
	static{
		try {
			mongo=new Mongo(HOST,new Integer(port));
			db=mongo.getDB(dbName);
			collection=db.getCollection(COLLECTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static List getServices(String username){
		db.authenticate(id, pwd.toCharArray());
		BasicDBObject query=new BasicDBObject();
		query.put("username", username);
		DBCursor cursor=collection.find(query);
		List list=new ArrayList();
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		return list;
	}
}
