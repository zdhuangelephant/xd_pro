package com.xiaodou._2b_dashboard_report.thread;

public class MyThread2 implements Runnable{

  @Override
  public void run() {
    
  }
  
public static void main(String[] args) {
    MyThread2 thread2 = new MyThread2();
    Thread thread = new Thread(thread2);
    thread.start();
}

}
