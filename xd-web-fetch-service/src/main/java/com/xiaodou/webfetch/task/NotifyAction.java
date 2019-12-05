package com.xiaodou.webfetch.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.webfetch.action.IncisePoint;
import com.xiaodou.webfetch.engine.FlowExcutor;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.param.DependActionGroup;
import com.xiaodou.webfetch.service.QueueService;
import com.xiaodou.webfetch.service.QueueService.NotifyActionCommand;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.task.NotifyTask.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 唤醒依赖任务流程
 * @version 1.0
 */
@HandlerMessage("NotifyAction")
public class NotifyAction extends AbstractDefaultWorker {

	/** serialVersionUID */
	private static final long serialVersionUID = -7663312950949680414L;

	@Override
	public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) throws Exception {
		NotifyActionCommand command = FastJsonUtil.fromJson(message.getMessageBodyJson(), NotifyActionCommand.class);
		SandBoxContext sandBox = SandBoxContext.getSandBox(command.getSandBoxId());
		DependActionGroup actionGroup = sandBox.getTargetActionGroup(command.getAction());
		if (null != actionGroup && null != actionGroup.getActionList() && !actionGroup.getActionList().isEmpty()) {
			for (IncisePoint action : actionGroup.getActionList()) {
				FlowExcutor.flowAction(command.getTask(), action, sandBox);
				QueueService.notifyAction(
						new NotifyActionCommand(sandBox.unique(), command.getTask(), new Target(action.unique())));
			}
		}
	}

	@Override
	public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) throws Exception {
	}

	@Override
	public void onException(Throwable t, List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
	}

	@Override
	public void onException(Throwable t, DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
		LoggerUtil.error(String.format("唤醒依赖动作流程失败。[%s]", message.getMessageBodyJson()), t);
	}

}
