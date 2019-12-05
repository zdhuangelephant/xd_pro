package com.xiaodou.autotest.core.datasource.mysql;

/**
 * @name @see com.xiaodou.autotest.core.datasource.mysql.DataSourceConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 数据源参数项
 * @version 1.0
 */
public class DataSourceConfig {

  /** alias 别名 */
  private String alias;
  /** driverUrl 地址 */
  private String driverUrl;
  /** userName 用户名 */
  private String userName;
  /** password 密码 */
  private String password;

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getDriverUrl() {
    return driverUrl;
  }

  public void setDriverUrl(String driverUrl) {
    this.driverUrl = driverUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
