package com.xiaodou.async.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.async.exception.MessageConsumeIgnoreException;
import com.xiaodou.async.model.AbstractAsyncMessage;
import com.xiaodou.async.model.MessageFeedBack;
import com.xiaodou.async.service.AsyncMessageService;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mqcr.service.MessageEntityService;
import com.xiaodou.summer.web.BaseController;

@Controller("asyncMessageController")
@RequestMapping("asyncMessage")
public class AsyncMessageController extends BaseController {

  @Resource
  AsyncMessageService asyncMessageService;

  @Resource
  MessageEntityService messageEntityService;

  @RequestMapping(value = "messageConsume", method = RequestMethod.POST)
  @ResponseBody
  public String messageConsume(JMSGPojo pojo) {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    Errors errors = pojo.validate();
    if (errors.hasErrors()) {
      result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
      result.appendRetdesc(getErrMsg(errors));
      return result.toString();
    }
    /** 消息判重 && 插入判重记录 */
    if (!messageEntityService.canConsumMessage(pojo.getTag())
        || !messageEntityService.insertNewMessage(pojo.getTag())) {
      return result.toString();
    }
    try {
      asyncMessageService.sendMessage(FastJsonUtil.fromJson(pojo.getMessage(),
          AbstractAsyncMessage.class));
      messageEntityService.updateMessage2Succ(pojo.getTag());
    } catch (Exception e) {
      LoggerUtil.error("消息处理失败。", e);
      if (e instanceof MessageConsumeIgnoreException) {
        result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
        return JSON.toJSONString(result);
      } else {
        // 删除tag
        messageEntityService.deleteMessageByTag(pojo.getTag());
        // AlarmEntity alarmEntity = new AlarmEntity(AlarmEntityType.ORDER_EXCEPTION.getType());
        // alarmEntity.setAlertMessage("异步消息消费异常" + "[" + ExceptionUtil.getErrorStack(e) + "]");
        // 记录堆栈没用。记录pojo 方便处理
        // alarmEntity.setAlertMessage(FastJsonUtil.toJson(pojo));
        // alarmEntity.setAlertTitle("异步消息消费异常" + "[" + e.getMessage() + "]");
        // LoggerUtil.alarmInfo(alarmEntity);
        result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
        return JSON.toJSONString(result);
      }
    }
    return result.toString();
  }

  @RequestMapping(value = "feed_back", method = RequestMethod.POST)
  @ResponseBody
  public String feedBack(MessageFeedBack pojo) {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    Errors errors = pojo.validate();
    if (errors.hasErrors()) {
      result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
      result.appendRetdesc(getErrMsg(errors));
      return result.toString();
    }
    asyncMessageService.dealMessage(pojo);

    return result.toString();
  }
}
