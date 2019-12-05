package com.xiaodou.summer.tree;

/**
 * Created by zyp on 14-9-11.
 */
public class TreeNode {
  /**
   * id
   */
  private String id;

  /**
   * 父id
   */
  private String parentId;

  /**
   * 名称
   */
  private String name;

  /**
   * 数据
   */
  private Object data;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
