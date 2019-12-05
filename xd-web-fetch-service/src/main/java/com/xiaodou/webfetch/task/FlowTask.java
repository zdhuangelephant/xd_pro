package com.xiaodou.webfetch.task;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.enums.WebFetchEnums.HttpApiType;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.plugin.jsoup.JsoupPojo;
import com.xiaodou.webfetch.plugin.jsoup.JsoupResponse;
import com.xiaodou.webfetch.plugin.phantom.PhantomPojo;
import com.xiaodou.webfetch.plugin.phantom.PhantomResponse;
import com.xiaodou.webfetch.service.QueueService;
import com.xiaodou.webfetch.service.QueueService.FlowTaskCommand;
import com.xiaodou.webfetch.service.QueueService.NotifyActionCommand;
import com.xiaodou.webfetch.util.HttpApiExcutor;

/**
 * @name @see com.xiaodou.webfetch.task.FlowTask.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 处理任务流程
 * @version 1.0
 */
@HandlerMessage("FlowTask")
public class FlowTask extends AbstractDefaultWorker {

	/** serialVersionUID */
	private static final long serialVersionUID = -7663312950949680414L;

	@Override
	public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) throws Exception {
		FlowTaskCommand command = FastJsonUtil.fromJson(message.getMessageBodyJson(), FlowTaskCommand.class);
		SandBoxContext sandBox = SandBoxContext.getSandBox(command.getSandBoxId());
		if (null == sandBox) {
			throw new RuntimeException("未能找到沙盒环境");
		}
		FetchTask task = command.getTask();
		if (null == task || StringUtils.isBlank(task.getUrl())) {
			throw new RuntimeException("无效任务");
		}
		if (HttpApiType.Phantomjs.equals(sandBox.getApiType())) {
			Document doc = excuteThroughPhantomjs(task, sandBox);
			if (null == doc) {
				throw new RuntimeException("获取Html文档失败");
			}
			sandBox.registTargetElements(task.getContentTarget(), doc);
		} else if (HttpApiType.Jsoup.equals(sandBox.getApiType())) {
			Document doc = excuteThroughJsoup(task, sandBox);
			if (null == doc) {
				throw new RuntimeException("获取Html文档失败");
			}
			sandBox.registTargetElements(task.getContentTarget(), doc);
		}
		QueueService.notifyAction(new NotifyActionCommand(sandBox.unique(), task, task.getDefaultAction()));
	}

	private Document excuteThroughJsoup(FetchTask task, SandBoxContext sandBox) throws Exception {
		JsoupPojo pojo = HttpApiExcutor.buildJsoupPojo(task, sandBox);
		JsoupResponse response = HttpApiExcutor.excuteJsoupApi(pojo);
		System.out.println(response.getResponse().body());
		return Jsoup.parse(response.getResponse().body());

	}

	private Document excuteThroughPhantomjs(FetchTask task, SandBoxContext sandBox) throws Exception {
		PhantomPojo pojo = HttpApiExcutor.buildPhantomPojo(task, sandBox);
		PhantomResponse response = HttpApiExcutor.executePhantomApi(pojo);
		return Jsoup.parse(response.getContent());
	}

	@Override
	public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) throws Exception {
	}

	@Override
	public void onException(Throwable t, List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
	}

	@Override
	public void onException(Throwable t, DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
		LoggerUtil.error(String.format("处理任务流程失败。[%s]", message.getMessageBodyJson()), t);
	}

}
