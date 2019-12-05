//package com.xiaodou.oms.service.message;
//
//import com.xiaodou.oms.BaseSpringTest;
//import com.xiaodou.oms.service.message.MessageManager;
//import com.xiaodou.oms.service.message.MessageRecordService;
//import com.xiaodou.oms.statemachine.Context;
//import com.xiaodou.oms.statemachine.param.CoreParams;
//import com.xiaodou.oms.statemachine.param.PayParam;
//
//import org.junit.Assert;
//
//import javax.annotation.Resource;
//
//import java.util.UUID;
//
//import static org.mockito.Mockito.*; 
//
//public class MessageManagerTest extends BaseSpringTest {
//
//  @Resource
//  MessageManager messageManager;
//  
//  @Resource
//  MessageRecordService recordService;
//
////  @Test
//  public void testSend() {
//    // for (int i = 0; i < 101; i++) {
//    Context context = new Context();
//    CoreParams coreParams = new CoreParams();
//    coreParams.setEvent("PaySuccess");
//    coreParams.setGorderId("8734");
//    coreParams.setIp("192.168.59.188");
//    PayParam payParam = new PayParam();
//    payParam.setFailureReason("");
//    payParam.setPayNo("-100000000000005023");
//    coreParams.setProductLine("05");
//    coreParams.setPayParam(payParam);
//    context.setCoreParams(coreParams);
//    String uuid = UUID.randomUUID().toString();
//    MessageManager messageManager1 = mock(MessageManager.class);
//    when(messageManager1.send(context, uuid,
//        "train_fromInitToPaySuccessOnPaySuccess")).thenReturn(uuid);
//    Assert.assertEquals(uuid,messageManager1.send(context, uuid,
//            "train_fromInitToPaySuccessOnPaySuccess"));
//    verify(messageManager1, times(1)).send(context, uuid,
//        "train_fromInitToPaySuccessOnPaySuccess");
//  }
//
//  // }
//
//  // @Test
//  public void testSendSleep() throws InterruptedException {
//    int i = 0;
//    while (i++ < 20) {
//      testSend();
//      Thread.sleep(10 * 1000 * 60);
//    }
//  }
//  
////  @Test
//  public void testInsertMessageRecord(){
//      String uuid = UUID.randomUUID().toString();
//      System.out.println(uuid);
//      //Assert.assertTrue(recordService.insertNewMessage(uuid, "05"));
//  }
//  
////  @Test
//  public void testCheckMessage(){
//      recordService.checkMessageLostThenSend("05");
//  }
//  
//
//}
