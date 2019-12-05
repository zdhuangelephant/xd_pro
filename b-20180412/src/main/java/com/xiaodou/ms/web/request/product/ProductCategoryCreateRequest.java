package com.xiaodou.ms.web.request.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryCreateRequest extends BaseRequest {

	// 课程分类父ID
	private Long parentId = 0L;

	/** pictureUrl 专业选择封面 */
	private String pictureUrl;

	/** showCover 列表展示封面 */
	private String showCover;

	// 课程分类详细介绍
	private String detail;

	// 模块
	private String module;

	/** classify 专业分类 */
	private Integer classify;

	// APP分类码
	private String typeCode;

	// 1一般专业 2新手专业
	private Integer courseCategoryType;

	private Integer showStatus;

	private String majorName;

	// zwj 新加字段
	private Integer isCooperation; // '是否为合作专业：1表示是，0表示否',
	private Integer isSync;// '是否为同步云测评：1表示是，0表示否',
	private Integer isBuy;// '是否为可以购买：1表示是，0表示否',

	private Integer courseCount;// '课程数量',

	private String chiefAcademy;// '主考院校',

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getCourseCategoryType() {
		return courseCategoryType;
	}

	public void setCourseCategoryType(Integer courseCategoryType) {
		this.courseCategoryType = courseCategoryType;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
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
