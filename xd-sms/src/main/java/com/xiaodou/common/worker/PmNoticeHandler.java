package com.xiaodou.common.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.service.facade.IMsServiceFacade;
import com.xiaodou.message.service.platform.IMsPlatformService;
import com.xiaodou.message.vo.MessagePushInfo;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("pm_notice")
public class PmNoticeHandler extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -457166590540741515L;

@SuppressWarnings("unchecked")
@Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    IMsServiceFacade msServiceFacade = SpringWebContextHolder.getBean("msServiceFacade");
    IMsPlatformService jPushMsService = SpringWebContextHolder.getBean("jPushMsService");
    // 取出数据
    String str = message.getMessageBodyJson();
    JSONObject object = JSONObject.parseObject(str);
    String targetType = object.getString("targetType");
    String messageType = object.getString("messageType");
    String spreadRange = object.getString("spreadRange");
    String noticeContent = (String) object.get("noticeContent");
    String messageContent = (String) object.get("messageContent");
    long messagedbid = object.getLongValue("messagedbid");
    List<String> alias = object.getObject("alias", List.class);
    List<String> tags = (List<String>) object.get("tags");
    List<String> registrationIds = object.getObject("registrationIds", List.class);

    Map<String, String> messageextras = object.getObject("messageextras", Map.class);
    /** noticeextras 通知参数 */
    Map<String, String> noticeextras = object.getObject("noticeextras", Map.class);
    // 生成发送对象
    MessagePushInfo messagePushModel = new MessagePushInfo();
    messagePushModel.setTargetType(targetType);
    messagePushModel.setMessageType(messageType);// 2
    messagePushModel.setSpreadRange(spreadRange);
    messagePushModel.setNoticeContent(noticeContent);
    messagePushModel.setMessageContent(messageContent);
    messagePushModel.setAlias(alias);
    messagePushModel.setTags(tags);
    messagePushModel.setRegistrationIds(registrationIds);
    messagePushModel.setMessageextras(messageextras);
    messagePushModel.setNoticeextras(noticeextras);
    Map<String, Object> condition = new HashMap<String, Object>();
    condition.put("id", messagedbid);
    // 生成push消息成功之后，修改表状态数据
    MessageModel messageModel1 = new MessageModel();
    messageModel1.setMsg("正在推送");
    msServiceFacade.updateMessageModelEntity(condition, messageModel1);
    MessageModel messageModel = jPushMsService.sendMs(messagePushModel);
    if(null == messageModel) {
        messageModel = new MessageModel();
    }
    // 生成push消息成功之后，修改表返回状态数据
    if (StringUtils.isBlank(messageModel.getMsg())) {
        messageModel.setMsg("没有返回说明");
    }
    msServiceFacade.updateMessageModelEntity(condition, messageModel);
    callback.onSuccess(message);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送通知失败.", t);
    callback.onFail(CallBackStatus.EXCEPTION, message);
  }

}
