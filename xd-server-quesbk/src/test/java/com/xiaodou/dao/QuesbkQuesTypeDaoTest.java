package com.xiaodou.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.product.QuesbkQuesTypeDao;

public class QuesbkQuesTypeDaoTest extends BaseUnitils {
  @SpringBean("quesbkQuesTypeDao")
  QuesbkQuesTypeDao quesbkQuesTypeDao;
  
  
  
  @Test
  public void selectByPrimaryKey() {
    try {
      System.out.println(quesbkQuesTypeDao.selectByPrimaryKey("7"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void selectQuesType() {
    try {
      System.out.println(FastJsonUtil.toJson(quesbkQuesTypeDao.selectQuesType()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
