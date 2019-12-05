package com.xiaodou.dashboard.service.alarm;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.dao.alarm.AlarmEventDao;
import com.xiaodou.dashboard.dao.alarm.AlarmPolicyDao;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;
import com.xiaodou.dashboard.vo.alarm.request.AlarmEventRequest;
import com.xiaodou.dashboard.vo.alarm.request.PolicyRequest;
import com.xiaodou.dashboard.vo.alarm.response.AlarmEventResponse;
import com.xiaodou.dashboard.vo.alarm.response.PolicyResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.dashboard.service.alarm.AlarmConfigService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月9日
 * @description 报警配置Service
 * @version 1.0
 */
@Service("alarmConfigService")
public class AlarmConfigService {

  @Resource
  AlarmPolicyDao alarmPolicyDao;

  @Resource
  AlarmEventDao alarmEventDao;

  public List<AlarmPolicyDo> getPolicyList() {
    return alarmPolicyDao.getAllPolicyModel();
  }

  public PolicyResponse addPolicyDo(PolicyRequest policy) {
    PolicyResponse response = new PolicyResponse(ResultType.SUCCESS);
    AlarmPolicyDo policyDo = policy.getPolicyDo();
    policyDo.setAlarmPolicyId(UUID.randomUUID().toString());
    policyDo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    alarmPolicyDao.insert(policyDo);
    response.setPolicy(policyDo);
    return response;
  }

  public PolicyResponse getPolicyDo(String policyId) {
    PolicyResponse response = new PolicyResponse(ResultType.SUCCESS);
    response.setPolicy(alarmPolicyDao.getPolicyModelById(policyId));
    return response;
  }

  public PolicyResponse updatePolicyDo(PolicyRequest policy) {
    if (null == policy || StringUtils.isBlank(policy.getAlarmPolicyId()))
      return new PolicyResponse(ResultType.SYSFAIL);
    PolicyResponse response = new PolicyResponse(ResultType.SUCCESS);
    AlarmPolicyDo policyDo = policy.getPolicyDo();
    alarmPolicyDao.updatePolicyModelById(policyDo);
    return response;
  }

  public PolicyResponse deletePolicyDo(String policyId) {
    PolicyResponse response = new PolicyResponse(ResultType.SUCCESS);
    alarmPolicyDao.deletePolicyModelById(policyId);
    return response;
  }

  public List<AlarmEventDo> getExceptionEventList() {
    return alarmEventDao.getEventModelByType(Constant.ALARM_EXCEPTION_TYPE);
  }

  public List<AlarmEventDo> getMonitorEventList() {
    return alarmEventDao.getEventModelByType(Constant.ALARM_MONITOR_TYPE);
  }

  public AlarmEventResponse getEventDo(String eventId) {
    AlarmEventResponse response = new AlarmEventResponse(ResultType.SUCCESS);
    response.setEvent(alarmEventDao.getEventModelById(eventId));
    return response;
  }

  public AlarmEventResponse addEventDo(AlarmEventRequest event) {
    if (StringUtils.isOrBlank(event.getModule(), event.getName()))
      return new AlarmEventResponse(ResultType.SYSFAIL);
    AlarmEventDo eventDo = alarmEventDao.getEventModel(event.getModule(), event.getName());
    if (null != eventDo) return new AlarmEventResponse(ResultType.SYSFAIL);
    AlarmEventResponse response = new AlarmEventResponse(ResultType.SUCCESS);
    eventDo = event.getEventDo();
    eventDo.setAlarmEventId(UUID.randomUUID().toString());
    eventDo.setType(Constant.ALARM_EXCEPTION_TYPE);
    eventDo.setCount(0d);
    eventDo.setSystime(System.currentTimeMillis());
    alarmEventDao.insert(eventDo);
    response.setEvent(eventDo);
    return response;
  }

  public AlarmEventResponse updateEventDo(AlarmEventRequest event) {
    if (null == event || StringUtils.isBlank(event.getAlarmEventId()))
      return new AlarmEventResponse(ResultType.SYSFAIL);
    AlarmEventResponse response = new AlarmEventResponse(ResultType.SUCCESS);
    AlarmEventDo eventDo = event.getEventDo();
    alarmEventDao.updateEventModelById(eventDo);
    return response;
  }

  public AlarmEventResponse deleteEventDo(String eventId) {
    AlarmEventResponse response = new AlarmEventResponse(ResultType.SUCCESS);
    alarmEventDao.deleteEventModelById(eventId);
    return response;
  }

}
