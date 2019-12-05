package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@Repository("asyncMessageMapper")
public class AsyncMessageMapper extends ExtendBaseDao<AsyncMessage> {

  @Override
  @Autowired(required = false)
  public void setSummerSqlSessionFactory(
      @Qualifier("asyncMessageSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }

  public Integer ignoreUnReadMessage(String uid) {
    return getSqlSession().update("AsyncMessage.ignoreUnReadMessage", uid);
  }

  public Integer countAsyncMessage(Map<String, Object> message) {
    return (Integer) getSqlSession().selectOne("AsyncMessage.countAsyncMessage", message);
  }

  @SuppressWarnings("unchecked")
  public List<AsyncMessage> queryList(Map<String, Object> message) {
    return getSqlSession().selectList("AsyncMessage.queryList", message);
  }

  public AsyncMessage queryOneById(String id) {
    return (AsyncMessage) getSqlSession().selectOne("AsyncMessage.queryOneById", id);
  }

  public Integer updateList(Map<String, Object> input, Map<String, Object> value) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.putAll(value);
    cond.put("input", input);
    return getSqlSession().update("AsyncMessage.updateList", cond);
  }

  /**
   * @Title: count
   * @Description: 根据条件 查询数量
   * @param queryCond 查询条件
   * @return int
   */
  public Integer count(Map<String, Object> queryCond) {
    Map<String, Map<String, Object>> mapParam = new HashMap<String, Map<String, Object>>();
    mapParam.put("input", queryCond);
    return (Integer) getSqlSession().selectOne(this.getEntityClass().getSimpleName() + ".count",
        mapParam);
  }

}
