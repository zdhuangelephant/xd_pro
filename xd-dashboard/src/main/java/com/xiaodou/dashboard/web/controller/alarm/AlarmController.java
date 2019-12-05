package com.xiaodou.dashboard.web.controller.alarm;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;
import com.xiaodou.dashboard.request.AlarmRequestPojo;
import com.xiaodou.dashboard.service.alarm.AlarmConfigService;
import com.xiaodou.dashboard.service.alarm.AlarmService;
import com.xiaodou.dashboard.vo.alarm.request.AlarmEventRequest;
import com.xiaodou.dashboard.vo.alarm.request.PolicyRequest;

/**
 * @name @see com.xiaodou.dashboard.web.controller.AlarmController.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警服务controller
 * @version 1.0
 */
@Controller
@RequestMapping("/dashboard")
public class AlarmController {

  /** alarmService 报警服务service */
  @Resource
  AlarmService alarmService;

  /** alarmConfigService 报警配置service */
  @Resource
  AlarmConfigService alarmConfigService;

  /**
   * 报警接口
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/alarm")
  @ResponseBody
  public void alarm(AlarmRequestPojo pojo) {
    alarmService.filterAlarm(pojo);
  }

  /**
   * 报警规则列表
   * 
   * @return
   */
  @RequestMapping("/alarmpolicy/list")
  public ModelAndView policyList() {
    ModelAndView mv = new ModelAndView("/alarm/policyList");
    List<AlarmPolicyDo> policyDoList = alarmConfigService.getPolicyList();
    mv.addObject("policyList", policyDoList);
    return mv;
  }

  /**
   * 获取报警规则详情
   * 
   * @return
   */
  @RequestMapping("/alarmpolicy/get")
  @ResponseBody
  public String getPolicy(String policyId) {
    return alarmConfigService.getPolicyDo(policyId).toString0();
  }

  /**
   * 插入报警规则
   * 
   * @param policy
   * @return
   */
  @RequestMapping("/alarmpolicy/add")
  @ResponseBody
  public String addPolicy(PolicyRequest policy) {
    return alarmConfigService.addPolicyDo(policy).toString0();
  }

  /**
   * 更新报警规则
   * 
   * @param policy
   * @return
   */
  @RequestMapping("/alarmpolicy/update")
  @ResponseBody
  public String updatePolicy(PolicyRequest policy) {
    return alarmConfigService.updatePolicyDo(policy).toString0();
  }

  /**
   * 删除报警规则
   * 
   * @param policyId
   * @return
   */
  @RequestMapping("/alarmpolicy/delete")
  @ResponseBody
  public String deletePolicy(String policyId) {
    return alarmConfigService.deletePolicyDo(policyId).toString0();
  }

  /**
   * 报警规则列表
   * 
   * @return
   */
  @RequestMapping("/alarm_event/list")
  public ModelAndView eventList() {
    ModelAndView mv = new ModelAndView("/alarm/eventList");
    List<AlarmEventDo> excptionEventDoList = alarmConfigService.getExceptionEventList();
    List<AlarmEventDo> monitorEventDoList = alarmConfigService.getMonitorEventList();
    List<AlarmPolicyDo> policyDoList = alarmConfigService.getPolicyList();
    Map<String, AlarmPolicyDo> policyMap = getPolicyMap(policyDoList);
    packageEventPolicy(excptionEventDoList, policyMap);
    packageEventPolicy(monitorEventDoList, policyMap);
    mv.addObject("excptionEventList", excptionEventDoList);
    mv.addObject("monitorEventList", monitorEventDoList);
    mv.addObject("policyList", policyDoList);
    return mv;
  }

  /**
   * 封装报警策略
   * 
   * @param eventDoList
   * @param policyMap
   */
  private void packageEventPolicy(List<AlarmEventDo> eventDoList,
      Map<String, AlarmPolicyDo> policyMap) {
    if (null == eventDoList || eventDoList.size() == 0 || null == policyMap
        || policyMap.size() == 0) return;
    for (AlarmEventDo eventDo : eventDoList) {
      AlarmPolicyDo alarmPolicyDo = policyMap.get(eventDo.getAlarmPolicyId());
      if (null != alarmPolicyDo) eventDo.setAlarmPolicyName(alarmPolicyDo.getAlarmPolicyName());
    }
  }

  /**
   * 获取报警策略Map
   * 
   * @param policyDoList 报警策略列表
   * @return 报警策略Map
   */
  private Map<String, AlarmPolicyDo> getPolicyMap(List<AlarmPolicyDo> policyDoList) {
    if (null == policyDoList || policyDoList.size() == 0) return null;
    Map<String, AlarmPolicyDo> policyMap = Maps.newHashMap();
    for (AlarmPolicyDo policyDo : policyDoList)
      policyMap.put(policyDo.getAlarmPolicyId(), policyDo);
    return policyMap;
  }

  /**
   * 获取报警规则详情
   * 
   * @return
   */
  @RequestMapping("/alarm_event/get")
  @ResponseBody
  public String getEvent(String eventId) {
    return alarmConfigService.getEventDo(eventId).toString0();
  }

  /**
   * 插入报警规则
   * 
   * @param policy
   * @return
   */
  @RequestMapping("/alarm_event/add")
  @ResponseBody
  public String addEvent(AlarmEventRequest event) {
    return alarmConfigService.addEventDo(event).toString0();
  }

  /**
   * 更新报警规则
   * 
   * @param policy
   * @return
   */
  @RequestMapping("/alarm_event/update")
  @ResponseBody
  public String updateEvent(AlarmEventRequest event) {
    return alarmConfigService.updateEventDo(event).toString0();
  }

  /**
   * 删除报警规则
   * 
   * @param policyId
   * @return
   */
  @RequestMapping("/alarm_event/delete")
  @ResponseBody
  public String deleteEvent(String eventId) {
    return alarmConfigService.deleteEventDo(eventId).toString0();
  }
}
