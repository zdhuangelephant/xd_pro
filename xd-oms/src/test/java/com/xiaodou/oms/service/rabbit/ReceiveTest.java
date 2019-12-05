//package com.xiaodou.oms.service.rabbit;
//
//import com.xiaodou.oms.util.FileUtils;
//import com.xiaodou.oms.vo.GorderVo;
//import com.xiaodou.oms.vo.result.ResultInfo;
//
///**
// * Date: 2014/11/19
// * Time: 16:56
// *
// * @author Tian.Dong
// */
//public class ReceiveTest {
//  //@Test
//  public void test(){
//    RabbitPool rabbitPool = RabbitPool.getInstance();
//    rabbitPool.setHostName("192.168.78.34");
//    rabbitPool.setHostPort(5672);
//    rabbitPool.setUsername("guest");
//    rabbitPool.setPassword("guest");
//
//    RabbitService rabbitService = new RabbitService();
//    rabbitService.setRabbitPool(rabbitPool);
//
//    GorderVo vo = new GorderVo();
//    vo.setGorderId("1111");
//
//    rabbitService.sendMessage(vo,"oms_queryPayment","oms_queryPayment");
//  }
//
//  public static void main(String[] args) throws Exception {
//    RabbitPool rabbitPool = RabbitPool.getInstance();
//    rabbitPool.setHostName("192.168.78.34");
//    rabbitPool.setHostPort(5672);
//    rabbitPool.setUsername("guest");
//    rabbitPool.setPassword("guest");
//
//    RabbitService rabbitService = new RabbitService();
//    rabbitService.setRabbitPool(rabbitPool);
//
//
//    rabbitService.readMessageAndDeal(new RabbitAction() {
//      
//      private String exchangeName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      private String endPointName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      @Override
//      public String getExchangeName() {
//        return exchangeName;
//      }
//      @Override
//      public String getEndPointName() {
//        return endPointName;
//      }
//      @Override
//      public ResultInfo execute(String message) {
//        System.out.println(message);
//        return null;
//      }
//    });
//    System.out.println();
//    rabbitService.readMessageAndDeal(new RabbitAction() {
//      private String exchangeName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      private String endPointName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      @Override
//      public String getExchangeName() {
//        return exchangeName;
//      }
//      @Override
//      public String getEndPointName() {
//        return endPointName;
//      }
//      @Override
//      public ResultInfo execute(String message) {
//        System.out.println(message);
//        return null;
//      }
//    });
//    System.out.println();
//    rabbitService.readMessageAndDeal(new RabbitAction() {
//      private String exchangeName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      private String endPointName=FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue");
//      @Override
//      public String getExchangeName() {
//        return exchangeName;
//      }
//      @Override
//      public String getEndPointName() {
//        return endPointName;
//      }
//      @Override
//      public ResultInfo execute(String message) {
//        System.out.println(message);
//        return null;
//      }
//    });
//    System.out.println();
//    rabbitService.stopTask();
//    System.out.println("1123");
//  }
//}
