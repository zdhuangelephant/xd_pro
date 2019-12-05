package com.xiaodou.ms.model.admin;


import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zyp on 14-9-1.
 * <p/>
 * 管理员
 */
public class Admin {

  /**
   * 管理员id
   */
  private Integer id;
  /**
   * 名称
   */
  private String userName;

  /**
   * 密码
   */
  private String password;

  /**
   * 盐值
   */
  private String salt;

  /**
   * 最后登陆ip
   */
  private String lastLoginIp;

  /**
   * 最后登陆时间
   */
  private Timestamp lastLoginTime;

  /**
   * 邮件
   */
  private String email;

  /**
   * 手机号码
   */
  private String telphone;

  /**
   * 真实姓名
   */
  private String realName;

  /**
   * 角色列表
   */
  private List<Role> roleList;
  /**
   * 学习机构
   */
  private String merchant;

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelphone() {
    return telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }
}
