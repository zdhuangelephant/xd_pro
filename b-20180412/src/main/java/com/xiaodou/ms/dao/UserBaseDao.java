package com.xiaodou.ms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

public class UserBaseDao<Entity> extends ExtendBaseDao<Entity>{
  @Autowired(required = false)
  public final void setSummerSqlSessionFactory(
      @Qualifier("userBaseSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }
}
