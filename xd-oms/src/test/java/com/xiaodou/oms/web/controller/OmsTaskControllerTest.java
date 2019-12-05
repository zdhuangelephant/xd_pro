package com.xiaodou.oms.web.controller;
//package com.elong.oms.web.controller;
//
//import com.elong.common.util.warp.FastJsonUtil;
//import com.elong.oms.BaseSpringTest;
//import com.elong.oms.vo.input.task.BatchPojo;
//import org.junit.Test;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by wwk on 2014/7/31.
// */
//public class OmsTaskControllerTest extends BaseSpringTest {
//
//  @Resource
//  OmsTaskController omsTaskController;
//
//  @Test
//  public void sendPayRequestTest() {
//
//    BatchPojo batchPojo = new BatchPojo();
//    batchPojo.setTop(20);
//    batchPojo.setRemainder(2);
//    batchPojo.setMod(10);
//
//    try {
//      String str = omsTaskController.sendPayRequest(batchPojo);
//      FastJsonUtil.toJson(str);
//      Map<String, String> map = FastJsonUtil.fromJson(str, HashMap.class);
//      assertEquals("0", map.get("retcode"));
//    } catch (Exception e) {
//
//      e.printStackTrace();
//    }
//
//  }
//
//  @Test
//  public void getPayTypeTest() {
//
//    BatchPojo batchPojo = new BatchPojo();
//    batchPojo.setTop(20);
//    batchPojo.setRemainder(2);
//    batchPojo.setMod(10);
//
//    try {
//      String str = omsTaskController.getPayType(batchPojo);
//      FastJsonUtil.toJson(str);
//      Map<String, String> map = FastJsonUtil.fromJson(str, HashMap.class);
//      assertEquals("0", map.get("retcode"));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//  }
//
//  /**
//   * 测试清除过期消息 *
//   */
//  @Test
//  public void testCleanMessage() {
//    String resultinfo = omsTaskController.clean_message();
//    System.out.println(resultinfo);
//  }
//
//  @Test
//  public void queryPaymentTest() {
//    //omsPayController.queryPayment();
//  }
//
//}
