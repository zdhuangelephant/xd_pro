package com.xiaodou.dashboard.web.controller.crontab;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.enums.CommonEnums;
import com.xiaodou.dashboard.service.crontab.CrontabService;
import com.xiaodou.dashboard.vo.crontab.request.CrontabConfigOperationRequest;
import com.xiaodou.dashboard.vo.crontab.request.CrontabListRequest;
import com.xiaodou.dashboard.vo.crontab.request.CrontabScheduleListRequest;
import com.xiaodou.dashboard.vo.crontab.response.CrontabConfigModel;
import com.xiaodou.dashboard.vo.crontab.response.CrontabJobLogModel;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.dashboard.web.controller.crontab.CrontabController.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月2日
 * @description 任务调度controller
 * @version 1.0
 */
@Controller("crontabController")
@RequestMapping("/crontab")
public class CrontabController extends BaseController {

  /** crontabService 定时任务Service */
  @Resource
  CrontabService crontabService;

  /**
   * @param request
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView crontabList(CrontabListRequest request) {
    ModelAndView view = new ModelAndView("/crontab/list");
    List<CrontabConfigModel> crontabList = crontabService.crontabList(request);
    view.addObject("businessTypeList", Lists.newArrayList(CommonEnums.BussinessCode.values()));
    if (null != crontabList && crontabList.size() > 0) {
      view.addObject("list", crontabList);
    }
    return view;
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/doAdd")
  @ResponseBody
  public String crontDoAdd(CrontabConfigOperationRequest request) {
    return crontabService.addCrontab(request).toString();
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/edit")
  @ResponseBody
  public String crontabEdit(CrontabConfigOperationRequest request) {
    CrontabConfigModel config = crontabService.queryCrontab(request);
    return FastJsonUtil.toJson(config);
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/doEdit")
  @ResponseBody
  public String crontDoEdit(CrontabConfigOperationRequest request) {
    return crontabService.editCrontab(request).toString();
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String crontDelete(CrontabConfigOperationRequest request) {
    crontabService.deleteCrontab(request);
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * @return
   */
  @RequestMapping("/schedule_list")
  public ModelAndView crontabScheduleList(CrontabScheduleListRequest request) {
    ModelAndView view = new ModelAndView("/crontab/scheduleList");
    List<CrontabJobLogModel> crontabList = crontabService.crontabScheduleList(request);
    if (null != crontabList && crontabList.size() > 0) {
      view.addObject("list", crontabList);
    }
    return view;
  }

}
