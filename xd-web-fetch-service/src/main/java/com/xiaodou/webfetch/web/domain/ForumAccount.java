package com.xiaodou.webfetch.web.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
@Xml(tableName = "xd_resource_fetch_account", outputDir = "F:\\xdworks\\xd-web-fetch-service\\src\\main\\resources\\conf\\mybatis")
public class ForumAccount {
  @Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private Integer id;
  private Short platform;
  private String signature;
  private Short state;
  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ForumAccount.class).buildXml();
  }
}
