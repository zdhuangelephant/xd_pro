package com.xiaodou.summer.sceduling.exception;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.xiaodou.common.util.log.LoggerUtil;

public class TaskRejectedExecutionHandler implements RejectedExecutionHandler {

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    LoggerUtil.error("Task " + r.toString() + " rejected from " + executor.toString(),
        new RejectedExecutionException());
  }

}
