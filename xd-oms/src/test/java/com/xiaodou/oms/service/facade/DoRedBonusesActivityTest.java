package com.xiaodou.oms.service.facade;

import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.service.facade.OrderServiceFacade;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * Date: 2014/9/12
 * Time: 18:31
 *
 * @author Tian.Dong
 */
public class DoRedBonusesActivityTest extends BaseSpringTest {

  @Resource
  OrderServiceFacade orderServiceFacade;

/*//  @Test
  public void test(){
    PayRecord payRecord = new PayRecord();
    payRecord.setProductType("0501");
    payRecord.setOuterOrigin("app");
    payRecord.setBuyAccountId("testtttt");
    payRecord.setGorderId("20006");
    orderServiceFacade.doRedBonusesActivity(payRecord);
  }*/
}
