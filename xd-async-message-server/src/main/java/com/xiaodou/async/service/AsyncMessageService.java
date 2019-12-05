package com.xiaodou.async.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.google.common.collect.Maps;
import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.dao.AsyncMessageMapper;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.async.model.AbstractAsyncMessage;
import com.xiaodou.async.model.MessageFeedBack;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;
import com.xiaodou.summer.web.BaseController;

@Service("asyncMessageService")
public class AsyncMessageService {

  @Resource
  AsyncMessageMapper asyncMessageMapper;

  private static String MESSAGEID = "messageId";

  public <T extends AbstractAsyncMessage> void sendMessage(T message) {
    Errors errors = message.validate();
    if (errors.hasErrors()) {
      LoggerUtil.error("消息体不完整." + BaseController.getErrMsg(errors), new RuntimeException());
      return;
    }
    AsyncMessage domain = message.getDomain();
    asyncMessageMapper.addEntity(domain);
    Message pushMessage = new Message();
    pushMessage.setModule(message.getModule());
    pushMessage.setMessageContent(message.getMessageBody());
    pushMessage.setNoticeContent(message.getMessageBody());
    pushMessage.setMessageType(MessageType.ALL);
    pushMessage.setScope(SpreadRange.ALIAS);
    pushMessage.setTarget(TargetType.ALL);
    Map<String, String> messageextras = new HashMap<String, String>();
    Map<String, String> noticeextras = new HashMap<String, String>();
    pushMessage.addReciever(new String[] {message.getToUid()});
    PushUtil.setRetCode(noticeextras, message.getRetCode());
    PushUtil.setRetDesc(noticeextras, message.getRetDesc());
    noticeextras.put(MESSAGEID, domain.getId());
    PushUtil.setRetCode(messageextras, message.getRetCode());
    PushUtil.setRetDesc(messageextras, message.getRetDesc());
    messageextras.put(MESSAGEID, domain.getId());
    pushMessage.setMessageextras(messageextras);// 消息参数
    pushMessage.setNoticeextras(noticeextras);// 通知参数
    PushClient.push(pushMessage);

  }

  public void dealMessage(MessageFeedBack message) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", message.getMessageId());
    input.put("toUid", message.getToUid());
    cond.put("input", input);
    List<AsyncMessage> messageList = asyncMessageMapper.queryList(cond);
    if (null == messageList || messageList.size() == 0) return;
    AsyncMessage entity = messageList.get(0);
    if (!entity.getDealResult().equals(AsyncMessageConst.DOMAIN_DEALRESULT_INIT)) return;
    entity.setDealResult(message.getResult());
    asyncMessageMapper.updateEntity(entity);
    if (StringUtils.isNotBlank(entity.getCallBackMessage())) {
      RabbitMQSender.getInstance().send(entity, UUID.randomUUID().toString(),
          entity.getCallBackMessage());
    }
  }
}
