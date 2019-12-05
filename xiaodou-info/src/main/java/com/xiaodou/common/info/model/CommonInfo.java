package com.xiaodou.common.info.model;

import com.xiaodou.common.util.warp.FastJsonUtil;


/**
 * 公共信息项
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-13
 */
public class CommonInfo {

  private Integer id;

  private String infoType;

  private String infoCode;

  private String infoVersion;
  
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

}
