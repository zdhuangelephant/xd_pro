package com.xiaodou.server.mapi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;

@Controller("message")
public class MessageController {

  @RequestMapping(value = "messageConsume", method = RequestMethod.POST)
  @ResponseBody
  public String messageConsume() {
    return new MessageRemoteResult(MessageRemoteResultType.SUCCESS).toString();
  }

}
