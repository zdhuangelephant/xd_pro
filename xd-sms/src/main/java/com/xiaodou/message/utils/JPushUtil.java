package com.xiaodou.message.utils;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.google.common.collect.Maps;
import com.xiaodou.message.enums.JpushTagsEnums;
import com.xiaodou.message.vo.MessagePushInfo;

public class JPushUtil {
  private static final String APP_KEY_TMP = "jpush.uptoken.init.appKey.%s";
  private static final String MASTER_SECRET_TMP = "jpush.uptoken.init.masterSecret.%s";

  private static final Map<String, JPushClient> instanceMapping = Maps.newHashMap();
  static {
    for (String env : JPushProp.getParams("jpush.uptoken.init.env").split(",")) {
      // 环境区分
      instanceMapping.put(
          env,
          new JPushClient(JPushProp.getParams(String.format(MASTER_SECRET_TMP, env)), JPushProp
              .getParams(String.format(APP_KEY_TMP, env)), 3));
    }
  }

  /**isProduct 保证可见*/
  private static volatile boolean isProduct = JPushProp.isOnline();

  /**
   * @description 不同的服务器环境用不同的client
   * @author 李德洪
   * @Date 2018年5月23日
   * @return
   */
  public static JPushClient getClient() {
    return instanceMapping.get(JPushProp.getEnvParams());
  }

  /**
   * 构建推送对象：平台是 IOS
   */
  public static PushPayload buildPushObject_ios(MessagePushInfo messagePushInfo) {
    List<String> aliases = messagePushInfo.getAlias();
    List<String> tags = messagePushInfo.getTags();
    List<String> registrationIds = messagePushInfo.getRegistrationIds();
    String notice = messagePushInfo.getNoticeContent();
    String message = messagePushInfo.getMessageContent();
    String spreadRange = messagePushInfo.getSpreadRange();
    Map<String, String> messageextras = messagePushInfo.getMessageextras();
    Map<String, String> noticeextras = messagePushInfo.getNoticeextras();
    PushPayload payload = null;
    if (("0").equals(spreadRange))
      payload = buildPushObject_ios(notice, message, payload, messageextras, noticeextras);
    if (("1").equals(spreadRange))
      payload =
          buildPushObject_ios_6(aliases, notice, message, payload, messageextras, noticeextras);
    if (("2").equals(spreadRange))
      payload = buildPushObject_ios_5(tags, notice, message, payload, messageextras, noticeextras);
    if (("3").equals(spreadRange))
      payload =
          buildPushObject_ios_7(registrationIds, notice, message, payload, messageextras,
              noticeextras);
    if (("4").equals(spreadRange))
      payload =
          buildPushObject_ios_2(aliases, tags, notice, message, payload, messageextras,
              noticeextras);
    if (("5").equals(spreadRange))
      payload =
          buildPushObject_ios_3(aliases, registrationIds, notice, message, payload, messageextras,
              noticeextras);
    if (("6").equals(spreadRange))
      payload =
          buildPushObject_ios_4(tags, registrationIds, notice, message, payload, messageextras,
              noticeextras);
    if (("7").equals(spreadRange))
      payload =
          buildPushObject_ios_1(aliases, tags, registrationIds, notice, message, payload,
              messageextras, noticeextras);
    // 如果有tag,则根据tag确定apns（针对ms后台的手动推送）
    if(!CollectionUtils.isEmpty(tags) && null != JpushTagsEnums.getByName(tags) && tags.contains(JpushTagsEnums.XD_PRE_RELEASE.getName())){
      isProduct = false;
    }
    payload.resetOptionsApnsProduction(isProduct);
    return payload;
  }

