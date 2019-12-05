package com.xiaodou.st.dashboard.domain.staticinfo;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class StaticInfoDO {
  @Column(isMajor=true,sortBy = false)
  private Long id;
  @Column(canUpdate = true,sortBy = false)
  private String examDate;
  @Column(canUpdate = true,sortBy = false)
  private String beginApplyTime;
  @Column(canUpdate = true,sortBy = false)
  private String endApplyTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(StaticInfoDO.class,
        "xd_dashboard_static_info",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/staticinfo/")
        .buildXml();
  }
}
