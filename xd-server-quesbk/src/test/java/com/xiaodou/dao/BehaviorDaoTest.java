package com.xiaodou.dao;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.dao.behavior.UserChapterRecordDao;
import com.xiaodou.dao.behavior.UserFinalExamRecordDao;
import com.xiaodou.dao.behavior.UserWrongRecordCollectDao;
import com.xiaodou.dao.behavior.UserWrongRecordDao;
import com.xiaodou.domain.behavior.UserFinalExamRecord;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;

public class BehaviorDaoTest extends BaseUnitils {
  @SpringBean("userWrongRecordDao")
  UserWrongRecordDao userWrongRecordDao;
  @SpringBean("userChapterRecordDao")
  UserChapterRecordDao userChapterRecordDao;

  @SpringBean("userFinalExamRecordDao")
  UserFinalExamRecordDao userFinalExamRecordDao;

  @SpringBean("userWrongRecordCollectDao")
  UserWrongRecordCollectDao userWrongRecordCollectDao;

//  @Test
//  public void UserChapterRecord1() {
//    Map<String, Object> input = new HashMap<String, Object>();
//    Page<UserWrongRecord> page = new Page<>();
//    input.put("id", 2236);
//
  // Page<UserWrongRecord> findListByCond = userWrongRecordDao.findListByCond(input, page);
//    System.out.println(findListByCond.getResult());
//  }



  @Test
  public void UserChapterRecord() {
    userChapterRecordDao.selectByPrimaryKey("447");
  }

  @Test
  public void userFinalExamRecord() {
    try {
      UserFinalExamRecord record = new UserFinalExamRecord();
      record.setPaperNo("1");
      record.setId("123");
      record.setFinalExamId("1");
      record.setOperation(1);
      record.setUserId("1");
      // Object o =
      userFinalExamRecordDao.insert(record);
      userFinalExamRecordDao.selectByUidAndExamId("1", "1");
      userFinalExamRecordDao.deleteUserFinalExamRecord("123");
      // System.out.println(o.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void userWrongRecordCollect() {
    String userId = "1401";
    String productId = "275557820";
    String itemId = "328685237";
    try {
      UserWrongRecordCollect entity = new UserWrongRecordCollect();
      entity.setCourseId("123");
      entity.setUserId("123");
      entity.setChapterId(123l);
      entity.setQuestionNumber(12);
      // Object o =
      // userWrongRecordCollectDao.insertEntity(entity);
      userWrongRecordCollectDao.selectByUserIdAndCourseId(userId, productId);
      userWrongRecordCollectDao.selectByUserIdAndCourseIdItemId(userId, productId, itemId);
      // userWrongRecordCollectDao.updateByUSISelective(entity);
      // System.out.println(o.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
