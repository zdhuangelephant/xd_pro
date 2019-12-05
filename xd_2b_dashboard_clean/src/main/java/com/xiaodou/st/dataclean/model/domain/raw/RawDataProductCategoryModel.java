package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataProductCategoryModel
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 产品专业模型
 * @version 1.0
 */
@Data
public class RawDataProductCategoryModel {

	public RawDataProductCategoryModel() {

	}

	// 主键
	@Column(isMajor = true, canUpdate = false)
	private Integer id;

	// 父
	private Integer parentId;

	// 子
	private String childId;

	// 展示状态
	private Integer showStatus;

	// 所有父id
	private String allParentId;

	// 所有子id
	private String allChildId;

	// 所在层级(从1级开始，最好不要超过3级)
	private Integer level;

	// 名称
	private String name;

	// 分类类型
	private Integer courseCategoryType;

	// 描述
	private String detail;

	private String misc;

	// 创建时间
	@Column(betweenScope = true)
	private Timestamp createTime;

	/** classify 专业分类 */
	private Integer classify;

	// 更新时间
	@Column(betweenScope = true)
	private Timestamp updateTime;

	// 是否为叶节点
	private Integer isLeaf;

	// app模块
	private String module;

	// 分类（专业代码）
	private String typeCode;

	/* 专业层次(eg:专科) */
	private String majorLevel;
	/* 主考院校 */
	private String chiefAcademy;

	/** isCooperation 是否为合作专业 */
	private Short isCooperation;
	/** isSync 是否同步到云测评 */
	private Short isSync;
	/** isBuy 是否可以购买 */
	private Short isBuy;
	/** courseCount 课程数 */
	private Integer courseCount;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataProductCategoryModel.class,
				"xd_raw_data_product_category",
				"src/main/resources/conf/mybatis/raw/").buildXml();
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

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
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

	public String getMajorLevel() {
		return majorLevel;
	}

	public void setMajorLevel(String majorLevel) {
		this.majorLevel = majorLevel;
	}

	public String getChiefAcademy() {
		return chiefAcademy;
	}

	public void setChiefAcademy(String chiefAcademy) {
		this.chiefAcademy = chiefAcademy;
	}

	public Short getIsCooperation() {
		return isCooperation;
	}

	public void setIsCooperation(Short isCooperation) {
		this.isCooperation = isCooperation;
	}

	public Short getIsSync() {
		return isSync;
	}

	public void setIsSync(Short isSync) {
		this.isSync = isSync;
	}

	public Short getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Short isBuy) {
		this.isBuy = isBuy;
	}

	public Integer getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(Integer courseCount) {
		this.courseCount = courseCount;
	}

}
