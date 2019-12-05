package com.xiaodou.autotest.web.task;

import java.util.List;

import com.xiaodou.autotest.core.ActionScheduler;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.web.service.mail.MailService;
import com.xiaodou.autotest.web.service.operation.RequestService;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.resources.task.forum.UpdatePraiseNumberWorker.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 跟新点赞数量worker
 * @version 1.0
 */
@HandlerMessage("ExecuteTask")
public class ExecuteTask extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
	RequestService requestService = SpringWebContextHolder.getBean("requestService");
	MailService mailService = SpringWebContextHolder.getBean("mailService");
    Action action =FastJsonUtil.fromJson(message.getMessageBodyJson(), Action.class);
	ActionScheduler.getInstance().schedule(action);
	requestService.updateCaseResults(action);
	mailService.sendMail(action, requestService.getFailApi(action.getApiList()));
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("执行失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("执行失败", t);
  }

}
