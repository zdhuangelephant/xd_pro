package com.xiaodou.resources.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.resources.request.order.CreateOrderRequest;
import com.xiaodou.resources.request.order.PayOrderRequest;
import com.xiaodou.resources.service.product.order.ProductOmsService;
import com.xiaodou.summer.web.BaseController;

@RequestMapping("/product")
@Controller
public class ProductOmsController extends BaseController {

  @Resource
  ProductOmsService productOmsService;

  /**
   * 1、创建课程产品订单
   * 
   * @param request
   * @return
   */
  @RequestMapping("/create_order")
  @ResponseBody
  public String createOrder(CreateOrderRequest request) {
    return productOmsService.creatGorder(request).toString0();
  }

  /**
   * 2、支付课程产品订单
   * 
   * @param request
   * @return
   */
  @RequestMapping("/pay")
  @ResponseBody
  public String pay(PayOrderRequest request) {
    return productOmsService.pay(request).toString0();
  }

}
