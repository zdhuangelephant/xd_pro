package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.QueryService;
import com.xiaodou.payment.vo.input.GetPayTypePojo;
import com.xiaodou.payment.vo.request.GetPayTypeRequest;
import com.xiaodou.payment.vo.response.PayTypeResponse;

/**
 * 获取支付方式
 * Date: 2014/7/18
 * Time: 14:33
 *
 * @author Tian.Dong
 */
public class GetPayTypeTest {
  /**
   * {"rep_code":"0","error_info":"","trans_type":3002,"operation_id":2005,"result_status":-1,"payment_status_desc":"开始扣除您信用卡冻结款项","payment_type_info_list":[{"amt":11.11,"record_id":31524,"trans_type":3002,"trans_type_desc":"信用卡","result_status":-1,"result_status_desc":"正在处理"}]}
   */

  /**
   * {"rep_code":"0","error_info":"","trans_type":3002,"operation_id":2005,"result_status":1,"payment_status_desc":"您的信用卡已支付成功","payment_type_info_list":[{"amt":11.11,"record_id":31524,"trans_type":3002,"trans_type_desc":"信用卡","result_status":1,"result_status_desc":"成功"}]}
   */

  /**
   * {"error_info":"","operation_id":2005,"payment_status_desc":"开始扣除您信用卡冻结款项","payment_type_info_list":[{"amt":300.22,"record_id":31813,"result_status":1,"result_status_desc":"成功","trans_type":3002,"trans_type_desc":"信用卡"}],"rep_code":"0","result_status":-1,"trans_type":3002}
   */

  /**
   * {"error_info":"","operation_id":2005,"payment_status_desc":"您的信用卡已支付成功","payment_type_info_list":[{"amt":300.22,"record_id":31813,"result_status":1,"result_status_desc":"成功","trans_type":3002,"trans_type_desc":"信用卡"}],"rep_code":"0","result_status":1,"trans_type":3002}
   */
  //@Test
  public void test() {
    GetPayTypePojo pojo = new GetPayTypePojo();
    pojo.setProductLine("05");
    pojo.setToken("-100000000000042702");
    pojo.setGorderId("491555");
    PayTypeResponse response = PaymentService.getPayType(pojo);

    System.out.println(FastJsonUtil.toJson(response));
  }

 // @Test
  public void testGetPayType() {

    GetPayTypeRequest request = new GetPayTypeRequest();
    request.setOrderId("100010");
    request.setBusinessType(1011);
    request.setTradeNo("-100000000000009792");
    PayTypeResponse response = QueryService.getPayType(request);

    System.out.println(FastJsonUtil.toJson(response));
  }
}
