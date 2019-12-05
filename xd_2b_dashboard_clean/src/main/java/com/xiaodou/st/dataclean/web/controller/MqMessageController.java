package com.xiaodou.st.dataclean.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.jmsg.exception.MessageConsumeIgnoreException;
import com.xiaodou.mqcr.service.MessageEntityService;
import com.xiaodou.st.dataclean.exception.DcMsgConsuFailException;
import com.xiaodou.st.dataclean.service.message.MqMessageService;
import com.xiaodou.st.dataclean.util.ErrorsWrapper;
import com.xiaodou.st.dataclean.vo.alarm.MessageExceptionAlarm;
import com.xiaodou.summer.web.BaseController;

@Controller("mqMessageController")
@RequestMapping("/st/data_clean/message")
public class MqMessageController extends BaseController {

  @Resource
  MqMessageService mqMessageService;

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
      result = mqMessageService.routeMessage(pojo);
      messageEntityService.updateMessage2Succ(pojo.getTag());
    } catch (Exception e) {
      return processException(pojo, e).toString();
    }
    Throwable ex = ErrorsWrapper.getWrapper().getAndRemove();
    if (null != ex) {
      return processException(pojo, ex).toString();
    }
    return result.toString();
  }

  /**
   * 处理异常通用逻辑
   * 
   * @param pojo 参数POJO
   * @param ex 异常信息
   * @return 消息处理结果
   */
  private MessageRemoteResult processException(JMSGPojo pojo, Throwable ex) {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (ex instanceof MessageConsumeIgnoreException) {
      LoggerUtil.error("消息处理失败。", ex);
      result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
      return result;
    } else if (ex instanceof DcMsgConsuFailException) {
      // 消费异常报警
      if (((DcMsgConsuFailException) ex).getOnlyLog()) {
        LoggerUtil.logAlarmInfo(new MessageExceptionAlarm(pojo.getTag(), ex.getMessage()));
      } else {
        LoggerUtil.alarmInfo(new MessageExceptionAlarm(pojo.getTag(), ex.getMessage()));
      }
      result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
      return result;
    } else {
      LoggerUtil.error("消息处理失败。", ex);
      // 删除tag
      messageEntityService.deleteMessageByTag(pojo.getTag());
      result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
      return result;
    }
  }

}
