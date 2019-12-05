package com.xiaodou.userCenter.web.controller.user;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.jmsg.exception.MessageConsumeIgnoreException;
import com.xiaodou.mqcr.service.MessageEntityService;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.userCenter.service.MqMessageService;
import com.xiaodou.userCenter.vo.alarm.MqMessageExceptionAlarm;

@Controller("mqMessageController")
@RequestMapping("message")
public class MqMessageController extends BaseController {

  @Resource
  MqMessageService mqMessageService;

  @Resource
  MessageEntityService messageEntityService;

  @RequestMapping(value = "messageConsume", method = RequestMethod.POST)
  @ResponseBody
  public String messageConsume(JMSGPojo pojo) {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    result.setTag(pojo.getTag());
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
//      String[] split = pojo.getMessageName().split("_");
//      String methodName = split[1];
      String methodName = pojo.getMessageName();
      Class<?> asyncMessageServiceClass = MqMessageService.class;
      Method method = asyncMessageServiceClass.getMethod(methodName, JMSGPojo.class);
      result = (MessageRemoteResult) method.invoke(mqMessageService, pojo);
      messageEntityService.updateMessage2Succ(pojo.getTag());
    } catch (Exception e) {
      LoggerUtil.error("异步消息消费失败", e);
      if (e instanceof MessageConsumeIgnoreException) {
        result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
        return JSON.toJSONString(result);
      } else {
        // 删除tag
        messageEntityService.deleteMessageByTag(pojo.getTag());
        LoggerUtil.alarmInfo(new MqMessageExceptionAlarm(pojo.getTag()));
        result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
        return JSON.toJSONString(result);
      }
    }
    return result.toString();
  }

}
