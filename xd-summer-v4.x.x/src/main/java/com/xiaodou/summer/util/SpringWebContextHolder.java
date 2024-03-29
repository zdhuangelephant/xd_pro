package com.xiaodou.summer.util;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 */
public class SpringWebContextHolder implements ApplicationContextAware, DisposableBean {

  private static ApplicationContext applicationContext = null;

  /**
   * 取得存储在静态变量中的ApplicationContext.
   */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    Object bean = applicationContext.getBean(name);
    if (null == bean) return null;
    return (T) bean;
  }

  /**
   * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  public static <T> T getBean(Class<T> requiredType) {
    return applicationContext.getBean(requiredType);
  }

  /**
   * 清除SpringWebContextHolder中的ApplicationContext为Null.
   */
  public static void clearHolder() {
    applicationContext = null;
  }

  /**
   * 实现ApplicationContextAware接口, 注入Context到静态变量中.
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringWebContextHolder.applicationContext = applicationContext; // NOSONAR
  }

  /**
   * 实现DisposableBean接口, 在Context关闭时清理静态变量.
   */
  @Override
  public void destroy() throws Exception {
    SpringWebContextHolder.clearHolder();
  }


}
