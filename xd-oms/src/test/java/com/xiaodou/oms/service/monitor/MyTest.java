package com.xiaodou.oms.service.monitor;

/**
 * Date: 2014/11/17
 * Time: 17:58
 *
 * @author Tian.Dong
 */
public class MyTest {
  public static void main(String[] args) throws InterruptedException {
    MyThread myThread = new MyThread();
    myThread.start();
    Thread.sleep(1000);
    myThread.start();
  }
}
class MyThread extends Thread{
  public void run(){

  }
}
