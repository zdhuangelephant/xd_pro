package com.xiaodou.userCenter.model;

import lombok.Data;

@Data
public class BaseUserInfo {

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 性别
   */
  private Integer gender;

  /**
   * 头像URL地址
   */
  private String portrait;

  /**
   * 用户年龄
   */
  private Integer age;

  /**
   * 用户地址
   */
  private String address;
  private String credit; // 积分
}
