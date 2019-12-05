package com.xiaodou.oms.agent.service;

import com.xiaodou.oms.agent.vo.request.SendQQMsgRequest;
import com.xiaodou.oms.agent.vo.response.BaseResponse;

/**
 * Date: 2014/12/31 Time: 16:27
 * 
 * @author Tian.Dong
 */
public class OmsMessageServiceTest {
  // @Test
  public void testSend() {
    SendQQMsgRequest request = new SendQQMsgRequest();
    request.setContent("艺龙&新腾");
    request.setName("test群123");

    BaseResponse response = OmsMessageService.sendGroupMessage(request);
    System.out.println(response);
  }

  // @Test
  public void test() {

  }
}
