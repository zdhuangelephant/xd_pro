package com.xiaodou.st.dataclean.crontab;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
import com.xiaodou.st.dataclean.model.domain.dashboard.everyday.EverydaySummaryDetailVO;
import com.xiaodou.st.dataclean.model.domain.dashboard.everyday.EverydaySummaryVO;
import com.xiaodou.st.dataclean.model.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dataclean.service.common.CommonService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class CalculateEverydaySummaryJob {
  DashBoardServiceFacade facade;
  CommonService commonService;

  public void work() {
    facade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    commonService = SpringWebContextHolder.getBean("commonService");
    StaticInfoDO staticInfo = facade.staticInfo();
    Date nowDate = new Date();
    try {
      if(null == staticInfo) return;
      if(nowDate.getTime() > (DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()).getTime() + DateUtil.MILLIS_PER_DAY)) return;
    } catch (Exception e) {}

    // 更新首页饼图数据
    saveOrUpdatePie();
    // 更新详情数据
    saveOrUpdateDetail();
  }

  private void saveOrUpdateDetail() {
    String curDate = DateUtil.SDF_YMD.format(new Date());
    Map<String, Set<String>> info2 = wrapChiefForPilotUnit();
    Page<Unit> pilots = facade.queryPilotUnit();
    if (null != pilots && pilots.getResult().size() != 0) for (Unit u : pilots.getResult()) {
      if(u.getId() == 24) {
        System.out.println("hello");
      }
      // 试点单位
      EverydaySummaryDetailVO detailVo = new EverydaySummaryDetailVO();
      detailVo
          .setLearnNoneCounts(commonService.getPilotUnitNoneStuSubjects(String.valueOf(u.getId())));
      detailVo.setLearnPercent(commonService.getPilotUnitStuSubjects(String.valueOf(u.getId())));
      detailVo.setLevelFullCreditApplyCounts(
          commonService.getPilotUnitFullCreditSubjects(String.valueOf(u.getId())));
      detailVo.setLevelExcellentApplyCounts(
          commonService.getPilotUnitExcellentSubjects(String.valueOf(u.getId())));
      detailVo.setLevelGeneralApplyCounts(
          commonService.getPilotUnitGeneralSubjects(String.valueOf(u.getId())));
      detailVo.setLevelGoodApplyCounts(
          commonService.getPilotUnitGoodSubjects(String.valueOf(u.getId())));
      detailVo.setLevelPoorApplyCounts(
          commonService.getPilotUnitPoorSubjects(String.valueOf(u.getId())));
      detailVo.setPassPercent(
          commonService.getPilotUnitAboveGeneralSubjects(String.valueOf(u.getId())));
      detailVo.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      detailVo.setTotalStudents(
          commonService.getPilotUnitStudentCount(String.valueOf(u.getId())).longValue());
      detailVo.setTotalSubjectsAndStus(
          commonService.getPilotUnitSubjects(String.valueOf(u.getId())).longValue());
      detailVo.setUnitId(u.getId());
      detailVo.setPilotUnitId(u.getId());
      detailVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

      HashMap<String, Object> input = Maps.newHashMap();
      input.put("updateTimeLower", curDate);
      input.put("roleType", detailVo.getRoleType());
      input.put("unitId", detailVo.getUnitId());
      input.put("pilotUnitId", detailVo.getPilotUnitId());
      Page<EverydaySummaryDetailVO> page = facade.queryEverydaySummaryDetailByCond(input,
          CommUtil.getGeneralField(EverydaySummaryDetailVO.class));

      if (null == page || page.getResult().size() == 0) {
        detailVo.setId(Long.valueOf(RandomUtil.randomNumber(12)));
        detailVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        facade.insertEverydaySummaryDetail(detailVo);
      } else {
        detailVo.setId(page.getResult().get(0).getId());
        facade.updateEverydaySummaryDetailById(detailVo);
      }



      // 主考院校
      for (Map.Entry<String, Set<String>> ele : info2.entrySet()) {
        if (!ele.getValue().contains(String.valueOf(u.getId()))) continue;
        EverydaySummaryDetailVO detailVo2 = new EverydaySummaryDetailVO();
        detailVo2.setLearnNoneCounts(
            commonService.getChiefUnitNoneStuSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLearnPercent(
            commonService.getChiefUnitStuSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLevelFullCreditApplyCounts(
            commonService.getChiefUnitFullCreditSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLevelExcellentApplyCounts(
            commonService.getChiefUnitExcellentSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLevelGeneralApplyCounts(
            commonService.getChiefUnitGeneralSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLevelGoodApplyCounts(
            commonService.getChiefUnitGoodSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setLevelPoorApplyCounts(
            commonService.getChiefUnitPoorSubjects(ele.getKey(), String.valueOf(u.getId())));
        detailVo2.setPassPercent(commonService.getChiefUnitAboveGeneralSubjects(ele.getKey(),
            String.valueOf(u.getId())));
        detailVo2.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
        detailVo2.setTotalStudents(
            commonService.unitStundetCount(ele.getKey(), String.valueOf(u.getId())).longValue());
        detailVo2.setTotalSubjectsAndStus(
            commonService.unitSubjects(ele.getKey(), String.valueOf(u.getId())).longValue());
        detailVo2.setUnitId(Integer.parseInt(ele.getKey())); // 主考院校ID
        detailVo2.setPilotUnitId(u.getId());
        detailVo2.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        HashMap<String, Object> input2 = Maps.newHashMap();
        input2.put("updateTimeLower", curDate);
        input2.put("roleType", detailVo2.getRoleType());
        input2.put("unitId", detailVo2.getUnitId());
        input2.put("pilotUnitId", detailVo2.getPilotUnitId());
        Page<EverydaySummaryDetailVO> page2 = facade.queryEverydaySummaryDetailByCond(input2,
            CommUtil.getGeneralField(EverydaySummaryDetailVO.class));

        if (null == page2 || page2.getResult().size() == 0) {
          detailVo2.setId(Long.valueOf(RandomUtil.randomNumber(12)));
          detailVo2.setCreateTime(new Timestamp(System.currentTimeMillis()));
          facade.insertEverydaySummaryDetail(detailVo2);
        } else {
          detailVo2.setId(page2.getResult().get(0).getId());
          facade.updateEverydaySummaryDetailById(detailVo2);
        }

      }

      // 自考办
      EverydaySummaryDetailVO detailVo3 = new EverydaySummaryDetailVO();
      detailVo3
          .setLearnNoneCounts(commonService.getPilotUnitNoneStuSubjects(String.valueOf(u.getId())));
      detailVo3.setLearnPercent(commonService.getPilotUnitStuSubjects(String.valueOf(u.getId())));
      detailVo3.setLevelFullCreditApplyCounts(
          commonService.getPilotUnitFullCreditSubjects(String.valueOf(u.getId())));
      detailVo3.setLevelExcellentApplyCounts(
          commonService.getPilotUnitExcellentSubjects(String.valueOf(u.getId())));
      detailVo3.setLevelGeneralApplyCounts(
          commonService.getPilotUnitGeneralSubjects(String.valueOf(u.getId())));
      detailVo3.setLevelGoodApplyCounts(
          commonService.getPilotUnitGoodSubjects(String.valueOf(u.getId())));
      detailVo3.setLevelPoorApplyCounts(
          commonService.getPilotUnitPoorSubjects(String.valueOf(u.getId())));
      detailVo3.setPassPercent(
          commonService.getPilotUnitAboveGeneralSubjects(String.valueOf(u.getId())));
      detailVo3.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      detailVo3.setTotalStudents(
          commonService.getPilotUnitStudentCount(String.valueOf(u.getId())).longValue());
      detailVo3.setTotalSubjectsAndStus(
          commonService.getPilotUnitSubjects(String.valueOf(u.getId())).longValue());
      detailVo3.setUnitId(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
      detailVo3.setPilotUnitId(u.getId());
      detailVo3.setUpdateTime(new Timestamp(System.currentTimeMillis()));

      HashMap<String, Object> input3 = Maps.newHashMap();
      input3.put("updateTimeLower", curDate);
      input3.put("roleType", detailVo3.getRoleType());
      input3.put("unitId", detailVo3.getUnitId());
      input3.put("pilotUnitId", detailVo3.getPilotUnitId());

      Page<EverydaySummaryDetailVO> page3 = facade.queryEverydaySummaryDetailByCond(input3,
          CommUtil.getGeneralField(EverydaySummaryDetailVO.class));

      if (null == page3 || page3.getResult().size() == 0) {
        detailVo3.setId(Long.valueOf(RandomUtil.randomNumber(12)));
        detailVo3.setCreateTime(new Timestamp(System.currentTimeMillis()));
        facade.insertEverydaySummaryDetail(detailVo3);
      } else {
        detailVo3.setId(page3.getResult().get(0).getId());
        facade.updateEverydaySummaryDetailById(detailVo3);
      }
    }
  }


  private void saveOrUpdatePie() {
    saveOrUpdatePie1();
    saveOrUpdatePie2();
    saveOrUpdatePie3();
  }


  private void saveOrUpdatePie1() {
    // 自考办
    EverydaySummaryVO summVo = new EverydaySummaryVO();
    summVo.setLearnPercent(commonService.getTaughtUnitStuSubjects());
    summVo.setPassPercent(commonService.getTaughtUnitAboveGeneralSubjects());
    summVo.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    summVo.setTotalStudents(commonService.getTaughtUnitStudentCount().longValue());
    summVo.setTotalSubjectsAndStus(commonService.getTaughtUnitSubjects().longValue());
    summVo.setUnitId(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode());
    summVo.setZeroPercent(commonService.getTaughtUnitZeroScoreSubjects());
    summVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

    HashMap<String, Object> input = Maps.newHashMap();
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(new Date()));
    input.put("roleType", summVo.getRoleType());
    input.put("unitId", summVo.getUnitId());
    Page<EverydaySummaryVO> page =
        facade.queryEverydaySummaryByCond(input, CommUtil.getGeneralField(EverydaySummaryVO.class));

    if (null == page || page.getResult().size() == 0) {
      summVo.setId(Long.valueOf(RandomUtil.randomNumber(12)));
      summVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
      facade.insertEverydaySummary(summVo);
    } else {
      summVo.setId(page.getResult().get(0).getId());
      facade.updateEverydaySummaryById(summVo);
    }
  }

  private void saveOrUpdatePie3() {
    Page<Unit> pilots = facade.queryPilotUnit();
    if (null != pilots && pilots.getResult().size() != 0) for (Unit u : pilots.getResult()) {
      EverydaySummaryVO summVo = new EverydaySummaryVO();
      summVo.setLearnPercent(commonService.getPilotUnitStuSubjects(String.valueOf(u.getId())));
      summVo.setPassPercent(
          commonService.getPilotUnitAboveGeneralSubjects(String.valueOf(u.getId())));
      summVo.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode());
      summVo.setTotalStudents(
          commonService.getPilotUnitStudentCount(String.valueOf(u.getId())).longValue());
      summVo.setTotalSubjectsAndStus(
          commonService.getPilotUnitSubjects(String.valueOf(u.getId())).longValue());
      summVo.setUnitId(Integer.parseInt(String.valueOf(u.getId())));
      summVo.setZeroPercent(commonService.getPilotUnitZeroScoreSubjects(String.valueOf(u.getId())));
      summVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

      HashMap<String, Object> input = Maps.newHashMap();
      input.put("updateTimeLower", DateUtil.SDF_YMD.format(new Date()));
      input.put("roleType", summVo.getRoleType());
      input.put("unitId", summVo.getUnitId());
      Page<EverydaySummaryVO> page = facade.queryEverydaySummaryByCond(input,
          CommUtil.getGeneralField(EverydaySummaryVO.class));
      if (null == page || page.getResult().size() == 0) {
        summVo.setId(Long.valueOf(RandomUtil.randomNumber(12)));
        summVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        facade.insertEverydaySummary(summVo);
      } else {
        summVo.setId(page.getResult().get(0).getId());
        facade.updateEverydaySummaryById(summVo);
      }
    }
  }

  private void saveOrUpdatePie2() {
    // 主考院校
    Page<Unit> chiefs = facade.queryChiefUnit();
    HashMap<String, String> info = Maps.newHashMap();
    if (null != chiefs && chiefs.getResult().size() > 0) for (Unit ele : chiefs.getResult()) {
      ChiefUnitRelationModel model = facade.queryCategoryByChiefId(ele.getId());
      if (!info.containsKey(String.valueOf(ele.getId())) && null != model)
        info.put(String.valueOf(ele.getId()), String.valueOf(model.getCatId()));
    }

    for (String chiefUnitId : info.keySet()) {
      EverydaySummaryVO summVo = new EverydaySummaryVO();
      summVo.setLearnPercent(commonService.getChiefUnitStuSubjects(chiefUnitId));
      summVo.setPassPercent(commonService.getChiefUnitAboveGeneralSubjects(chiefUnitId));
      summVo.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode());
      summVo.setTotalStudents(commonService.unitStundetCount(chiefUnitId).longValue());
      summVo.setTotalSubjectsAndStus(commonService.unitSubjects(chiefUnitId).longValue());
      summVo.setUnitId(Integer.parseInt(chiefUnitId));
      if (summVo.getTotalSubjectsAndStus().longValue() != 0d)
        summVo.setZeroPercent(commonService.getChiefUnitZeroScoreSubjects(chiefUnitId));
      summVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

      HashMap<String, Object> input = Maps.newHashMap();
      input.put("updateTimeLower", DateUtil.SDF_YMD.format(new Date()));
      input.put("roleType", summVo.getRoleType());
      input.put("unitId", summVo.getUnitId());
      Page<EverydaySummaryVO> page = facade.queryEverydaySummaryByCond(input,
          CommUtil.getGeneralField(EverydaySummaryVO.class));

      if (null == page || page.getResult().size() == 0) {
        summVo.setId(Long.valueOf(RandomUtil.randomNumber(12)));
        summVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        facade.insertEverydaySummary(summVo);
      } else {
        summVo.setId(page.getResult().get(0).getId());
        facade.updateEverydaySummaryById(summVo);
      }
    }
  }

  /**
   * key chiefId; value pilotUnitList 键为主考院校ID,值为报考该专业的试点单位IDs
   * 
   * @return
   */
  private Map<String, Set<String>> wrapChiefForPilotUnit() {
    Page<Unit> chiefs = facade.queryChiefUnit();
    HashMap<String, String> info = Maps.newHashMap();
    if (null != chiefs && chiefs.getResult().size() > 0) for (Unit ele : chiefs.getResult()) {
      ChiefUnitRelationModel model = facade.queryCategoryByChiefId(ele.getId());
      if (!info.containsKey(String.valueOf(ele.getId())) && null != model)
        info.put(String.valueOf(ele.getId()), String.valueOf(model.getCatId()));
    }

    HashMap<String, Set<String>> info2 = Maps.newHashMap();
    for (String chiefId : info.keySet()) {
      List<ApplyModel> applyList = facade.getPilotsByChief(Integer.parseInt(info.get(chiefId)));
      Set<String> bulk = Sets.newHashSet();
      if (applyList != null) for (ApplyModel app : applyList) {
        bulk.add(String.valueOf(app.getPilotUnitId()));
      }
      if (!info2.containsKey(chiefId)) info2.put(chiefId, bulk);
    }
    return info2;
  }
}
