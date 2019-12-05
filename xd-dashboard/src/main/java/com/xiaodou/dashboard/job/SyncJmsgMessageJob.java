package com.xiaodou.dashboard.job;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.dao.jmsg.JmsgMessageBodyDao;
import com.xiaodou.dashboard.dao.log.JmsgMessageDao;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageBody;
import com.xiaodou.dashboard.model.log.JmsgMessageModel;
import com.xiaodou.dashboard.service.alarm.AlarmService;
import com.xiaodou.dashboard.util.DashboardLoggerUtil;
import com.xiaodou.dashboard.util.EnvironMentUtil;
import com.xiaodou.dashboard.util.log.SyncJmsgEntity;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

@Component("syncJmsgMessageJob")
public class SyncJmsgMessageJob {

  /** isReset 昨天间隔标记 */
  private Boolean isReset = true;
  @Resource
  AlarmService alarmService;
  @Resource
  JmsgMessageDao jmsgMessageDao;
  @Resource
  JmsgMessageBodyDao jmsgMessageBodyDao;
  private Integer unknown = 0;
  private String messageBodyFail = "1";
  private Long[] timeInterval = {0l, 1l * 60 * 1000, 5l * 60 * 1000, 10l * 60 * 1000,
      20l * 60 * 1000, 30l * 60 * 1000, 60l * 60 * 1000};

