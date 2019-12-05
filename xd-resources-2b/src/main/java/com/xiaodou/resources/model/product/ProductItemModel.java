package com.xiaodou.resources.model.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.annotation.BaseField;

/**
 * @name ProductItemModel CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月11日
 * @description 产品节点模型
 * @version 1.0
 */
public class ProductItemModel {
  // 主键ID
  @BaseField
  @Column(isMajor = true, canUpdate = false)
  private Long id;

  // 产品Id
  @BaseField
  @Column(canUpdate = false)
  private Long productId;

  // 父ID
  @BaseField
  @Column(canUpdate = false)
  private Long parentId;

  // 资源Id
  @BaseField
  @Column(canUpdate = false)
  private String resourceId;

  // 资源类型
  @BaseField
  @Column(canUpdate = false)
  private Integer resourceType;

  // 节点等级（所在层级(从0级开始，最好不要超过3级) level=0表示章， level=1表示节，level=2表示节下面的课件、视频。）
  @BaseField
  @Column(canUpdate = false)
  private Integer level;

  // item 名称
  @BaseField
  @Column(canUpdate = false)
  private String name;

  // 是否展示 (1：显示，0：不显示)
  @BaseField
  @Column(canUpdate = false)
  private Integer showStatus;

  // 详情
  @BaseField
  @Column(canUpdate = false)
  private String detail;

  // 杂项
  @BaseField
  @Column(canUpdate = false)
  private String misc;

  // 所有父Id
  @BaseField
  @Column(canUpdate = false)
  private String allParentId;

  // 子ID
  @BaseField
  @Column(canUpdate = false)
  private String childId;

  // 所有子Id
  @BaseField
  @Column(canUpdate = false)
  private String allChildId;

  // 创建时间
  @BaseField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;

  // 更新时间
  @BaseField
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp updateTime;

  // 是否免费
  @BaseField
  @Column(canUpdate = false)
  private Integer isFree;

  // 是否子节点
  @BaseField
  @Column(canUpdate = false)
  private Integer isLeaf;

  // 章节号
  @BaseField
  @Column(canUpdate = false)
  private String chapterId;

  // 排序
  @BaseField
  @Column(canUpdate = false)
  private Long listOrder;

  // 题目数
  @BaseField
  private Integer quesNum;

  // 资源
  @BaseField
  @Column(canUpdate = false)
  private String resource;

  // 任务比例
  @BaseField
  @Column(canUpdate = false)
  private Integer taskRatio;

  // 关联资源
  @BaseField
  @Column(canUpdate = false)
  private Long relationItem;

  // 关联资源名称
  @BaseField
  @Column(canUpdate = false)
  private String relationItemName;

  /** commentCount 回复数量 */
  @BaseField
  @Column(canUpdate = false)
  private Integer commentCount;

  /** topUserList 用户列表 */
  @BaseField
  @Column(canUpdate = false)
  private String topUserList;

  /** completeCount 完成者数量 */
  @BaseField
  @Column(canUpdate = false)
  private Integer completeCount;

  /** pictureUrl 封面 */
  @BaseField
  @Column(canUpdate = false)
  private String pictureUrl;

  /** deadline 截止时间 */
  @BaseField
  @Column(canUpdate = false)
  private Timestamp deadline;

  /** score 得分 */
  @BaseField
  private Double score = 0d;

  /** recordNum 我发表的回复记录数量 */
  @BaseField
  private Integer recordNum = 0;

  /** examTime 练习时长 */
  @BaseField
  @Column(canUpdate = false)
  private Integer examTime;

  /** frequency 练习次数 */
  @BaseField
  @Column(canUpdate = false)
  private Integer frequency;

  /** 赞数量 */
  @BaseField
  @Column(canUpdate = false)
  private Integer praiseCount;


  public Integer getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(Integer praiseCount) {
    this.praiseCount = praiseCount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
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

  public Integer getIsFree() {
    return isFree;
  }

  public void setIsFree(Integer isFree) {
    this.isFree = isFree;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public Long getListOrder() {
    return listOrder;
  }

  public void setListOrder(Long listOrder) {
    this.listOrder = listOrder;
  }

  public Integer getQuesNum() {
    return quesNum;
  }

  public void setQuesNum(Integer quesNum) {
    this.quesNum = quesNum;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public Integer getTaskRatio() {
    return taskRatio;
  }

  public void setTaskRatio(Integer taskRatio) {
    this.taskRatio = taskRatio;
  }

  public Long getRelationItem() {
    return relationItem;
  }

  public void setRelationItem(Long relationItem) {
    this.relationItem = relationItem;
  }

  public String getRelationItemName() {
    return relationItemName;
  }

  public void setRelationItemName(String relationItemName) {
    this.relationItemName = relationItemName;
  }

  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public String getTopUserList() {
    return topUserList;
  }

  public void setTopUserList(String topUserList) {
    this.topUserList = topUserList;
  }

  public Integer getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(Integer completeCount) {
    this.completeCount = completeCount;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public Timestamp getDeadline() {
    return deadline;
  }

  public void setDeadline(Timestamp deadline) {
    this.deadline = deadline;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Integer getRecordNum() {
    return recordNum;
  }

  public void setRecordNum(Integer recordNum) {
    this.recordNum = recordNum;
  }

  public Integer getExamTime() {
    return examTime;
  }

  public void setExamTime(Integer examTime) {
    this.examTime = examTime;
  }

  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }

  public static class Resourcer {
    private String fileUrl;
    private String cover;

    public String getFileUrl() {
      return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
      this.fileUrl = fileUrl;
    }

    public String getCover() {
      return cover;
    }

    public void setCover(String cover) {
      this.cover = cover;
    }
  }
}
