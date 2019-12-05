package com.xiaodou.ms.web.request.ruide;

import com.xiaodou.ms.model.ruide.MajorCategoryModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class MajorCategoryAddRequest extends BaseRequest {
	private long id;
	/* 专业类别 */
	private String majorCategory;
	/* 配图 */
	private String image;
	private String remark;
	public MajorCategoryModel initModel() {
		MajorCategoryModel model = new MajorCategoryModel();
		model.setMajorCategory(majorCategory);
		model.setImage(image);
		model.setId(id);
		model.setRemark(remark);
		return model;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMajorCategory() {
		return majorCategory;
	}
	public void setMajorCategory(String majorCategory) {
		this.majorCategory = majorCategory;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
