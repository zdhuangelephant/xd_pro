package com.xiaodou.course.enums;


/**
 * Created by zyp on 14-6-23.
 */
public enum BuyProductStatus {

  INIT(0, "未添加"), SUCCESS(1, "添加成功");


  /**
   * 
   * @Title: getDescByStatusId
   * @param statusId
   * @return String 返回类型
   */
  public static String getDescByStatusId(Integer statusId) {
    if (statusId == null) {
      return null;
    }
    // for-each
    for (BuyProductStatus ticketStatus : BuyProductStatus.values()) {
      if (ticketStatus.getStatusId().equals(statusId)) {
        return ticketStatus.getStatusDesc();
      }
    }
    return null;
  }



  // 火车票状态id
  private Integer statusId;
  // 火车票状态描述
  private String statusDesc;

  private BuyProductStatus(Integer statusId, String statusDesc) {
    this.statusId = statusId;
    this.statusDesc = statusDesc;
  }

  public Integer getStatusId() {
    return statusId;
  }

  public String getStatusDesc() {
    return statusDesc;
  }
}
