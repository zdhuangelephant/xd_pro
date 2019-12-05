package com.xiaodou.userCenter.module.selfTaught.request;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.userCenter.prop.InitMedalProp;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

@OverComeField(field= {"module"},annotiation=AnnotationType.NotEmpty)
public class StUserInfoNewLogin extends NewLoginRequest {

  public StUserInfoNewLogin() {
    this(null);
  }

  public StUserInfoNewLogin(UserBaseInfo info) {
    super(info);
  }

  private String major;// 用户已选专业
  private String sign; // 签名
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

  public String getPicList() {
    return picList;
  }

  public void setPicList(String picList) {
    this.picList = picList;
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

  @Override
  public String getMoudelInfo() {
    StUserInfo userInfo = new StUserInfo();
    userInfo.setMajor(getMajor());
    userInfo.setRegion(this.region);
    userInfo.setRegionName(this.regionName);
    userInfo.setMajorId(this.majorId);
    userInfo.setMajorName(this.majorName);
    userInfo.setSign(getSign());
    userInfo.setPicList(getPicList());
    /* 勋章 */
    StMedal stMedal = new StMedal();
    stMedal.setMedalId(StringUtils.isNotBlank(getMedalId()) ? getMedalId() : InitMedalProp.getParams("init.medalId"));
    stMedal.setMedalName(StringUtils.isNotBlank(getMedalName()) ? getMedalName() : InitMedalProp.getParams("init.medalName"));
    stMedal.setMedalImg(StringUtils.isNotBlank(getMedalImg()) ? getMedalImg() : InitMedalProp.getParams("init.medalImg"));
    userInfo.setMedal(FastJsonUtil.toJson(stMedal));
    return FastJsonUtil.toJson(userInfo);
  }

}
