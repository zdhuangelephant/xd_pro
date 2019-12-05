package com.xiaodou.autotest.core.interfaces;

import com.xiaodou.autotest.core.vo.SandBoxContext;

/**
 * @name @see com.xiaodou.autotest.core._interface.SetVar.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月11日
 * @description 标准行为:设置沙盒变量
 * @version 1.0
 */
public interface SetVar {

  /**
   * 设置沙盒变量
   * 
   * @param sandBox 沙盒
   */
  public void setVar(SandBoxContext sandBox);

}
