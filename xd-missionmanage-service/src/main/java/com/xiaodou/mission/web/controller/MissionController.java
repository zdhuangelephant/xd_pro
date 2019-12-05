package com.xiaodou.mission.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.EventListenerHolder;
import com.xiaodou.mission.service.EventProcessService;
import com.xiaodou.mission.service.MissionService;
import com.xiaodou.mission.vo.alarm.TaskListAlarm;
import com.xiaodou.mission.vo.request.EventRequest;
import com.xiaodou.mission.vo.request.MedalListRequest;
import com.xiaodou.mission.vo.request.TaskCompleteStatusRequest;
import com.xiaodou.mission.vo.request.TaskListRequest;
import com.xiaodou.mission.vo.request.TaskStatusRequest;
import com.xiaodou.mission.vo.request.UnReachedTaskCountRequest;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.mission.web.controller.MissionController.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月8日
 * @description 任务系统控制器
 * @version 1.0
 */
@RequestMapping("mission")
@Controller("missionController")
public class MissionController extends BaseController {

  @Resource
  MissionService missionService;

  @Resource
  EventProcessService eventProcessService;

  /**
   * 触发事件方法
   * 
   * @param eventPojo 触发事件
   * @return 结果
   * @throws Exception
   */
  @RequestMapping("on_event")
  @ResponseBody
  public String onEvent(EventRequest eventPojo) throws Exception {
    try {
      String requestJson = JedisUtil.getStringFromJedis(eventPojo.getEventTag());
      if (StringUtils.isJsonNotBlank(requestJson)) {
        return new ResultInfo(ResultType.SUCCESS).toString0();
      }
      JedisUtil.addStringToJedis(eventPojo.getEventTag(), eventPojo.getEventTag(), 300);
      return FastJsonUtil.toJson(eventProcessService.onEvent(eventPojo));
    } catch (Exception e) {
      JedisUtil.delKeyFromJedis(eventPojo.getEventTag());
      LoggerUtil.error("处理事件异常", e);
      return new ResultInfo(ResultType.SYSFAIL).toString0();
    }
  }

  @RequestMapping("medal_list")
  @ResponseBody
  public String medalList(MedalListRequest request) {
    return missionService.medalList(request).toString0();
  }

  @RequestMapping("task_list")
  @ResponseBody
  public String taskList(TaskListRequest request) {
    try {
      return missionService.taskList(request).toString0();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new TaskListAlarm(e.getMessage()));
      throw e;
    }
  }

  @RequestMapping("un_reach_task")
  @ResponseBody
  public String unReachedTaskCount(UnReachedTaskCountRequest request) {
    return missionService.unReachedTaskCount(request).toString0();
  }

  @RequestMapping("task_status")
  @ResponseBody
  public String taskStatus(TaskStatusRequest request) {
    return missionService.taskStatus(request).toString0();
  }

  @RequestMapping("show_all")
  @ResponseBody
  public String showAll() {
    return FastJsonUtil.toJson(EventListenerHolder.getInUse());
  }


  @RequestMapping("task_complete_list")
  @ResponseBody
  public String taskCompleteList(TaskCompleteStatusRequest request) {
    return missionService.taskCompleteStatus(request).toString0();
  }

  public static void main(String[] args) {
    System.out.println(RandomUtil.randomString(20));
  }
}
