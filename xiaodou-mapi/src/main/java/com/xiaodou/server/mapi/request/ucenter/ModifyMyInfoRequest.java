package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.constant.UcenterConstant;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = { "phoneNum",
		"password", "confirmPassword", "checkCode", "publishId", "systemType" })
@AddValidField(annotiation = AnnotationType.NotEmpty, field = { "sessionToken" })
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyMyInfoRequest extends RegistAccountRequest {
	public ModifyMyInfoRequest() {
	}

	@NotEmpty
	@LegalValueList({ UcenterConstant.MODIFY_INFO, UcenterConstant.IMPROVE_INFO })
	private String type; // 操作类型

	private String sign;

	@NotEmpty(field = "type", value = UcenterConstant.IMPROVE_INFO)
	private String major;// 用户已选专业
	private String picList;// 图片
	private String medalId;// 勋章id
	private String medalName;// 勋章名称
	private String medalImg;// 勋章图片

	/** module 地区 */
	private String region;
	/** moduleName 地区名称 */
	private String regionName;
	/** majorId 专业id */
	private String majorId;
	/** majorName 专业名称 */
	private String majorName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPicList() {
		return picList;
	}

	public void setPicList(String picList) {
		this.picList = picList;
	}

	public String getMedalId() {
		return medalId;
	}

	public void setMedalId(String medalId) {
		this.medalId = medalId;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}

	public String getMedalImg() {
		return medalImg;
	}

	public void setMedalImg(String medalImg) {
		this.medalImg = medalImg;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

}
