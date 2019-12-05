package com.xiaodou.oms.agent.common.enums;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;

/**
 * Created by zyp on 14-7-24.
 */

public enum AlarmEntityType {

  HTTP_SERVICE_UNVALID("[通信服务不可用]");

  private String serviceName = "OMSAGENT";
  private String moduleName;

  {
    if (StringUtils.isNotBlank(ConfigProp.getParams("alarm.omsagent.serviceName")))
      this.serviceName = ConfigProp.getParams("alarm.omsagent.serviceName");
  }

  AlarmEntityType(String moduleName) {
    this.moduleName = moduleName;
  }

}
