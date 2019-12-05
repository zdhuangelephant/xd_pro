package com.xiaodou.userCenter.request;

/**
 * @name @see AddCreditRequest CopyRright (c) 2016 by <a
 *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月7日
 * @description 增加积分请求
 * @version 1.0
 */
public class AddCreditRequest {

  /** module 所属模块 */
  private String module;
  /** uid 用户ID */
  private String uid;
  /** type 积分变更类型描述 */
  private String type;
  /** creditUpper 积分增幅 */
  private Integer creditUpper;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getCreditUpper() {
    return creditUpper;
  }

  public void setCreditUpper(Integer creditUpper) {
    this.creditUpper = creditUpper;
  }


}
