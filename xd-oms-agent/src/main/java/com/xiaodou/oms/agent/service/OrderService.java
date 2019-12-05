package com.xiaodou.oms.agent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.agent.common.enums.GorderProperty;
import com.xiaodou.oms.agent.common.enums.OrderItemProperty;
import com.xiaodou.oms.agent.common.enums.OrderProperty;
import com.xiaodou.oms.agent.model.Gorder;
import com.xiaodou.oms.agent.model.Order;
import com.xiaodou.oms.agent.model.OrderItem;
import com.xiaodou.oms.agent.util.OrderConfigService;
import com.xiaodou.oms.agent.vo.request.CascadeGorderDetailRequest;
import com.xiaodou.oms.agent.vo.request.CascadeGorderOrderDetailRequest;
import com.xiaodou.oms.agent.vo.request.FirstPayInfoRequest;
import com.xiaodou.oms.agent.vo.request.GorderDetailRequest;
import com.xiaodou.oms.agent.vo.request.GorderListRequest;
import com.xiaodou.oms.agent.vo.request.GorderOrderDetailRequest;
import com.xiaodou.oms.agent.vo.request.GorderOrderItemDetailRequest;
import com.xiaodou.oms.agent.vo.request.GorderOrderItemListReuqest;
import com.xiaodou.oms.agent.vo.request.GorderOrderListRequest;
import com.xiaodou.oms.agent.vo.request.GorderQueryParam;
import com.xiaodou.oms.agent.vo.request.NewOrderRequest;
import com.xiaodou.oms.agent.vo.request.OrderDetailRequest;
import com.xiaodou.oms.agent.vo.request.OrderFireListRequest;
import com.xiaodou.oms.agent.vo.request.OrderFireRequest;
import com.xiaodou.oms.agent.vo.request.OrderItemDetailRequest;
import com.xiaodou.oms.agent.vo.request.OrderItemListRequest;
import com.xiaodou.oms.agent.vo.request.OrderItemNoteRequest;
import com.xiaodou.oms.agent.vo.request.OrderItemQueryParam;
import com.xiaodou.oms.agent.vo.request.OrderListRequest;
import com.xiaodou.oms.agent.vo.request.OrderNoteRequest;
import com.xiaodou.oms.agent.vo.request.OrderOrderItemListRequest;
import com.xiaodou.oms.agent.vo.request.OrderQueryParam;
import com.xiaodou.oms.agent.vo.request.Page;
import com.xiaodou.oms.agent.vo.request.PaymentQueryParam;
import com.xiaodou.oms.agent.vo.request.ReassignMerchantRequest;
import com.xiaodou.oms.agent.vo.request.ShopTagRequest;
import com.xiaodou.oms.agent.vo.request.UpdateBuyAccountIdByGorderIdRequest;
import com.xiaodou.oms.agent.vo.request.UpdateGorderRequest;
import com.xiaodou.oms.agent.vo.response.BaseResponse;
import com.xiaodou.oms.agent.vo.response.BaseResponseList;
import com.xiaodou.oms.agent.vo.response.FirstPayInfoResponse;
import com.xiaodou.oms.agent.vo.response.GorderDetailResponse;
import com.xiaodou.oms.agent.vo.response.GorderListResponse;
import com.xiaodou.oms.agent.vo.response.GorderOrderDetailResponse;
import com.xiaodou.oms.agent.vo.response.GorderOrderItemDetailResponse;
import com.xiaodou.oms.agent.vo.response.GorderOrderItemListResponse;
import com.xiaodou.oms.agent.vo.response.GorderOrderListResponse;
import com.xiaodou.oms.agent.vo.response.NewOrderResponse;
import com.xiaodou.oms.agent.vo.response.OrderDetailResponse;
import com.xiaodou.oms.agent.vo.response.OrderFireResponse;
import com.xiaodou.oms.agent.vo.response.OrderItemDetailResponse;
import com.xiaodou.oms.agent.vo.response.OrderItemListResponse;
import com.xiaodou.oms.agent.vo.response.OrderItemNoteResponse;
import com.xiaodou.oms.agent.vo.response.OrderListResponse;
import com.xiaodou.oms.agent.vo.response.OrderNoteResponse;
import com.xiaodou.oms.agent.vo.response.OrderOrderItemResponse;
import com.xiaodou.oms.agent.vo.response.ShopTagResponse;
import com.xiaodou.oms.agent.vo.response.UpdateBuyAccountIdByGorderIdResponse;

