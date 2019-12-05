package com.xiaodou.dashboard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.MongoDbFactory;

import com.googlecode.mjorm.annotations.Entity;
import com.xiaodou.summer.dao.mongo.ExtendMongoBaseDao;

@SuppressWarnings({"hiding"})
public class LogMonitorBaseDao<Entity> extends ExtendMongoBaseDao<Entity> {

  @Autowired(required = false)
  public final void setSummerMongoFactory(
      @Qualifier("logMonitorMongoDbFactory") MongoDbFactory mongoFactory) throws Exception {
    super.setSummerMongoFactory(mongoFactory);
  }

}
