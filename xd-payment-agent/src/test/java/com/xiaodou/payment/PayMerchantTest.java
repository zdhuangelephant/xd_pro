package com.xiaodou.payment;

import com.xiaodou.payment.vo.PayMerchant;

/**
 * Date: 2014/7/4
 * Time: 17:18
 *
 * @author Tian.Dong
 */
public class PayMerchantTest {

  //@Test
  public void test() {
    System.out.println(PayMerchant.getBusinessType("05"));
    System.out.println(PayMerchant.getMerchantId("05"));
    System.out.println(PayMerchant.getKey("05"));

    System.out.println(PayMerchant.getBusinessType("02"));
    System.out.println(PayMerchant.getMerchantId("02"));
    System.out.println(PayMerchant.getKey("02"));
  }
}
