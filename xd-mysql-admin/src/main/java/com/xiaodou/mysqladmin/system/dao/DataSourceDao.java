package com.xiaodou.mysqladmin.system.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.mysqladmin.system.entity.DataSource;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.mysqladmin.system.dao.DataSourceDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月20日
 * @description 数据源实体操作Dao
 * @version 1.0
 */
@Repository
public class DataSourceDao extends MongoBaseDao<DataSource> {

  private Map<String, DataSource> dataSourceMap = Maps.newConcurrentMap();

  public DataSource getDataSource(String dataSourceId) {
    if (dataSourceMap.containsKey(dataSourceId)) {
      return dataSourceMap.get(dataSourceId);
    }
    DataSource ds = new DataSource();
    ds.setSid(dataSourceId);
    DataSource entity = findEntityById(ds);
    if (null != entity) {
      dataSourceMap.put(entity.getSid(), entity);
    }
    return entity;
  }

  @Override
  public DataSource addEntity(DataSource entity) {
    dataSourceMap.put(entity.getSid(), entity);
    return super.addEntity(entity);
  }

  @Override
  public boolean updateEntityById(DataSource entity) {
    dataSourceMap.put(entity.getSid(), entity);
    return super.updateEntityById(entity);
  }

}