  /**
   * 构建推送对象：平台是 ANDROID
   */
  public static PushPayload buildPushObject_android(MessagePushInfo messagePushInfo) {
    List<String> aliases = messagePushInfo.getAlias();
    List<String> tags = messagePushInfo.getTags();
    List<String> registrationIds = messagePushInfo.getRegistrationIds();
    String notice = messagePushInfo.getNoticeContent();
    String message = messagePushInfo.getMessageContent();
    String spreadRange = messagePushInfo.getSpreadRange();
    Map<String, String> messageextras = messagePushInfo.getMessageextras();
    Map<String, String> noticeextras = messagePushInfo.getNoticeextras();
    PushPayload payload = null;
    if (("0").equals(spreadRange))
      payload = buildPushObject_android(notice, message, payload, messageextras, noticeextras);
    if (("1").equals(spreadRange))
      payload =
          buildPushObject_android_6(aliases, notice, message, payload, messageextras, noticeextras);
    if (("2").equals(spreadRange))
      payload =
          buildPushObject_android_5(tags, notice, message, payload, messageextras, noticeextras);
    if (("3").equals(spreadRange))
      payload =
          buildPushObject_android_7(registrationIds, notice, message, payload, messageextras,
              noticeextras);
    if (("4").equals(spreadRange))
      payload =
          buildPushObject_android_2(aliases, tags, notice, message, payload, messageextras,
              noticeextras);
    if (("5").equals(spreadRange))
      payload =
          buildPushObject_android_3(aliases, registrationIds, notice, message, payload,
              messageextras, noticeextras);
    if (("6").equals(spreadRange))
      payload =
          buildPushObject_android_4(tags, registrationIds, notice, message, payload, messageextras,
              noticeextras);
    if (("7").equals(spreadRange))
      payload =
          buildPushObject_android_1(aliases, tags, registrationIds, notice, message, payload,
              messageextras, noticeextras);
    return payload;
  }

  public static PushPayload buildPushObject_all_all_alert() {
    return PushPayload.alertAll("hello");
  }

  public static PushPayload buildPushObject_ios(String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    payload =
        PushPayload
            .newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.all())
            .setMessage(
                Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
            .setNotification(
                Notification
                    .newBuilder()
                    .addPlatformNotification(
                        IosNotification.newBuilder().setAlert(notice).setBadge(1)
                            .setSound("happy.caf").addExtras(noticeextras).build()).build())
            .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_7(List<String> registrationIds, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder()
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  public static PushPayload buildPushObject_ios_6(List<String> aliases, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != aliases)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(aliases)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              // .setOptions(Options.newBuilder().setTimeToLive(60l).build())
              .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_5(List<String> tags, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != tags)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_4(List<String> tags, List<String> registrationIds,
      String notice, String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != tags && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_3(List<String> aliases,
      List<String> registrationIds, String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    if (null != aliases && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(aliases))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_2(List<String> aliases, List<String> tags,
      String notice, String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != tags && null != aliases)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.alias(aliases)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  private static PushPayload buildPushObject_ios_1(List<String> aliases, List<String> tags,
      List<String> registrationIds, String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    if (null != tags && null != aliases && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.ios())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.alias(aliases))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          IosNotification.newBuilder().setAlert(notice).setBadge(1)
                              .setSound("happy.caf").addExtras(noticeextras).build()).build())
              .build();
    return payload;
  }

  public static PushPayload buildPushObject_android(String notice, String message,
      PushPayload payload, Map<String, String> messageextras, Map<String, String> noticeextras) {
    payload =
        PushPayload
            .newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.all())
            .setMessage(
                Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
            .setNotification(
                Notification
                    .newBuilder()
                    .addPlatformNotification(
                        AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                            .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_7(List<String> registrationIds, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder()
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_6(List<String> aliases, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != aliases)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(aliases)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_5(List<String> tags, String notice,
      String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != tags)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_4(List<String> tags,
      List<String> registrationIds, String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    if (null != tags && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_3(List<String> aliases,
      List<String> registrationIds, String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    if (null != aliases && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(aliases))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_2(List<String> aliases, List<String> tags,
      String notice, String message, PushPayload payload, Map<String, String> messageextras,
      Map<String, String> noticeextras) {
    if (null != tags && null != aliases)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.alias(aliases)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }

  private static PushPayload buildPushObject_android_1(List<String> aliases, List<String> tags,
      List<String> registrationIds, String notice, String message, PushPayload payload,
      Map<String, String> messageextras, Map<String, String> noticeextras) {
    if (null != tags && null != aliases && null != registrationIds)
      payload =
          PushPayload
              .newBuilder()
              .setPlatform(Platform.android())
              .setAudience(
                  Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
                      .addAudienceTarget(AudienceTarget.alias(aliases))
                      .addAudienceTarget(AudienceTarget.registrationId(registrationIds)).build())
              .setMessage(
                  Message.newBuilder().setMsgContent(message).addExtras(messageextras).build())
              .setNotification(
                  Notification
                      .newBuilder()
                      .addPlatformNotification(
                          AndroidNotification.newBuilder().setAlert(notice).addExtras(noticeextras)
                              .build()).build()).build();
    return payload;
  }



}
