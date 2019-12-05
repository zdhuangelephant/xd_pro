package com.xiaodou.ms.model.admin;

/**
 * Created by zyp on 15/8/26.
 */
public class AdminPrivilege {

  // 管理员Id
  private Integer adminId;

  // 权限Id
  private Integer privilegeId;

  public Integer getAdminId() {
    return adminId;
  }

  public void setAdminId(Integer adminId) {
    this.adminId = adminId;
  }

  public Integer getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(Integer privilegeId) {
    this.privilegeId = privilegeId;
  }
}
