package com.xiaodou.ms.model.admin;

/**
 * Created by zyp on 14-9-1.
 * 角色表
 */
public class Role {

  /**
   * 角色id
   */
  private Integer id;

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 角色描述
   */
  private String roleDescription;

  /**
   * 排序
   */
  private Integer sortOrder;

  /**
   * 是否生效
   */
  private Integer disabled;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleDescription() {
    return roleDescription;
  }

  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public Integer getDisabled() {
    return disabled;
  }

  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }
}
