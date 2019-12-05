package com.xiaodou.st.dataclean.crontab;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
import com.xiaodou.st.dataclean.model.domain.dashboard.lastweek.LastWeekLearnTimeModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.st.dataclean.crontab.LastWeekLearnTimeRankJob.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 上周学霸榜定时任务
 * @crontab 每周一凌晨2点更新//0 0 2 ? * MON
 * @version 1.0
 */
public class LastWeekLearnTimeRankJob {
  DashBoardServiceFacade dashBoardServiceFacade;

  public void work() {
    dashBoardServiceFacade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    updateTaughtRecord();
    updateChiefRecord();
    updatePilotRecord();
  }

  private void updateTaughtRecord() {
    if (null == dashBoardServiceFacade) return;
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) return;
    Map<String, Object> cond = getTimeLimit();
    IQueryParam param = new QueryParam();
    updateUnitRecord(dashBoardServiceFacade, cond, param, taughtUnit);
  }

  private void updateChiefRecord() {
    if (null == dashBoardServiceFacade) return;
    Page<Unit> chiefUnitPage = dashBoardServiceFacade.queryChiefUnit();
    if (null == chiefUnitPage || null == chiefUnitPage.getResult()
        || chiefUnitPage.getResult().size() == 0) return;
    for (Unit chiefUnit : chiefUnitPage.getResult()) {
      Map<String, Object> cond = getTimeLimit();
      cond.put("chiefUnitId", chiefUnit.getId());
      IQueryParam param = new QueryParam();
      param.addInput("chiefUnitId", chiefUnit.getId());
      updateUnitRecord(dashBoardServiceFacade, cond, param, chiefUnit);
    }
  }

  private void updatePilotRecord() {
    if (null == dashBoardServiceFacade) return;
    Page<Unit> pilotUnitPage = dashBoardServiceFacade.queryPilotUnit();
    if (null == pilotUnitPage || null == pilotUnitPage.getResult()
        || pilotUnitPage.getResult().size() == 0) return;
    for (Unit pilotUnit : pilotUnitPage.getResult()) {
      Map<String, Object> cond = getTimeLimit();
      cond.put("pilotUnitId", pilotUnit.getId());
      IQueryParam param = new QueryParam();
      param.addInput("pilotUnitId", pilotUnit.getId());
      updateUnitRecord(dashBoardServiceFacade, cond, param, pilotUnit);
    }
  }

  private void updateUnitRecord(DashBoardServiceFacade dashBoardServiceFacade,
      Map<String, Object> learnRecordCond, IQueryParam examTotalParam, Unit unit) {
    List<RawDataLearnRecordModel> userLearnRecordList =
        dashBoardServiceFacade.getLaskWeekLearnTimeRank(learnRecordCond);
    if (null == userLearnRecordList || userLearnRecordList.size() == 0) return;
    List<String> userIdList = Lists.newArrayList();
    Map<String, LastWeekLearnTimeModel> lastWeekLearnMap = Maps.newHashMap();
    for (int i = 0; i < userLearnRecordList.size(); i++) {
      RawDataLearnRecordModel userLearnRecord = userLearnRecordList.get(i);
      userIdList.add(userLearnRecord.getUserId());
      if (lastWeekLearnMap.containsKey(userLearnRecord.getUserId())) continue;
      LastWeekLearnTimeModel model = new LastWeekLearnTimeModel();
      model.setStudentId(userLearnRecord.getStudentId());
      model.setLearnTime(userLearnRecord.getLearnTime());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setRank(i + 1);
      lastWeekLearnMap.put(userLearnRecord.getUserId(), model);
    }
    examTotalParam.addInput("userIdList", userIdList);
    List<RawDataExamTotalModel> userExamRecordList =
        queryTotalStatistic(dashBoardServiceFacade, examTotalParam);
    if (userExamRecordList != null && userExamRecordList.size() > 0) {
      for (RawDataExamTotalModel userExamRecord : userExamRecordList) {
        if (!lastWeekLearnMap.containsKey(userExamRecord.getUserId())) continue;
        LastWeekLearnTimeModel model = lastWeekLearnMap.get(userExamRecord.getUserId());
        if (null == model) continue;
        model.setAnswerCount(userExamRecord.getTotalQues());
        model.setRightPercent(userExamRecord.getRightPercent());
      }
    }
    dashBoardServiceFacade.deleteLastWeekLearnTimeByRank(unit.getId());
    for (LastWeekLearnTimeModel model : lastWeekLearnMap.values()) {
      model.setRoleType(unit.getRole().intValue());
      model.setUnitId(unit.getId());
      dashBoardServiceFacade.insertLastWeekLearnTime(model);
    }
  }

  // 获取上周查询日期条件
  private Map<String, Object> getTimeLimit() {
    SimpleDateFormat ftime = new SimpleDateFormat("YYYY-MM-dd");// 时间格式化不用年月日，只用时
    Date monday = new Date(DateUtil.getTimesWeekmorning());
    Calendar cal = Calendar.getInstance();
    cal.setTime(monday);
    List<String> dataTimeList = Lists.newArrayList();
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周日
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周六
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周五
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周四
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周三
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周二
    dataTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周一
    dataTimeList.add(ftime.format(cal.getTime()));
    Map<String, Object> cond = new HashMap<>();
    cond.put("dateTimes", dataTimeList);
    return cond;
  }

  private List<RawDataExamTotalModel> queryTotalStatistic(
      DashBoardServiceFacade dashBoardServiceFacade, IQueryParam param) {
    Page<RawDataExamTotalModel> examTotalPage = dashBoardServiceFacade.queryTotalStatistic(param);
    if (null == examTotalPage || examTotalPage.getResult() == null
        || examTotalPage.getResult().size() == 0) return null;
    return examTotalPage.getResult();
  }

}
