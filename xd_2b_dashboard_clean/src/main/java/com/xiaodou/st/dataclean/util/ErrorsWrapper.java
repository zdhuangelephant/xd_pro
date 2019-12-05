package com.xiaodou.st.dataclean.util;


/**
 * @name @see com.xiaodou.st.dataclean.util.ErrorsWrapper.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年3月19日
 * @description Error包装器, 线程内使用的异常捕获/包装/获取包装器, 不需要传参即可使用
 * @version 1.0
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
