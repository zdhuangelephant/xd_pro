package com.xiaodou.summer.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * @name @see com.xiaodou.summer.dao.mongo.BaseDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月22日
 * @description 自动注入数据源
 * @version 1.0
 * @param <Entity>
 */
public class MongoBaseDao<Entity> extends ExtendMongoBaseDao<Entity> {

  @Autowired(required = false)
  public final void setSummerMongoFactory(
      @Qualifier("summerMongoDbFactory") MongoDbFactory mongoFactory) throws Exception {
    super.setSummerMongoFactory(mongoFactory);
  }

}
