package com.xiaodou.oms.util;


/**
 * <p>
 * Errors包装器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月30日
 */
public class ErrorsWrapper {

  private ErrorsWrapper() {}

  private static final ThreadLocal<ErrorsWrapper> localContext = new ThreadLocal<ErrorsWrapper>();

  static {
    initWrapper();
  }

  private Throwable value;

  public Throwable getValue() {
    return value;
  }

  public Throwable getAndRemove() {
    Throwable res = value;
    value = null;
    return res;
  }

  public void setValue(Throwable value) {
    this.value = value;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    ErrorsWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static ErrorsWrapper getWrapper() {
    ErrorsWrapper ctx = (ErrorsWrapper) localContext.get();
    if (ctx == null) {
      ctx = new ErrorsWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(ErrorsWrapper wrapper) {
    localContext.set(wrapper);
  }

}
