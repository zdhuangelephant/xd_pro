package com.xiaodou.amqp.connectpool;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProxyTest {
  
  @Test
  public void testNothing(){
    String a = "a";
    assertEquals(a, "a");

//    System.out.println("调度线程-" + Thread.currentThread().getId());
//    Executor e = new ThreadPoolExecutor(4, 4,
//        0L, TimeUnit.MILLISECONDS,
//        new SynchronousQueue<Runnable>(true),
//        new RabbitThreadFactory("test"),
//        new RejectedExecutionHandler() {
//          @Override
//          public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//            try {
//              System.out.println("Reject线程-"  + Thread.currentThread().getId() + ":" + "+500ms");
//              Thread.sleep(500);
//              executor.execute(r);
//            } catch (InterruptedException e1) {
//              e1.printStackTrace();
//            }
//          }
//        });
//    Runnable r = new Runnable() {
//      @Override
//      public void run() {
//        System.out.println("任务线程-"  + Thread.currentThread().getId() + ":" + "sleep 5s");
//        try {
//          Thread.sleep(5000);
//        } catch (InterruptedException e1) {
//          e1.printStackTrace();
//        }
//        System.out.println("任务线程-"  + Thread.currentThread().getId() + ":" + "finished");
//      }
//    };
//    for (int i = 0; i < 20; i++) {
//      e.execute(r);
//      System.out.println("第" + (i+1) + "个线程执行");
//    }
//
//    try {
//      Thread.sleep(10000);
//    } catch (InterruptedException e1) {
//      e1.printStackTrace();
//    }
  }

}
