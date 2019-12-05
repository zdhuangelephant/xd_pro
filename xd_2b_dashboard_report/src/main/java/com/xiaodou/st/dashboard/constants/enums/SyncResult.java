package com.xiaodou.st.dashboard.constants.enums;

public enum SyncResult {

  SyncWait("0", "同步中"), SyncFinish("1", "同步完成");
  SyncResult(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private String code;
  private String desc;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