  @PostConstruct
  public void run() {
    if (EnvironMentUtil.isJobNode()) {
      /** initialDelay 初始调度任务时机 */
      Integer initialDelay = 0;

      /** jobScheduledDelay 任务间隔延迟 */
      Integer jobScheduledDelay = Integer.parseInt(ConfigProp.getParams("job.schedule.delay"));

      /** 同步异步消息状态任务 */
      SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
        @Override
        public void doMain() {
          if (checkTime()) {
            clearMessage();
            SyncJmsgEntity logEntity = new SyncJmsgEntity();
            checkMessage(logEntity);
            if (logEntity.isEmpty()) {
              return;
            }
            logEntity.setDesc();
            DashboardLoggerUtil.syncJmsg(logEntity);
            isReset = false;
          } else {
            isReset = true;
          }
        }

        private boolean checkTime() {
          long now = System.currentTimeMillis();
          Timestamp curTime = new Timestamp(now);
          Long startTime = DateUtil.getStartTimeOfDay(curTime);
          Long endTime = DateUtil.getLastTimeOfDay(curTime);
          long endTargetTime = now + 30 * 60 * 1000;
          long startTargetTime = now - 30 * 60 * 1000;
          return startTime < startTargetTime && endTargetTime < endTime;
        }

        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("同步异步消息状态异常.", t);
        }
      }, initialDelay, jobScheduledDelay, TimeUnit.SECONDS);
    }
  }

  protected void clearMessage() {
    IDeleteParam param = new DeleteParam();
    MongoFieldParam customTagParam = new MongoFieldParam();
    customTagParam.setScope(Scope.EXIST);
    customTagParam.setValue(Boolean.FALSE);
    param.addInput("customTag", customTagParam);
    jmsgMessageDao.deleteEntityByCond(param);
  }

  private void checkMessage(SyncJmsgEntity logEntity) {
    IQueryParam param = new QueryParam();
    MongoFieldParam customTagParam = new MongoFieldParam();
    customTagParam.setScope(Scope.EXIST);
    customTagParam.setValue(Boolean.TRUE);
    param.addInput("customTag", customTagParam);
    MongoFieldParam nextSendTime = new MongoFieldParam();
    nextSendTime.setScope(Scope.LE);
    nextSendTime.setValue(System.currentTimeMillis());
    param.addInput("nextSendTime", nextSendTime);
    param.addInput("status", unknown);
    param.addOutputs(CommUtil.getAllField(JmsgMessageModel.class));
    param.addSort("nextSendTime", Sort.ASC);
    Page<JmsgMessageModel> messagePage = new Page<JmsgMessageModel>();
    messagePage.setPageSize(1000);
    messagePage = jmsgMessageDao.findEntityListByCond(param, messagePage);
    if (null == messagePage || null == messagePage.getResult() || messagePage.getResult().isEmpty()) {
      return;
    }
    Map<String, JmsgMessageModel> messageMap = Maps.newHashMap();
    List<String> customTagList = Lists.newArrayList();
    List<String> successTagList = Lists.newArrayList();
    Set<String> alarmTagSet = Sets.newHashSet();
    for (JmsgMessageModel message : messagePage.getResult()) {
      String customTag = message.getCustomTag();
      if (StringUtils.isBlank(customTag)) {
        continue;
      }
      messageMap.put(customTag, message);
      customTagList.add(customTag);
    }
    SimpleDateFormat f = new SimpleDateFormat("_yy_MM_dd");
    String surfix = f.format(new Date());
    param = new QueryParam();
    param.addInput("surfix", surfix);
    if (null != customTagList && customTagList.size() > 0) {
      param.addInput("customTagList", customTagList);
    }
    param.addOutput("customTag", StringUtils.EMPTY);
    param.addOutput("result", StringUtils.EMPTY);
    Page<JmsgMessageBody> messageBodyPage = jmsgMessageBodyDao.findEntityListByCond(param, null);
    if (null != messageBodyPage && null != messageBodyPage.getResult()
        && messageBodyPage.getResult().size() > 0) {
      for (JmsgMessageBody body : messageBodyPage.getResult()) {
        if (null == body || StringUtils.isBlank(body.getCustomTag())) {
          continue;
        }
        if (messageBodyFail.equals(body.getResult())) {
          markFailResend(messageMap.get(body.getCustomTag()), alarmTagSet);
        } else {
          successTagList.add(body.getCustomTag());
        }
        messageMap.remove(body.getCustomTag());
      }
      markSuccess(successTagList);
    }
    // 判断昨天的记录
    if (isReset && messageMap.size() > 0) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(new Date());
      cal.add(Calendar.DATE, -1);
      surfix = f.format(cal.getTime());
      param = new QueryParam();
      param.addInput("surfix", surfix);
      if (null != customTagList && customTagList.size() > 0) {
        param.addInput("customTagList", customTagList);
      }
      param.addOutput("customTag", StringUtils.EMPTY);
      param.addOutput("result", StringUtils.EMPTY);
      messageBodyPage = jmsgMessageBodyDao.findEntityListByCond(param, null);
      if (null != messageBodyPage && null != messageBodyPage.getResult()
          && messageBodyPage.getResult().size() > 0) {
        for (JmsgMessageBody body : messageBodyPage.getResult()) {
          if (null == body || StringUtils.isBlank(body.getCustomTag())) {
            continue;
          }
          if (messageBodyFail.equals(body.getResult())) {
            markFailResend(messageMap.get(body.getCustomTag()), alarmTagSet);
          } else {
            successTagList.add(body.getCustomTag());
          }
          messageMap.remove(body.getCustomTag());
        }
        markSuccess(successTagList);
      }
    }
    if (messageMap.size() == 0) {
      return;
    }
    for (JmsgMessageModel model : messageMap.values()) {
      markUnProcessResend(model, alarmTagSet);
    }
    if (!alarmTagSet.isEmpty()) {
      syncJmsgAlarm(alarmTagSet);
    }
    // 记录同步操作日志

    logEntity.setCustomTagList(customTagList);
    logEntity.setSuccessTagList(successTagList);
    logEntity.setAlarmTagSet(alarmTagSet);
  }

  private void syncJmsgAlarm(Set<String> alarmTagSet) {
    SyncJmsgAlarmPojo pojo = new SyncJmsgAlarmPojo(alarmTagSet);
    alarmService.alarm(pojo);
  }

  private void markSuccess(List<String> customTagList) {
    if (null == customTagList || customTagList.isEmpty()) return;
    List<String> toDeleteCtList = Lists.newArrayList();
    for (int i = 0; i < customTagList.size(); i++) {
      toDeleteCtList.add(customTagList.get(i));
      if (toDeleteCtList.size() > 50) {
        MongoFieldParam field = new MongoFieldParam(toDeleteCtList, Scope.IN);
        IQueryParam param = new QueryParam();
        param.addInput("customTag", field);
        jmsgMessageDao.deleteEntityByCond(param);
        toDeleteCtList.clear();
      }
      if (!toDeleteCtList.isEmpty()) {
        MongoFieldParam field = new MongoFieldParam(toDeleteCtList, Scope.IN);
        IQueryParam param = new QueryParam();
        param.addInput("customTag", field);
        jmsgMessageDao.deleteEntityByCond(param);
      }
    }
  }

  private void markFailResend(JmsgMessageModel message, Set<String> alarmTagSet) {
    markResend0(null, message, alarmTagSet);
  }

  private void markUnProcessResend(JmsgMessageModel message, Set<String> alarmTagSet) {
    markResend0(2, message, alarmTagSet);
  }

  private void markResend0(Integer resendTime, JmsgMessageModel message, Set<String> alarmTagSet) {
    if (null == message) return;
    JmsgMessageModel updateMessage = new JmsgMessageModel();
    updateMessage.setCustomTag(message.getCustomTag());
    Integer retryTimes = null == message.getRetryTimes() ? 1 : message.getRetryTimes() + 1;
    Long nextSendTime = System.currentTimeMillis();
    if (retryTimes > resendTime) {
      alarmTagSet.add(message.getCustomTag());
      resend(message);
    }
    if (retryTimes > 6) {
      nextSendTime += 60l * 60 * 1000;
    } else {
      nextSendTime += timeInterval[retryTimes];
    }
    updateMessage.setRetryTimes(retryTimes);
    updateMessage.setNextSendTime(nextSendTime);
    jmsgMessageDao.updateEntityById(updateMessage);
  }

  @SuppressWarnings("unchecked")
  private void resend(JmsgMessageModel message) {
    if (null == message.getMessageBody()) return;
    DefaultMessage msg = message.getMessageBody();
    AbstractMessagePojo messagePojo = new AbstractMessagePojo(msg.getMessageName());
    messagePojo.setCustomTag(msg.getCustomTag());
    Map<String, String> map = FastJsonUtil.fromJson(msg.getTransferObjectJSON(), HashMap.class);
    messagePojo.setTransferObject(map);
    RabbitMQSender.getInstance().send(messagePojo);
  }

}
