package com.xiaodou.st.dashboard.web.controller.order;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.order.OrderDO;
import com.xiaodou.st.dashboard.domain.order.OrderDTO;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.order.OrderService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("orderController")
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Resource
  OrderService orderService;
  @Resource
  ApplyService applyService;

  /**
   * 订单列表
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param orderDO
   * @return
   */
  @RequestMapping("/order_list")
  public ModelAndView orderList(OrderDTO orderDTO) {
    ModelAndView mv = new ModelAndView("/order/orderList");
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("orderDTO", orderDTO);
    mv.addObject("listOrder", orderService.listOrder(orderDTO));
    return mv;
  }

  /**
   * 生成订单
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param orderDO
   * @return
   */
  @RequestMapping("/save_order")
  @ResponseBody
  // public Long saveOrder(String applyIds, Integer studentCount, Integer applyCount,
  // Double totalAmount) {
  // return orderService.saveOrder(applyIds, studentCount, applyCount, totalAmount);
  // }
  public Long saveOrder(HttpServletRequest request) {
    HttpSession session = request.getSession();
    ApplyDO applyDO = (ApplyDO) session.getAttribute("applyDO");
//    Integer studentCount = (Integer) session.getAttribute("studentCount");
//    Integer applyCount = (Integer) session.getAttribute("applyCount");
//    Double totalAmount = (Double) session.getAttribute("totalAmount");
    Integer studentCount = 0;
    Integer applyCount = 0;
    Double totalAmount = 0d;
    List<Long> applyIds = Lists.newArrayList();
    List<ApplyDO> listApply = applyService.listApply(applyDO);
    if (null != applyDO && Constants.NOTPAYMENT == applyDO.getOrderStatus()) {
      if (null != listApply && !listApply.isEmpty()) {
        applyCount = listApply.size();
        applyIds = Lists.transform(listApply, new Function<ApplyDO, Long>() {
          @Override
          public Long apply(ApplyDO input) {
            if (null == input) return null;
            return input.getId();
          }
        });
        for (ApplyDO ado : listApply) {
          totalAmount += ado.getOriginalAmount();
        }
      }
      studentCount = applyService.getStudentCount(listApply);
    }
    return orderService.saveOrder(applyIds, studentCount, applyCount, totalAmount);
  }


  /**
   * 关闭订单
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param orderId
   * @return
   */
  @RequestMapping("/close_order")
  @ResponseBody
  public String closeOrder(Long orderId, Long orderNumber) {
    boolean flag = orderService.closeOrder(orderId, orderNumber);
    return String.valueOf(flag);
  }

  /**
   * 刪除訂單
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月2日
   * @param orderId
   * @return
   */
  @RequestMapping("/remove_order")
  @ResponseBody
  public String removeOrder(Long orderId) {
    boolean flag = orderService.removeOrder(orderId);
    return String.valueOf(flag);
  }

  @RequestMapping("/default_pay")
  @ResponseBody
  public String defaultPay(Long orderNumber) {
    boolean flag = orderService.updateOrder(orderNumber, Constants.ALREADYPAYMENT);
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 查看订单详情
   * @author 李德洪
   * @Date 2017年4月5日
   * @param orderNumber
   * @return
   */
  @RequestMapping("/order_detail")
  public ModelAndView detailOrder(Long orderNumber) {
    ModelAndView mv = new ModelAndView("/order/orderDeatil");
    mv.addObject("adminUser", super.getAdminUser());

    OrderDO order = orderService.getOrder(orderNumber);
    mv.addObject("orderDO", order);
    ApplyDO applyDO = new ApplyDO();
    applyDO.setOrderNumber(orderNumber);

    List<ApplyDO> listApply = applyService.listApply(applyDO);
    if(!CollectionUtils.isEmpty(listApply)){
      for (ApplyDO ado : listApply) {
        ado.setRegTelephone(ado.getTelephone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
      }
    }
    mv.addObject("listApply", listApply);
    return mv;
  }

  /**
   * 
   * @description 查看提交订单详情
   * @author 李德洪
   * @Date 2017年4月2日
   * @return
   */
  @RequestMapping("/save_order_detail")
  public ModelAndView saveOrderDetail(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("/order/saveOrderDeatil");
    HttpSession session = request.getSession();
    ApplyDO applyDO = (ApplyDO) session.getAttribute("applyDO");
    List<ApplyDO> listApply = applyService.listApply(applyDO);

    Integer studentCount = 0;
    Integer applyCount = 0;
    Double totalAmount = 0d;
    if (null != applyDO && Constants.NOTPAYMENT == applyDO.getOrderStatus()) {
      if (null != listApply && !listApply.isEmpty()) {
        applyCount = listApply.size();
        for (ApplyDO ado : listApply) {
          totalAmount += ado.getOriginalAmount();
        }
      }
      studentCount = applyService.getStudentCount(listApply);
    }
    
    mv.addObject("adminUser", super.getAdminUser());
    // mv.addObject("listApply", applyService.listApplyByIds(applyIds));
    mv.addObject("listApply", listApply);
    mv.addObject("studentCount", studentCount);
    mv.addObject("applyCount", applyCount);
    mv.addObject("totalAmount",totalAmount);
    return mv;
  }
}
