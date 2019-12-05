package com.xiaodou.webfetch.web.domain;

import java.sql.Timestamp;
import java.text.ParseException;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

@Data
public class ForumModel {
  /** id 主键 */
  @Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private String forumId;
  private Short status;
  @Column(persistent = false)
  private String statusDesc;
  @GeneralField
  @Column(canUpdate = true, sortBy = false, listValue = true)
  private String forumType;
  @Column(persistent = false)
  private String forumTypeDesc;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String forumClassify;
  @Column(persistent = false)
  private String forumClassifyDesc;
  @Column(canUpdate = true, sortBy = false)
  private String forumCover;
  @GeneralField
  @Column(canUpdate = true, sortBy = false, likeValue = true)
  private String forumTitle;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String forumContent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String forumMedia;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Integer forumPraiserNum;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Integer forumReaderNum;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String authorId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String forumTag;
  @Column(persistent = false)
  private String forumTagName;
  @GeneralField
  @Column(canUpdate = true, sortBy = true, betweenScope = true)
  private Timestamp createTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = true, betweenScope = true)
  private Timestamp updateTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String platform;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String selfId;
  @Column(persistent = false)
  private AuthorModel author;
  

  public static void main(String[] args) throws ParseException {
    MybatisXmlTool.getInstance(ForumModel.class, "xd_resource_list",
        "src/main/resources/conf/mybatis").buildXml();
  }

}