package com.xiaodou.ms.service.admin;


import com.xiaodou.ms.util.tree.TreeNode;

/**
 * Created by zyp on 14-9-17.
 */
public class PrivilegeNode extends TreeNode {

  private Integer sortOrder;

  private String url;

  private Integer display;

  private String params;

  private String icon;

  private Integer level;

  private String parentNode;

  public String getParentNode() {
    return parentNode;
  }

  public void setParentNode(String parentNode) {
    this.parentNode = parentNode;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getDisplay() {
    return display;
  }

  public void setDisplay(Integer display) {
    this.display = display;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
