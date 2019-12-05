package com.xiaodou.oms.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.junit.Test;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.vo.input.order.FirePojo;

public class OmsOrderControllerTest extends BaseSpringTest  {
  
  @Resource
  OmsOrderController omsOrderController;

  @Test
  public void testFire() {
    String jsonStr =
        "{\"coreParams\":{\"event\":\"CancelByUser\",\"ip\":\"0:0:0:0:0:0:0:1\",\"orderId\":\"20140729210569714\",\"productLine\":\"05\"}}";
    FirePojo pojo = FastJsonUtil.fromJson(jsonStr, FirePojo.class);
    try {
      String res = omsOrderController.fireEvent(pojo);
      System.out.println(res);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(URLDecoder.decode("火车票","utf8"));
  }
}
