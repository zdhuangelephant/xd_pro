package com.xiaodou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.behavior.UserExamTotalDao;
import com.xiaodou.domain.behavior.UserExamTotal;

/**
 * @name UserExamRecordTest CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月10日
 * @description TODO
 * @version 1.0
 */
public class UserExamTotalTest extends BaseUnitils {

  @SpringBean("userExamTotalDao")
  UserExamTotalDao userExamTotalDao;

  @Test
  public void selectAllUserCountBySubjectId() {
    try {
      int selectAllUserCountBySubjectId = userExamTotalDao.selectAllUserCountBySubjectId("18");
      System.out.println(selectAllUserCountBySubjectId);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param record
   * @return integer insert的结果
   */
  // @Test
  public void insert() {
    try {
      UserExamTotal record = new UserExamTotal();
      record.setUserId("23");
      record.setCourseId(23L);
      record.setTotalQues(23);
      record.setAvgTotal(23);
      record.setTotalRank(23);
      record.setRightQues(23);
      record.setAvgRight(23);
      int insert = userExamTotalDao.insert(record);
      System.out.println(insert);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param record
   * @return integer insert的结果
   */
  // @Test
  public void insertSelective() {
    try {
      UserExamTotal record = new UserExamTotal();
      record.setUserId("34");
      record.setCourseId(34L);
      record.setTotalQues(34);
      record.setAvgTotal(34);
      record.setTotalRank(34);
      record.setRightQues(34);
      record.setAvgRight(34);
      record.setRightPercRank(123);
      System.out.println(record);


      int insert = userExamTotalDao.insertSelective(record);
      System.out.println(insert);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // public static void main(String[] args) {
  // UserExamTotal record = new UserExamTotal();
  // record.setUserId("19");
  // record.setCourseId(19L);
  // record.setTotalQues(19);
  // record.setAvgTotal(19);
  // record.setTotalRank(19);
  // record.setRightQues(19);
  // record.setAvgRight(19);
  // record.setAvgChapterScore(19.18);
  // record.setFinalExamScore(19.18);
  // record.setMissionFinishScore(19.18);
  //
  // System.out.println(FastJsonUtil.toJson(record).replaceAll("\"", "\'"));
  // }


  /**
   * @param value
   * @param column
   * @return insert的结果 onDuplicate
   */
  // @Test
  public void updateOrAddEntity() {

    try {
      UserExamTotal record = new UserExamTotal();
      record.setUserId("20");
      record.setCourseId(20L);
      record.setTotalQues(20);
      record.setAvgTotal(20);
      record.setTotalRank(20);
      record.setRightQues(20);
      record.setAvgRight(20);
      Map<String, Object> cond = CommUtil.getAllField(UserExamTotal.class);
      int insert = userExamTotalDao.updateOrAddEntity(record, cond);
      System.out.println(insert);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  /**
   * @param id
   * @return UserExamTotal
   */
  @Test
  // success
  public void selectByPrimaryKey() {
    UserExamTotal selectByPrimaryKey = this.userExamTotalDao.selectByPrimaryKey("1684");
    System.out.println(selectByPrimaryKey);
  }

  /**
   * @param record
   * @return
   */
  // @Test
  public void updateByPrimaryKeySelective() {

    try {
      UserExamTotal record = new UserExamTotal();
      record.setId(1684L);
      record.setAllChapterScore(110.110);
      int result = this.userExamTotalDao.updateByPrimaryKeySelective(record);
      System.out.println(result);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void updateByPrimaryKey() {
    try {
      UserExamTotal record = new UserExamTotal();
      record.setId(1684L);
      record.setAllChapterScore(110.110);
      int result = this.userExamTotalDao.updateByPrimaryKey(record);
      System.out.println(result);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * @param uid
   * @param subjectId
   * @return
   */
  @Test
  public void selectByUidAndSubjectId() {

    try {
      UserExamTotal select = this.userExamTotalDao.selectByUidAndSubjectId("19", "19");
      System.out.println(select);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param uid
   * @return List<UserExamTotal>
   */
  @Test
  public void selectByUid() {

    try {
      List<UserExamTotal> select = this.userExamTotalDao.selectByUid("19");
      System.out.println(select);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param input
   * @return List<UserExamTotal>
   */
  @Test
  public void selectByUidNotAll() {
    try {
      Map<String, Object> input = new HashMap();
      List<UserExamTotal> select = this.userExamTotalDao.selectByUidNotAll(input);
      System.out.println(select);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  @Test
  public void queryByCond() {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", "1684");
    System.out.println("19999" + this.userExamTotalDao.queryByCond(cond));
  }

  @Test
  public void queryTotalRankByCond() {
    Integer queryTotalRankByCond = this.userExamTotalDao.queryTotalRankByCond("10", "19", "19");
    System.out.println(queryTotalRankByCond);
  }

  @Test
  public void queryRightRankByCond() {

    Integer queryTotalRankByCond = this.userExamTotalDao.queryRightRankByCond("10", "19", "19");
    System.out.println(queryTotalRankByCond);

  }



  //



}
