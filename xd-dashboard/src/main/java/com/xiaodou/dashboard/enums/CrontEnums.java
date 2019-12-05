package com.xiaodou.dashboard.enums;

import com.xiaodou.dashboard.model.crontab.inner.CrontHttpConfig;
import com.xiaodou.dashboard.model.crontab.inner.CrontProtocolConfig;

public class CrontEnums {
  public enum Protocol {
    HTTP(CrontHttpConfig.class), HTTPS(CrontHttpConfig.class);
    Protocol(Class<? extends CrontProtocolConfig> config) {
      this.config = config;
    }

    private Class<? extends CrontProtocolConfig> config;

    public Class<? extends CrontProtocolConfig> getConfig() {
      return config;
    }

    public void setConfig(Class<? extends CrontProtocolConfig> config) {
      this.config = config;
    }

  }
}
