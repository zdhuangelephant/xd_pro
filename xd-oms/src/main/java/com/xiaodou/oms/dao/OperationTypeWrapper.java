package com.xiaodou.oms.dao;


/**
 * <p>
 * Errors包装器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月30日
 */
public class OperationTypeWrapper {

  private OperationTypeWrapper() {}

  private static final ThreadLocal<OperationTypeWrapper> localContext = new ThreadLocal<OperationTypeWrapper>();

  static {
    initWrapper();
  }

  private OperationType value;


  public OperationType getValue() {
    return value;
  }

  public OperationType getAndRemove() {
    OperationType res = value;
    value = null;
    return res;
  }

  public void setValue(OperationType value) {
    this.value = value;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    OperationTypeWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static OperationTypeWrapper getWrapper() {
    OperationTypeWrapper ctx = (OperationTypeWrapper) localContext.get();
    if (ctx == null) {
      ctx = new OperationTypeWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(OperationTypeWrapper wrapper) {
    localContext.set(wrapper);
  }

}