/**
 * Created by zyp on 14-6-18.
 */
@Service("orderService")
public class OrderService extends BaseService {

  /**
   * 状态机执行
   * 
   * @param orderFireRequest
   * @return
   */
  public BaseResponse orderFire(OrderFireRequest orderFireRequest) {
    BaseResponse baseResponse = null;
    try {
      String body = JSON.toJSONString(orderFireRequest);
      String url = OrderConfigService.getParams("oms.order.fire");
      String timeOut = OrderConfigService.getParams("oms.order.fire.timeOut");
      String retry = OrderConfigService.getParams("oms.order.fire.retry");
      baseResponse = sendHttp(url, body, BaseResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][orderFire]", e);
    }
    return baseResponse;
  }

  /**
   * 状态机批量执行
   * 
   * @param orderFireListRequest
   * @return
   */
  public BaseResponseList<OrderFireResponse> orderListFire(OrderFireListRequest orderFireListRequest) {
    BaseResponseList<OrderFireResponse> response = null;
    try {
      String body = JSON.toJSONString(orderFireListRequest);
      String url = OrderConfigService.getParams("oms.order.listfire");
      String timeOut = OrderConfigService.getParams("oms.order.listfire.timeOut");
      String retry = OrderConfigService.getParams("oms.order.listfire.retry");
      HttpResult result = this.httpRequest(url, body, timeOut, retry, true);
      if (result.isResultOk()) {
        try {
          response =
              JSON.parseObject(result.getContent(),
                  new TypeReference<BaseResponseList<OrderFireResponse>>() {});
        } catch (Exception e) {
          LoggerUtil.error("转换异常", e);
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][orderListFire]", e);
    }
    return response;
  }

  /**
   * 下单
   * 
   * @param newOrderRequest
   * @return
   */
  public NewOrderResponse createGorder(NewOrderRequest newOrderRequest) {
    NewOrderResponse newOrderResponse = null;
    try {
      String body = JSON.toJSONString(newOrderRequest);
      String url = OrderConfigService.getParams("oms.order.createGorder");
      String timeOut = OrderConfigService.getParams("oms.order.createGorder.timeOut");
      String retry = OrderConfigService.getParams("oms.order.createGorder.retry");
      newOrderResponse = sendHttp(url, body, NewOrderResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][createGorder]", e);
    }
    return newOrderResponse;
  }

  /**
   * 查询gorder列表
   * 
   * @param gorderListRequest
   * @return
   */
  public GorderListResponse queryGorderList(GorderListRequest gorderListRequest) {
    GorderListResponse gorderListResponse = null;
    try {
      String body = JSON.toJSONString(gorderListRequest);
      String url = OrderConfigService.getParams("oms.order.queryGorderList");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderList.retry");
      gorderListResponse = sendHttp(url, body, GorderListResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderList]", e);
    }
    return gorderListResponse;
  }

  /**
   * 查询Gorder详情
   * 
   * @param gorderDetailRequest
   */
  public GorderDetailResponse queryGorderDetail(GorderDetailRequest gorderDetailRequest) {
    GorderDetailResponse gorderDetailResponse = null;
    try {
      String body = JSON.toJSONString(gorderDetailRequest);
      String url = OrderConfigService.getParams("oms.order.queryGorderDetail");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderDetail.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderDetail.retry");
      gorderDetailResponse = sendHttp(url, body, GorderDetailResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderDetail]", e);
    }
    return gorderDetailResponse;
  }


  /**
   * 构建查询gorder-order对象结构
   * 
   * @param cascadeGorderOrderDetailRequest
   * @return
   */
  public Gorder cascadeGorderOrderDetail(
      CascadeGorderOrderDetailRequest cascadeGorderOrderDetailRequest) {
    Gorder gorder = null;
    try {
      // 1.构建查询请求
      GorderOrderListRequest gorderOrderListRequest = new GorderOrderListRequest();
      // 1.1 分页条件
      Page page = new Page();
      page.setPageNo(1);
      page.setPageSize(1000);
      gorderOrderListRequest.setPage(page);
      // 1.2 order查询条件
      OrderQueryParam orderQueryParams = new OrderQueryParam();
      gorderOrderListRequest.setOrderQueryParams(orderQueryParams);
      // 1.3 gorder查询条件
      GorderQueryParam gorderQueryParams = new GorderQueryParam();
      if (StringUtils.isNotBlank(cascadeGorderOrderDetailRequest.getBuyAccountId())) {
        gorderQueryParams.setBuyAccountId(cascadeGorderOrderDetailRequest.getBuyAccountId());
      }
      gorderQueryParams.setId(cascadeGorderOrderDetailRequest.getGorderId());
      gorderOrderListRequest.setGorderQueryParams(gorderQueryParams);
      // 1.4 设置输出
      gorderOrderListRequest.setGorderOutputProperties(cascadeGorderOrderDetailRequest
          .getGorderOutputProperties());
      gorderOrderListRequest.setOrderOutputProperties(cascadeGorderOrderDetailRequest
          .getOrderOutputProperties());
      gorderOrderListRequest.setProductLine(cascadeGorderOrderDetailRequest.getProductLine());
      // 1.5 执行查询
      GorderOrderListResponse gorderOrderListResponse =
          this.queryGorderOrderList(gorderOrderListRequest);
      // 2 构建返回值

      if (gorderOrderListResponse == null || gorderOrderListResponse.getRetCode() != 0
          || gorderOrderListResponse.getList().size() == 0) {
        return null;
      } else {
        List<Order> orderList = gorderOrderListResponse.getList();
        // 设置gorder
        gorder = orderList.get(0).getGorder();
        // 设置order
        gorder.setOrderList(orderList);
      }
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][cascadeGorderOrderDetail]", e);
    }
    return gorder;
  }


