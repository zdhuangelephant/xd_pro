package com.xiaodou.server.pay;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.server.pay.service.PayService;


public class AliPayTest extends BaseUnitils {

  @SpringBean("payService")
  PayService payService;

  @Test
  public void queryEntity() {
      
  }


}
