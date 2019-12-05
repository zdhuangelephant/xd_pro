package com.xiaodou.dashboard.task;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.info.ding.DingSender;
import com.xiaodou.common.info.ding.req.TextDingReq;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

/**
 * @name @see com.xiaodou.dashboard.task.DingHandler.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月9日
 * @description 钉钉机器人通知任务
 * @version 1.0
 */
@HandlerMessage("SendDing")
public class DingHandler extends AbstractDefaultWorker {

  /** MESSAGE_INFO_TEMP 短信 */
  private static final String MESSAGE_INFO_TEMP = "[%s]";
  /** serialVersionUID */
  private static final long serialVersionUID = -5456116775139308956L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    SendInfoVo infoVo = FastJsonUtil.fromJson(message.getMessageBodyJson(), SendInfoVo.class);
    if (null == infoVo) {
      return;
    }
    String dingURL = infoVo.getEvent().getDingURL();
    if (StringUtils.isBlank(dingURL)) {
      return;
    }
    String[] dingURLS = dingURL.split(";");
    String messageInfo = String.format(MESSAGE_INFO_TEMP, infoVo.getPojo().getMailInfo());
    try {
      // 发送钉钉通知消息
      TextDingReq req = new TextDingReq(Lists.newArrayList(dingURLS));
      TextDingReq.Text text = new TextDingReq.Text();
      text.setContent(messageInfo);
      req.setText(text);
      DingSender.send(req);
    } catch (Exception e) {
      message.setMessageBodyJson(FastJsonUtil.toJson(infoVo));
      callback.onFail(CallBackStatus.FAIL, message);
    }
    callback.onSuccess(message);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("发送钉钉消息异常", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送钉钉消息异常", t);
  }

}
