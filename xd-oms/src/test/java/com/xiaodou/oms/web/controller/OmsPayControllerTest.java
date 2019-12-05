package com.xiaodou.oms.web.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import junitx.framework.Assert;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.vo.input.pay.ContinuePayPojo;
import com.xiaodou.oms.vo.input.pay.GetTokenPojo;
import com.xiaodou.oms.vo.input.pay.QueryFirstPayInfoPojo;
import com.xiaodou.oms.vo.input.pay.QueryRecordPojo;
import com.xiaodou.oms.vo.result.pay.FirstPayInfoVO;

/**
 * Created by wwk on 2014/7/30.
 */

public class OmsPayControllerTest extends BaseSpringTest {

  @Resource
  OmsPayController omsPayController;

  //@Test
  public void getTokenTest() throws Exception {

    GetTokenPojo getTokenPojo = new GetTokenPojo();
    getTokenPojo.setProductLine("05");

    String res = omsPayController.getToken(getTokenPojo);
    FastJsonUtil.toJson(res);
    Map<String, String> map = FastJsonUtil.fromJson(res, HashMap.class);
    assertEquals("0", map.get("retcode"));

  }

//  @Test
  public void continuePayTest() {

    ContinuePayPojo continuePayPojo = new ContinuePayPojo();
    continuePayPojo.setProductLine("05");
    continuePayPojo.setGorderId("20001");

    String expected = "20302";
    try {
      String str = omsPayController.continuePay(continuePayPojo);
      //输出值是{"retcode":"20302","retdesc":"订单状态不是支付失败","serverIp":"192.168.31.142"}
      Map map = FastJsonUtil.fromJson(str, HashMap.class);
      assertEquals(expected, map.get("retcode"));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  //@Test
  public void queryPayRecordTest() {

    String expected = "0";
    QueryRecordPojo queryRecordPojo = new QueryRecordPojo();

    queryRecordPojo.setProductLine("05");
    queryRecordPojo.setGorderId("20001");

    try {
      String str = omsPayController.queryPayRecord(queryRecordPojo);
      //System.out.println("queryRecord output="+str);
      //t{"list":[{"amount":28.00,"buyAccountId":"190000002101055236","clientType":"1","createTime":1406798956000,"gorderId":"451511","id":568170,"operType":0,"outerOrigin":"ew","payNo":"-100000000000017999","payStatus":1,"payType":1,"productType":"0501","sentTime":1406798960000}],"page":{"pageNo":1,"pageSize":1,"totalCount":1,"totalPage":1},"retcode":"0","retdesc":"操作成功","serverIp":"192.168.31.142"}
      Map map = FastJsonUtil.fromJson(str, HashMap.class);
      assertEquals(expected, map.get("retcode"));
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

//  @Test
  public void queryFirstPayInfoTest() throws Exception {
    QueryFirstPayInfoPojo pojo = new QueryFirstPayInfoPojo();
    pojo.setGorderId("20006");
    pojo.setProductLine("05");
    String voJson = omsPayController.queryFirstPayInfo(pojo);
    FirstPayInfoVO vo = FastJsonUtil.fromJson(voJson,FirstPayInfoVO.class);
    Assert.assertEquals(vo.getNotifyUrl(), "http://192.168.14.99:8102/pay/first_pay_notify.do");
    junit.framework.Assert.assertEquals(vo.getPayAmount(), 100.00);
    Assert.assertEquals(vo.getTradeNo(), "-100000000000000006");
  }


}
