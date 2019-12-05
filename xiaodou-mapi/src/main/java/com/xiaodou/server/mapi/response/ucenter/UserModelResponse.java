package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.server.mapi.response.ucenter.UserModelResponse.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月12日
 * @description 用户模型（整洁代码）
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserModelResponse extends BaseResponse {
    
	public UserModelResponse() {}

	public UserModelResponse(ResultType type) {
		super(type);
	}
	
	/** id 主键id */
	private String id;
	/** userId 用戶ID */
	private String userId;
	/** salt 盐值 */
	private String salt;
	/** tokenTime token初始时间 */
	private String tokenTime;
	/** token token值 */
	private String token;
	/** createTime 创建时间 */
	private String createTime;
	/** usedDeviceId 用过的设备号列表,设备号之间以逗号分隔 */
	private String usedDeviceId;
	/** age 用户年龄 */
	private String age;
	/** address 用户地址 */
	private String address;
	/** latestDeviceIp 最新device ip */
	private String latestDeviceIp;
	/** userName 用户名(手机号) */
	private String userName;
	/** nickName 昵称 */
	private String nickName;
	/** gender 性别 */
	private String gender;
	/** portrait 头像URL地址 */
	private String portrait;
	// local/qq/weibo/weixin;所属平台
	private String platform;
	// 第三方唯一标识
	private String uniqueId;
	private String credit;; // 积分
	private String bePraiseNum;;// 被赞数
	/** majorName 专业名称 */
	private String majorName;
	private String userType;// 用户类型 1 普通用户 2 导入用户
	private Long learnDays = 0l;// 学习天数
	private Long loginDays = 1l;// 登录天数
	/** major 用户已选专业专业(typeCode) */
	private String major;
	private String sign; // 签名
	private List<String> picList = Lists.newArrayList();// 图片
	private String medalId;// 勋章id
	private String medalName;// 勋章名称
	private String medalImg;// 勋章图片
	/** realName 导入用户数据 start */
	private String realName;// 真实姓名
	private String officialGender;// 官方导入性别
	private String identificationCardCode;// 身份证号
	private String admissionCardCode; // 准考证号
	private String degreeLevel; // 学习层次
	private String merchant; // 学习机构
	private String benchmarkFace; // 基准面容
	private String officialStatus;// 0：初次导入用户，1：完成密码修改，2：完成上传基准面容
	private String xdUniqueId;
	/** mainAccount base_user 主账号类型 */
	private String mainAccount;
	/*
	 * 1.5.0 引入-start
	 */
	/** majorId 专业id */
	private String majorId;
	/** region 地域 */
	private String region;
	/** regionName 地域名称 */
	private String regionName;
	/**
	 * 1.5.0 引入-end
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(String tokenTime) {
		this.tokenTime = tokenTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUsedDeviceId() {
		return usedDeviceId;
	}

	public void setUsedDeviceId(String usedDeviceId) {
		this.usedDeviceId = usedDeviceId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatestDeviceIp() {
		return latestDeviceIp;
	}

	public void setLatestDeviceIp(String latestDeviceIp) {
		this.latestDeviceIp = latestDeviceIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getBePraiseNum() {
		return bePraiseNum;
	}

	public void setBePraiseNum(String bePraiseNum) {
		this.bePraiseNum = bePraiseNum;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getLearnDays() {
		return learnDays;
	}

	public void setLearnDays(Long learnDays) {
		this.learnDays = learnDays;
	}

	public Long getLoginDays() {
		return loginDays;
	}

	public void setLoginDays(Long loginDays) {
		this.loginDays = loginDays;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOfficialGender() {
		return officialGender;
	}

	public void setOfficialGender(String officialGender) {
		this.officialGender = officialGender;
	}

	public String getIdentificationCardCode() {
		return identificationCardCode;
	}

	public void setIdentificationCardCode(String identificationCardCode) {
		this.identificationCardCode = identificationCardCode;
	}

	public String getAdmissionCardCode() {
		return admissionCardCode;
	}

	public void setAdmissionCardCode(String admissionCardCode) {
		this.admissionCardCode = admissionCardCode;
	}

	public String getDegreeLevel() {
		return degreeLevel;
	}

	public void setDegreeLevel(String degreeLevel) {
		this.degreeLevel = degreeLevel;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getBenchmarkFace() {
		return benchmarkFace;
	}

	public void setBenchmarkFace(String benchmarkFace) {
		this.benchmarkFace = benchmarkFace;
	}

	public String getOfficialStatus() {
		return officialStatus;
	}

	public void setOfficialStatus(String officialStatus) {
		this.officialStatus = officialStatus;
	}

	public String getXdUniqueId() {
		return xdUniqueId;
	}

	public void setXdUniqueId(String xdUniqueId) {
		this.xdUniqueId = xdUniqueId;
	}

	public String getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(String mainAccount) {
		this.mainAccount = mainAccount;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
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

}
