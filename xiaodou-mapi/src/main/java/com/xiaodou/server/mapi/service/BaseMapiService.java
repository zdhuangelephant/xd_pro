package com.xiaodou.server.mapi.service;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.engine.ActionHolder;
import com.xiaodou.server.mapi.engine.model.Action;
import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.server.mapi.vo.alarm.RemoteInvokeExceptionAlarm;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.service.BaseMapiService.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月28日
 * @description Mapi基础Service
 * @version 1.0
 */
public class BaseMapiService {


  public HttpResult router(String mpackage, String name, Object... paramPojo) {
    return router(mpackage, name, UserTokenWrapper.getWrapper().getHeadParam().getVersion(),
        paramPojo);
  }

  public HttpResult router(String mpackage, String name, String version, Object... paramPojo) {
    try {
      Action action = ActionHolder.getInstance().getAction(mpackage, name, version);
      if (null == action) {
        return null;
      }
      return action.excute(paramPojo);
    } catch (Exception e) {

      LoggerUtil.alarmInfo(new RemoteInvokeExceptionAlarm(String.format(
          "[GET API ERROR][mpackage:%s, name:%s, version:%s, paramPojo:%s]", mpackage, name,
          version, FastJsonUtil.toJson(paramPojo))));
      LoggerUtil.error(String.format(
          "[GET API ERROR][mpackage:%s, name:%s, version:%s, paramPojo:%s]", mpackage, name,
          version, FastJsonUtil.toJson(paramPojo)), e);
      throw e;
    }
  }

  public String getRouter(String mpackage, String name, String version, Object... pojo) {
    HttpResult httpResult = router(mpackage, name, version, pojo);
    if (null != httpResult && httpResult.getStatusCode() == 200) {
        return httpResult.getContent();
    }
    return FastJsonUtil.toJson(new ResultInfo(ResultType.SYSFAIL));
  }

  /**
   * 根据module格式化productLine
   * 
   * @param module
   * @return
   */
  public String formatProductLine(String module) {
    return module.length() > 2 ? module.substring(module.length() - 2) : module.length() == 1
        ? String.format("0%s", module)
        : module;
  }

  public String getRouter(String mpackage, String name, Object... pojo) {
    return getRouter(mpackage, name, UserTokenWrapper.getWrapper().getHeadParam().getVersion(),
        pojo);
  }

  public String getClientType() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientType();
  }

}
