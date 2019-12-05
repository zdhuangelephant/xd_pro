package com.xiaodou.course.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.course.service.order.ProductOmsService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.order.CreateOrderRequest;
import com.xiaodou.course.web.request.order.PayOrderRequest;

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
