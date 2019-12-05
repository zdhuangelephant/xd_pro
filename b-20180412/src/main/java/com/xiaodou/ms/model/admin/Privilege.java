package com.xiaodou.ms.model.admin;

/**
 * Created by zyp on 14-9-1.
 * <p/>
 * 权限表
 */
public class Privilege {

  public final static Integer show = 1;

  public final static Integer hidden = 0;

  /**
   * 权限id
   */
  private Integer id;

  /**
   * 权限名称
   */
  private String name;

  /**
   * 父权限id
   */
  private Integer parentId;

  /**
   * url
   */
  private String url;

  /**
   * 排序
   */
  private Integer sortOrder;

  /**
   * 是否显示
   */
  private Integer display;

  /**
   * 参数
   */
  private String params;

  /**
   * 图标
   */
  private String icon;

  /**
   * 子Id
   */
  private String childId;

  /**
   * 所有父节点
   */
  private String allParentId;

  /**
   * 所有子节点
   */
  private String allChildId;

  /**
   * 等级
   */
  private Integer level;

  /**
   * 是否为叶节点
   */
  private Integer isLeaf;

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public String getAllParentId() {
    return allParentId;
  }

  public void setAllParentId(String allParentId) {
    this.allParentId = allParentId;
  }

  public String getAllChildId() {
    return allChildId;
  }

  public void setAllChildId(String allChildId) {
    this.allChildId = allChildId;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
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
}
