package com.xiaodou.autotest.core.interfaces;

import com.xiaodou.autotest.core.vo.SandBoxContext;

/**
 * @name @see com.xiaodou.autotest.core._interface.Condition.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 标准行为:校验沙盒变量
 * @version 1.0
 */
public interface Condition {

  /**
   * 校验沙盒变量方法
   * 
   * @param sandBox 沙盒
   * @return 校验结果
   */
  public Boolean validate(SandBoxContext sandBox);

}
