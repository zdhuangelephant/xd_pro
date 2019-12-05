package com.xiaodou.summer.sceduling.exception;

import java.lang.reflect.InvocationTargetException;

import com.xiaodou.common.util.log.LoggerUtil;


/**
 * Created by PengZhenjin on 2015/12/9.
 */
public class TaskErrorHandler extends TaskRejectedExecutionHandler implements ITaskErrorHandler {

  @Override
  public void handleError(Throwable exception) {
    LoggerUtil.error("handle Exception", exception);
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    LoggerUtil.error(String.format("UncaughtException throw by Thread %s", thread.getName()), ex);
  }

  /**
   * Rethrow the given {@link Throwable exception}, which is presumably the
   * <em>target exception</em> of an {@link InvocationTargetException}. Should only be called if no
   * checked exception is expected to be thrown by the target method.
   * <p>
   * Rethrows the underlying exception cast to an {@link RuntimeException} or {@link Error} if
   * appropriate; otherwise, throws an {@link IllegalStateException}.
   * 
   * @param ex the exception to rethrow
   * @throws RuntimeException the rethrown exception
   */
  public static void rethrowRuntimeException(Throwable ex) {
    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    if (ex instanceof Error) {
      throw (Error) ex;
    }
    handleUnexpectedException(ex);
  }


  /**
   * Throws an IllegalStateException with the given exception as root cause.
   * 
   * @param ex the unexpected exception
   */
  private static void handleUnexpectedException(Throwable ex) {
    throw new IllegalStateException("Unexpected exception thrown", ex);
  }
}
