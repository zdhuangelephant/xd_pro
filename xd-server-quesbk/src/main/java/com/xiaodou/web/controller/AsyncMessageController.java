package com.xiaodou.web.controller;

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
import com.xiaodou.service.AsyncMessageService;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.vo.alarm.MqMessageExceptionAlarm;

@Controller("asyncMessageController")
@RequestMapping("message")
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
        try {
            if (!messageEntityService.canConsumMessage(pojo.getTag())
                    || !messageEntityService.insertNewMessage(pojo.getTag())) {
                return result.toString();
            }
        } catch (Exception e) {
            LoggerUtil.error("消息判重 && 插入判重记录 失败。", e);
        }
        try {
            Class<?> asyncMessageServiceClass = AsyncMessageService.class;
            Method method =
                    asyncMessageServiceClass.getMethod(pojo.getMessageName(), JMSGPojo.class);
            result = (MessageRemoteResult) method.invoke(asyncMessageService, pojo);
            try {
                messageEntityService.updateMessage2Succ(pojo.getTag());
            } catch (Exception e) {
                LoggerUtil.error("修改tag 失败。", e);
            }
        } catch (Exception e) {
            LoggerUtil.error("消息处理失败。", e);
            if (e instanceof MessageConsumeIgnoreException) {
                result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
                return JSON.toJSONString(result);
            } else {
                // 删除tag
                try {
                    messageEntityService.deleteMessageByTag(pojo.getTag());
                } catch (Exception e2) {
                    LoggerUtil.error("删除tag 失败。", e);
                }
                LoggerUtil.alarmInfo(new MqMessageExceptionAlarm(pojo.getTag()));
                result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
                return JSON.toJSONString(result);
            }
        }
        return result.toString();
    }

}
