package com.xiaodou.summer.support;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;
import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;

public abstract class SummerDaoSupport extends DaoSupport {

  private SqlSession defaultSqlSession;
  // 多数据源扩展
  private Map<String, SqlSession> sqlSessionHolder = Maps.newConcurrentMap();

  private ThreadLocal<SqlSession> realSession = new ThreadLocal<SqlSession>();

  private boolean externalSqlSession;

  private void setRealSession(String operation) {
    if (StringUtils.isBlank(operation)) {
      realSession.set(defaultSqlSession);
    } else {
      SqlSession sqlSession = sqlSessionHolder.get(operation);
      if (null == sqlSession) {
        realSession.set(defaultSqlSession);
      } else {
        realSession.set(sqlSession);
      }
    }
  }

  public void setSummerSqlSessionFactory(SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    if (!this.externalSqlSession) {
      SqlSession sqlSession =
          this.defaultSqlSession = new SqlSessionTemplate(sqlSessionFactory.getDefaultObject());
      if (!CollectionUtils.isEmpty(sqlSessionFactory.getOthersObject())) {
        for (Entry<String, SqlSessionFactory> entry : sqlSessionFactory.getOthersObject()
            .entrySet()) {
          sqlSessionHolder.put(entry.getKey(), new SqlSessionTemplate(entry.getValue()));
        }
        if (null != RealSqlSessionKeyHolder.getHolder().getRealSqlSessionKey()) {
          sqlSession =
              sqlSessionHolder.get(RealSqlSessionKeyHolder.getHolder().getRealSqlSessionKey());
          if (null == sqlSession) {
            LoggerUtil.error("未找到对应的sqlSession！", null);
            throw new IllegalArgumentException("未找到对应的sqlSession！");
          }
        }
      }
      this.realSession.set(sqlSession);
    }
  }

  /**
   * Users should use this method to get a SqlSession to call its statement methods This is
   * SqlSession is managed by spring. Users should not commit/rollback/close it because it will be
   * automatically done.
   * 
   * @return Spring managed thread safe SqlSession
   */
  public final SqlSession getSqlSession() {
    setRealSession(RealSqlSessionKeyHolder.getHolder().getRealSqlSessionKey());
    return this.realSession.get();
  }

  /**
   * {@inheritDoc}
   */
  protected void checkDaoConfig() {
    Assert.notNull(this.realSession.get(),
        "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
  }

}
