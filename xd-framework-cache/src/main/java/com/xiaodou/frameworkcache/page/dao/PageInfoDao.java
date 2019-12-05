package com.xiaodou.frameworkcache.page.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.xiaodou.frameworkcache.page.model.PageInfo;
import com.xiaodou.summer.dao.mongo.ExtendMongoBaseDao;

/**
 * @name @see com.xiaodou.frameworkcache.page.dao.PageInfoDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月7日
 * @description 页面静态化操作Dao
 * @version 1.0
 */
@Repository
public class PageInfoDao extends ExtendMongoBaseDao<PageInfo> {
  @Autowired(required = false)
  public final void setSummerMongoFactory(
      @Qualifier("fwCacheMongoDbFactory") MongoDbFactory mongoFactory) throws Exception {
    super.setSummerMongoFactory(mongoFactory);
  }
}
