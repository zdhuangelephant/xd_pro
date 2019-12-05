package com.xiaodou.mysqladmin.system.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.mysqladmin.system.entity.SearchHistory;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.mysqladmin.system.dao.SerachHistoryMongoDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月18日
 * @description 用户搜索历史操作Dao
 * @version 1.0
 */
@Repository
public class SearchHistoryMongoDao extends MongoBaseDao<SearchHistory> {
  public SearchHistory saveSearchHistory(String name, String sql, String dbName, String userId) {
    SearchHistory entity = new SearchHistory();
    entity.setRId(UUID.randomUUID().toString());
    entity.setUserId(userId);
    entity.setUserName(name);
    entity.setDbName(dbName);
    entity.setSqls(sql);
    entity.setCreateTimestamp(new Timestamp(System.currentTimeMillis()).toString());
    return addEntity(entity);
  }

  public List<SearchHistory> selectSearchHistory() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(SearchHistory.class));
    Page<SearchHistory> searchHistoryPage = findEntityListByCond(param, null);
    if (null == searchHistoryPage || null == searchHistoryPage.getResult()
        || searchHistoryPage.getResult().isEmpty()) {
      return null;
    }
    return searchHistoryPage.getResult();
  }
}
