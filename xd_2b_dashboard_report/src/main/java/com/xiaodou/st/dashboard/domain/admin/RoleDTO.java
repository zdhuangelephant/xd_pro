package com.xiaodou.st.dashboard.domain.admin;

import com.xiaodou.autobuild.annotations.Column;

import lombok.Data;

@Data
public class RoleDTO {
  /*角色名称*/
  private String roleName;
  /* 角色描述 */
  @Column(canUpdate = true)
  private String description;
  /*有效状态 on:有效 off:无效*/
  private String validStatus;
  
}
