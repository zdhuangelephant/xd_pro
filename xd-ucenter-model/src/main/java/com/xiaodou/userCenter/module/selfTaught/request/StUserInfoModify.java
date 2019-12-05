package com.xiaodou.userCenter.module.selfTaught.request;

import java.util.Map;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;
import com.xiaodou.userCenter.request.selftaught.ModifyStMyInfoRequest;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class StUserInfoModify extends ModifyStMyInfoRequest {

	public StUserInfoModify(UserBaseInfo info) {
		super(info);
	}

	public StUserInfoModify() {
		this(null);
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

	public String getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public String getPicList() {
		return picList;
	}

	public void setPicList(String picList) {
		this.picList = picList;
	}

	@Override
	public String getMoudelInfo() {
		if("2".equals(getType()))
			if(StringUtils.isBlank(getMajor()))
				return UcenterResType.UnCompleteInfo.getCode();
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

	@Override
	public String setMoudelInfo(Map<String, Object> moudelInfoMap) {
		if("2".equals(getType()))
			if(StringUtils.isBlank(getMajor())&&null == moudelInfoMap.get("major"))
				return UcenterResType.UnCompleteInfo.getCode();
		StUserInfo userInfo = new StUserInfo();
		if (null != moudelInfoMap.get("major"))
			userInfo.setMajor((String) moudelInfoMap.get("major"));
		if (null != moudelInfoMap.get("selectedCourses"))
			userInfo.setSelectedCourses((String) moudelInfoMap
					.get("selectedCourses"));
		if (null != moudelInfoMap.get("sign"))
			userInfo.setSign((String) moudelInfoMap.get("sign"));
		if (null != moudelInfoMap.get("coin"))
			userInfo.setCoin((String) moudelInfoMap.get("coin"));
		if (null != moudelInfoMap.get("credit"))
			userInfo.setCredit((String) moudelInfoMap.get("credit"));
		if (null != moudelInfoMap.get("title"))
			userInfo.setTitle((String) moudelInfoMap.get("title"));
		if (null != moudelInfoMap.get("picList"))
			userInfo.setPicList((String) moudelInfoMap.get("picList"));

		if (null != getMajor())
			userInfo.setMajor(getMajor());
		if (null != getSelectedCourses())
			userInfo.setSelectedCourses(getSelectedCourses());
		if (null != getSign())
			userInfo.setSign(getSign());
		if (null != getCoin())
			userInfo.setCoin(getCoin());
		if (null != getCredit())
			userInfo.setCredit(getCredit());
		if (null != getTitle())
			userInfo.setTitle(getTitle());
		if (null != getPicList())
			userInfo.setPicList(getPicList());

		return FastJsonUtil.toJson(userInfo);
	};

	@Override
	public ModifyMyInfoRequest getModifyAccountInfo() {
		// 通过构造方法设置基础属性
		StUserInfoModify info = new StUserInfoModify(this);
		// 模块独有属性需要单独设置
		info.setMajor(getMajor());
		info.setSelectedCourses(getSelectedCourses());
		info.setSign(getSign());
		info.setCoin(getCoin());
		info.setCredit(getCredit());
		info.setTitle(getTitle());
		info.setPicList(getPicList());
		return info;
	}

}
