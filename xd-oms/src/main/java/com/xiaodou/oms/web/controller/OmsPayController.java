package com.xiaodou.oms.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.oms.service.oms.OmsOrderService;
import com.xiaodou.oms.service.order.CascadeQueryService;
import com.xiaodou.oms.service.task.TaskService;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.oms.util.model.QueryPaymentEntity;
import com.xiaodou.oms.vo.input.pay.ContinuePayPojo;
import com.xiaodou.oms.vo.input.pay.GetTokenPojo;
import com.xiaodou.oms.vo.input.pay.QueryFirstPayInfoPojo;
import com.xiaodou.oms.vo.input.pay.QueryOrderPayDetailPojo;
import com.xiaodou.oms.vo.input.pay.QueryPaymentAndSyncPojo;
import com.xiaodou.oms.vo.input.pay.QueryRecordPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.pay.ContinuePayVO;
import com.xiaodou.oms.vo.result.pay.FirstPayInfoVO;
import com.xiaodou.oms.vo.result.pay.GetTokenVO;
import com.xiaodou.oms.vo.result.pay.PayRecordListVO;
import com.xiaodou.oms.web.BaseController;
import com.xiaodou.oms.web.ServiceHandler;
import com.xiaodou.payment.vo.response.PayDetailResponse;

/**
 * 获取支付流水号
 * payment回调
 * 生成支付请求记录
 * 查询支付请求记录
 * <p/>
 * Date: 2014/6/30
 * Time: 16:44
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("pay")
public class OmsPayController extends BaseController {

  @Resource
  OrderServiceFacade orderServiceFacade;

  @Resource
  TaskService taskService;

  @Resource
  OmsOrderService omsOrderService;

  @Resource
  CascadeQueryService cascadeQueryService;

  /**
   * 获取token
   *
   * @param pojo GetTokenPojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "get_token", headers = {"content-type=application/json;charset=utf-8"})
  public String getToken(@RequestBody GetTokenPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GetTokenPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GetTokenVO doService(GetTokenPojo pojo) throws Exception {
        return orderServiceFacade.getToken(pojo);
      }
    });
  }

  /**
   * 继续支付
   * 生成支付请求记录，用于客户端用户选择继续支付的时候
   */
  @ResponseBody
  @RequestMapping(value = "continue_pay", headers = {"content-type=application/json;charset=utf-8"})
  public String continuePay(@RequestBody ContinuePayPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<ContinuePayPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ContinuePayVO doService(ContinuePayPojo pojo) throws Exception {
        //0 正常；1 订单不存在；2 订单状态不是支付失败；3 记录重复;4 找不到失败记录;5 订单Id为空
        return orderServiceFacade.continuePay(pojo);
      }
    });

  }

  /**
   * 查询payRecord列表
   *
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "query_payrecord_list", headers = {"content-type=application/json;charset=utf-8"})
  public String queryPayRecord(@RequestBody QueryRecordPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<QueryRecordPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public PayRecordListVO doService(QueryRecordPojo pojo) throws Exception {
        return orderServiceFacade.queryPayRecordList(pojo);
      }
    });
  }

  @ResponseBody
  @RequestMapping(value = "query_first_pay_info", headers = {"content-type=application/json;charset=utf-8"})
  public String queryFirstPayInfo(@RequestBody QueryFirstPayInfoPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<QueryFirstPayInfoPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public FirstPayInfoVO doService(QueryFirstPayInfoPojo pojo) throws Exception {
        FirstPayInfoVO vo = orderServiceFacade.queryFirstPayInfo(pojo);
        return vo;
      }
    });
  }

  @ResponseBody
  @RequestMapping(value = "query_order_pay_detail", headers = {"content-type=application/json;charset=utf-8"})
  public String queryOrderPayDetail(@RequestBody QueryOrderPayDetailPojo pojo) throws Exception {
    PayDetailResponse vo = orderServiceFacade.queryOrderPayDetail(pojo);
    return FastJsonUtil.toJson(vo);
  }


  @ResponseBody
  @RequestMapping("/query_payment_and_sync")
  public String queryPayment(@RequestBody QueryPaymentAndSyncPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<QueryPaymentAndSyncPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(QueryPaymentAndSyncPojo pojo) throws Exception {
        String gorderId = pojo.getGorderId();
        ResultInfo resultInfo = omsOrderService.queryPaymentAndFireEvent(gorderId);

        QueryPaymentEntity queryPaymentEntity = new QueryPaymentEntity();
        queryPaymentEntity.setGorderId(gorderId);
        queryPaymentEntity.setRetCode(resultInfo.getRetcode());
        queryPaymentEntity.setRetDesc(resultInfo.getRetdesc());
        OrderLoggerUtil.loggerQueryPaymentInfo(queryPaymentEntity);
        return resultInfo;
      }
    });

  }
  
  public static void main(String[] args) {
    String boundary = "------WebKitFormBoundaryQd08fpuXxDWwOFF4";
    StringBuffer contentBuffer = new StringBuffer();
    String content = "------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"id\"114733------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"OrderCode\"54173------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"product\"1022------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"paymentType\"creditCard------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"handleTime\"2014-12-10 18:49:20------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"Status\"3------WebKitFormBoundaryQd08fpuXxDWwOFF4Content-Disposition: form-data; name=\"type\"0------WebKitFormBoundaryQd08fpuXxDWwOFF4--";
    System.out.println(content.replace(boundary + "Content-Disposition: form-data; name=", "").replaceFirst("--\"", " ").replace(boundary, "").trim());
    String[] array =content.replace(boundary + "Content-Disposition: form-data; name=", "").replaceFirst("--\"", " ").replace(boundary, "").trim().replace("--", "").split("--\"");
  }

}

