package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.async.model.AbstractAsyncMessage;
import com.xiaodou.async.request.MessageResRequest;
import com.xiaodou.async.request.QueryListRequest;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.jmsg.exception.MessageConsumeIgnoreException;
import com.xiaodou.mqcr.service.MessageEntityService;
import com.xiaodou.userCenter.service.AsyncMessageService;

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
      asyncMessageService
          .sendMessage(FastJsonUtil.fromJson(pojo.getMessage(), AbstractAsyncMessage.class));
      messageEntityService.updateMessage2Succ(pojo.getTag());
    } catch (Exception e) {
      LoggerUtil.error("消息处理失败。", e);
      if (e instanceof MessageConsumeIgnoreException) {
        result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
        return JSON.toJSONString(result);
      } else {
        // 删除tag
        messageEntityService.deleteMessageByTag(pojo.getTag());
        result = new MessageRemoteResult(MessageRemoteResultType.FAIL);
        return JSON.toJSONString(result);
      }
    }
    return result.toString();
  }

  @RequestMapping(value = "message_res")
  @ResponseBody
  public String feedBack(MessageResRequest request)
      throws InstantiationException, IllegalAccessException {
    return asyncMessageService.dealMessage(request).toJsonString();
  }

  @RequestMapping("my_message")
  @ResponseBody
  public String queryList(QueryListRequest request) throws Exception {
    return FastJsonUtil.toJson(asyncMessageService.queryList(request));
  }

}
