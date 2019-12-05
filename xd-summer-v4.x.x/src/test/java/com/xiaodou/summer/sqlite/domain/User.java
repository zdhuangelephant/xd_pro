package com.xiaodou.summer.sqlite.domain;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
@Xml(tableName = "xd_user", outputDir = "")
public class User {

  @Column(isMajor = true)
  private Integer id;
  private String name;
  private Integer age;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(User.class).buildXml();
  }

}
