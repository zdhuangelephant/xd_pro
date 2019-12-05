package com.xiaodou.oms.util;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.xiaodou.common.util.log.model.InOutEntity;


/**
 * <p>InOut日志包装器</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月4日
 */
public class InOutLogWrapper {
  private InOutLogWrapper(){
  }
  

  private static final ThreadLocal<InOutLogWrapper> localContext = new ThreadLocal<InOutLogWrapper>();
  
  static {
    initWrapper();
  }
  
  /**
   * 获取一个Errors对象的线程副本
   * @return Errors对象
   */
  public static Errors getErrors(Object target){
    return new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
  }
  

  private InOutEntity value;


  public InOutEntity getValue() {
    return value;
  }

  public void setValue(InOutEntity value) {
    this.value = value;
  }

  /**
   * 构造方法
   *
   */
  public static void initWrapper() {
    InOutLogWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   *
   * @return InOutLogWrapper
   */
  public static InOutLogWrapper getWrapper() {
    InOutLogWrapper ctx = (InOutLogWrapper) localContext.get();
    if (ctx == null) {
      ctx = new InOutLogWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   *
   * @param wrapper
   */
  public static void setWrapper(InOutLogWrapper wrapper) {
    localContext.set(wrapper);
  }
}
