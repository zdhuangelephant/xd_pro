package com.xiaodou.st.dataclean.crontab;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
import com.xiaodou.st.dataclean.model.domain.dashboard.lastweek.LastWeekMissionPercentModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.st.dataclean.crontab.LastWeekMissionPercentRankJob.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 上周每日任务完成度定时任务
 * @crontab 每周一凌晨2点更新//0 0 2 ? * MON
 * @version 1.0
 */
public class LastWeekMissionPercentRankJob {

  private DashBoardServiceFacade dashBoardServiceFacade;

  public void work() {
    dashBoardServiceFacade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    updateTaughtRecord();
    updateChiefRecord();
  }

  private void updateTaughtRecord() {
    if (null == dashBoardServiceFacade) return;
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      // dashBoardServiceFacade.deleteLastWeekMissionPercent(RoleTypeEnum.RoleTypeEnum_Taught_Unit);
      return;
    }
    List<Integer> pilotUnitIdList = dashBoardServiceFacade.queryPilotUnitIdByCatId(null);
    // 专业下试点单位列表为空,跳出
    if (null == pilotUnitIdList || pilotUnitIdList.size() == 0) {
      // dashBoardServiceFacade.deleteLastWeekMissionPercent(taughtUnit.getId());
      return;
    }
    updateLastWeekMissionPercent(null, taughtUnit, pilotUnitIdList);
  }

  private void updateChiefRecord() {
    if (null == dashBoardServiceFacade) return;
    Page<Unit> chiefUnitPage = dashBoardServiceFacade.queryChiefUnit();
    if (null == chiefUnitPage || null == chiefUnitPage.getResult()
        || chiefUnitPage.getResult().size() == 0) {
      dashBoardServiceFacade.deleteLastWeekMissionPercent(RoleTypeEnum.RoleTypeEnum_Chief_Unit);
      return;
    }
    for (Unit chiefUnit : chiefUnitPage.getResult()) {
      List<ChiefUnitRelationModel> catList =
          dashBoardServiceFacade.getUnitByChief(chiefUnit.getId());
      if (null == catList || catList.size() == 0) {
        // dashBoardServiceFacade.deleteLastWeekMissionPercent(chiefUnit.getId());
        continue;
      }
      List<Integer> catIdList = Lists.newArrayList();
      for (ChiefUnitRelationModel relation : catList)
        catIdList.add(relation.getCatId());
      List<Integer> pilotUnitIdList = dashBoardServiceFacade.queryPilotUnitIdByCatId(catIdList);
      // 专业下试点单位列表为空,跳出
      if (null == pilotUnitIdList || pilotUnitIdList.size() == 0) {
        // dashBoardServiceFacade.deleteLastWeekMissionPercent(chiefUnit.getId());
        continue;
      }
      updateLastWeekMissionPercent(catIdList, chiefUnit, pilotUnitIdList);
    }
  }

  private void updateLastWeekMissionPercent(List<Integer> catIdList, Unit unit,
      List<Integer> pilotUnitIdList) {
    List<LastWeekMissionPercentModel> lastWeekLearnPercentList = Lists.newArrayList();
    for (Integer unitId : pilotUnitIdList) {
      lastWeekLearnPercentList.add(buildUnitRecord(catIdList, unit, unitId));
    }
    if (lastWeekLearnPercentList.size() == 0) {
      dashBoardServiceFacade.deleteLastWeekMissionPercent(unit.getId());
    } else {
      Collections.sort(lastWeekLearnPercentList, new Comparator<LastWeekMissionPercentModel>() {
        @Override
        public int compare(LastWeekMissionPercentModel o1, LastWeekMissionPercentModel o2) {
          if (null == o1 && null == o2) return 0;
          if (null == o1) return 1;
          if (null == o2) return -1;
          return (int) ((o2.getMissionPercent() - o1.getMissionPercent()) * 1000);
        }
      });
      List<LastWeekMissionPercentModel> wait4Insert = Lists.newArrayList();
      for (int i = 0; i < lastWeekLearnPercentList.size() && i < 3; i++) {
        LastWeekMissionPercentModel lastWeekMissionPercentModel = lastWeekLearnPercentList.get(i);
        lastWeekMissionPercentModel.setRank(i + 1);
        LastWeekMissionPercentModel oldLastWeekMissionPercentModel =
            dashBoardServiceFacade.queryLastWeekMissionPercentByPilotIdAndTaughtId(
                lastWeekMissionPercentModel.getPilotUnitId(),
                lastWeekMissionPercentModel.getUnitId());
        if (null == oldLastWeekMissionPercentModel
            || null == oldLastWeekMissionPercentModel.getRank()
            || lastWeekMissionPercentModel.getRank() > oldLastWeekMissionPercentModel.getRank())
          lastWeekMissionPercentModel.setTendency(Constant.TENDENCY_UP);
        else if (lastWeekMissionPercentModel.getRank() < oldLastWeekMissionPercentModel.getRank())
          lastWeekMissionPercentModel.setTendency(Constant.TENDENCY_DOWN);
        else
          lastWeekMissionPercentModel.setTendency(Constant.TENDENCY_SAME);
        wait4Insert.add(lastWeekMissionPercentModel);
      }
      if (wait4Insert != null && !wait4Insert.isEmpty())
        dashBoardServiceFacade.deleteLastWeekMissionPercent(unit.getId());
      for (LastWeekMissionPercentModel lastWeekMissionPercent : wait4Insert)
        dashBoardServiceFacade.insertLastWeekMissionPercent(lastWeekMissionPercent);
    }
  }

  // 构建有效记录逻辑
  private LastWeekMissionPercentModel buildUnitRecord(List<Integer> catIdList, Unit unit,
      Integer unitId) {
    if (null == catIdList)
      catIdList = dashBoardServiceFacade.queryCatIdByPilotUnitId(Lists.newArrayList(unitId));
    LastWeekMissionPercentModel lastWeekMissionPercent = new LastWeekMissionPercentModel();
    // 根据条件查询学生任务完成度
    Map<String, Object> input = getTimeLimit();
    if (null != unitId) input.put("pilotUnitId", unitId);
    if (null != catIdList && catIdList.size() > 0) input.put("catIdList", catIdList);
    input.put("roleType", unit.getRole());
    input.put("unitId", unit.getId());
    Double allgMissionPercent = dashBoardServiceFacade.queryAllMissionPercent(input);
    Double avgMissionPercent = allgMissionPercent / 7;
    if (null != catIdList && catIdList.size() > 0)
      avgMissionPercent = avgMissionPercent / catIdList.size();
    lastWeekMissionPercent.setMissionPercent(avgMissionPercent);
    lastWeekMissionPercent.setPilotUnitId(unitId);
    lastWeekMissionPercent.setRoleType(unit.getRole());
    lastWeekMissionPercent.setUnitId(unit.getId());
    lastWeekMissionPercent.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return lastWeekMissionPercent;
  }

  // 获取上周查询日期条件
  private Map<String, Object> getTimeLimit() {
    Map<String, Object> cond = new HashMap<>();
    cond.put("dateTimes", getTimeLimitList());
    return cond;
  }

  /**
   * 获取上周查询日期条件List
   */
  private List<String> getTimeLimitList() {
    SimpleDateFormat ftime = new SimpleDateFormat("YYYY-MM-dd");// 时间格式化不用年月日，只用时
    Date monday = new Date(DateUtil.getTimesWeekmorning());
    Calendar cal = Calendar.getInstance();
    cal.setTime(monday);
    List<String> dateTimeList = Lists.newArrayList();
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周日
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周六
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周五
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周四
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周三
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周二
    dateTimeList.add(ftime.format(cal.getTime()));
    cal.add(Calendar.DAY_OF_MONTH, -1); // 上周一
    dateTimeList.add(ftime.format(cal.getTime()));
    return dateTimeList;
  }


}
