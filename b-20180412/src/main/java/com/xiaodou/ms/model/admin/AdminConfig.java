package com.xiaodou.ms.model.admin;

/**
 * Created by zyp on 14-9-1.
 * <p/>
 * 配置属性
 */
public class AdminConfig {

  /**
   * 属性键值
   */
  private String configKey;

  /**
   * 属性值
   */
  private String configValue;

  /**
   * 属性模块
   */
  private String systemModule;

  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  public String getSystemModule() {
    return systemModule;
  }

  public void setSystemModule(String systemModule) {
    this.systemModule = systemModule;
  }
}
