package com.xiaodou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.behavior.UserChapterRecordDao;
import com.xiaodou.dao.behavior.UserWrongRecordCollectDao;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name UserChapterRecordDaoTest 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月10日
 * @description 测试类
 * @version 1.0
 */
public class UserChapterRecordDaoTest  extends BaseUnitils {
  
  @SpringBean("userChapterRecordDao")
  UserChapterRecordDao userChapterRecordDao;

  @SpringBean("userWrongRecordCollectDao")
  UserWrongRecordCollectDao userWrongRecordCollectDao;
  
  
  @Test
  public  void  selectByUserIdAndCourseId() {
    try {
      List<UserWrongRecordCollect> result = userWrongRecordCollectDao.selectByUserIdAndCourseId("38", "2");
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
 // @Test
  public  void  deleteByPrimaryKey() {
    try {
      int deleteByPrimaryKey = userChapterRecordDao.deleteByPrimaryKey("1448");
      System.out.println(deleteByPrimaryKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //@Test
  public void insertSelective() {
    try {
      UserChapterRecord record = new UserChapterRecord();
      record.setUserId("18");
      record.setCourseId(18L);
      record.setChapterId(18L);
      record.setItemId(18L);
      record.setStarLevel("1");
      record.setScore(18.18);
      record.setStatus(18);
      userChapterRecordDao.insertSelective(record);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
//public static void main(String[] args) {
//  UserChapterRecord record = new UserChapterRecord();
//  record.setUserId("18");
//  record.setCourseId(18L);
//  record.setChapterId(18L);
//  record.setItemId(18L);
//  record.setStarLevel("1");
//  record.setScore(18.18);
//  record.setStatus(18);
//  System.out.println(FastJsonUtil.toJson(record).replace("\"", "\'"));
//  record = 
//      FastJsonUtil.fromJson(FastJsonUtil.toJson(record).replace("\"", "\'"), UserChapterRecord.class);
//  
//  System.out.println(record.toString());
//  
//}
  
  
  @Test
  public void selectByPrimaryKey() {
    try {
      System.out.println("start");
      UserChapterRecord selectByPrimaryKey = userChapterRecordDao.selectByPrimaryKey("449");
      System.out.println(selectByPrimaryKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
 public void selectByCond() {
    try {
      Map<String,Object> cond = new HashMap<String,Object>();
      Map<String,Object> input = new HashMap<String,Object>();
      input.put("id", "449");
      cond.put("input", input);
      cond.put("output", CommUtil.getAllField(UserChapterRecord.class));    
      System.out.println(userChapterRecordDao.selectByCond(cond));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

// @Test
 public void updateByPrimaryKey() {
   UserChapterRecord record = new UserChapterRecord();
   record.setId(449L);
   record.setStarLevel("3");
   userChapterRecordDao.updateByPrimaryKey(record);    
 }
}