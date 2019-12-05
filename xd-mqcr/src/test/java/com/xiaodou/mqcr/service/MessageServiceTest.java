//package com.xiaodou.mqcr.service;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//
//import com.xiaodou.mqcr.BaseSpringTest;
//
//
//public class MessageServiceTest extends BaseSpringTest {
//
//  @Resource
//  MessageEntityService messageEntityService;
//
///*  @Test
//  public void testInsert() {
//    assert messageEntityService.insertNewMessage(UUID.randomUUID().toString());
//    assert messageEntityService.insertSuccessMessage(UUID.randomUUID().toString());
//    assert messageEntityService.insertFailMessage(UUID.randomUUID().toString());
//  }*/
//
//
///*  @Test
//  public void testCanConsume() {
//    String tagNew = UUID.randomUUID().toString();
//    assert messageEntityService.insertNewMessage(tagNew);
//    assert !messageEntityService.canConsumMessage(tagNew);
//    assert messageEntityService.updateMessage2Succ(tagNew);
//    assert !messageEntityService.canConsumMessage(tagNew);
//    String tagSucc = UUID.randomUUID().toString();
//    assert messageEntityService.insertSuccessMessage(tagSucc);
//    assert !messageEntityService.canConsumMessage(tagSucc);
//    String tagFail = UUID.randomUUID().toString();
//    assert messageEntityService.insertFailMessage(tagFail);
//    assert messageEntityService.canConsumMessage(tagFail);
//    assert messageEntityService.updateFailMessage2Succ(tagFail);
//    assert !messageEntityService.canConsumMessage(tagFail);
//  }*/
//
///*  @Test
//  public void testTruncate() {
//    String tagSucc = UUID.randomUUID().toString();
//    assert messageEntityService.insertSuccessMessage(tagSucc);
//    assert !messageEntityService.canConsumMessage(tagSucc);
//    messageEntityService.truncateMessage();
//    assert messageEntityService.canConsumMessage(tagSucc);
//  }*/
//
////  @Test
//  public void testDelete() {
////    String tagSucc = UUID.randomUUID().toString();
////    assert messageEntityService.insertSuccessMessage(tagSucc);
////    assert !messageEntityService.canConsumMessage(tagSucc);
////    assert 0 == messageEntityService.deleteMessageAfter(new Timestamp(
////        System.currentTimeMillis() + 10000));
////    assert messageEntityService.deleteMessageBefore(new Timestamp(System.currentTimeMillis())) > 0;
////    assert messageEntityService.canConsumMessage(tagSucc);
////    assert messageEntityService.insertSuccessMessage(tagSucc);
////    assert 1 == messageEntityService.deleteMessageBetween(new Timestamp(
////        System.currentTimeMillis() - 10000), new Timestamp(System.currentTimeMillis()));
//  }
//  
///*  @Test
//  public void testLimitDelete() {
//    Map<String, Object> cond = Maps.newHashMap();
//    Map<String, Object> input = Maps.newHashMap();
//    input.put("status", MessageStatus.SUCCESS);
//    input.put("insertTimeUpper", new Timestamp(System.currentTimeMillis() - 24000*3600));
//    input.put("limit", 300);
//    cond.put("input", input);
//    messageEntityService.deleteMessage(cond);
//  }*/
//}
