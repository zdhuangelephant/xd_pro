package com.xiaodou.summer.support;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import com.xiaodou.summer.dao.OperationType;
import com.xiaodou.summer.dao.OperationTypeWrapper;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

public abstract class SummerDaoSupport extends DaoSupport {

  // 多数据源扩展
  private SqlSession sqlSession;

  private SqlSession rSqlSession;

  private ThreadLocal<SqlSession> realSession = new ThreadLocal<SqlSession>();

  private boolean externalSqlSession;

  private void setRealSession(OperationType operation) {
    if (null != operation) {
      switch (operation) {
        case READ:
          realSession.set(rSqlSession);
          break;
        case WRITE:
          realSession.set(sqlSession);
          break;
        case READWRITE:
          realSession.set(sqlSession);
          break;
        default:
          realSession.set(sqlSession);
          break;
      }
    } else {
      realSession.set(sqlSession);
    }
  }

  public void setSummerSqlSessionFactory(SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    if (!this.externalSqlSession) {
      this.sqlSession = new SqlSessionTemplate(sqlSessionFactory.getMObject());
      this.rSqlSession = new SqlSessionTemplate(sqlSessionFactory.getSObject());
      this.realSession.set(sqlSession);
    }
  }

  /**
   * Users should use this method to get a SqlSession to call its statement methods This is
   * SqlSession is managed by spring. Users should not commit/rollback/close it because it will be
   * automatically done.
   * @return Spring managed thread safe SqlSession
   */
  public final SqlSession getSqlSession() {
    setRealSession(OperationTypeWrapper.getWrapper().getValue());
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
