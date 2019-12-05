package com.xiaodou.domain.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/6/26.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CourseProductItem extends BaseEntity {

  // 主键ID
  @Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private Long id;

  // 产品Id
  private Long productId;

  // 父ID
  private Long parentId;

  // 资源Id
  private String resourceId;

  // 资源类型
  private Long resourceType;

  // 节点等级
  private Short level;

  // item 名称
  private String name;

  // 是否展示
  private Integer showStatus;

  // 详情
  private String detail;

  // 杂项
  private String misc;

  // 所有父Id
  private String allParentId;

  // 子ID
  private String childId;

  // 所有子Id
  private String allChildId;

  // 创建时间
  private Timestamp createTime;

  // 更新时间
  private Timestamp updateTime;

  // 是否免费
  private Short isFree;

  // 是否子节点
  private Short isLeaf;

  // 子章节
  private List<CourseProductItem> childList = new ArrayList<>();

  // 章节号
  private String chapterId;

  // 重要程度
  private Double importanceLevel;

  // 排序
  private Long listOrder;

  private String points;

  private String feeType;

  private String topUserList;

  private Integer completeCount;
  /** quesNum 问题数量 */
  private Integer quesNum = 0;

  private Double weight = 0.00;

  /** examDetail 用户练习情况 */
  private ExamDetail examDetail = new ExamDetail();

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


public Long getResourceType() {
	return resourceType;
}


public void setResourceType(Long resourceType) {
	this.resourceType = resourceType;
}


public Short getLevel() {
	return level;
}


public void setLevel(Short level) {
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


public Short getIsFree() {
	return isFree;
}


public void setIsFree(Short isFree) {
	this.isFree = isFree;
}


public Short getIsLeaf() {
	return isLeaf;
}


public void setIsLeaf(Short isLeaf) {
	this.isLeaf = isLeaf;
}


public List<CourseProductItem> getChildList() {
	return childList;
}


public void setChildList(List<CourseProductItem> childList) {
	this.childList = childList;
}


public String getChapterId() {
	return chapterId;
}


public void setChapterId(String chapterId) {
	this.chapterId = chapterId;
}


public Double getImportanceLevel() {
	return importanceLevel;
}


public void setImportanceLevel(Double importanceLevel) {
	this.importanceLevel = importanceLevel;
}


public Long getListOrder() {
	return listOrder;
}


public void setListOrder(Long listOrder) {
	this.listOrder = listOrder;
}


public String getPoints() {
	return points;
}


public void setPoints(String points) {
	this.points = points;
}


public String getFeeType() {
	return feeType;
}


public void setFeeType(String feeType) {
	this.feeType = feeType;
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


public Integer getQuesNum() {
	return quesNum;
}


public void setQuesNum(Integer quesNum) {
	this.quesNum = quesNum;
}


public Double getWeight() {
	return weight;
}


public void setWeight(Double weight) {
	this.weight = weight;
}


public ExamDetail getExamDetail() {
	return examDetail;
}


public void setExamDetail(ExamDetail examDetail) {
	this.examDetail = examDetail;
}


public Integer getChapterType() {
    return parentId == 0l
        ? QuesBaseConstant.RESOURCE_TYPE_CHAPTER
        : QuesBaseConstant.RESOURCE_TYPE_ITEM;
  }
  

  /**
   * @name @see com.xiaodou.domain.ExamDetail.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月9日
   * @description 个人练习情况
   * @version 1.0
   */
  @Data
  public class ExamDetail {
    /** examQuesNum 练习题目数量 */
    private Integer examQuesNum = 0;
    /** rightQuesNum 掌握题目数量 */
    private Integer rightQuesNum = 0;
	public Integer getExamQuesNum() {
		return examQuesNum;
	}
	public void setExamQuesNum(Integer examQuesNum) {
		this.examQuesNum = examQuesNum;
	}
	public Integer getRightQuesNum() {
		return rightQuesNum;
	}
	public void setRightQuesNum(Integer rightQuesNum) {
		this.rightQuesNum = rightQuesNum;
	}
    
  }


  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(CourseProductItem.class, "xd_course_product_item",
            "F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
        .buildXml();
  }
}
