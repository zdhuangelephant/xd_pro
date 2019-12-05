package com.xiaodou.ms.model.user;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 公共信息项
 * 
 */
public class CommonInfoModel {

	private Integer id;

	private String infoType;

	private String infoCode;

	private String infoVersion;
	
	private String module;

	private String appId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getInfoCode() {
		return infoCode;
	}

	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}

	public String getInfoVersion() {
		return infoVersion;
	}

	public void setInfoVersion(String infoVersion) {
		this.infoVersion = infoVersion;
	}

	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

public String getAppId() {
	return appId;
}

public void setAppId(String appId) {
	this.appId = appId;
}
	
	

}
