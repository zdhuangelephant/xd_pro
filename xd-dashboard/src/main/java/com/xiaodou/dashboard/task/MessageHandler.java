package com.xiaodou.dashboard.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.cache.AlarmDescCache;
import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

/**
 * @name @see com.xiaodou.dashboard.task.MessageHandler.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description 短信发送任务
 * @version 1.0
 */
@HandlerMessage("SendMessage")
public class MessageHandler extends AbstractDefaultWorker {

  /** MESSAGE_INFO_TEMP 短信 */
  private static final String MESSAGE_INFO_TEMP = "[%s]";
  /** serialVersionUID */
  private static final long serialVersionUID = -5456116775139308956L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isBlank(message.getMessageBodyJson())) {
      return;
    }
    SendInfoVo infoVo = FastJsonUtil.fromJson(message.getMessageBodyJson(), SendInfoVo.class);
    if (StringUtils.isBlank(infoVo.getEvent().getMessage())) {
      return;
    }
    String messageInfo = String.format(MESSAGE_INFO_TEMP, infoVo.getPojo().getEventName());
    try {
      SendInfoVo model = AlarmDescCache.getAlarmDescFromCache(infoVo);
      // 1分钟内不能重发判重
      if (null != model && !isBeforeSomeMinutes(new Date(), model.getCreateDate(), new Double(1))) {
        return;
      }
      // 调用短信发送接口
      ShortMessage sm = new ShortMessage();
      String[] messages = infoVo.getEvent().getMessage().split("\\;");
      sm.setTelephone(messages);
      sm.setVariables("desc", messageInfo);
      sm.setTemplateId("2");
      PushClient.push(sm);

      // 覆盖缓存中报警信息,向15条cache List中add该条code
      AlarmDescCache.addAlarmDescToRedis(infoVo);
    } catch (Exception e) {
      message.setMessageBodyJson(FastJsonUtil.toJson(infoVo));
      callback.onFail(CallBackStatus.FAIL, message);
    }
    callback.onSuccess(message);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("发送短信异常", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送短信异常", t);
  }

  /**
   * 
   * 判断某一事件是否是几分钟之前
   * 
   * @param now
   * @param when
   * @param someMinutes
   * @return
   */
  public static boolean isBeforeSomeMinutes(Date now, Date when, Double someMinutes) {
    Double someHoursMs = DateUtils.MILLIS_PER_MINUTE * someMinutes;
    Long befMs = when.getTime();
    Long aftMs = now.getTime();
    return ((aftMs - befMs) > someHoursMs) ? true : false;
  }
}