  /**
   * 大订单级联查询 获取到 Gorder Order orderItem全部信息
   * 
   * @param cascadeGorderDetailRequest
   * @return
   */
  public Gorder cascadeGorderDetail(CascadeGorderDetailRequest cascadeGorderDetailRequest) {
    Gorder gorder = null;
    try {
      // 1.构建查询请求
      GorderOrderItemListReuqest gorderOrderItemListReuqest = new GorderOrderItemListReuqest();
      // 设置:产品线
      gorderOrderItemListReuqest.setProductLine(cascadeGorderDetailRequest.getProductLine());
      // 设置:gorder查询条件
      // Gorder gorderQueryParams = new Gorder();
      GorderQueryParam gorderQueryParams = new GorderQueryParam();
      if (StringUtils.isNotBlank(cascadeGorderDetailRequest.getBuyAccountId())) {
        gorderQueryParams.setBuyAccountId(cascadeGorderDetailRequest.getBuyAccountId());
      }
      gorderQueryParams.setId(cascadeGorderDetailRequest.getGorderId());
      gorderOrderItemListReuqest.setGorderQueryParams(gorderQueryParams);
      // 设置:order查询条件
      OrderQueryParam orderQueryParams = new OrderQueryParam();
      gorderOrderItemListReuqest.setOrderQueryParams(orderQueryParams);
      // 设置:orderItem查询条件
      OrderItemQueryParam orderItemQueryParams = new OrderItemQueryParam();
      gorderOrderItemListReuqest.setOrderItemQueryParams(orderItemQueryParams);
      // 设置:gorder输出参数(保证id必须被查询出来)
      if (cascadeGorderDetailRequest.getGorderOutputProperties().get(
          GorderProperty.id.getPropertyName()) == null) {
        cascadeGorderDetailRequest.getGorderOutputProperties().put(
            GorderProperty.id.getPropertyName(), GorderProperty.id.getPropertyType());
      }
      gorderOrderItemListReuqest.setGorderOutputProperties(cascadeGorderDetailRequest
          .getGorderOutputProperties());
      // 设置:order输出参数(保证id必须被查询出来)
      if (cascadeGorderDetailRequest.getOrderOutputProperties().get(
          OrderProperty.id.getPropertyName()) == null) {
        cascadeGorderDetailRequest.getOrderOutputProperties().put(
            OrderProperty.id.getPropertyName(), OrderProperty.id.getPropertyType());
      }
      gorderOrderItemListReuqest.setOrderOutputProperties(cascadeGorderDetailRequest
          .getOrderOutputProperties());
      // 设置:orderItem输出参数(保证id必须被查询出来)
      if (cascadeGorderDetailRequest.getOrderItemOutputProperties().get(
          OrderItemProperty.id.getPropertyName()) == null) {
        cascadeGorderDetailRequest.getOrderItemOutputProperties().put(
            OrderItemProperty.id.getPropertyName(), OrderItemProperty.id.getPropertyType());
      }
      gorderOrderItemListReuqest.setOrderItemOutputProperties(cascadeGorderDetailRequest
          .getOrderItemOutputProperties());
      // 设置:分页信息
      Page page = new Page();
      page.setPageNo(1);
      page.setPageSize(1000);
      gorderOrderItemListReuqest.setPage(page);
      // 执行:查询
      GorderOrderItemListResponse gorderOrderItemListResponse =
          this.queryGorderOrderItemList(gorderOrderItemListReuqest);
      // 结果

      // 结果组装
      if (gorderOrderItemListResponse == null || gorderOrderItemListResponse.getRetCode() != 0
          || gorderOrderItemListResponse.getList() == null
          || gorderOrderItemListResponse.getList().size() == 0) {
        return null;
      } else {
        List<OrderItem> orderItemList = gorderOrderItemListResponse.getList();
        // item检查
        for (OrderItem orderItem : orderItemList) {
          if (orderItem.getOrder() == null || orderItem.getGorder() == null) {
            return null;
          } else {
            // 保证id必须存在才行
            if (StringUtils.isBlank(orderItem.getId())
                || StringUtils.isBlank(orderItem.getOrder().getId())
                || StringUtils.isBlank(orderItem.getGorder().getId())) {
              return null;
            }
          }
        }
        // 获得gorder
        gorder = orderItemList.get(0).getGorder();
        // 设置order
        Map<String, Order> orders = new HashMap<String, Order>();
        for (OrderItem orderItem : orderItemList) {
          Order order = orderItem.getOrder();
          if (orders.get(order.getId()) == null) {
            orders.put(order.getId(), order);
          }
        }
        List<Order> orderList = new ArrayList<Order>();
        for (String key : orders.keySet()) {
          orderList.add(orders.get(key));
        }
        gorder.setOrderList(orderList);
        // 设置orderItem
        for (Order order : orderList) {
          List<OrderItem> tempOrderItemList = new ArrayList<OrderItem>();
          for (OrderItem orderItem : orderItemList) {
            if (orderItem.getOrder().getId().equals(order.getId())) {
              tempOrderItemList.add(orderItem);
            }
          }
          order.setOrderItemList(tempOrderItemList);
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][cascadeGorderDetail]", e);
    }
    return gorder;
  }

  /**
   * 查询order列表
   * 
   * @param orderListRequest
   * @return
   */
  public OrderListResponse queryOrderList(OrderListRequest orderListRequest) {
    OrderListResponse orderListResponse = null;
    try {
      String body = JSON.toJSONString(orderListRequest);
      String url = OrderConfigService.getParams("oms.order.queryOrderList");
      String timeOut = OrderConfigService.getParams("oms.order.queryOrderList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryOrderList.retry");
      orderListResponse = sendHttp(url, body, OrderListResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryOrderList]", e);
    }
    return orderListResponse;
  }

  /**
   * 查询order详情
   * 
   * @param orderDetailRequest
   * @return
   */
  public OrderDetailResponse queryOrderDetail(OrderDetailRequest orderDetailRequest) {
    OrderDetailResponse orderDetailResponse = null;
    try {
      String body = JSON.toJSONString(orderDetailRequest);
      String url = OrderConfigService.getParams("oms.order.queryOrderDetail");
      String timeOut = OrderConfigService.getParams("oms.order.queryOrderDetail.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryOrderDetail.retry");
      orderDetailResponse = sendHttp(url, body, OrderDetailResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryOrderDetail]", e);
    }
    return orderDetailResponse;
  }

  /**
   * 查询orderItem列表
   * 
   * @param orderItemListRequest
   * @return
   */
  public OrderItemListResponse queryOrderItemList(OrderItemListRequest orderItemListRequest) {
    OrderItemListResponse orderItemListResponse = null;
    try {
      String body = JSON.toJSONString(orderItemListRequest);
      String url = OrderConfigService.getParams("oms.order.queryOrderItemList");
      String timeOut = OrderConfigService.getParams("oms.order.queryOrderItemList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryOrderItemList.retry");
      orderItemListResponse =
          sendHttp(url, body, OrderItemListResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryOrderItemList]", e);
    }
    return orderItemListResponse;
  }

  /**
   * 查询orderItem详情
   * 
   * @param orderItemDetailRequest
   * @return
   */
  public OrderItemDetailResponse queryOrderItemDetail(OrderItemDetailRequest orderItemDetailRequest) {
    OrderItemDetailResponse orderItemDetailResponse = null;
    try {
      String body = JSON.toJSONString(orderItemDetailRequest);
      String url = OrderConfigService.getParams("oms.order.queryOrderItemDetail");
      String timeOut = OrderConfigService.getParams("oms.order.queryOrderItemDetail.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryOrderItemDetail.retry");
      orderItemDetailResponse =
          sendHttp(url, body, OrderItemDetailResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryOrderItemDetail]", e);
    }
    return orderItemDetailResponse;
  }

  /**
   * gorder-order-item 级联查询列表
   * 
   * @param gorderOrderItemListReuqest
   * @return
   */
  public GorderOrderItemListResponse queryGorderOrderItemList(
      GorderOrderItemListReuqest gorderOrderItemListReuqest) {
    GorderOrderItemListResponse gorderOrderItemListResponse = null;
    try {
      String body = JSON.toJSONString(gorderOrderItemListReuqest);
      String url = OrderConfigService.getParams("oms.order.queryGorderOrderItemList");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderOrderItemList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderOrderItemList.retry");
      gorderOrderItemListResponse =
          sendHttp(url, body, GorderOrderItemListResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderOrderItemList]", e);
    }
    return gorderOrderItemListResponse;
  }

  /**
   * Gorder-order-orderItem 级联查询详情
   * 
   * @param gorderOrderItemDetailRequest
   * @return
   */
  public GorderOrderItemDetailResponse queryGorderOrderItemDetail(
      GorderOrderItemDetailRequest gorderOrderItemDetailRequest) {
    GorderOrderItemDetailResponse gorderOrderItemDetailResponse = null;
    try {
      String body = JSON.toJSONString(gorderOrderItemDetailRequest);
      String url = OrderConfigService.getParams("oms.order.queryGorderOrderItemDetail");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderOrderItemDetail.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderOrderItemDetail.retry");
      gorderOrderItemDetailResponse =
          sendHttp(url, body, GorderOrderItemDetailResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderOrderItemDetail]", e);
    }
    return gorderOrderItemDetailResponse;
  }

  /**
   * gorder-order级联查询列表
   * 
   * @param gorderOrderListRequest
   * @return
   */
  public GorderOrderListResponse queryGorderOrderList(GorderOrderListRequest gorderOrderListRequest) {
    GorderOrderListResponse gorderOrderListResponse = null;
    try {
      String body = JSON.toJSONString(gorderOrderListRequest);
      String url = OrderConfigService.getParams("oms.order.queryGorderOrderList");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderOrderList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderOrderList.retry");
      gorderOrderListResponse =
          sendHttp(url, body, GorderOrderListResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderOrderList]", e);
    }
    return gorderOrderListResponse;
  }

  /**
   * gorder-order 级联详情查询
   * 
   * @param gorderOrderDetailRequest
   * @return
   */
  public GorderOrderDetailResponse queryGorderOrderDetail(
      GorderOrderDetailRequest gorderOrderDetailRequest) {
    GorderOrderDetailResponse gorderOrderDetailResponse = null;
    try {
      String body = JSON.toJSONString(gorderOrderDetailRequest);
      String url = OrderConfigService.getParams("oms.order.queryGorderOrderDetail");
      String timeOut = OrderConfigService.getParams("oms.order.queryGorderOrderDetail.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryGorderOrderDetail.retry");
      gorderOrderDetailResponse =
          sendHttp(url, body, GorderOrderDetailResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryGorderOrderDetail]", e);
    }
    return gorderOrderDetailResponse;
  }

  /**
   * order-orderItem级联查询列表
   * 
   * @param orderOrderItemListRequest
   * @return
   */
  public OrderOrderItemResponse queryOrderOrderItemList(
      OrderOrderItemListRequest orderOrderItemListRequest) {
    OrderOrderItemResponse orderOrderItemListResponse = null;
    try {
      String body = JSON.toJSONString(orderOrderItemListRequest);
      String url = OrderConfigService.getParams("oms.order.queryOrderOrderItemList");
      String timeOut = OrderConfigService.getParams("oms.order.queryOrderOrderItemList.timeOut");
      String retry = OrderConfigService.getParams("oms.order.queryOrderOrderItemList.retry");
      orderOrderItemListResponse =
          sendHttp(url, body, OrderOrderItemResponse.class, timeOut, retry, false);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryOrderOrderItemList]", e);
    }
    return orderOrderItemListResponse;
  }

