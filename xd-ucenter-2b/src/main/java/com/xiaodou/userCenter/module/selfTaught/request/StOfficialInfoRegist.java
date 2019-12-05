package com.xiaodou.userCenter.module.selfTaught.request;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.module.selfTaught.model.StOfficialInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.userCenter.prop.InitMedalProp;
import com.xiaodou.userCenter.request.OfficialRegistRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

public class StOfficialInfoRegist extends OfficialRegistRequest {

  public StOfficialInfoRegist() {
    this(null);
  }

  public StOfficialInfoRegist(UserBaseInfo info) {
    super(info);
  }

  private String major;// 用户已选专业(专业码值)
  private String sign; // 签名
  private String medalId;// 勋章id
  private String medalName;// 勋章名称
  private String medalImg;// 勋章图片
  private String picList;// 图片

  /* 导入用户数据 start */
  private String realName;// 真实姓名
  private String officialGender;// 官方导入性别
  private String identificationCardCode;// 身份证号
  private String admissionCardCode; // 准考证号
  private String majorName; // 专业名称
  private String degreeLevel; // 学习层次
  private String merchant; // 学习机构
  private String benchmarkFace; // 基准面容
  private String officialStatus;// 0：初次导入用户，1：完成密码修改，2：完成上传基准面容
  /* 导入用户数据 end */

  /** module 地区 */
  private String region;
  /** moduleName 地区名称 */
  private String regionName;

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

  public String getSign() {
    return sign;
  }

  public String getOfficialStatus() {
    return officialStatus;
  }

  public void setOfficialStatus(String officialStatus) {
    this.officialStatus = officialStatus;
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

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
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

  @Override
  public String getMoudelInfo() {
    StUserInfo userInfo = new StUserInfo();
    userInfo.setMajor(getMajor());
    userInfo.setRegion(this.region);
    userInfo.setRegionName(this.regionName);
    userInfo.setSign(getSign());
    userInfo.setPicList(getPicList());
    /* 勋章 */
    StMedal stMedal = new StMedal();
    stMedal.setMedalId(StringUtils.isNotBlank(getMedalId()) ? getMedalId() : InitMedalProp
        .getParams("init.medalId"));
    stMedal.setMedalName(StringUtils.isNotBlank(getMedalName()) ? getMedalName() : InitMedalProp
        .getParams("init.medalName"));
    stMedal.setMedalImg(StringUtils.isNotBlank(getMedalImg()) ? getMedalImg() : InitMedalProp
        .getParams("init.medalImg"));
    userInfo.setMedal(FastJsonUtil.toJson(stMedal));
    return FastJsonUtil.toJson(userInfo);
  }

  public String getOfficialInfo() {
    StOfficialInfo stOfficialInfo = new StOfficialInfo();
    stOfficialInfo.setIdentificationCardCode(getIdentificationCardCode());
    stOfficialInfo.setAdmissionCardCode(getAdmissionCardCode());
    stOfficialInfo.setMajorName(getMajorName());
    stOfficialInfo.setDegreeLevel(getDegreeLevel());
    stOfficialInfo.setMerchant(getMerchant());
    stOfficialInfo.setRealName(getRealName());
    stOfficialInfo.setOfficialGender(getOfficialGender());
    stOfficialInfo.setOfficialStatus(getOfficialStatus());
    stOfficialInfo.setBenchmarkFace(getBenchmarkFace());
    stOfficialInfo.setRegion(getRegion()); // 从report导入地域信息
    stOfficialInfo.setRegionName(getRegionName()); // 从report导入地域信息
    return FastJsonUtil.toJson(stOfficialInfo);
  };

  @Override
  public String setMoudelInfo(Map<String, Object> moudelInfoMap) {
    StUserInfo userInfo = new StUserInfo();
    if (null != moudelInfoMap.get("major")) userInfo.setMajor((String) moudelInfoMap.get("major"));
    if (null != moudelInfoMap.get("sign")) userInfo.setSign((String) moudelInfoMap.get("sign"));
    StMedal stMedal = new StMedal();
    if (null != moudelInfoMap.get("medal"))
      stMedal = FastJsonUtil.fromJson((String) moudelInfoMap.get("medal"), StMedal.class);
    stMedal.setMedalId(StringUtils.isNotBlank(stMedal.getMedalId())
        ? stMedal.getMedalId()
        : InitMedalProp.getParams("init.medalId"));
    stMedal.setMedalName(StringUtils.isNotBlank(stMedal.getMedalName())
        ? stMedal.getMedalName()
        : InitMedalProp.getParams("init.medalName"));
    stMedal.setMedalImg(StringUtils.isNotBlank(stMedal.getMedalImg())
        ? stMedal.getMedalImg()
        : InitMedalProp.getParams("init.medalImg"));
    userInfo.setMedal(FastJsonUtil.toJson(stMedal));

    if (null != moudelInfoMap.get("picList"))
      userInfo.setPicList((String) moudelInfoMap.get("picList"));

    if (StringUtils.isNotBlank(getMajor())) userInfo.setMajor(getMajor());
    if (StringUtils.isNotBlank(getSign())) userInfo.setSign(getSign());
    StMedal _stMedal = new StMedal();
    if (StringUtils.isNotBlank(getMedalId())) _stMedal.setMedalId(getMedalId());
    if (StringUtils.isNotBlank(getMedalName())) _stMedal.setMedalName(getMedalName());
    if (StringUtils.isNotBlank(getMedalImg())) _stMedal.setMedalImg(getMedalImg());
    if (StringUtils.isNotBlank(getMedalId()) || StringUtils.isNotBlank(getMedalName())
        || StringUtils.isNotBlank(getMedalImg())) userInfo.setMedal(FastJsonUtil.toJson(_stMedal));
    if (StringUtils.isNotBlank(getPicList())) userInfo.setPicList(getPicList());
    return FastJsonUtil.toJson(userInfo);
  }

  @Override
  public void setRegistOfficialInfo(UserModel model) {
    model.setNickName(StringUtils.isNotBlank(getRealName()) ? getRealName() : "小逗"
        + getPhoneNum().substring(6)); // 设置昵称
    model.setPortrait(getPortrait()); // 设置头像
    model.setAge(getAge()); // 设置年龄
    model.setAddress(getAddress()); // 设置地址
    model.setGender(null != getGender() ? getGender() : Gender.MAN.getCode()); // 设置性别
    model.setCreateTime(getCreateTime()); // 设置创建时间
    model.setToken(getToken()); // 设置令牌
    model.setTokenTime(getTokenTime()); // 设置令牌创建时间
    model.setUsedDeviceId(getDeviceId()); // 设置设备号
    model.setLatestDeviceIp(getClientIp()); // 设置当前IP
    model.setModuleInfo(getMoudelInfo()); // 设置模块信息
    model.setOfficialInfo(getOfficialInfo());// 设置官方导入用户数据
    model.setUserType(2);
    model.setLoginDays(0l);
    String tag = ConfigProp.getParams("xd.user.package.tag");
    if (StringUtils.isNotBlank(tag)) {
      model.setPackageTag(tag);
    }
  }
}
