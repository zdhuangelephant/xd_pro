package com.xiaodou.message.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.utils.JPushUtil;
import com.xiaodou.message.utils.http.SendMsByJPush;
import com.xiaodou.message.vo.MessagePushInfo;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;

public class PushExample {
  protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

  public static final String TITLE = "Test from API example";
  public static final String ALERT = "Test from API Example - alert";
  public static final String MSG_CONTENT = "Test from API Example - msgContent";
  public static final String REGISTRATION_ID = "0900e8d85ef";
  public static final String TAG = "tag_api";

  public static void main(String[] args) {
    // testSendPush();
    testSendMsByJPush();
  }


  public static void testSendPush() {
    JPushClient jpushClient = JPushUtil.getClient();
    // JPushClient jpushClient = JPushUtil.getClient("2"."test");
    System.out.println("jpushClient:" + jpushClient);
    PushPayload payload = null;

    SpreadRange spreadRange = SpreadRange.ALIAS;
    TargetType targetType = TargetType.ALL;
    MessageType messageType = MessageType.ALL;
    MessagePushInfo messagePushModel = new MessagePushInfo();
    messagePushModel.setTargetType(targetType.getTypeCode());
    messagePushModel.setMessageType(messageType.getMessageType());// 2
    messagePushModel.setSpreadRange(spreadRange.getSpreadRange());
    messagePushModel.setNoticeContent("亲，小逗有新的系统通知了！");
    messagePushModel.setMessageContent("亲，小逗有新的系统通知了！");
    List<String> alias = new ArrayList<String>();
    alias.add("201710249240586");
    messagePushModel.setAlias(alias);
    List<String> tags = new ArrayList<String>();
    // tags.add("xd_online");
    // tags.add(JpushTagsEnums.XD_TEST.getName());
    messagePushModel.setTags(tags);
    List<String> registrationIds = new ArrayList<String>();
    registrationIds.add("190e35f7e077e842bd6");
    messagePushModel.setRegistrationIds(registrationIds);
    Map<String, String> messageextras = new HashMap<String, String>();
    Map<String, String> noticeextras = new HashMap<String, String>();
    messageextras.put("retcode", "20419");
    messageextras.put("retdesc", "后台添加了新的系统通知");
    noticeextras.put("retcode", "20419");
    noticeextras.put("retdesc", "后台添加了新的系统通知");
    messagePushModel.setMessageextras(messageextras);
    messagePushModel.setNoticeextras(noticeextras);
    payload = JPushUtil.buildPushObject_android(messagePushModel);
    // payload = JPushUtil.buildPushObject_ios(messagePushModel);
    payload.resetOptionsApnsProduction(true);
    // payload = JPushUtil.buildPushObject_all_all_alert();
    System.out.println("payload:" + payload);
    try {
      // jpushClient.getReportReceiveds(msgIds)
      PushResult result = jpushClient.sendPush(payload);
      // sendno就是随机生成的一个数字
      System.out.println("result" + result);
      LOG.info("Got result - " + result);
    } catch (APIConnectionException e) {
      System.out.println("e1" + e);
      LOG.error("Connection error. Should retry later. ", e);
    } catch (APIRequestException e) {
      LoggerUtil.error("错误", e);
      LOG.error("Error response from JPush server. Should review and fix it. \n", e);
      System.err.print("Error response from JPush server. Should review and fix it. " + e);
      LOG.info("HTTP Status: " + e.getStatus());
      System.out.println("HTTP Status: " + e.getStatus());
      LOG.info("Error Code: " + e.getErrorCode());
      System.out.println("Error Code: " + e.getErrorCode());
      LOG.info("Error Message: " + e.getErrorMessage());
      System.out.println("Error Message: " + e.getErrorMessage());
      LOG.info("Msg ID: " + e.getMsgId());
      System.out.println("Msg ID: " + e.getMsgId());
    }
  }

  public static void testSendMsByJPush() {
    SpreadRange spreadRange = SpreadRange.ALIAS;
    TargetType targetType = TargetType.ALL;
    // targetType = TargetType.ANDROID;
    // targetType = TargetType.IOS;
    MessageType messageType = MessageType.ALL;
    MessagePushInfo messagePushModel = new MessagePushInfo();
    messagePushModel.setTargetType(targetType.getTypeCode());
    messagePushModel.setMessageType(messageType.getMessageType());// 2
    messagePushModel.setSpreadRange(spreadRange.getSpreadRange());
    messagePushModel.setNoticeContent("亲，小逗有新的系统通知了！");
    messagePushModel.setMessageContent("亲，小逗有新的系统通知了！");
    List<String> alias = new ArrayList<String>();
    alias.add("201710249240586");
    messagePushModel.setAlias(alias);
    List<String> tags = new ArrayList<String>();
    // tags.add("xd_online");
    // tags.add(JpushTagsEnums.XD_TEST.getName());
    messagePushModel.setTags(tags);
    List<String> registrationIds = new ArrayList<String>();
    registrationIds.add("190e35f7e077e842bd6");
    messagePushModel.setRegistrationIds(registrationIds);
    Map<String, String> messageextras = new HashMap<String, String>();
    Map<String, String> noticeextras = new HashMap<String, String>();
    messageextras.put("retcode", "20419");
    messageextras.put("retdesc", "后台添加了新的系统通知");
    noticeextras.put("retcode", "20419");
    noticeextras.put("retdesc", "后台添加了新的系统通知");
    messagePushModel.setMessageextras(messageextras);
    messagePushModel.setNoticeextras(noticeextras);
    SendMsByJPush send = new SendMsByJPush();
    MessageModel m = send.sendNoticePush(messagePushModel);
    m.getAppMessageId();
  }

  public static void test1() {
    // HttpProxy proxy = new HttpProxy("localhost", 3128);
    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
    // JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
    JPushClient jpushClient = JPushUtil.getClient();
    System.out.println("jpushClient:" + jpushClient);
    try {
      jpushClient.getReportReceiveds("");
    } catch (APIConnectionException | APIRequestException e) {
      e.printStackTrace();
    }

  }
}
