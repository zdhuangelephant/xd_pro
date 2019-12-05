package com.xiaodou.dao;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.dao.product.QuesbkExamRuleDao;

public class QuesbkExamRuleDaoTest extends BaseUnitils{
  @SpringBean("quesbkExamRuleDao")
  QuesbkExamRuleDao quesbkExamRuleDao;
  
  @Test
  public void selectByProductIdAndExamType() {
    try {
      System.out.println(quesbkExamRuleDao.selectByProductIdAndExamType("744321601","50"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
}
