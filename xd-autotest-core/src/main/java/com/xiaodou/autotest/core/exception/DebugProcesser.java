package com.xiaodou.autotest.core.exception;

import com.xiaodou.autotest.core.annotions.ApiExProcesser;
import com.xiaodou.autotest.core.interfaces.ProcessApiException;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.core.exception.DebugProcesser.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 调试处理器
 * @version 1.0
 */
@ApiExProcesser(isDebug = true)
public class DebugProcesser implements ProcessApiException {

  @Override
  public void process(Action action, Api api, Exception exception) {
    LoggerUtil.debug(StringUtils.toString(exception));
  }

}
