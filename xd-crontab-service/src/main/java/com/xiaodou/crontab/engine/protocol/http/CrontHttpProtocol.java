package com.xiaodou.crontab.engine.protocol.http;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.alarm.CrontabAlarm;
import com.xiaodou.crontab.engine.enums.Protocol;
import com.xiaodou.crontab.engine.protocol.AbstractCrontProtocol;
import com.xiaodou.crontab.engine.protocol.CrontResult;
import com.xiaodou.crontab.engine.protocol.http.proxy.HttpProxyFactory;
import com.xiaodou.crontab.engine.protocol.http.proxy.IHttpProxy;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class CrontHttpProtocol extends AbstractCrontProtocol<CrontHttpProtocolConfig> {

  private String protocol;
  private CrontHttpProtocolConfig config;

  public String getProtocol() {
    return protocol;
  }

  public void setConfig(CrontHttpProtocolConfig config) {
    this.config = config;
  }

  public CrontHttpProtocolConfig getConfig() {
    return config;
  }

  @Override
  public CrontResult crontabJob() {
    IHttpProxy proxy = HttpProxyFactory.buildProxy(this);
    if (null == proxy) return new CrontResult(ResultType.SYSFAIL);
    try {
      HttpResult result = proxy.getHttpResult();
      CrontResult crontRes = null;
      if (result.isResultOk()) {
        crontRes = new CrontResult(ResultType.SUCCESS);
        if (null != getStructCheck() && getStructCheck()) {
          ResultInfo info = FastJsonUtil.fromJson(result.getContent(), ResultInfo.class);
          if (!ResultType.SUCCESS.getCode().equals(info.getRetcode())) {
            LoggerUtil.alarmInfo(new CrontabAlarm(this.getTargetAddr(), result.getContent()));
          }
        }
      } else {
        crontRes = new CrontResult(ResultType.SYSFAIL);
      }
      return crontRes;
    } catch (Exception e) {
      LoggerUtil.error("发送http请求失败", e);
      LoggerUtil.alarmInfo(new CrontabAlarm(this.getTargetAddr(), e.getMessage()));
      return new CrontResult(ResultType.SYSFAIL);
    }
  }

  @Override
  public void setProtocol(Protocol protocol) {
    this.protocol = protocol.name().toLowerCase();
  }

}
