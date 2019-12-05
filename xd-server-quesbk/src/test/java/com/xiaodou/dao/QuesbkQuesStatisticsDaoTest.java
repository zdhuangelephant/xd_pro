package com.xiaodou.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.dao.product.QuesbkQuesStatisticsDao;
import com.xiaodou.domain.product.QuesbkQuesStatistics;

public class QuesbkQuesStatisticsDaoTest extends BaseUnitils {
  @SpringBean("quesbkQuesStatisticsDao")
  QuesbkQuesStatisticsDao quesbkQuesStatisticsDao;
  
  
  
  @Ignore
  public void insert() {
    try {
      QuesbkQuesStatistics record = new QuesbkQuesStatistics();
      record.setId(525652l);
      record.setCourseId(2l);
      record.setQuestionId(2l);
      record.setExamTimes(2);
      record.setWrongTimes(2);
      System.out.println(JSON.toJSONString(record));
//      System.out.println(quesbkQuesStatisticsDao.insert(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  @Ignore
  public void insertSelective() {
    try {
      QuesbkQuesStatistics record = new QuesbkQuesStatistics();
      record.setId(5256522l);
      record.setCourseId(2l);
      record.setQuestionId(2l);
      record.setExamTimes(2);
      record.setWrongTimes(2);
      System.out.println(JSON.toJSONString(record));
//      System.out.println(quesbkQuesStatisticsDao.insertSelective(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Ignore
  public void updateByExam() {
    try {
      QuesbkQuesStatistics record = new QuesbkQuesStatistics();
      record.setId(10L);
      record.setCourseId(2L);
      System.out.println(JSON.toJSONString(record));
//      System.out.println(quesbkQuesStatisticsDao.updateByExam(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void selectByQuesId() {
    try {
      System.out.println(quesbkQuesStatisticsDao.selectByQuesId("122", "0"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
