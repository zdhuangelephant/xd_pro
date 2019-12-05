package com.xiaodou.userCenter.module.selfTaught.response;

import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * @description 小逗自考用户响应信息
 * @version 1.0
 * @waste
 */
public class StUserInfoResponse extends BaseUserModel {

	public StUserInfoResponse() {
	}

	/**
	 * 用户已选专业
	 */
	// @LegalValueList({ "1", "2" })
	private String major;

	/**
	 * 已选课程
	 */
	private String selectedCourses;

	private String sign; // 签名
	private String coin; // 金币
	private String credit; // 积分
	private String title; // 称号
	private String picList;// 图片

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicList() {
		return picList;
	}

	public void setPicList(String picList) {
		this.picList = picList;
	}

	@Override
	public void initModuleInfo(UserModelVo model) {/*
		if (null != model) {
			if (null != model.getGender())
				genderCode = model.getGender().toString();
			if (StringUtils.isJsonNotBlank(model.getModuleInfo())) {
				StUserInfo userInfo = FastJsonUtil.fromJson(
						model.getModuleInfo(), StUserInfo.class);
				if (null != userInfo) {
					if (null != userInfo.getCity()) {
						City cityInfo = CityUtil.getCityInfoByCode(userInfo
								.getCity());
						if (null != cityInfo) {
							city = cityInfo.getName();
							cityCode = cityInfo.getId().toString();
						} else {
							city = UcneterModelConstant.UNKNOWN_INFO;
							cityCode = StringUtils.EMPTY;
						}
					}
					degree = Degree.getByCode(userInfo.getDegree()).getName();
					if (null != userInfo.getDegree())
						degreeCode = userInfo.getDegree().toString();
					examDate = userInfo.getExamDate();
					type = ExamType.getByCode(userInfo.getType()).getName();
					if (null != userInfo.getType())
						typeCode = userInfo.getType().toString();
				}
			}
		}
	*/}

}
