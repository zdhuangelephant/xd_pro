package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.QueryService;
import com.xiaodou.payment.vo.input.GetPayStatusPojo;
import com.xiaodou.payment.vo.request.GetPayStatusRequest;
import com.xiaodou.payment.vo.response.PayStatusResponse;

import org.junit.Test;

/**
 * Date: 2014/11/10
 * Time: 14:10
 *
 * @author Tian.Dong
 */
public class GetPayStatusTest {
  @Test
  public void test() {
    GetPayStatusRequest request = new GetPayStatusRequest();
    request.setBusinessType(1022);
    request.setOrderId("38008");
    request.setTradeNo("-100000000000130445");
    PayStatusResponse response = QueryService.getPayStatus(request);
    System.out.println(FastJsonUtil.toJson(response));
  }

  @Test
  public void test2() {
    GetPayStatusPojo pojo = new GetPayStatusPojo();
    pojo.setGorderId("35413");
    pojo.setProductLine("05");
    pojo.setToken("-100000000000100572");
    PayStatusResponse response = PaymentService.getPayStatus(pojo);
    System.out.println(FastJsonUtil.toJson(response));
  }
}
