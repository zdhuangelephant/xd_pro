package com.xiaodou.mysqladmin.system.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiaodou.mysqladmin.system.entity.Admin;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@Repository("adminDao")
public class AdminDao extends ExtendBaseDao<Admin> {

  @Autowired(required = false)
  public final void setSummerSqlSessionFactory(
      @Qualifier("summerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }

}
