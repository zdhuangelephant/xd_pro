package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class FeedBackModel {
  @Column(isMajor = true, sortBy = true, persistent = true, autoIncrement = true)
  private Long id;
  @Column(canUpdate = false, sortBy = false)
  private String content;
  @Column(canUpdate = false, sortBy = true)
  private Timestamp createTime;

  /* 类型描述列表 */
  @Column(canUpdate = false, sortBy = false)
  private String categoryDescs;

  /* 邮箱或者手机号 */
  @Column(canUpdate = false)
  private String number;

  /* 图片列表 */
  @Column(canUpdate = false, sortBy = false)
  private String imageUrls;

  /* 手机设备类型 */
  @Column(canUpdate = false)
  private String deviceType;

  /* os版本 */
  @Column(canUpdate = false)
  private String osVersion;

  /* 用户id */
  @Column(canUpdate = false)
  private Long userId;
  /* 当前app版本号 */
  @Column(canUpdate = false)
  private String appVersion;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(FeedBackModel.class, "xd_user_feedback",
        "D:/work/workspace_xd/xd-ucenter-22b/src/main/resources/conf/mybatis/").buildXml();
  }
}
