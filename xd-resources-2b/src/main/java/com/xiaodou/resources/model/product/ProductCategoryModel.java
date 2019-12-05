package com.xiaodou.resources.model.product;

import java.sql.Timestamp;
import com.xiaodou.autobuild.annotations.Column;

/**
 * Created by zyp on 15/6/26.
 */
public class ProductCategoryModel {

  // 主键
  @Column(isMajor = true, canUpdate = false)
  private Integer id;

  // 父
  @Column(canUpdate = false)
  private Integer parentId;

  // 子
  @Column(canUpdate = false)
  private String childId;

  // 展示状态
  @Column(canUpdate = false)
  private Integer showStatus;

  // 所有父id
  @Column(canUpdate = false)
  private String allParentId;

  // 所有子id
  @Column(canUpdate = false)
  private String allChildId;

  // 所在层级(从1级开始，最好不要超过3级)
  @Column(canUpdate = false)
  private Integer level;

  // 名称
  @Column(canUpdate = false)
  private String name;

  // 描述
  @Column(canUpdate = false)
  private String detail;

  /** misc  */
  @Column(canUpdate = false)
  private String misc;

  // 创建时间
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;

  // 更新时间
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp updateTime;

  // 是否为叶节点
  @Column(canUpdate = false)
  private Integer isLeaf;

  // 类别
  @Column(canUpdate = false)
  private String typeCode;

  /** pictureUrl 封面图片 */
  @Column(canUpdate = false)
  private String pictureUrl;

  /** userId 管理员ID */
  @Column(canUpdate = false)
  private String userId;

  /** listOrder 排序字段 */
  @Column(canUpdate = false)
  private String listOrder;

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getListOrder() {
    return listOrder;
  }

  public void setListOrder(String listOrder) {
    this.listOrder = listOrder;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public Integer getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(Integer showStatus) {
    this.showStatus = showStatus;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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