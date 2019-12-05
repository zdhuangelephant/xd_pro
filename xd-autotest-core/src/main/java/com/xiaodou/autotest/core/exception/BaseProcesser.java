package com.xiaodou.autotest.core.exception;

import com.xiaodou.autotest.core.annotions.ApiExProcesser;
import com.xiaodou.autotest.core.interfaces.ProcessApiException;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.core.exception.BaseProcesser.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 基本处理器
 * @version 1.0
 */
@ApiExProcesser
public class BaseProcesser implements ProcessApiException {

  /** mesTmp 消息模板 */
  private String mesTmp = "[Action:%s][Api:%s][Error:%s]";

  @Override
  public void process(Action action, Api api, Exception exception) {
    LoggerUtil.error(
        String.format(mesTmp, action.getName(), api.getName(), exception.getMessage()), exception);
  }

}
