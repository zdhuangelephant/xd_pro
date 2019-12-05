package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.service.QueryService;
import com.xiaodou.payment.vo.request.GetPayDetailRequest;
import com.xiaodou.payment.vo.response.PayDetailResponse;

/**
 * Date: 2014/10/22
 * Time: 11:42
 *
 * @author Tian.Dong
 */
public class GetPayDetailTest {
  //@Test
  public void test(){
    GetPayDetailRequest request = new GetPayDetailRequest();
    request.setBusinessType(1022);
    request.setOrderId("24628");
    PayDetailResponse response =  QueryService.getPayDetail(request);
    System.out.println(FastJsonUtil.toJson(response));
  }
}
