package com.xiaodou.ucenter.module;


/**
 * 存储请求模块号
 * 
 * @author 李德洪
 * @date 2016年9月23日
 * 
 */
public class ModuleWrapper {
  private ModuleWrapper() {}

  private static final ThreadLocal<ModuleWrapper> localContext = new ThreadLocal<ModuleWrapper>();

  static {
    initWrapper();
  }

  private String module;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    ModuleWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static ModuleWrapper getWrapper() {
    ModuleWrapper ctx = (ModuleWrapper) localContext.get();
    if (ctx == null) {
      ctx = new ModuleWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(ModuleWrapper wrapper) {
    localContext.set(wrapper);
  }
}