  /**
   * 添加订单备注
   * 
   * @param orderNoteRequest
   * @return
   */
  public OrderNoteResponse addOrderNote(OrderNoteRequest orderNoteRequest) {
    OrderNoteResponse orderNoteResponse = null;
    try {
      String body = JSON.toJSONString(orderNoteRequest);
      String url = OrderConfigService.getParams("oms.order.addOrderNote");
      String timeOut = OrderConfigService.getParams("oms.order.addOrderNote.timeOut");
      String retry = OrderConfigService.getParams("oms.order.addOrderNote.retry");
      orderNoteResponse = sendHttp(url, body, OrderNoteResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][addOrderNote]", e);
    }
    return orderNoteResponse;
  }

  /**
   * @param orderItemNoteRequest
   * @return
   */
  public OrderItemNoteResponse addOrderItemNote(OrderItemNoteRequest orderItemNoteRequest) {
    OrderItemNoteResponse orderItemNoteResponse = null;
    try {
      String body = JSON.toJSONString(orderItemNoteRequest);
      String url = OrderConfigService.getParams("oms.order.addItemNote");
      String timeOut = OrderConfigService.getParams("oms.order.addItemNote.timeOut");
      String retry = OrderConfigService.getParams("oms.order.addItemNote.retry");
      orderItemNoteResponse =
          sendHttp(url, body, OrderItemNoteResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][addOrderItemNote]", e);
    }
    return orderItemNoteResponse;
  }

