package com.xiaodou.st.dashboard.domain.admin;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class RolePrivilegeDO {
  
  @Column(isMajor = true,listValue=true,autoIncrement=true,persistent=true)
  private Integer id;
  @Column(listValue=true)
  private Integer roleId;
  @Column(listValue=true)
  private Integer privilegeId;
  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RolePrivilegeDO.class, "xd_dashboard_role_privilege",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/admin/")
        .buildXml();
  }
}
