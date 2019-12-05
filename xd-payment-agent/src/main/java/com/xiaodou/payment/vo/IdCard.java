package com.xiaodou.payment.vo;

/**
 * 证件信息
 *
 * @author Jiejun.Gao
 */
public enum IdCard {
  /**
   * 身份证
   */
  ID_CARD(8001, "身份证"),
  /**
   * 护照
   */
  PASSPORT(8002, "护照"),
  /**
   * 其他证件
   */
  OTHER(8003, "其他证件"),
  /**
   * 军官证
   */
  OFFICER_CARD(8004, "军官证"),
  /**
   * 警察证
   */
  POLICE_ID(8005, "警察证"),
  /**
   * 回乡证
   */
  REENTRY_PERMIT(8006, "回乡证");

  private int code;
  private String info;

  private IdCard(int code, String info) {
    this.code = code;
    this.info = info;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }


}
