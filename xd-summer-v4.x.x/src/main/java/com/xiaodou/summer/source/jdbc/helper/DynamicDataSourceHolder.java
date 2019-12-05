package com.xiaodou.summer.source.jdbc.helper;

import com.xiaodou.summer.enums.DynamicDataSourceGlobal;

/**
 * @name DynamicDataSourceHolder CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年1月25日
 * @description 本地线程
 * @version 1.0
 */
public final class DynamicDataSourceHolder {

  private static final ThreadLocal<DynamicDataSourceHolder> wrapper =
      new ThreadLocal<DynamicDataSourceHolder>();

  private DynamicDataSourceHolder() {}

  private DynamicDataSourceGlobal dataSourceType;

  public DynamicDataSourceGlobal getDataSourceType() {
    return dataSourceType;
  }

  public void setDataSourceType(DynamicDataSourceGlobal dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  static {
    initHolder();
  }

  /**
   * 构造方法
   * 
   */
  public static void initHolder() {
    DynamicDataSourceHolder ctx = getHolder();
    setHolder(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static DynamicDataSourceHolder getHolder() {
    DynamicDataSourceHolder ctx = wrapper.get();
    if (ctx == null) {
      ctx = new DynamicDataSourceHolder();
      wrapper.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setHolder(DynamicDataSourceHolder holder) {
    wrapper.set(holder);
  }

  /**
   * 清理包装器
   */
  public void clear() {
    wrapper.remove();
  }

}
