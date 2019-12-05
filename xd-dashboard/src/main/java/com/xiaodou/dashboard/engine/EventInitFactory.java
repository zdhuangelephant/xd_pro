package com.xiaodou.dashboard.engine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.dashboard.dao.alarm.AlarmEventDao;
import com.xiaodou.dashboard.dao.alarm.AlarmPolicyDao;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.alarm.domain.AlarmPolicyDo;
import com.xiaodou.dashboard.model.alarm.local.EventPojo;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.dashboard.engine.EventProcesser.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 事件处理器
 * @version 1.0
 */
public class EventInitFactory {

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** refreshInterval 刷新间隔时间 */
  private Integer refreshInterval = 30;

  /** alarmDao 报警信息Dao */
  private AlarmEventDao eventDao = SpringWebContextHolder.getBean(AlarmEventDao.class);

  /** policyDao 报警策略Dao */
  private AlarmPolicyDao policyDao = SpringWebContextHolder.getBean(AlarmPolicyDao.class);

  /** alarmPolicyMap 报警策略Map */
  private Map<String, AlarmPolicyDo> alarmPolicyMap = Maps.newHashMap();

  /**
   * 构造方法
   * 
   * @param dataSource
   * @param register
   */
  public void init() {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        refreshAlarmPolicyMap();
        refreshAlarmEventContainer();
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("刷新报警事件数据异常.", t);
      }
    }, initialDelay, refreshInterval, TimeUnit.SECONDS);

  }

  private void refreshAlarmPolicyMap() {
    List<AlarmPolicyDo> policyDoList = policyDao.getAllPolicyModel();
    if (null == policyDoList || policyDoList.size() == 0) return;
    for (AlarmPolicyDo policy : policyDoList)
      alarmPolicyMap.put(policy.getAlarmPolicyId(), policy);
  }

  private void refreshAlarmEventContainer() {
    if (null == alarmPolicyMap || alarmPolicyMap.size() == 0) return;
    List<AlarmEventDo> eventDoList = eventDao.getAllEventModel();
    if (null == eventDoList || eventDoList.size() == 0) return;

    for (AlarmEventDo model : eventDoList) {
      if (null == model || StringUtils.isOrBlank(model.getModule(), model.getName())
          || null == model.getAlarmPolicyId()) continue;
      AlarmPolicyDo policy = alarmPolicyMap.get(model.getAlarmPolicyId());
      if (null == policy) continue;
      EventPojo pojo = new EventPojo();
      pojo.setModule(model.getModule());
      pojo.setName(model.getName());
      pojo.setRate(model.getRate());
      pojo.setThreshold(model.getThreshold());
      pojo.setStarttime(policy.getStarttime());
      pojo.setEndtime(policy.getEndtime());
      pojo.setMail(policy.getMail());
      pojo.setMessage(policy.getMessage());
      pojo.setDingURL(policy.getDingURL());
      EventContainer.getContainer().add(pojo);
    }
  }

}
