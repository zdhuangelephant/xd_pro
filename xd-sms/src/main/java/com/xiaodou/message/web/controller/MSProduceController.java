package com.xiaodou.message.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.CheckDuplicateUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.message.enums.JpushTagsEnums;
import com.xiaodou.message.service.message.MessageProduceService;
import com.xiaodou.message.utils.JPushProp;
import com.xiaodou.message.web.response.MessageResponse;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.sms.common.enums.ResultType;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.message.web.controller.MSProduceController.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年5月22日
 * @description 推送
 * @version 1.0
 */
@Controller
@RequestMapping("push")
public class MSProduceController extends BaseController {

  @Resource
  MessageProduceService messageProduceService;

  private static final int duplicateTime = 60 * 5;
  /**
   * @description push
   * @author 李德洪
   * @Date 2018年5月24日
   * @param pojo
   * @return
   */
  @RequestMapping("/produce")
  public @ResponseBody
  String produce(Message pojo) {
    if (CheckDuplicateUtil.checkDuplicate(pojo.getAppMessageId(), duplicateTime)){
      return FastJsonUtil.toJson(new MessageResponse(ResultType.SUCCESS));
    }
    List<String> tags = pojo.getGroupList();
    MessageResponse response = new MessageResponse(ResultType.SUCCESS);
    //校验tag合法性（tag属于预定义列表中）
    if (CollectionUtils.isEmpty(tags) || null == JpushTagsEnums.getByName(tags)) {
      response.setStatus(1);
      response.setStatusDesc("生成消息失败：tag为空或者为非法值！");
      return FastJsonUtil.toJson(response);
    }
    //校验tag正确性（tag与环境相符合）
    // 向线上环境推预发布的环境 为正常情况
    if(tags.contains(JpushTagsEnums.XD_PRE_RELEASE.getName()) && JPushProp.isOnline()){
      //正常情况
    }else{
      //tag与环境不符
      if (!tags.contains(JPushProp.getEnvParams())) {
        response.setStatus(1);
        response.setStatusDesc("生成消息失败：发送tag与环境不符！");
        return FastJsonUtil.toJson(response);
      }
    }
    
    return FastJsonUtil.toJson(messageProduceService.produce(pojo));
  }
  
}
