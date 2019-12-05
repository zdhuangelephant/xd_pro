package com.xiaodou.st.dashboard.util;


/**
 * <p>
 * Exception包装器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月30日
 */
public class ExceptionWrapper {

  private ExceptionWrapper() {}

  private static final ThreadLocal<ExceptionWrapper> localContext =
      new ThreadLocal<ExceptionWrapper>();

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
    ExceptionWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static ExceptionWrapper getWrapper() {
    ExceptionWrapper ctx = (ExceptionWrapper) localContext.get();
    if (ctx == null) {
      ctx = new ExceptionWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(ExceptionWrapper wrapper) {
    localContext.set(wrapper);
  }

}
