package com.xiaodou.oms.service.message;

import java.lang.reflect.Field;

import org.apache.commons.httpclient.methods.PostMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.oms.util.log.AlarmEntityType;
import com.xiaodou.oms.util.model.AgentMessageSendEntity;
import com.xiaodou.oms.util.model.MessageRecord;
import com.xiaodou.oms.vo.agent.request.SendMessageRequest;
import com.xiaodou.oms.vo.agent.response.SendMessageResponse;

/**
 * 直接发异步消息到各个业务线
 * <p/>
 * Date: 2014/12/2 Time: 15:27
 * 
 * @author Tian.Dong
 */
public class MessageAgentSendService {
  public static void send(String productLine, MessageRecord messageRecord) {
    // message_messageConsumeUrl_05
    String url = ConfigProp.getParams("message_messageConsumeUrl_" + productLine);
    if (url == null) {
      return;
    }

    PostMethod postMethod = null;
    try {
      String sendContent = transform(messageRecord);
      postMethod =
          HttpMethodUtil.getPostMethod(url, "application/x-www-form-urlencoded", "utf-8",
              sendContent);
    } catch (Exception e) {
      LoggerUtil.error("MessageAgentSendService.transform异常", e);
      logAndAlarmException(messageRecord);
      return;
    }

    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(5000);
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    if (result.getHttpStatue() != 200) {
      logAndAlarmNot200(messageRecord, result);
      return;
    }

    SendMessageResponse response =
        FastJsonUtil.fromJson(result.getContent(), SendMessageResponse.class);
    logResponseAndAlarm(messageRecord, response);
  }


  // 抛异常 报警
  private static void logAndAlarmException(MessageRecord messageRecord) {
    AgentMessageSendEntity entity = new AgentMessageSendEntity();
    entity.setTag(messageRecord.getTag());
    entity.setRetCode("-1");
    entity.setRetDesc("异常");
    OrderLoggerUtil.loggerAgentMessageSendInfo(entity);
    // 报警
    // TODO 报警
    // AlarmEntity alarmEntity = new AlarmEntity(AlarmEntityType.ASYNCMESSAGE_LOST_ERROR.getType());
    // alarmEntity.setUrl("MessageAgentSendService.transform异常");
    // alarmEntity.setAlertMessage(FastJsonUtil.toJson(messageRecord));
    // LoggerUtil.alarmInfo(alarmEntity);
  }

  // http 返回不是200 记录、报警
  private static void logAndAlarmNot200(MessageRecord messageRecord, HttpResult result) {
    AgentMessageSendEntity entity = new AgentMessageSendEntity();
    entity.setTag(messageRecord.getTag());
    entity.setRetCode("-1");
    entity.setRetDesc("http状态码非200|" + result.getHttpStatue());
    OrderLoggerUtil.loggerAgentMessageSendInfo(entity);
    // 报警
    // TODO 报警
    // AlarmEntity alarmEntity = new AlarmEntity(AlarmEntityType.ASYNCMESSAGE_LOST_ERROR.getType());
    // alarmEntity.setUrl("http状态码非200");
    // alarmEntity.setAlertMessage(FastJsonUtil.toJson(messageRecord));
    // LoggerUtil.alarmInfo(alarmEntity);
  }

  // 记录返回值 并判断是否报警
  private static void logResponseAndAlarm(MessageRecord messageRecord, SendMessageResponse response) {
    AgentMessageSendEntity entity = new AgentMessageSendEntity();
    entity.setTag(messageRecord.getTag());
    entity.setRetCode(String.valueOf(response.getResponseCode()));
    entity.setRetDesc(response.getExceptionMessgage());
    OrderLoggerUtil.loggerAgentMessageSendInfo(entity);
    if (response.getResponseCode() != 0) {
      alarmResponseNot0(messageRecord);
    }
  }

  // 返回值不是0 报警
  private static void alarmResponseNot0(MessageRecord messageRecord) {
    // 报警
    // TODO 报警
    // AlarmEntity alarmEntity = new AlarmEntity(AlarmEntityType.ASYNCMESSAGE_LOST_ERROR.getType());
    // alarmEntity.setUrl("返回值非0");
    // alarmEntity.setAlertMessage(FastJsonUtil.toJson(messageRecord));
    // LoggerUtil.alarmInfo(alarmEntity);
  }


  // 生成request str
  private static String transform(MessageRecord messageRecord) throws Exception {
    SendMessageRequest sendMessageRequest = new SendMessageRequest();
    sendMessageRequest.setTag(messageRecord.getTag());
    sendMessageRequest.setMessageName(messageRecord.getMessageName());
    sendMessageRequest.setMessage(messageRecord.getContent());
    return transform(sendMessageRequest);
  }

  // 生成request str
  private static String transform(SendMessageRequest obj) throws Exception {
    StringBuilder formString = new StringBuilder();
    for (Field field : obj.getClass().getDeclaredFields()) {
      String key = field.getName();
      field.setAccessible(true);
      String value = (String) field.get(obj);
      formString.append(key).append("=");
      formString.append(value).append("&");
    }
    formString.deleteCharAt(formString.length() - 1);
    return formString.toString();
  }
}
