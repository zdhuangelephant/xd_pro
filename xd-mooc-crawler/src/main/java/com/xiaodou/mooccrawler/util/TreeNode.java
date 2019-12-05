package com.xiaodou.mooccrawler.util;

import java.util.List;

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

  /**
   * 是否为叶子结点
   */
  private Boolean isLeaf;

  /**
   * 层次
   */
  private Integer level;

  /**
   * 排序
   */
  private Integer listOrder;

  /**
   * 子节点
   */
  private List<TreeNode> childNodes;

  /**
   * 额外信息
   */
  private String extData;

  public String getExtData() {
    return extData;
  }

  public void setExtData(String extData) {
    this.extData = extData;
  }

  public Integer getListOrder() {
    return listOrder;
  }

  public void setListOrder(Integer listOrder) {
    this.listOrder = listOrder;
  }

  public List<TreeNode> getChildNodes() {
    return childNodes;
  }

  public void setChildNodes(List<TreeNode> childNodes) {
    this.childNodes = childNodes;
  }

  public Boolean getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

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

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }
}
