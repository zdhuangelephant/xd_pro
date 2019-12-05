package com.xiaodou.autotest.web.request;

import lombok.Data;

@Data
public class DataSourceRequest {
  private String id;
  private String alias;

  private String driverUrl;

  private String userName;

  private String password;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

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
