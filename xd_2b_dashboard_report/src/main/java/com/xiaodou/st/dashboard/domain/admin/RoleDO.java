package com.xiaodou.st.dashboard.domain.admin;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dashboard.constants.Constants;
@Data
public class RoleDO {
  /* id 主键id */
  @Column(isMajor = true)
  private Integer id;
  /*角色名称*/
  private String roleName;
  /* 角色描述 */
  @Column(canUpdate = true)
  private String description;
  /*有效状态 0:有效 1:无效*/
  private Short validStatus;
  
  private Timestamp createTime;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RoleDO.class, "xd_dashboard_role",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/admin/")
        .buildXml();
  }

  public RoleDO instance(RoleDTO roleDTO) {
    this.roleName = roleDTO.getRoleName();
    this.description = roleDTO.getDescription();
    if(StringUtils.isNotBlank(roleDTO.getValidStatus()))
      this.validStatus = Constants.ROLE_ON;
    else
      this.validStatus = Constants.ROLE_OFF;
    return this;
  }
}
