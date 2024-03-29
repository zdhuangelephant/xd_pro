package com.xiaodou.dashboard.task;

import java.util.List;

import com.xiaodou.common.info.mail.MailSender;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.util.EnvironMentUtil;
import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

/**
 * @name @see com.xiaodou.dashboard.task.MailHandler.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description 邮件发送任务
 * @version 1.0
 */
@HandlerMessage("SendMail")
public class MailHandler extends AbstractDefaultWorker {

  /** MAIL_INFO_TEMP 邮件标题模板 */
  private static final String MAIL_INFO_TEMP = "[%s][%s][%s]";
  /** serialVersionUID */
  private static final long serialVersionUID = -2946797657678655526L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isBlank(message.getMessageBodyJson())) {
      return;
    }
    SendInfoVo infoVo = FastJsonUtil.fromJson(message.getMessageBodyJson(), SendInfoVo.class);
    if (StringUtils.isBlank(infoVo.getEvent().getMail())) {
      return;
    }
    String mailInfo =
        String.format(MAIL_INFO_TEMP, EnvironMentUtil.getEnvType(), infoVo.getPojo()
            .getEventModule(), infoVo.getPojo().getEventName());
    MailSender.getInstance().send(infoVo.getEvent().getMail(), false, mailInfo,
        infoVo.getPojo().getMailInfo(), null);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("发送邮件异常", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送邮件异常", t);
  }

}
