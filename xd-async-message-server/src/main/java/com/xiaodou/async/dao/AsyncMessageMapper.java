package com.xiaodou.async.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.summer.dao.ExtendBaseDao;
import com.xiaodou.summer.dao.datasource.SummerSqlSessionFactory;

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

}
