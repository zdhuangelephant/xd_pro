package com.xiaodou.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.service.QuesRedBonusService;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.vo.request.GetAwardPojo;
import com.xiaodou.vo.request.InitAwardPojo;

@Controller("quesRedBonusController")
@RequestMapping("/quesbk")
public class QuesRedBonusController extends BaseController {

  /** quesRedBonusService 红包操作service */
  @Resource
  QuesRedBonusService quesRedBonusService;
  
  @RequestMapping("init_award")
  @ResponseBody
  public String initAward(InitAwardPojo pojo){
    return quesRedBonusService.initAward(pojo).toString();
  }
  
  @RequestMapping("get_award")
  @ResponseBody
  public String getAward(GetAwardPojo pojo){
    return quesRedBonusService.getAward(pojo).toString();
  }
  
}
