package com.xiaodou.mission;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.dao.MissionModelDao;
import com.xiaodou.mission.engine.MissionEnums.MissionCondition;
import com.xiaodou.mission.engine.MissionEnums.MissionConditionType;
import com.xiaodou.mission.engine.MissionEnums.MissionJumpType;
import com.xiaodou.mission.engine.MissionEnums.MissionPreCondition;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.enums.RedBonusType;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.MissionPreConditionInstance;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * @name @see com.xiaodou.mission.MissionModelTest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 单测类
 * @version 1.0
 */
public class MissionModelTest extends BaseSpringTest {

  @Resource
  SummerDataSourceManager summerDataSource;

  @Resource
  MissionModelDao missionModelDao;

  @Resource
  MissionOperationFacade missionOperationFacade;

  public List<MissionInstance> buildModel() {
    List<MissionInstance> instanceList = Lists.newArrayList();
    Set<String> courseIdSet = Sets.newHashSet();
    Set<String> itemIdSet = Sets.newHashSet();
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    int order = 1;
    int pkOrder = 1000;
    try {
      con = summerDataSource.getmDataSource().getConnection();
      stmt = con.createStatement();
      rs =
          stmt.executeQuery("SELECT xcpc.id AS major_id, xcpc.module, xcp.id AS course_id, xcpi.parent_id AS chapter_id, xcpi.id AS item_id, xcpi.chapterName chapter_name, xcpi.chapter_id AS item_name FROM xd_course_product_category xcpc, xd_course_product_relation xcpr, xd_course_product xcp, ( SELECT chapter.chapter_id AS chapterName, item.* FROM xd_course_product_item chapter, xd_course_product_item item WHERE chapter.id = item.parent_id AND chapter.parent_id = 0 AND chapter.resource_type = 1 AND item.resource_type = 1 ) xcpi WHERE xcpc.id = xcpr.product_category_id AND xcp.id = xcpr.product_id AND xcp.id = xcpi.product_id");
      while (rs.next()) {
        String courseId = rs.getString("course_id");
        if (!courseIdSet.contains(courseId)) {
          {
            MissionInstance instance = new MissionInstance();
            instance.setModule(rs.getString("module"));
            instance.setCourseId(courseId);
            instance.setChapterId(rs.getString("chapter_id"));
            instance.setMissionType(MissionType.task);
            MissionPreConditionInstance preCond = new MissionPreConditionInstance();
            preCond.setPreCond(MissionPreCondition.have_wrongques);
            preCond.setThreshold("10");
            instance.setPreCond(preCond);
            instance.setCondType(MissionConditionType.wrongques);
            instance.setCondition(MissionCondition.wrongques_solve_oneday);
            instance.setScope(ValueScope.EQ);
            instance.setThreshold(courseId);
            instance.setCreditUpper(20);
            instance.setMissionName(MissionCondition.wrongques_solve_oneday.getDesc());
            instance.setMissionDesc("消灭错题集");
            instance.setTaskType(TaskType.dynamic);
            instance.setMissionState(MissionConstant.MISSON_STATE_INUSE);
            instance.setJumpType(MissionJumpType.wrongQues);
            instance.setMissionOrder(pkOrder++);
            instance.setMissionPicUrl("");
            instance.setExpiryDate(180);
            instanceList.add(instance);
          }
          {
            MissionInstance instance = new MissionInstance();
            instance.setModule(rs.getString("module"));
            instance.setCourseId(courseId);
            instance.setChapterId(rs.getString("chapter_id"));
            instance.setMissionType(MissionType.task);
            MissionPreConditionInstance preCond = new MissionPreConditionInstance();
            preCond.setPreCond(MissionPreCondition.complete_pre_course);
            preCond.setThreshold(courseId);
            instance.setPreCond(preCond);
            instance.setCondType(MissionConditionType.wrongques);
            instance.setCondition(MissionCondition.complete_random_pk);
            instance.setScope(ValueScope.EQ);
            instance.setThreshold(courseId);
            instance.setCreditUpper(20);
            instance.setMissionName(MissionCondition.complete_random_pk.getDesc());
            instance.setMissionDesc("完成一次PK并获胜");
            instance.setTaskType(TaskType.dynamic);
            instance.setMissionState(MissionConstant.MISSON_STATE_INUSE);
            instance.setJumpType(MissionJumpType.randomPk);
            instance.setRedBonusType(RedBonusType.Random_pk_win);
            instance.setMissionOrder(pkOrder++);
            instance.setMissionPicUrl("");
            instance.setExpiryDate(180);
            instanceList.add(instance);
          }
          courseIdSet.add(courseId);
        }
        if (itemIdSet.contains(rs.getString("item_id"))) {
          continue;
        }
        itemIdSet.add(rs.getString("item_id"));
        MissionInstance instance = new MissionInstance();
        instance.setModule(rs.getString("module"));
        instance.setCourseId(courseId);
        instance.setChapterId(rs.getString("chapter_id"));
        instance.setMissionType(MissionType.task);
        instance.setCondType(MissionConditionType.tollgate);
        instance.setCondition(MissionCondition.tollgate_designated);
        instance.setScope(ValueScope.EQ);
        instance.setThreshold(rs.getString("item_id"));
        instance.setCreditUpper(20);
        instance.setMissionName(MissionCondition.tollgate_designated.getDesc());
        instance.setMissionDesc(String.format("完成%s%s学习并闯关成功", rs.getString("chapter_name"),
            rs.getString("item_name")));
        instance.setTaskType(TaskType.standard);
        instance.setMissionState(MissionConstant.MISSON_STATE_INUSE);
        instance.setJumpType(MissionJumpType.tollgate);
        instance.setRedBonusType(RedBonusType.Tollgate_complete_item);
        instance.setMissionOrder(order++);
        instance.setMissionPicUrl("");
        instance.setExpiryDate(180);
        instanceList.add(instance);
      }

    } catch (Exception e) {
      LoggerUtil.error("getSeqId failure", e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return instanceList;
  }

  // @Test
  public void addModel() {
    System.out.println("--------------------add-------------------");
  }

  @Test
  public void testMission() {
    System.out.println("!23");
  }

}
