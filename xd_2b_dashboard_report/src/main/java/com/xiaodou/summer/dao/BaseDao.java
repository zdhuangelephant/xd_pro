package com.xiaodou.summer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.googlecode.mjorm.annotations.Entity;
import com.xiaodou.summer.dao.datasource.SummerSqlSessionFactory;

/**
 * @name @see com.xiaodou.summer.dao.BaseDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月11日
 * @description 自动注入数据源
 * @version 1.0
 * @param <Entity>
 */
@SuppressWarnings("hiding")
public abstract class BaseDao<Entity> extends ExtendBaseDao<Entity> {

  @Autowired(required = false)
  public final void setSummerSqlSessionFactory(
      @Qualifier("summerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }

}
