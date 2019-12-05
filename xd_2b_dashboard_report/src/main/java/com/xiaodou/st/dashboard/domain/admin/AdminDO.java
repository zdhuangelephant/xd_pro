package com.xiaodou.st.dashboard.domain.admin;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name AdminDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月29日
 * @description TODO
 * @version 1.0
 */
@Data
public class AdminDO {

  /**
   * 管理员id
   */
  @Column(isMajor = true)
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
  private Long telephone;

  /**
   * 真实姓名
   */
  private String realName;

  /**
   * 头像
   */
  private String portrait;

  /**
   * 单位id
   */
  private Long unitId;

  /**
   * 单位名称
   */
  private String unitName;

  /**
   * 所属机构角色
   */
  private Short role;

  /**
   * 单位头像
   */
  @Column(persistent = false)
  private String unitPortrait;

  /**
   * 折扣率
   */
  @Column(persistent = false)
  private Double priceRate;
  /*
   * 创建时间
   */
  private Timestamp createTime;

  private String type;

  /*
   * 子权限（现只有第三集单位用到）0、默认权限1、子权限
   */
  private Short childRole;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AdminDO.class, "xd_dashboard_admin",
        "src/main/resources/conf/mybatis/admin/").buildXml();
  }
}
