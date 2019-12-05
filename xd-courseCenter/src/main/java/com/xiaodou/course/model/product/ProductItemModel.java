package com.xiaodou.course.model.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.common.annotation.BaseField;

/**
 * Created by zyp on 15/6/26.
 */
public class ProductItemModel {

  // 主键ID
  @BaseField
  private Integer id;

  // 产品Id
  @BaseField
  private Integer productId;

  // 父ID
  @BaseField
  private Integer parentId;

  // 资源Id
  @BaseField
  private String resourceId;

  // 资源类型
  @BaseField
  private Integer resourceType;

  // 节点等级
  @BaseField
  private Integer level;

  // item 名称
  @BaseField
  private String name;

  // 是否展示
  @BaseField
  private Integer showStatus;

  // 详情
  @BaseField
  private String detail;

  // 杂项
  @BaseField
  private String misc;

  // 所有父Id
  @BaseField
  private String allParentId;

  // 子ID
  @BaseField
  private String childId;

  // 所有子Id
  @BaseField
  private String allChildId;

  // 创建时间
  @BaseField
  private Timestamp createTime;

  // 更新时间
  @BaseField
  private Timestamp updateTime;

  // 是否免费
  @BaseField
  private Integer isFree;

  // 是否子节点
  @BaseField
  private Integer isLeaf;

  // 子章节
  @BaseField
  private List<ProductItemModel> childList = new ArrayList<>();

  // 章节号
  @BaseField
  private String chapterId;

  // 重要程度
  @BaseField
  private Integer importanceLevel;

  // 排序
  @BaseField
  private Integer listOrder;

  // 题目数
  @BaseField
  private Integer quesNum;

  // 资源
  @BaseField
  private String resource;

  // 任务比例
  @BaseField
  private Integer taskRatio;

  // 关联资源
  @BaseField
  private Integer relationItem;

  // 关联资源名称
  @BaseField
  private String relationItemName;

  public Integer getTaskRatio() {
    return taskRatio;
  }

  public void setTaskRatio(Integer taskRatio) {
    this.taskRatio = taskRatio;
  }

  public String getRelationItemName() {
    return relationItemName;
  }

  public void setRelationItemName(String relationItemName) {
    this.relationItemName = relationItemName;
  }

  public Integer getRelationItem() {
    return relationItem;
  }

  public void setRelationItem(Integer relationItem) {
    this.relationItem = relationItem;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public Integer getQuesNum() {
    return quesNum;
  }

  public void setQuesNum(Integer quesNum) {
    this.quesNum = quesNum;
  }

  public Integer getListOrder() {
    return listOrder;
  }

  public void setListOrder(Integer listOrder) {
    this.listOrder = listOrder;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public Integer getImportanceLevel() {
    return importanceLevel;
  }

  public void setImportanceLevel(Integer importanceLevel) {
    this.importanceLevel = importanceLevel;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public List<ProductItemModel> getChildList() {
    return childList;
  }

  public void setChildList(List<ProductItemModel> childList) {
    this.childList = childList;
  }

  public Integer getIsFree() {
    return isFree;
  }

  public void setIsFree(Integer isFree) {
    this.isFree = isFree;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public Integer getResourceType() {
    return resourceType;
  }

  public void setResourceType(Integer resourceType) {
    this.resourceType = resourceType;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(Integer showStatus) {
    this.showStatus = showStatus;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }

  public String getAllParentId() {
    return allParentId;
  }

  public void setAllParentId(String allParentId) {
    this.allParentId = allParentId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public String getAllChildId() {
    return allChildId;
  }

  public void setAllChildId(String allChildId) {
    this.allChildId = allChildId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }
}
