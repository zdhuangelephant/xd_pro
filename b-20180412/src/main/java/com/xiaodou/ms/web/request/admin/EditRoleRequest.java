package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-18.
 */
public class EditRoleRequest extends BaseRequest {
  /**
   * 角色id
   */
  private Integer roleId;

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

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
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

  @Override
  public void validate(Object o, Errors errors) {
    super.validate(o, errors);
    if (StringUtils.isBlank(this.roleName)) {
      errors.reject("roleName", "角色名称不能为空");
    }
    if (this.roleId == null) {
      errors.reject("roleId", "角色id不能为空");
    }

  }

}
