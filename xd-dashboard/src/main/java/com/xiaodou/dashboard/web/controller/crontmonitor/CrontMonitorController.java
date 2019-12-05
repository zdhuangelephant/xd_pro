package com.xiaodou.dashboard.web.controller.crontmonitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApi;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;
import com.xiaodou.dashboard.service.crontmonitor.CrontMonitorService;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiListRequest;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiLogListRequest;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiOperationRequest;
import com.xiaodou.dashboard.vo.crontmonitor.response.MonitorApiLogResponse;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.dashboard.web.controller.crontmonitor.CrontMonitorController.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 主动监控服务Controller
 * @version 1.0
 */
@Controller("crontMonitorController")
@RequestMapping("/cront_monitor")
public class CrontMonitorController extends BaseController {

  /** crontMonitorService 主动监控服务Service */
  @Resource
  CrontMonitorService crontMonitorService;

  /**
   * @param request
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView crontmonitorList(MonitorApiListRequest request) {
    ModelAndView view = new ModelAndView("/crontmonitor/list");
    List<MonitorApi> monitorApiList = crontMonitorService.crontMonitorList(request);
    if (null != monitorApiList && monitorApiList.size() > 0) {
      view.addObject("list", monitorApiList);
    }
    return view;
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/do_add")
  @ResponseBody
  public String crontDoAdd(MonitorApiOperationRequest request) {
    return crontMonitorService.addCrontMonitor(request).toString();
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/edit")
  @ResponseBody
  public String crontmonitorEdit(MonitorApiOperationRequest request) {
    MonitorApi api = crontMonitorService.queryCrontMonitor(request);
    return FastJsonUtil.toJson(api);
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/do_edit")
  @ResponseBody
  public String crontDoEdit(MonitorApiOperationRequest request) {
    return crontMonitorService.editCrontMonitor(request).toString();
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String crontDelete(MonitorApiOperationRequest request) {
    crontMonitorService.deleteCrontMonitor(request);
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * @return
   */
  @RequestMapping("/monitor_log_list")
  public ModelAndView crontmonitorScheduleList(MonitorApiLogListRequest request) {
    ModelAndView view = new ModelAndView("/crontmonitor/monitorLogList");
    MonitorApiOperationRequest req = new MonitorApiOperationRequest();
    req.setId(request.getApiId());
    MonitorApi api = crontMonitorService.queryCrontMonitor(req);
    if (null == api || StringUtils.isBlank(api.getId())) {
      return view;
    }
    List<MonitorApiLog> crontmonitorList = crontMonitorService.monitorApiLogList(request);
    if (null == crontmonitorList || crontmonitorList.isEmpty()) {
      return view;
    }
    List<MonitorApiLogResponse> response = Lists.newArrayList();
    for (MonitorApiLog log : crontmonitorList) {
      if (null == log || StringUtils.isBlank(log.getApiId()) || !log.getApiId().equals(api.getId())) {
        continue;
      }
      MonitorApiLogResponse logResponse = new MonitorApiLogResponse(log);
      logResponse.setApiName(api.getName());
      response.add(logResponse);
    }
    view.addObject("list", response);
    return view;
  }

}
