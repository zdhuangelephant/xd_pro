package com.xiaodou.im.request;

/**
 * @name @see com.xiaodou.im.vo.RigistUserPojo.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月2日
 * @description 注册用户参数类
 * @version 1.0
 */
public class RegistUserPojo extends BasePojo {

  /** account 账号 */
  private String account;
  /** password 密码 */
  private String password;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
