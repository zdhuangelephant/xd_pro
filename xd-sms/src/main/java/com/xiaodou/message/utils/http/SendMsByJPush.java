package com.xiaodou.message.utils.http;

import org.apache.commons.lang.StringUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.utils.JPushUtil;
import com.xiaodou.message.vo.MessagePushInfo;
import com.xiaodou.sms.model.AlarmEntity;
import com.xiaodou.sms.model.AlarmEntity.FailTypeEnum;

public class SendMsByJPush {

  private static final String IOS_SUCCESS = "ios推送成功！";
  private static final String IOS_ERROR = "ios推送失败！";
  private static final String ANDROID_SUCCESS = "android推送成功！";
  private static final String ANDROID_ERROR = "android推送失败！";
  private static final String PUSH_PARAM_ERROR = "推送参数异常！";
  private static final String PUSH_ERROR = "推送失败！";

  @SuppressWarnings({"finally", "unused"})
  public MessageModel sendNoticePush(MessagePushInfo messagePushInfo) {
    MessageModel messageModel = new MessageModel();
    try {
      JPushClient jpushClient = JPushUtil.getClient();
      if (null == jpushClient) {
          return null;
      }
      // For push, all you need do is to build PushPayload object.
      PushPayload payload = null;
      PushResult result = null;
      if (null != messagePushInfo && ("1").equals(messagePushInfo.getTargetType())) { // android
        payload = JPushUtil.buildPushObject_android(messagePushInfo);
        result = jpushClient.sendPush(payload);
        messageModel.setStatus("1");
        messageModel.setMsg(ANDROID_SUCCESS);
      } else if (null != messagePushInfo && ("2").equals(messagePushInfo.getTargetType())) { // ios
        payload = JPushUtil.buildPushObject_ios(messagePushInfo);
        result = jpushClient.sendPush(payload);
        messageModel.setStatus("1");
        messageModel.setMsg(IOS_SUCCESS);
      }
      // 当发送条件为all时，如果账号在ios和android都登录过的时候，后台发送都是成功，但前端app只有正常登录的用户才可以收到消息
      // 当后台发送都失败的话，那就推送必然失败
      // ios_android
      else if (null != messagePushInfo && ("0").equals(messagePushInfo.getTargetType())) {
        String status = "0";
        String msg = StringUtils.EMPTY;
        try {
          payload = JPushUtil.buildPushObject_ios(messagePushInfo);
          result = jpushClient.sendPush(payload);
          status = "1";
          msg = IOS_SUCCESS;
        } catch (Exception e) {
          msg = IOS_ERROR;
          msg = String.format("%s%s. ", msg, e.getMessage());
          if (e instanceof APIRequestException) {
            msg = String.format("%s%s. ", msg, ((APIRequestException) e).getErrorMessage());
          }
        } finally {
          try {
            payload = JPushUtil.buildPushObject_android(messagePushInfo);
            result = jpushClient.sendPush(payload);
            status = "1";
            msg += ANDROID_SUCCESS;
          } catch (Exception e2) {
            msg += ANDROID_ERROR;
            msg = String.format("%s%s. ", msg, e2.getMessage());
            if (e2 instanceof APIRequestException) {
              msg = String.format("%s%s. ", msg, ((APIRequestException) e2).getErrorMessage());
            }
          } finally {
            messageModel.setStatus(status);
            messageModel.setMsg(msg);
          }
        }
      } else {
        messageModel.setStatus("0");
        String msg = null == messagePushInfo ? "null == messagePushInfo" : messagePushInfo.getTargetType();
        messageModel.setMsg(String.format("%s%s. ", PUSH_PARAM_ERROR, msg));
      }
    } catch (APIConnectionException e) {
      messageModel.setStatus("0");
      messageModel.setMsg(String.format("%s%s. ", PUSH_ERROR, e.getMessage()));
    } catch (APIRequestException e) {
      messageModel.setStatus(String.valueOf(e.getStatus()));
      messageModel.setMsg(e.getErrorMessage());
    } finally {
      if (!messageModel.getStatus().equals("1")) {
        AlarmEntityImpl alarm = new AlarmEntity(FailTypeEnum.SEND_MS_FAIL, null);
        LoggerUtil.alarmInfo(alarm);
      }
      return messageModel;
    }
  }
}
