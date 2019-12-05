package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class FeedBackCategoryDO {
  @Column(isMajor = true,canUpdate=false,sortBy=true)
  private Long id;
  @Column(canUpdate=false)
  private Long feedbackId;
  @Column(canUpdate=false)
  private String categoryDesc;
  @Column(canUpdate=false)
  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(FeedBackCategoryDO.class, "xd_user_feedback_category",
        "D:/work/workspace_xd/xd-ucenter-22b/src/main/resources/conf/mybatis/")
        .buildXml();
  }
}
