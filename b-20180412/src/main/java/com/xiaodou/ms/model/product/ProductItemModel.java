package com.xiaodou.ms.model.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class ProductItemModel {

	// 主键ID
    @GeneralField
	private Long id;

	// 产品Id
    @GeneralField
	private Long productId;

	// 父ID
    @GeneralField
	private Long parentId;

	// 资源Id
    @GeneralField
	private String resourceId;

	// 资源类型
    @GeneralField
	private Integer resourceType;

	// 节点等级
    @GeneralField
	private Integer level;

	// item 名称
    @GeneralField
	private String name;

	/** pictureUrl 章节封面 */
    @GeneralField
	private String pictureUrl;

	// 是否展示
    @GeneralField
	private Integer showStatus;

	// 详情
    @GeneralField
	private String detail;

	// 杂项
    @GeneralField
	private String misc;

	// 所有父Id
    @GeneralField
	private String allParentId;

	// 子ID
    @GeneralField
	private String childId;

	// 所有子Id
    @GeneralField
	private String allChildId;

	// 创建时间
    @GeneralField
	private Timestamp createTime;

	// 更新时间
	private Timestamp updateTime;

	// 是否免费
	@GeneralField
	private Integer isFree;

	// 是否子节点
	@GeneralField
	private Integer isLeaf;

	// 子章节
	private List<ProductItemModel> childList = new ArrayList<>();

	// 章节号
	private String chapterId;

	// 重要程度
	private Integer importanceLevel;

	// 排序
	private Integer listOrder;

	// 资源描述
	private String resource;

	// 任务比例
	private Integer taskRatio;

	// 关联Item
	private Long relationItem;

	// 关联ItemType
	private String relationItemName;

	// 章节题目数
	private Integer quesNum;

	// 闯关任务ID
	private String missionId;

	/** missionOrder 任务排序字段 */
	private Integer missionOrder;

	// 闯关任务状态
	private String missionState;

	// 章节序号
	private String chapterIdDesc;
	// 章节名称
	private String chapterName;

	// 权重
	private Double weight;
	
	
	// 地域
	private String module;
	private String courseItem;

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


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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


	public List<ProductItemModel> getChildList() {
		return childList;
	}


	public void setChildList(List<ProductItemModel> childList) {
		this.childList = childList;
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


	public Integer getListOrder() {
		return listOrder;
	}


	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
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


	public Integer getQuesNum() {
		return quesNum;
	}


	public void setQuesNum(Integer quesNum) {
		this.quesNum = quesNum;
	}


	public String getMissionId() {
		return missionId;
	}


	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}


	public Integer getMissionOrder() {
		return missionOrder;
	}


	public void setMissionOrder(Integer missionOrder) {
		this.missionOrder = missionOrder;
	}


	public String getMissionState() {
		return missionState;
	}


	public void setMissionState(String missionState) {
		this.missionState = missionState;
	}


	public String getChapterIdDesc() {
		return chapterIdDesc;
	}


	public void setChapterIdDesc(String chapterIdDesc) {
		this.chapterIdDesc = chapterIdDesc;
	}


	public String getChapterName() {
		return chapterName;
	}


	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}


	public Double getWeight() {
		return weight;
	}


	public void setWeight(Double weight) {
		this.weight = weight;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public String getCourseItem() {
		return courseItem;
	}


	public void setCourseItem(String courseItem) {
		this.courseItem = courseItem;
	}
	
}
