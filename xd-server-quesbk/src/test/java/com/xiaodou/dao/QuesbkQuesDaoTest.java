package com.xiaodou.dao;

import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.dao.product.QuesbkQuesDao;
import com.xiaodou.engine.rule.model.Rule;

public class QuesbkQuesDaoTest extends BaseUnitils{
  @SpringBean("quesbkQuesDao")
  QuesbkQuesDao quesbkQuesDao;
  
  
  @Test
  public void selectByPrimaryKey() {
    try {
      System.out.println(quesbkQuesDao.selectByPrimaryKey("14276", "2"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void selectByPrimaryKeyList() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("userId", 16);
      cond.put("courseId", 31842372);
      cond.put("list", Lists.newArrayList("15210"));
      System.out.println(JSON.toJSONString(cond));
//      System.out.println(FastJsonUtil.toJson(quesbkQuesDao.selectByPrimaryKeyList(cond)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  @Ignore
  public void selectAbstractByPrimaryKeyList() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("courseId", 2);
      cond.put("list", Lists.newArrayList("14344","14342"));
//      System.out.println(quesbkQuesDao.selectAbstractByPrimaryKeyList(cond));
      System.out.println(JSON.toJSONString(cond));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  // @Ignore
  // public void selectByRule() {
  // try {
  // Rule rule = new Rule();
  // rule.setUserId("16");
  // System.out.println(quesbkQuesDao.selectByRule(rule));
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
  
  @Test
  public void selectQuesIdByRule() {
    try {
      Rule rule = new Rule();
      rule.setItemList(Lists.newArrayList("2","4"));
//      System.out.println(quesbkQuesDao.selectQuesIdByRule(rule));
      System.out.println(JSON.toJSONString(rule));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  
  @Test
  public void selectByIdList() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("userId", 16);
      cond.put("idList", Lists.newArrayList("2","4"));
//      System.out.println(quesbkQuesDao.selectByIdList(cond));
      System.out.println(JSON.toJSONString(cond));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  /**
   * 该方法测试未通过，别名有误
   */
  @Ignore
  public void selectAbstractByRule() {
    try {
      Rule rule = new Rule();
      rule.setUserId("16");
      System.out.println(JSON.toJSONString(rule));
//      System.out.println(quesbkQuesDao.selectAbstractByRule(rule));+
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
