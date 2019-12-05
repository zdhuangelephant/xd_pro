package com.xiaodou.dao;

import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.dao.product.QuesbkAudioLogDao;
import com.xiaodou.domain.product.QuesbkAudioLog;

public class QuesbkAudioLogDaoTest extends BaseUnitils {
  @SpringBean("quesbkAudioLogDao")
  QuesbkAudioLogDao quesbkAudioLogDao;
  
  
  @Test
  public void selectByUserId() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("userId", 1536);
//      System.out.println(JSON.toJSONString(cond));
      List<QuesbkAudioLog> page = quesbkAudioLogDao.selectByUserId(cond);
      System.out.println(page);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void selectCountQuesVideoLogListByUserId() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("userId", 1536);
      System.out.println(JSON.toJSONString(cond));
      Integer page = quesbkAudioLogDao.selectCountQuesVideoLogListByUserId(cond);
      System.out.println(page);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void selectByUserIdAndId() {
    try {
      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("id", 275);
      cond.put("userId", 1511);
      List<QuesbkAudioLog> page = quesbkAudioLogDao.selectByUserIdAndId(cond);
      System.out.println(page);
//      System.out.println(JSON.toJSONString(cond));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
