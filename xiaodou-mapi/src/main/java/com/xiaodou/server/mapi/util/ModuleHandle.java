package com.xiaodou.server.mapi.util;

/**
 * @name @see com.xiaodou.server.mapi.util.ModuleHandle.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月27日
 * @description 地域改造版本确认
 * @version 1.0
 * @deprecated
 */
public class ModuleHandle {

  private static final ThreadLocal<ModuleHandle> HANDLE = new ThreadLocal<ModuleHandle>();
  
  private Boolean isHasRegion = true;

  private ModuleHandle() {}
  
  public Boolean getIsHasRegion() {
    return isHasRegion;
  }

  public void setIsHasRegion(Boolean isHasRegion) {
    this.isHasRegion = isHasRegion;
  }
  
  public static ModuleHandle getModuleHandle() {
    ModuleHandle handle = HANDLE.get();
    if(null == handle) {
      handle = new ModuleHandle();
      HANDLE.set(handle);
    }
    return handle;
  }
  
  public static void clear() {
    HANDLE.remove();
  }
}
