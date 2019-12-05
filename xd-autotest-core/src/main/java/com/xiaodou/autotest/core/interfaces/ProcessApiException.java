package com.xiaodou.autotest.core.interfaces;

import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.Api;

/**
 * @name @see com.xiaodou.autotest.core._interface.ProcessApiException.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 标准行为:Api异常处理
 * @version 1.0
 */
public interface ProcessApiException {

  /**
   * 异常处理方法
   * 
   * @param action 用例
   * @param api 接口
   * @param exception 异常
   */
  public void process(Action action, Api api, Exception exception);

}
