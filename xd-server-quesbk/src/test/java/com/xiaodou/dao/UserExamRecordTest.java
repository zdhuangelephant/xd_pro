package com.xiaodou.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.behavior.UserExamRecordDao;
import com.xiaodou.domain.behavior.UserExamRecord;

/**
 * @name UserExamRecordTest 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月10日
 * @description TODO
 * @version 1.0
 */
public class UserExamRecordTest extends BaseUnitils{
  
  @SpringBean("userExamRecordDao")
  UserExamRecordDao userExamRecordDao;


  /**
   * @param id
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  @Test
  public void deleteByPrimaryKey(){
    
    try {
      int deleteByPrimaryKey = userExamRecordDao.deleteByPrimaryKey("1594");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  //@Test
  public void insert(){
    try {
      UserExamRecord record = new UserExamRecord();
      record.setUserId("18");
      record.setCourseId("18");
      record.setExamTypeId(2L);
      record.setCourseId(18L);
      record.setExamName("18test");
      record.setPaperNo("18");
      record.setQuestions("noquestion");
      record.setExamTime(new Timestamp(new Date().getTime()));
      record.setExamDetail("noquestion");
      record.setExamCost(100L);
      record.setMyScore(18.18);
      
      int insert = userExamRecordDao.insert(record);
      System.out.println(FastJsonUtil.toJson(record));
      System.out.println(insert);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param UserExamRecord record
   * @return 返回影响的结果 1：成功 ，0：失败
   */
 // @Test
  public void insertSelective(){
    try {
      UserExamRecord record = new UserExamRecord();
      record.setUserId("18");
      record.setCourseId("18");
      record.setExamTypeId(2L);
      record.setCourseId(18L);
      record.setExamName("18test");
      record.setPaperNo("18");
      record.setQuestions("noquestion");
      record.setExamTime(new Timestamp(new Date().getTime()));
      record.setExamDetail("noquestion");
      record.setExamCost(100L);
      record.setMyScore(18.18);
      
      userExamRecordDao.insertSelective(record);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  /**
//   * @param id
//   * @return UserExamRecord
//   */
 
  @Test
  public  void selectByPrimaryKey(){
    try {
      UserExamRecord selectByPrimaryKey = userExamRecordDao.selectByPrimaryKey("41011");
      System.out.println(selectByPrimaryKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  //成功
  public void selectByUidAndExamId(){
    try {
      UserExamRecord selectByUidAndExamId = userExamRecordDao.selectByUidAndExamId("41011", "18");
      System.out.println(selectByUidAndExamId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 还没完成
   */

  @Test

  public void selectByUidAndPaperId(){
     try {
          UserExamRecord selectByUidAndExamId = userExamRecordDao.selectByUidAndPaperId("18", "18");
          System.out.println(selectByUidAndExamId);
        } catch (Exception e) {
          e.printStackTrace();
        }
  }
//
//  /**
//   * @param userId
//   * @param subject
//   * @return List<UserExamRecord>
//   */
  @Test
  //成功
  public void selectByUidAndSubjectId(){
    try {
      List<UserExamRecord> result = userExamRecordDao.selectByUidAndSubjectId("18", "18");
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
//
//  /**
//   * @param userId
//   * @param subject
//   * @param examTypeId
//   * @return List<UserExamRecord>
//   */
  
  
  @Test
  // success
  public void selectByUidSubjectIdAndExamTypeId(){
    
    List<UserExamRecord> select= userExamRecordDao.selectByUidSubjectIdAndExamTypeId("18", "18", "0");
     System.out.println("select" + select);
  
  }
//
//  /**
//   * @param params
//   * @return List<UserExamRecord>
//   */
  
  @Test
  public void selectByNotUidSubjectIdAndExamTypeId(){
    try {
      Map<String, Object> params = Maps.newHashMap();
      params.put("uid", "18");
      
      List<UserExamRecord> select= userExamRecordDao.selectByNotUidSubjectIdAndExamTypeId(params);
      System.out.println("select" + select);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
//
//  /**
//   * @param paperId
//   * @return List<UserExamRecord>
//   */

  @Test
  public void selectByPaperId(){
     try {
      List<UserExamRecord> selectByPaperId = this.userExamRecordDao.selectByPaperId("18");
       System.out.println(selectByPaperId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
//
//  /**
//   * @param record
//   * @return 返回影响的结果 1：成功 ，0：失败
//   */
 
  //@Test
  //通过
  public void updateByPrimaryKeySelective(){
    
    try {
      UserExamRecord record = new UserExamRecord();
      record.setId(41011L);
      record.setUserId("19");
      this.userExamRecordDao.updateByPrimaryKeySelective(record);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  

//  /**
//   * @param record
//   * @return 返回影响的结果 1：成功 ，0：失败
//   */
  //@Test
  public void updateByPrimaryKey(){
    
    
  
  }
//  
//  /**
//   * @param condition
//   * @return List<UserExamRecord>
//   */
  @Test
  public void selectExamCostByExamCost(){
    try {
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", "18");
      cond.put("courseId","18");
      List<UserExamRecord> select = this.userExamRecordDao.selectExamCostByExamCost(cond);
      System.out.println(select.size());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  
}
