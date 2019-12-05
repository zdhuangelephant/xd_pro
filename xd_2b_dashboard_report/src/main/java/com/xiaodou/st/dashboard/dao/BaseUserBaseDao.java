package com.xiaodou.st.dashboard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.ExtendBaseDao;
import com.xiaodou.summer.dao.datasource.SummerSqlSessionFactory;

public class BaseUserBaseDao <Entity> extends ExtendBaseDao<Entity> {

  @Autowired(required = false)
  public final void setSummerSqlSessionFactory(
      @Qualifier("baseUserSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }
  
}
