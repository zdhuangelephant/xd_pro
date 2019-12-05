package com.xiaodou.control.task;

import java.util.List;

import com.xiaodou.common.util.MailSender;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.vo.SendInfoVo;
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
public class SendMail extends AbstractDefaultWorker {

  /** MAIL_INFO_TEMP 邮件标题模板 */
  private static final String MAIL_INFO_TEMP = "[%s]%s";
  /** serialVersionUID */
  private static final long serialVersionUID = -2946797657678655526L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    SendInfoVo infoVo = FastJsonUtil.fromJson(message.getMessageBodyJson(), SendInfoVo.class);
    String mailInfo =
        String.format(MAIL_INFO_TEMP,"检测时间:"+infoVo.getCreateDate(),infoVo.getMailInfo());
    MailSender.getInstance().send(infoVo.getMail(), false,infoVo.getName()+"|检测时间:"+infoVo.getCreateDate(),
    		mailInfo  , null);
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
