package com.xiaodou.ms.model.product;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.ms.model.major.MajorInfo;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class ProductCategoryModel {

	private Long id;

	private Long parentId;

	private String childId;

	private Integer showStatus;

	private String allParentId;

	private String allChildId;

	private Integer level;

	private String name;

	private String pictureUrl;

	private String showCover;

	private Integer classify;

	private Integer courseCategoryType;

	private String detail;

	private String misc;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Integer isLeaf;

	private String module;

	private String typeCode;

	private String moduleName;

	private String majorName;

	private String majorInfo;

	private MajorInfo majorInfoModel;

	// zwj 新加字段
	private Integer isCooperation; // '是否为合作专业：1表示是，0表示否',
	private Integer isSync;// '是否为同步云测评：1表示是，0表示否',
	private Integer isBuy;// '是否为可以购买：1表示是，0表示否',
	private Integer courseCount;// '课程数量',

	private String chiefAcademy;// '主考院校',

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getShowCover() {
		return showCover;
	}

	public void setShowCover(String showCover) {
		this.showCover = showCover;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public Integer getCourseCategoryType() {
		return courseCategoryType;
	}

	public void setCourseCategoryType(Integer courseCategoryType) {
		this.courseCategoryType = courseCategoryType;
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

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorInfo() {
		return majorInfo;
	}

	public void setMajorInfo(String majorInfo) {
		this.majorInfo = majorInfo;
	}

	public MajorInfo getMajorInfoModel() {
		return majorInfoModel;
	}

	public void setMajorInfoModel(MajorInfo majorInfoModel) {
		this.majorInfoModel = majorInfoModel;
	}

	public Integer getIsCooperation() {
		return isCooperation;
	}

	public void setIsCooperation(Integer isCooperation) {
		this.isCooperation = isCooperation;
	}

	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public Integer getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Integer isBuy) {
		this.isBuy = isBuy;
	}

	public Integer getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(Integer courseCount) {
		this.courseCount = courseCount;
	}

	public String getChiefAcademy() {
		return chiefAcademy;
	}

	public void setChiefAcademy(String chiefAcademy) {
		this.chiefAcademy = chiefAcademy;
	}

}
