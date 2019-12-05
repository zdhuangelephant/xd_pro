package com.xiaodou.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.dao.product.FinalExamDao;
import com.xiaodou.domain.product.FinalExamModel;

public class FinalExamDaoTest extends BaseUnitils {
  @SpringBean("finalExamDao")
  FinalExamDao finalExamDao;
  @Test
  public void selectFinalExamByCond() {
    List<FinalExamModel> lists = finalExamDao.selectFinalExamByCond("741451154", "1352");
    System.out.println(lists);
  }
  
  @Test
  public void selectByPrimaryKey() {
    System.out.println("===========");
    try {
      System.out.println(finalExamDao.selectByPrimaryKey(85460851L));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
