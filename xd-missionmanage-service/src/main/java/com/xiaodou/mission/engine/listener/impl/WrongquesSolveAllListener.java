package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.WrongquesSolveAllListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 解决课程全部错题监听器
 * @version 1.0
 */
public class WrongquesSolveAllListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (context != null && context.getCoreParam() != null
        && context.getCoreParam().getTollgateInfo() != null
        && context.getCoreParam().getTollgateInfo().getWrongQuesNum() == 0) {
      return context.getEvent().getCourseId();
    }
    return StringUtils.EMPTY;
  }
}
