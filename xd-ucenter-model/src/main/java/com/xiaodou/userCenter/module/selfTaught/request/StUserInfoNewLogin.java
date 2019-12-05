package com.xiaodou.userCenter.module.selfTaught.request;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

public class StUserInfoNewLogin extends NewLoginRequest {

	public StUserInfoNewLogin() {
		this(null);
	}

	public StUserInfoNewLogin(UserBaseInfo info) {
		super(info);
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

	public String getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
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
	public String getMoudelInfo() {
		StUserInfo userInfo = new StUserInfo();
		userInfo.setMajor(getMajor());
		userInfo.setSelectedCourses(getSelectedCourses());
		userInfo.setSign(getSign());
		userInfo.setCoin(getCoin());
		userInfo.setCredit(getCredit());
		userInfo.setTitle(getTitle());
		userInfo.setPicList(getPicList());
		return FastJsonUtil.toJson(userInfo);
	}
}
