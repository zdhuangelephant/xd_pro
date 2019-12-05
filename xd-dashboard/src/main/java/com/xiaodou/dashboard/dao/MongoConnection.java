package com.xiaodou.dashboard.dao;

import java.net.UnknownHostException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * @name @see com.xiaodou.summer.dao.mongo.MongoConnection.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description mongo链接管理类
 * @version 1.0
 */
public class MongoConnection {
  private MongoClient mongoClient;
  private DB db;
  private MongoDbCollection dbCollection;
  private static final Map<String, MongoConnection> mongoConnection = Maps.newHashMap();
  private static Object lock = new Object();

  /**
   * 
   * initMongoDB
   * 
   * @Title: initMongoDB
   * @Description: TODO 获取一个mongodb的连接
   * @return
   * @throws UnknownHostException
   */
  private static MongoConnection getDbConn(String dbName) throws UnknownHostException {
    if (mongoConnection.containsKey(dbName)) return mongoConnection.get(dbName);
    String host = MongoProp.getParams("mongo.host");
    Integer port = MongoProp.getInt("mongo.port");
    synchronized (lock) {
      if (!mongoConnection.containsKey(dbName)) {
        MongoConnection mongoConn = new MongoConnection();
        MongoClient mongoClient = new MongoClient(host, port);
        mongoConn.mongoClient = mongoClient;
        mongoConn.db = mongoClient.getDB(dbName);
        mongoConnection.put(dbName, mongoConn);
      }
    }
    return mongoConnection.get(dbName);
  }

  /**
   * @param dbName
   * @param column
   * @return
   * @throws UnknownHostException
   */
  public static MongoDbCollection getDbConn(String dbName, String column)
      throws UnknownHostException {
    MongoConnection connection = getDbConn(dbName);
    if (null == connection.dbCollection || !column.equals(connection.dbCollection.key)) {
      connection.dbCollection = new MongoDbCollection(column, connection.db.getCollection(column));
    }
    return connection.dbCollection;
  }

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public DB getDb() {
    return db;
  }

  public void closeConnection() {
    mongoClient.close();
  }

  public static void closeAllConnection() {
    synchronized (lock) {
      for (MongoConnection conn : mongoConnection.values()) {
        conn.mongoClient.close();
      }
      mongoConnection.clear();
    }
  }

  public static class MongoDbCollection {
    private String key;
    private DBCollection dbCollection;

    public MongoDbCollection(String key, DBCollection dbCollection) {
      this.key = key;
      this.dbCollection = dbCollection;
    }

    public String getKey() {
      return key;
    }

    public DBCollection getDbCollection() {
      return dbCollection;
    }

  }

}
