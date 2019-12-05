package com.xiaodou.course.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.course.service.trade.TradeService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.trade.TradeDetailRequest;

/**
 * @name TradeController CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月6日
 * @description 交易记录模块控制器
 * @version 1.0
 */
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {

  @Resource
  TradeService tradeService;

  /**
   * 交易记录列表
   * 
   * @param request
   * @return
   */
  @RequestMapping("/trade_detail")
  @ResponseBody
  public String tradeDetail(TradeDetailRequest request) {
    return tradeService.tradeDetail(request).toString0();
  }

}
