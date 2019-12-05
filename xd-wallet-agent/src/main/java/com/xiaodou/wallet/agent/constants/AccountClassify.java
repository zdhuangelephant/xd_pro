package com.xiaodou.wallet.agent.constants;

/**
 * @name @see com.xiaodou.wallet.agent.constants.AccountClassify.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月4日
 * @description 账户类型分类
 * @version 1.0
 */
public enum AccountClassify {
  /** ACCOUNT_CLASSIFY_LOCAL 账户分类:实体货币账户 */
  ACCOUNT_CLASSIFY_LOCAL(4601, "local"),
  /** ACCOUNT_CLASSIFY_ANDROID 账户分类:虚拟货币安卓账户 */
  ACCOUNT_CLASSIFY_ANDROID(4702, "android"),
  /** ACCOUNT_CLASSIFY_IOS 账户分类:虚拟货币苹果账户 */
  ACCOUNT_CLASSIFY_IOS(4701, "ios");
  AccountClassify(Integer code, String desc) {
    this.code = code.shortValue();
    this.desc = desc;
  }

  /** code 码值 */
  private Short code;
  /** desc 描述 */
  private String desc;

  public Short getCode() {
    return code;
  }

  public void setCode(Short code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
