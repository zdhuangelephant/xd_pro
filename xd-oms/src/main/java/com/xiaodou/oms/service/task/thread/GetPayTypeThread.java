package com.xiaodou.oms.service.task.thread;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.constant.order.PayType;
import com.xiaodou.oms.entity.GorderMisc;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.vo.input.GetPayTypePojo;
import com.xiaodou.payment.vo.response.PayTypeResponse;
import com.xiaodou.payment.vo.response.inner.MixPaymentTypeInfo;

/**
 * 批处理获取支付方式
 * <p/>
 * Date: 2014/7/23
 * Time: 15:32
 *
 * @author Tian.Dong
 */
public class GetPayTypeThread implements Runnable {

  private OrderServiceFacade orderServiceFacade;

  private Gorder gorder;

  public GetPayTypeThread(OrderServiceFacade orderServiceFacade, Gorder gorder) {
    this.orderServiceFacade = orderServiceFacade;
    this.gorder = gorder;
  }

  @Override
  public void run() {
    try {
      updateGorderPayMethod();
    } catch (Exception e) {
      LoggerUtil.error("批处理查询支付方式、更新gorder异常", e);
    }
  }

  private void updateGorderPayMethod() {
    String gorderId = gorder.getId();
    List<PayRecord> payRecords = orderServiceFacade.queryFirstPayRecord(gorder);
    if (payRecords == null || payRecords.size() == 0) {
      return;
    }
    for (PayRecord payRecord : payRecords) {
      if (!payRecord.getPayStatus().equals(PayRecord.PAY_STATUS_SUCCESS)) {
        //如果状态不是成功，跳过
        continue;
      }
      String productLine = gorder.getProductType().substring(0, 2);
      GetPayTypePojo getPayTypePojo = new GetPayTypePojo();
      getPayTypePojo.setProductLine(productLine);
      getPayTypePojo.setToken(payRecord.getPayNo());
      getPayTypePojo.setGorderId(gorderId);
      PayTypeResponse payTypeResponse = PaymentService.getPayType(getPayTypePojo);
      if (payTypeResponse.getMixPaymentTypeInfoList() != null
          && payTypeResponse.getMixPaymentTypeInfoList().size() > 0) {
        GorderMisc gorderMisc = new GorderMisc();
        gorderMisc.setPayMethod(FastJsonUtil.toJson(payTypeResponse.getMixPaymentTypeInfoList()));
        gorder.setGorderDesc(FastJsonUtil.toJson(gorderMisc));
        StringBuilder payMethod = new StringBuilder();

        for (MixPaymentTypeInfo payTypeInfo : payTypeResponse.getMixPaymentTypeInfoList()) {
          int transType = payTypeInfo.getTransType();
          PayType payType = new PayType(transType);
          payMethod.append(payType.getDesc());
          payMethod.append("+");
        }
        payMethod.deleteCharAt(payMethod.length() - 1);
        gorder.setRealPayMethod(payMethod.toString());
        orderServiceFacade.updateGorder(gorder, null);
      } else {
        gorder.setRealPayMethod("~~");
        orderServiceFacade.updateGorder(gorder, null);
      }
    }
  }
}
