package com.xiaodou.dashboard.vo.crontmonitor.response;

import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;

/**
 * @name @see com.xiaodou.dashboard.vo.crontmonitor.response.MonitorApiLogResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月21日
 * @description 主动监控响应类
 * @version 1.0
 */
public class MonitorApiLogResponse extends MonitorApiLog {

  public MonitorApiLogResponse(MonitorApiLog log) {
    if (null == log) {
      return;
    }
    setId(log.getId());
    setApiId(log.getApiId());
    setResult(log.getResult());
    setMessage(log.getMessage());
    setCreateTime(log.getCreateTime());
  }

  /** apiName 监控点名称 */
  private String apiName;

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

}
