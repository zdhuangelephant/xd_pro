package com.xiaodou.payment;

import com.xiaodou.payment.constant.PaymentStatus;

import org.junit.Test;

/**
 * Date: 2014/11/18
 * Time: 13:53
 *
 * @author Tian.Dong
 */
public class PaymentStatusTest {
  @Test
  public void test(){
    System.out.println(PaymentStatus.NOTIFY_DEALING);
    System.out.println(PaymentStatus.NOTIFY_FAILCODE);
    System.out.println(PaymentStatus.NOTIFY_OKCODE);
  }
}
