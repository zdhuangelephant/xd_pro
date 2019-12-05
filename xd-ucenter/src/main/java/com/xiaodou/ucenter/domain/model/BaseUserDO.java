package com.xiaodou.ucenter.domain.model;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.BaseField;

/**
 * 
 * @name UserModel CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年4月25日
 * @description 存储账号/密码信息
 * @version 1.0
 */
@Data
public class BaseUserDO {
  @BaseField
  @Column(isMajor = true)
  private Long id;
  /** module 地域码值 */
  @BaseField
  @Column(canUpdate = false)
  private String module;
  /**
   * 小逗自生产账号（唯一）
   */
  @BaseField
  @Column(canUpdate = false)
  private String xdUniqueId;

  /**
   * 掩码
   */
  @BaseField
  @Column(canUpdate = true)
  private String salt;

  /**
   * 用户密码
   */
  @BaseField
  @Column(canUpdate = true)
  private String password;

  /**
   * 手机号
   */
  @BaseField
  @Column(canUpdate = true)
  private String telephone;

  /**
   * qq账号
   */
  @BaseField
  @Column(canUpdate = true)
  private String qq;

  /**
   * 微信账号
   */
  @BaseField
  @Column(canUpdate = true)
  private String weixin;

  /**
   * 微博账号
   */
  @BaseField
  @Column(canUpdate = true)
  private String weibo;

  /**
   * 游客账号
   */
  @BaseField
  @Column(canUpdate = true)
  private String tourist;

  /**
   * 注册账号类型/主账号类型
   */
  @BaseField
  @Column(canUpdate = false)
  private String mainAccount;

  /**
   * 创建时间
   */
  @BaseField
  @Column(canUpdate = false)
  private Timestamp createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(BaseUserDO.class, "xd_base_user",
        "src/main/resources/conf/mybatis/").buildXml();
  }
}
