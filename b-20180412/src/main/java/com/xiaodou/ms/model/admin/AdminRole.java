package com.xiaodou.ms.model.admin;

/**
 * Created by zyp on 14-9-1.
 * <p/>
 * 管理员角色
 */
public class AdminRole {

  /**
   * 管理员id
   */
  private Integer adminId;

  /**
   * 角色id
   */
  private Integer roleId;

  /**
   * 是否默认角色
   */
  private Integer isDefault;

  public Integer getAdminId() {
    return adminId;
  }

  public void setAdminId(Integer adminId) {
    this.adminId = adminId;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Integer isDefault) {
    this.isDefault = isDefault;
  }
}
