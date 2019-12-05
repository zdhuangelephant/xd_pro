package com.xiaodou.queue.test;

import com.xiaodou.queue.manager.DefaultProcesserObserver;
/**
 * 拉取消息
 */
public class ProcessMain {
  public static void main(String[] args){
    DefaultProcesserObserver.startBatch();
  }
}
