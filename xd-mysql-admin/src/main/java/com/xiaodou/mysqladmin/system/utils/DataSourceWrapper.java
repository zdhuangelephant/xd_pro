package com.xiaodou.mysqladmin.system.utils;


/**
 * @name @see com.xiaodou.mysqladmin.system.utils.DataSourceWrapper.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月20日
 * @description 数据源线程备份
 * @version 1.0
 */
public class DataSourceWrapper {

  private DataSourceWrapper() {}

  private static final ThreadLocal<DataSourceWrapper> localContext =
      new ThreadLocal<DataSourceWrapper>();

  static {
    initWrapper();
  }
  /** dataSourceId 数据源ID */
  private String dataSourceId;

  public String getDataSourceId() {
    return dataSourceId;
  }

  public void setDataSourceId(String dataSourceId) {
    this.dataSourceId = dataSourceId;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    DataSourceWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static DataSourceWrapper getWrapper() {
    DataSourceWrapper ctx = localContext.get();
    if (ctx == null) {
      ctx = new DataSourceWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(DataSourceWrapper wrapper) {
    localContext.set(wrapper);
  }

}
