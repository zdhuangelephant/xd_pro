package com.xiaodou.summer.sceduling.exception;

import java.util.concurrent.RejectedExecutionHandler;

import com.xiaodou.common.util.exception.ErrorHandler;

/**
 * Created by PengZhenjin on 2015/12/9.
 */
public interface ITaskErrorHandler extends RejectedExecutionHandler, ErrorHandler, Thread.UncaughtExceptionHandler {
}
