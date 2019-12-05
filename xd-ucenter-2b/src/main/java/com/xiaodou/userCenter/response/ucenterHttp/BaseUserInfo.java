package com.xiaodou.userCenter.response.ucenterHttp;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BaseUserInfo {

  /**
   * 模块号
   */
  private String module;

  /**
   * 小逗自生产账号（唯一）
   */
  private String xdUniqueId;

  /**
   * 手机号
   */
  private String telephone;

  /**
   * qq账号
   */
  private String qq;

  /**
   * 微信账号
   */
  private String weixin;

  /**
   * 微博账号
   */
  private String weibo;

  /**
   * 游客账号
   */
  private String tourist;

  /**
   * 注册账号类型/主账号类型
   */
  private String mainAccount;
  /**
   * 创建时间
   */
  private Timestamp createTime;


}
