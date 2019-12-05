package com.xiaodou.ucerCenter.agent.module.selfTaught.request;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.ucerCenter.agent.prop.InitMedalProp;
import com.xiaodou.ucerCenter.agent.request.NewLoginRequest;
import com.xiaodou.ucerCenter.agent.request.UserBaseInfo;

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

  @Override
  public String getMoudelInfo() {
    StUserInfo userInfo = new StUserInfo();
    userInfo.setMajor(getMajor());
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

}
