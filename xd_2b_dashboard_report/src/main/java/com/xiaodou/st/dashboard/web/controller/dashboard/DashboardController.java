package com.xiaodou.st.dashboard.web.controller.dashboard;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryDetailVO;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryVO;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.dashboard.DashboardService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("dashboardController")
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

  @Resource
  DashboardService dashboardService;

  @Resource
  IStServiceFacade stServiceFacade;

  @RequestMapping()
  public ModelAndView dashboard() throws ParseException {
    ModelAndView mv = new ModelAndView("/dashboard");
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("listLastWeekLearnTimeVO", dashboardService.listLastWeekLearnTimeVO());
    mv.addObject("listLastWeekMissionPercentVO", dashboardService.listLastWeekMissionPercentVO());
    List<SessionLearnPercentDTO> list =
        dashboardService.getSessionLearnPercentDTO(Constants.DEFAULT_DAYS);
    if (null != list && list.size() > 0) {
      mv.addObject("jsonData", FastJsonUtil.toJson(list));
    }

    // 每日概览
    AdminUser adminUser = getAdminUser();
    Page<EverydaySummaryVO> page =
        dashboardService.getEveryDaySummary(adminUser.getUnitId(), new Date());
    if(null != page && !CollectionUtils.isEmpty(page.getResult())) {
      for (EverydaySummaryVO vo : page.getResult()) {
        vo.setLearnPercent(vo.getLearnPercent() * 100);
        vo.setPassPercent(vo.getPassPercent() * 100);
        vo.setZeroPercent(vo.getZeroPercent() * 100);
      }
    }
    EverydaySummaryVO vo = null;
    if (null != page && page.getResult().size() > 0) {
      vo = page.getResult().get(0);
    }
    if(vo == null ) vo = new EverydaySummaryVO(0L,0L,0D,0D,0D,new Timestamp(System.currentTimeMillis()));
    mv.addObject("summary", vo);
    mv.addObject("adminUser", adminUser);
    return mv;
  }

  @RequestMapping("echarts_line")
  @ResponseBody
  public String echartsLine(Integer days) throws ParseException {
    List<SessionLearnPercentDTO> list = dashboardService.getSessionLearnPercentDTO(days);
    return FastJsonUtil.toJson(list);
  }


  @RequestMapping("detail")
  public ModelAndView summaryDetail() throws ParseException {
    ModelAndView mv = new ModelAndView("/echarts/dashboardDetail");
    AdminUser adminUser = getAdminUser();
    mv.addObject("adminUser", adminUser);
    ArrayList<Long> unitIdList = Lists.newArrayList();
    
    if (adminUser.getRole() == Constants.SELF_TAUGHT_UNIT_ROLE) {
      List<UnitDO> pilots = stServiceFacade.getPilotsByTaught();
      for (UnitDO unitDO : pilots) {
        unitIdList.add(unitDO.getId());
      }
    } else if (adminUser.getRole() == Constants.CHIEF_UNIT_ROLE) {
      ChiefUnitRelationDO chief = stServiceFacade.getChiefUnitByUnitId(adminUser.getUnitId());
      if(chief == null) return mv;
      List<ApplyDO> pilots = stServiceFacade.getPilotsByChief(chief.getCatId());
      for (ApplyDO applyDO : pilots)
        if (!unitIdList.contains(applyDO.getPilotUnitId()))
          unitIdList.add(applyDO.getPilotUnitId());
      Page<EverydaySummaryDetailVO> learnSummaryPage =
          dashboardService.getEveryDaySummary(unitIdList,adminUser.getUnitId(), new Date());
      if(null != learnSummaryPage && !CollectionUtils.isEmpty(learnSummaryPage.getResult())) {
        for (EverydaySummaryDetailVO vo : learnSummaryPage.getResult()) {
          vo.setLearnPercent(vo.getLearnPercent() * 100);
          vo.setPassPercent(vo.getPassPercent() * 100);
        }
      }
      mv.addObject("learnSummaryPage", learnSummaryPage);
      return mv;
    } else if (adminUser.getRole() == Constants.POILT_UNIT_ROLE) {
      unitIdList.add(adminUser.getUnitId());
    }
    Page<EverydaySummaryDetailVO> page =
        dashboardService.getEveryDaySummary(unitIdList, new Date());
    if(null != page && !CollectionUtils.isEmpty(page.getResult())) {
      for (EverydaySummaryDetailVO vo : page.getResult()) {
        vo.setLearnPercent(vo.getLearnPercent() * 100);
        vo.setPassPercent(vo.getPassPercent() * 100);
      }
    }
    mv.addObject("learnSummaryPage", page);
    return mv;
  }


  @RequestMapping("/export_dashboard_list")
  public void exportScoreList(HttpServletResponse response) {
    Boolean signl = Boolean.FALSE;
    AdminUser adminUser = getAdminUser();
    ArrayList<Long> unitIdList = Lists.newArrayList();
    if (adminUser.getRole() == Constants.SELF_TAUGHT_UNIT_ROLE) {
      List<UnitDO> pilots = stServiceFacade.getPilotsByTaught();
      for (UnitDO unitDO : pilots) {
        unitIdList.add(unitDO.getId());
      }
    } else if (adminUser.getRole() == Constants.CHIEF_UNIT_ROLE) {
      signl = Boolean.TRUE;
      ChiefUnitRelationDO chief = stServiceFacade.getChiefUnitByUnitId(adminUser.getUnitId());
      List<ApplyDO> pilots = stServiceFacade.getPilotsByChief(chief.getCatId());
      for (ApplyDO applyDO : pilots)
        if (!unitIdList.contains(applyDO.getPilotUnitId()))
          unitIdList.add(applyDO.getPilotUnitId());
    } else if (adminUser.getRole() == Constants.POILT_UNIT_ROLE) {
      unitIdList.add(adminUser.getUnitId());
    }
    Page<EverydaySummaryDetailVO> page = null;
    if(signl) {
      page = dashboardService.getEveryDaySummary(unitIdList,adminUser.getUnitId(), new Date());
    }
    else {
      page = dashboardService.getEveryDaySummary(unitIdList, new Date());
    }

    if (null != page && !page.getResult().isEmpty()) {
      List<EverydaySummaryDetailVO> lists = Lists.newArrayList();
      for (EverydaySummaryDetailVO vo : page.getResult()) {
        if(vo.getTotalStudents() == 0D || vo.getTotalSubjectsAndStus() == 0D) continue;
        lists.add(vo);
      }
      String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
      dashboardService.exportDashboardList(lists, response, "dashboard_summary" + dateFileName);
    }
  }

}
