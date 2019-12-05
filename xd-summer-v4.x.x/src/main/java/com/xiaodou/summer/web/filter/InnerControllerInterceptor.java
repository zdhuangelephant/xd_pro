package com.xiaodou.summer.web.filter;


/**
 * @name ControllerInterceptor CopyRright (c) 2015 by <a
 *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月3日
 * @description 记录访问请求日志
 * @version 1.0
 */
public class InnerControllerInterceptor extends ControllerInterceptor {

  @Override
  protected boolean needBuildParam() {
    return false;
  }

}
