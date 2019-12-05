package com.xiaodou.dashboard.web.controller.log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;
import com.xiaodou.dashboard.model.log.ActionModel;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.dashboard.service.alarm.AlarmConfigService;
import com.xiaodou.dashboard.service.log.LogService;
import com.xiaodou.dashboard.util.ActionStatisticInfo;
import com.xiaodou.dashboard.vo.jmsg.request.Chart;
import com.xiaodou.dashboard.vo.log.ActionModelVo;
import com.xiaodou.dashboard.vo.log.request.ActionRequest;
import com.xiaodou.dashboard.vo.log.request.ProjectModelRequest;
import com.xiaodou.dashboard.vo.log.response.ProjectModelVO;
import com.xiaodou.summer.dao.pagination.Page;

@Controller
@RequestMapping("/log")
public class LogController {

  /** alarmService 报警服务service */
  @Resource
  LogService logService;
  /** alarmConfigService 报警配置service */
  @Resource
  AlarmConfigService alarmConfigService;

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/project_excution_list")
  public ModelAndView list(ProjectModelRequest request) {
    ModelAndView modelAndView = new ModelAndView("/log/list");
    Page<ProjectModelVO> projectPage =
        logService.getProjectModelList(request.getProjectName(), request.getExcutePoint(),
            request.getPage());
    if (null != projectPage) {
      modelAndView.addObject("projectList", projectPage.getResult());
      modelAndView.addObject("totalCount", projectPage.getTotalCount());
      modelAndView.addObject("pageNo", projectPage.getPageNo());
      modelAndView.addObject("totalPage", projectPage.getTotalPage());
    }
    List<AlarmPolicyDo> policyDoList = alarmConfigService.getPolicyList();
    modelAndView.addObject("policyList", policyDoList);
    modelAndView.addObject("s_projectName", request.getProjectName());
    modelAndView.addObject("s_excutePoint", request.getExcutePoint());
    return modelAndView;
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/project_error_excution_list")
  public ModelAndView errorList(ProjectModelRequest request) {
    ModelAndView modelAndView = new ModelAndView("/log/errorlist");
    Page<ProjectModelVO> projectPage =
        logService.getErrorProjectModelList(request.getProjectName(), request.getExcutePoint());
    if (null != projectPage) {
      modelAndView.addObject("projectList", projectPage.getResult());
      modelAndView.addObject("totalCount", projectPage.getTotalCount());
      modelAndView.addObject("pageNo", projectPage.getPageNo());
      modelAndView.addObject("totalPage", projectPage.getTotalPage());
    }
    List<AlarmPolicyDo> policyDoList = alarmConfigService.getPolicyList();
    modelAndView.addObject("policyList", policyDoList);
    modelAndView.addObject("s_projectName", request.getProjectName());
    modelAndView.addObject("s_excutePoint", request.getExcutePoint());
    return modelAndView;
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/wait_project_excution_list")
  public ModelAndView waitList() {
    ModelAndView modelAndView = new ModelAndView("/log/waitList");
    List<ProjectModelVO> waitProjectList = logService.getWaitProjectModelList();
    List<AlarmPolicyDo> policyDoList = alarmConfigService.getPolicyList();
    modelAndView.addObject("projectList", waitProjectList);
    modelAndView.addObject("policyList", policyDoList);
    return modelAndView;
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/get_project")
  @ResponseBody
  public String getProject(String projectId) {
    return logService.getProjectById(projectId).toString0();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/add_project")
  @ResponseBody
  public String addProject(ProjectModelRequest project) {
    return logService.addProductModel(project).toString0();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/add_all_project")
  @ResponseBody
  public String addAllProject(ProjectModelRequest project) {
    return logService.addAllProductModel(project).toString0();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/edit_project")
  @ResponseBody
  public String updateProject(ProjectModelRequest project) {
    return logService.updateProductModel(project).toString0();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/delete_project")
  @ResponseBody
  public String deleteProject(String projectId) {
    return logService.deleteProject(projectId).toString0();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/project_excution_statistic")
  public ModelAndView statistic(String projectId) {
    ModelAndView modelAndView = new ModelAndView("/log/statistic");
    ProjectModel project = logService.queryProjectById(projectId);
    List<String> serverList = logService.getServerList(project);
    modelAndView.addObject("project", project);
    modelAndView.addObject("serverList", serverList);
    return modelAndView;
  }

  /**
   * 动态日志表
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/getOutInInfo")
  public String getOutInInfo(String projectName, String excutePoint, String serverName) {
    try {
      Map<Object, Object> map = new HashMap<Object, Object>();
      ActionModelVo m = new ActionModelVo();
      m.setProjectName(projectName);
      m.setExcutePoint(excutePoint);
      Timestamp x = new Timestamp((System.currentTimeMillis() - Constant.TIME_INTERVAL));
      map.put("x", x);
      m.setLogTime(x);
      Integer count = 0;
      Integer failCount = 0;
      Integer overTimeCount=0;
      if (StringUtils.isNotBlank(serverName)) {
        m.setServerName(serverName);
        ActionStatisticInfo info = new ActionStatisticInfo(m);
        count = info.partAllTimeMin();
        failCount = info.partFailTimeMin();
        overTimeCount=info.partOverTimeMin();
      } else {
        ActionStatisticInfo info = new ActionStatisticInfo(m);
        count = info.totalAllTimeMin();
        failCount = info.totalFailTimeMin();
        overTimeCount=info.totalOverTimeMin();
      }
      map.put("y", count.intValue());
      map.put("z", failCount.intValue());
      map.put("r", overTimeCount.intValue());
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      return null;
    }
  }

  /**
   * 获取动态日志初始信息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/getStaticLogInfo")
  public String getStaticLogInfo(String projectName, String excutePoint, String serverName) {
    try {
      Map<Object, Object> map = new HashMap<Object, Object>();
      ActionModelVo m = new ActionModelVo();
      m.setProjectName(projectName);
      m.setExcutePoint(excutePoint);
      List<Object> xList = Lists.newArrayList();
      List<Integer> yList = Lists.newArrayList();
      List<Integer> y2List = Lists.newArrayList();
      List<Integer> y3List = Lists.newArrayList();
      List<Chart> chartList = new ArrayList<Chart>();
      Chart chart = new Chart();
      Chart chart2 = new Chart();
      Chart chart3 = new Chart();
      chart.setName("日志条数");
      chart2.setName("异常条数");
      chart3.setName("超时条数");
      long timesnight = System.currentTimeMillis();
      if (StringUtils.isNotBlank(serverName)) {
        m.setServerName(serverName);
        for (int i = 0; i <= 19; i++) {
          long t = i * 60000;
          Timestamp ts = new Timestamp(timesnight - t - Constant.TIME_INTERVAL);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, ts);
          yList.add(0, info.partAllTimeMin());
          y2List.add(0, info.partFailTimeMin());
          y3List.add(0, info.partOverTimeMin());
        }
      } else {
        for (int i = 0; i <= 19; i++) {
          long t = i * 60000;
          Timestamp ts = new Timestamp(timesnight - t - Constant.TIME_INTERVAL);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, ts);
          yList.add(0, info.totalAllTimeMin());
          y2List.add(0, info.totalFailTimeMin());
          y3List.add(0, info.totalOverTimeMin());
        }
      }
      chart3.setData(y3List);
      chart2.setData(y2List);
      chart.setData(yList);
      chartList.add(chart);
      chartList.add(chart2);
      chartList.add(chart3);
      map.put("x", xList);
      map.put("y", chartList);
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      return null;
    }
  }

  /**
   * 24小时日志表
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/getOneDayLogInfo")
  public String getOneDayLogInfo(String projectName, String excutePoint, String serverName,
      Integer targetTime) {
    try {
      if (null == targetTime) targetTime = 0;
      Map<Object, Object> map = new HashMap<Object, Object>();
      ActionModelVo m = new ActionModelVo();
      m.setProjectName(projectName);
      m.setExcutePoint(excutePoint);
      List<String> xList = Lists.newArrayList();
      List<Integer> yAllList = Lists.newArrayList();
      List<Integer> yErrorList = Lists.newArrayList();
      List<Integer> yOverTimeList = Lists.newArrayList();
      List<Chart> chartList = new ArrayList<Chart>();
      Chart chartAll = new Chart();
      Chart chartError = new Chart();
      Chart chartOverTime=new Chart();
      chartAll.setName("日志条数");
      chartError.setName("异常条数");
      chartOverTime.setName("超时条数");
      long timesnight = DateUtil.getTimesnight(targetTime);
      if (StringUtils.isNotBlank(serverName)) {
        m.setServerName(serverName);
        for (int i = 0; i <= 24; i++) {
          long t = i * 3600 * 1000;
          Timestamp ts = new Timestamp(timesnight - t);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, info.getTimeHour());
          yAllList.add(0, info.partAllTimeHour());
          yErrorList.add(0, info.partFailTimeHour());
          yOverTimeList.add(0, info.partOverTimeHour());
        }
      } else {
        for (int i = 0; i <= 24; i++) {
          long t = i * 3600 * 1000;
          Timestamp ts = new Timestamp(timesnight - t);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, info.getTimeHour());
          yAllList.add(0, info.totalAllTimeHour());
          yErrorList.add(0, info.totalFailTimeHour());
          yOverTimeList.add(0, info.totalOverTimeHour());
        }
      }
      chartAll.setData(yAllList);
      chartError.setData(yErrorList);
      chartOverTime.setData(yOverTimeList);
      chartList.add(chartAll);
      chartList.add(chartError);
      chartList.add(chartOverTime);
      map.put("x", xList);
      map.put("y", chartList);
      map.put("preDay", targetTime - 1);
      map.put("nextDay", targetTime + 1);
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      return null;
    }
  }

  /**
   * 月度日志表
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/getOneMonthLogInfo")
  public String getOneMonthLogInfo(String projectName, String excutePoint, String serverName,
      Integer targetTime) {
    try {
      if (null == targetTime) targetTime = 0;
      Map<Object, Object> map = new HashMap<Object, Object>();
      ActionModelVo m = new ActionModelVo();
      m.setProjectName(projectName);
      m.setExcutePoint(excutePoint);
      List<String> xList = Lists.newArrayList();
      List<Integer> yAllList = Lists.newArrayList();
      List<Integer> yErrorList = Lists.newArrayList();
      List<Integer> yOverTimeList = Lists.newArrayList();
      List<Chart> chartList = new ArrayList<Chart>();
      Chart chartAll = new Chart();
      Chart chartError = new Chart();
      Chart chartOverTime=new Chart();
      chartAll.setName("日志条数");
      chartError.setName("异常条数");
      chartOverTime.setName("超时条数");
      long timesmorning = getTimesMonthmorning(targetTime);
      long timesnight = getTimesMonthnight(targetTime);
      if (StringUtils.isNotBlank(serverName)) {
        m.setServerName(serverName);
        for (int i = 0; i <= 31; i++) {
          long t = i * 86400 * 1000;
          long middletime = timesnight - t;
          if (middletime < timesmorning) break;
          Timestamp ts = new Timestamp(middletime);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, info.getTimeDate());
          yAllList.add(0, info.partAllTimeDate());
          yErrorList.add(0, info.partFailTimeDate());
          yOverTimeList.add(0, info.partOverTimeDate());
        }
      } else {
        for (int i = 0; i <= 24; i++) {
          long t = i * 86400 * 1000;
          long middletime = timesnight - t;
          if (middletime < timesmorning) break;
          Timestamp ts = new Timestamp(timesnight - t);
          m.setLogTime(ts);
          ActionStatisticInfo info = new ActionStatisticInfo(m);
          xList.add(0, info.getTimeDate());
          yAllList.add(0, info.totalAllTimeDate());
          yErrorList.add(0, info.totalFailTimeDate());
          yOverTimeList.add(0, info.totalOverTimeDate());
        }
      }
      chartAll.setData(yAllList);
      chartError.setData(yErrorList);
      chartOverTime.setData(yOverTimeList);
      chartList.add(chartAll);
      chartList.add(chartError);
      chartList.add(chartOverTime);
      map.put("x", xList);
      map.put("y", chartList);
      map.put("preMonth", targetTime - 1);
      map.put("nextMonth", targetTime + 1);
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      return null;
    }
  }

  /**
   * 获得本月第一天0点时间
   * 
   * @return
   */
  private long getTimesMonthmorning(int targetTime) {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + targetTime);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    return cal.getTimeInMillis();
  }

  /**
   * 获得本月最后一天24点时间
   * 
   * @return
   */
  private long getTimesMonthnight(int targetTime) {
    Calendar cal = Calendar.getInstance();
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,
        0);
    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + targetTime);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 24);
    return cal.getTimeInMillis();
  }

  /**
   * 执行点列表
   * 
   * @return
   */
  @RequestMapping("/log_list")
  public ModelAndView logList(ActionRequest request) {
    ModelAndView modelAndView = new ModelAndView("/log/logList");
    Page<ActionModel> actionPage = logService.getActionPage(request);
    modelAndView.addObject("projectName", request.getProjectName());
    modelAndView.addObject("excutePoint", request.getExcutePoint());
    modelAndView.addObject("serverName", request.getServerName());
    modelAndView.addObject("hasError", request.getHasError());
    modelAndView.addObject("lowerTime", request.getLowerTime());
    modelAndView.addObject("upperTime", request.getUpperTime());
    ProjectModel project = logService.queryProjectById(request.getProjectId());
    if (null != project) {
      List<String> serverList = logService.getServerList(project);
      modelAndView.addObject("serverList", serverList);
    }
    modelAndView.addObject("project", project);
    if (null != actionPage) {
      for (ActionModel action : actionPage.getResult()) {
        if (StringUtils.isBlank(action.getExcutePointShortName()))
          action.setExcutePointShortName(action.getExcutePoint().substring(
              action.getExcutePoint().lastIndexOf(".")));
      }
      modelAndView.addObject("actionList", actionPage.getResult());
      modelAndView.addObject("totalCount", actionPage.getTotalCount());
      modelAndView.addObject("pageNo", actionPage.getPageNo());
      modelAndView.addObject("totalPage", actionPage.getTotalPage());
    }
    return modelAndView;
  }
}
