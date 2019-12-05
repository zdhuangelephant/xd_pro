package com.xiaodou.ms.model.admin;

import java.util.List;

/**
 * Created by zyp on 14-9-1.
 * 角色权限
 */
public class RolePrivilege {

  /**
   * 角色Id
   */
  private Integer roleId;

  /**
   * 权限Id
   */
  private Integer privilegeId;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(Integer privilegeId) {
    this.privilegeId = privilegeId;
  }
}
