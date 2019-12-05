package com.xiaodou.async.enums;

public class AsyncMessageEnums {

  public enum AsyncResInfo {

    AddFriend("30501", "%s请求加你为好友。", "请求加你为好友。"), AddFriendSuccessCallBack("30511", "%s通过了你的好友请求。",
        "通过了你的好友请求。"), AddFriendFailCallBack("30512", "%s拒绝了你的好友请求。", "拒绝了你的好友请求。"), PK("30502",
        "%s向你发起了挑战。", "向你发起了挑战。"), PKSuccessCallBack("30513", "%s接受了你的挑战。", "接受了你的挑战。"), PKFailCallBack(
        "30514", "%s拒绝了你的挑战。", "拒绝了你的挑战。"), SystemMessage("10003", "%s", null), SystemNotice(
        "10002", "%s", null);

    AsyncResInfo(String retCode, String retDesc, String mesTmp) {
      this.retCode = retCode;
      this.retDesc = retDesc;
      this.mesTmp = mesTmp;
    }

    private String retCode;
    private String retDesc;
    private String mesTmp;

    public String getRetCode() {
      return retCode;
    }

    public void setRetCode(String retCode) {
      this.retCode = retCode;
    }

    public String getRetDesc() {
      return retDesc;
    }

    public void setRetDesc(String retDesc) {
      this.retDesc = retDesc;
    }

    public String getMesTmp() {
      return mesTmp;
    }

    public void setMesTmp(String mesTmp) {
      this.mesTmp = mesTmp;
    }

  }

}