  /**
   * 改派供应商
   * 
   * @return
   */
  public BaseResponse reassignMerchant(ReassignMerchantRequest reassignMerchantRequest) {
    BaseResponse baseResponse = null;
    try {
      String body = JSON.toJSONString(reassignMerchantRequest);
      String url = OrderConfigService.getParams("oms.order.reassignMerchant");
      String timeOut = OrderConfigService.getParams("oms.order.reassignMerchant.timeOut");
      String retry = OrderConfigService.getParams("oms.order.reassignMerchant.retry");
      baseResponse = sendHttp(url, body, BaseResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][reassignMerchant]", e);
    }
    return baseResponse;
  }

  /**
   * 更新order表misc信息
   */
  public void updateOrderMisc() {

  }

  /**
   * 更新orderItem表misc信息
   */
  public void updateOrderItemMisc() {

  }

  /**
   * 主动查询pmt支付状态接口
   * 
   * @param param
   * @return
   */
  public BaseResponse syncPayment(PaymentQueryParam param) {
    BaseResponse baseResponse = null;
    try {
      FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/oms.properties");
      String urlStr = fileUtil.getProperties("oms.order.querypayment");
      String retry = fileUtil.getProperties("oms.order.querypayment.retry");
      String timeOut = fileUtil.getProperties("oms.order.querypayment.timeOut");
      String body = JSON.toJSONString(param);
      baseResponse = sendHttp(urlStr, body, BaseResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryPayment]", e);
    }
    return baseResponse;
  }

