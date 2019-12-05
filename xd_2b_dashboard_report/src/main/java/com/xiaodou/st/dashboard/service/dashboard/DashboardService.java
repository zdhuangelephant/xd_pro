package com.xiaodou.st.dashboard.service.dashboard;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryDetailVO;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryVO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekLearnTimeDO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekLearnTimeVO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekMissionPercentDO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekMissionPercentVO;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDO;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.st.dashboard.util.StDateUtil;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class DashboardService extends BaseDashboardService {

  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  public List<LastWeekLearnTimeDO> listLastWeekLearnTime() {
    Page<LastWeekLearnTimeDO> page = stServiceFacade.listLastWeekLearnTime(null,
        CommUtil.getAllField(LastWeekLearnTimeDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  public List<LastWeekLearnTimeVO> listLastWeekLearnTimeVO() {
    List<LastWeekLearnTimeVO> listVO = Lists.newArrayList();
    List<LastWeekLearnTimeDO> listDO = this.listLastWeekLearnTime();
    for (LastWeekLearnTimeDO ldo : listDO) {
      LastWeekLearnTimeVO lvo = new LastWeekLearnTimeVO();
      lvo.setRank(ldo.getRank());
      lvo.setPortrait(ldo.getPortrait());
      lvo.setRealName(ldo.getRealName());
      lvo.setPilotUnitName(ldo.getPilotUnitName());
      lvo.setClassName(ldo.getClassName());
      lvo.setLearnTime(StDateUtil.getCeilLong((ldo.getLearnTime() / 60d)));
      lvo.setAnswerCount(ldo.getAnswerCount());
      lvo.setRightPercent(Constants.PERCENT_FORMAT.format(ldo.getRightPercent()));
      listVO.add(lvo);
    }
    return listVO;
  }

  public List<LastWeekMissionPercentDO> listLastWeekMissionPercent() {
    Page<LastWeekMissionPercentDO> page = stServiceFacade.listLastWeekMissionPercent(null,
        CommUtil.getAllField(LastWeekMissionPercentDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 页面展示
   * @author 李德洪
   * @Date 2017年4月10日
   * @return
   */
  public List<LastWeekMissionPercentVO> listLastWeekMissionPercentVO() {
    List<LastWeekMissionPercentVO> listVO = Lists.newArrayList();
    List<LastWeekMissionPercentDO> listDO = this.listLastWeekMissionPercent();
    for (LastWeekMissionPercentDO ldo : listDO) {
      LastWeekMissionPercentVO lvo = new LastWeekMissionPercentVO();
      lvo.setTendency(ldo.getTendency());
      lvo.setRank(ldo.getRank());
      lvo.setPilotUnitPortrait(ldo.getPilotUnitPortrait());
      lvo.setPilotUnitName(ldo.getPilotUnitName());
      lvo.setMissionPercent(Constants.PERCENT_FORMAT.format(ldo.getMissionPercent()));
      listVO.add(lvo);
    }
    return listVO;
  }

  public List<SessionLearnPercentDO> listSessionLearnPercent() {
    Page<SessionLearnPercentDO> page = stServiceFacade.listSessionLearnPercent(null,
        CommUtil.getAllField(SessionLearnPercentDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  // ---------------------------------------------------

  public List<SessionLearnPercentDTO> getSessionLearnPercentDTO(int days) throws ParseException {
    List<SessionLearnPercentDTO> adtoList = Lists.newArrayList();
    List<String> dateTimeList = super.getBeforeDateStrList(days);
    List<SessionLearnPercentDO> list = this.listSessionLearnPercent();
    Map<String, String> learnPercentMap = Maps.newHashMap();
    Map<String, String> missionPercentMap = Maps.newHashMap();
    Map<String, Long> learnTimePercentMap = Maps.newHashMap();
    if (null != list && list.size() > 0) {
      StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
      Date productBeginApplyTime =
          new SimpleDateFormat("yyyy-MM-dd").parse(staticInfo.getBeginApplyTime());
      Date productEndApplyTime =
          new SimpleDateFormat("yyyy-MM-dd").parse(staticInfo.getEndApplyTime());
      for (SessionLearnPercentDO sdo : list) {
        Date dateTime = sdo.getDateTime();
        if (dateTime.after(productBeginApplyTime) && dateTime.before(productEndApplyTime)
            || dateTime.equals(productBeginApplyTime) || dateTime.equals(productEndApplyTime)) {
          learnPercentMap.put(DateUtil.SDF_MD.format(sdo.getDateTime()),
              Constants.DECIMAL_FORMAT.format(sdo.getLearnPercent() * 100));
          missionPercentMap.put(DateUtil.SDF_MD.format(sdo.getDateTime()),
              Constants.DECIMAL_FORMAT.format(sdo.getMissionPercent() * 100));
          learnTimePercentMap.put(DateUtil.SDF_MD.format(sdo.getDateTime()),
              StDateUtil.getCeilLong((sdo.getLearnTimePercent() / 60d)));
        }
      }
    }
    for (String dateTime : dateTimeList) {
      SessionLearnPercentDTO adto = new SessionLearnPercentDTO();
      adto.setDateTime(dateTime);
      if (null != learnPercentMap && learnPercentMap.containsKey(dateTime))
        adto.setLearnPercent(learnPercentMap.get(dateTime));
      if (null != missionPercentMap && missionPercentMap.containsKey(dateTime))
        adto.setMissionPercent(missionPercentMap.get(dateTime));
      if (null != learnTimePercentMap && learnTimePercentMap.containsKey(dateTime)) {
        Long learnTimePercent = 1440l;
        if (learnTimePercentMap.get(dateTime) < 1440l)
          learnTimePercent = learnTimePercentMap.get(dateTime);
        adto.setLearnTimePercent(learnTimePercent);
      }
      adtoList.add(adto);
    }
    // 重試
    if (!super.isSuccessOrderData(adtoList)) {
      getSessionLearnPercentDTO(days);
    }
    return adtoList;
  }

  public Page<EverydaySummaryVO> getEveryDaySummary(Long unitId, Date date) {
     Page<EverydaySummaryVO> page = stServiceFacade.getEveryDaySummary(unitId, date);
     return page;
  }

  public Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> unitIds, Date date) {
    // 如果是超管登陆则直接return
    if(CollectionUtils.isEmpty(unitIds)) return null;
    Page<EverydaySummaryDetailVO> page = stServiceFacade.getEveryDaySummary(unitIds, date);
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("ids", unitIds);
    Page<UnitDO> listUnitPage =
        stServiceFacade.listUnit(inputs, CommUtil.getAllField(UnitDO.class));
    HashMap<String, String> bulk = Maps.newHashMap();
    if (null != listUnitPage) for (UnitDO unit : listUnitPage.getResult()) {
      if (!bulk.containsKey(unit.getId().toString()))
        bulk.put(unit.getId().toString(), unit.getUnitName());
    }

    if (null != page) for (EverydaySummaryDetailVO ls : page.getResult()) {
      if (bulk.containsKey(ls.getUnitId().toString()))
        ls.setPilotUnitName(bulk.get(ls.getUnitId().toString()));
    }
    return page;
  }



  public void exportDashboardList(List<EverydaySummaryDetailVO> list, HttpServletResponse response,
      String fileName) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("pilotUnitName", "学校");
    fieldMap.put("totalStudents", "已报名学生");
    fieldMap.put("totalSubjectsAndStus", "已缴费科次");
    fieldMap.put("learnNoneCounts", "无学习记录科次");
    fieldMap.put("learnPercent", "学习使用率(%)");
    fieldMap.put("passPercent", "及格率(%)");
    fieldMap.put("levelFullCreditApplyCounts", "100分");
    fieldMap.put("levelExcellentApplyCounts", "(100-80]分");
    fieldMap.put("levelGoodApplyCounts", "(80-60]分");
    fieldMap.put("levelGeneralApplyCounts", "(60-0)分");
    fieldMap.put("levelPoorApplyCounts", "0分");
    
    if(!CollectionUtils.isEmpty(list))for (EverydaySummaryDetailVO vo : list) {
      if(null == vo) continue;
      if(vo.getLearnPercent() != null) 
        vo.setLearnPercent(new BigDecimal(vo.getLearnPercent() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
      if(vo.getPassPercent() != null) 
        vo.setPassPercent(new BigDecimal(vo.getPassPercent() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
    try {
      ExcelUtil.listToExcel(list, fieldMap, "汇总统计表", response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> pilotUnitIdList, Long unitId,
      Date date) {
    // 如果是超管登陆则直接return
    if(CollectionUtils.isEmpty(pilotUnitIdList)) return null;
    Page<EverydaySummaryDetailVO> page = stServiceFacade.getEveryDaySummary(pilotUnitIdList, unitId, date);
    Map<String, Object> inputs = Maps.newHashMap();
      inputs.put("role", 3);
    Page<UnitDO> listUnitPage =
        stServiceFacade.listUnit(inputs, CommUtil.getAllField(UnitDO.class));
    HashMap<String, String> bulk = Maps.newHashMap();
    if (null != listUnitPage) for (UnitDO unit : listUnitPage.getResult()) {
      if (!bulk.containsKey(unit.getId().toString()))
        bulk.put(unit.getId().toString(), unit.getUnitName());
    }

    if (null != page) for (EverydaySummaryDetailVO ls : page.getResult()) {
      if (bulk.containsKey(ls.getPilotUnitId().toString()))
        ls.setPilotUnitName(bulk.get(ls.getPilotUnitId().toString()));
    }
    return page;
  }

}
