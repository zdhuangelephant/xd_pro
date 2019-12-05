package com.xiaodou.oms.constant.order;

/**
 * CA(3001, "CA"),
 * CC(3002, "CC"),
 * DC(3003, "DC"),
 * FAST(3004, "FAST");
 * <p/>
 * Date: 2014/7/28
 * Time: 13:42
 *
 * @author Tian.Dong
 */
public class PayType {
  public static final int CA = 3001;
  public static final int CC = 3002;
  public static final int DC = 3003;
  public static final int FAST = 3004;

  public PayType(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public PayType(int code) {
    if (code == CA) {
      this.code = CA;
      this.desc = "CA";
    } else if (code == CC) {
      this.code = CC;
      this.desc = "CC";
    } else if (code == DC) {
      this.code = DC;
      this.desc = "DC";
    } else if (code == FAST) {
      this.code = FAST;
      this.desc = "FAST";
    } else {
      this.code = code;
      this.desc = code + "";
    }
  }

  private int code;
  private String desc;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