  /**
   * 通过GorderId更新BuyAccountId updateBuyAccountIdByGorderId
   * 
   * @param request
   * @return
   * @Title: updateBuyAccountIdByGorderId
   * @Description: TODO
   */
  public UpdateBuyAccountIdByGorderIdResponse updateBuyAccountIdByGorderId(
      UpdateBuyAccountIdByGorderIdRequest request) {
    UpdateBuyAccountIdByGorderIdResponse response = null;

    try {
      String body = JSON.toJSONString(request);
      FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/oms.properties");
      String url = fileUtil.getProperties("oms.order.updateBuyAccountIdByGorderId");
      String timeOut = fileUtil.getProperties("oms.order.updateBuyAccountIdByGorderId.timeOut");
      String retry = fileUtil.getProperties("oms.order.updateBuyAccountIdByGorderId.retry");
      response =
          sendHttp(url, body, UpdateBuyAccountIdByGorderIdResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][updateBuyAccountIdByGorderId]", e);
      response.setRetCode(-1);
    }

    return response;
  }

  /**
   * queryFirstPayInfo
   * 
   * @param firstPayInfoRequest
   * @return
   * @Title: queryFirstPayInfo
   * @Description: 查询gorder支付信息
   */
  public FirstPayInfoResponse queryFirstPayInfo(FirstPayInfoRequest firstPayInfoRequest) {
    FirstPayInfoResponse firstPayInfoResponse = new FirstPayInfoResponse();
    try {
      String body = JSON.toJSONString(firstPayInfoRequest);
      FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/oms.properties");
      String url = fileUtil.getProperties("oms.pay.queryFirstPayInfo");
      String timeOut = fileUtil.getProperties("oms.pay.queryFirstPayInfo.timeOut");
      String retry = fileUtil.getProperties("oms.pay.queryFirstPayInfo.retry");
      firstPayInfoResponse = sendHttp(url, body, FirstPayInfoResponse.class, timeOut, retry, true);
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][queryFirstPayInfo]", e);
      firstPayInfoResponse.setRetCode(-1);
    }
    return firstPayInfoResponse;
  }

  /**
   * 更新Gorder Tags
   * 
   * @param req
   * @return BaseResponse
   * @title: updateGorderTags
   * @date 2015年1月29日 下午5:49:43
   */
  public BaseResponse updateGorderTags(UpdateGorderRequest req) {
    BaseResponse res = new BaseResponse();
    try {
      String body = JSON.toJSONString(req);
      FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/oms.properties");
      String url = fileUtil.getProperties("oms.order.updateGorderTags");
      res = sendHttp(url, body, BaseResponse.class, "10000", "0", true);
      return res;
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][updateGorderTags]", e);
      throw e;
    }
  }

  public ShopTagResponse createShopTag(ShopTagRequest req) {
    ShopTagResponse res = new ShopTagResponse();
    try {
      String body = JSON.toJSONString(req);
      FileUtil fileUtil = FileUtil.getInstance("/conf/custom/env/oms.properties");
      String url = fileUtil.getProperties("oms.order.createShopTag");
      String timeOut = fileUtil.getProperties("oms.order.createShopTag.timeOut");
      res = sendHttp(url, body, ShopTagResponse.class, timeOut, "0", true);
      return res;
    } catch (Exception e) {
      LoggerUtil.error("[omsagent][createShopTag]", e);
      throw e;
    }
  }

}
