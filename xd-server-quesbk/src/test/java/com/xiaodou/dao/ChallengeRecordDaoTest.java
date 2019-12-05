package com.xiaodou.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.product.ChallengeRecordDao;
import com.xiaodou.domain.product.ChallengeRecord;

public class ChallengeRecordDaoTest extends BaseUnitils {
  
  @SpringBean("challengeRecordDao")
  ChallengeRecordDao challengeRecordDao;

  
  @Ignore
  public void deleteByPrimaryKey() {
    System.out.println(challengeRecordDao.deleteByPrimaryKey("26620a7e-4108-44ac-8ef2-6ab27dcc207f"));
  }
  
  @Test
  public void selectByPrimaryKey() {
    try {
      System.out.println(challengeRecordDao.selectByPrimaryKey("d9d2ebef-d7d2-4514-b3cd-daa10373ede3"));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Ignore
  public void selectTodayByCond() {
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  @Ignore
  public void updateByPrimaryKeySelective() {
    try {
      ChallengeRecord record = new ChallengeRecord(); 
      record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      record.setId("06331130-485d-4e97-bd54-c5ef70c09b59");
      System.out.println(JSON.toJSONString(record));
//      System.out.println(challengeRecordDao.updateByPrimaryKeySelective(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void selectByCond() {
    try {
      HashMap<String, Object> input = Maps.newHashMap();
      input.put("id", "789ab238-ffa3-499c-92af-f8a2146d2da7");
      System.out.println(JSON.toJSONString(input));
      System.out.println(challengeRecordDao.selectByCond(input));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  
  @Ignore
  public void insertSelective() {
    try {
      ChallengeRecord record = new ChallengeRecord(); 
      String uuid =  UUID.randomUUID().toString();
      System.out.println(uuid);
      record.setId(uuid);
      record.setCourseId("372700258");
      record.setCourseName("");
      record.setChallengerUid("");
      record.setBeChallengerUid("");
      record.setPaperId("");
      record.setType((short)0);
      record.setStatus((short)1);
      record.setWinner("");
      record.setResult((short)1);
      record.setCreateTime(new Timestamp(System.currentTimeMillis()));
      record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      System.out.println(FastJsonUtil.toJson(record));
//      System.out.println( challengeRecordDao.insertSelective(record));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
