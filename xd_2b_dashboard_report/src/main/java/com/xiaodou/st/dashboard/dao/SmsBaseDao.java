package com.xiaodou.st.dashboard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.ExtendBaseDao;
import com.xiaodou.summer.dao.datasource.SummerSqlSessionFactory;

public class SmsBaseDao<Entity> extends ExtendBaseDao<Entity> {

  @Autowired(required = false)
  public final void setSummerSqlSessionFactory(
      @Qualifier("smsSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }

}
