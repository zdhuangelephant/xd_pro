package com.xiaodou.ucerCenter.agent.util;

import com.xiaodou.ucerCenter.agent.module.mapping.ModuleMapping;

/**
 * 存储请求模块信息
 * 
 * @author bing.cheng
 * 
 */
public class ModuleMappingWrapper {
  private ModuleMappingWrapper() {}

  private static final ThreadLocal<ModuleMappingWrapper> localContext =
      new ThreadLocal<ModuleMappingWrapper>();

  static {
    initWrapper();
  }

  private ModuleMapping module;

  public ModuleMapping getModule() {
    return module;
  }

  public void setModule(ModuleMapping module) {
    this.module = module;
  }

  /**
   * 构造方法
   * 
   */
  public static void initWrapper() {
    ModuleMappingWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   * 
   * @return ErrorsWrapper
   */
  public static ModuleMappingWrapper getWrapper() {
    ModuleMappingWrapper ctx = (ModuleMappingWrapper) localContext.get();
    if (ctx == null) {
      ctx = new ModuleMappingWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   * 
   * @param wrapper
   */
  public static void setWrapper(ModuleMappingWrapper wrapper) {
    localContext.set(wrapper);
  }
}
