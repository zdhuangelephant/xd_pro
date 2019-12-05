package com.xiaodou.userCenter.enums;

public enum Especially {
  BEIJING("131");
  
  private String cityCode;

  private Especially(String cityCode){
    this.cityCode = cityCode;
  }
  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }
  
}
