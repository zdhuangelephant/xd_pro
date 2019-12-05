package com.xiaodou.sms.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.CheckDuplicateUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.message.web.response.MessageResponse;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.sms.common.enums.ResultType;
import com.xiaodou.sms.service.sms.ShortMessageProduceService;
import com.xiaodou.summer.web.BaseController;

/**
 * 短信
 * 
 */
@Controller
@RequestMapping("sms")
public class SMSProduceController extends BaseController {

  @Resource
  ShortMessageProduceService shortMessageProduceService;

  @RequestMapping("/produce")
  @ResponseBody
  public String produce(ShortMessage pojo) {
    if (CheckDuplicateUtil.checkDuplicate(pojo, 1)){
      return FastJsonUtil.toJson(new MessageResponse(ResultType.SUCCESS));
    }
    return FastJsonUtil.toJson(shortMessageProduceService.produce(pojo));
  }

}
