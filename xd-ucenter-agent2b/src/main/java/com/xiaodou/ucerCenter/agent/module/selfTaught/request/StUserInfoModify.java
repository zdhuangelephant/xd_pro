package com.xiaodou.ucerCenter.agent.module.selfTaught.request;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.ucerCenter.agent.constant.UcenterModelConstant;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.ucerCenter.agent.prop.InitMedalProp;
import com.xiaodou.ucerCenter.agent.request.ModifyMyInfoRequest;
import com.xiaodou.ucerCenter.agent.request.UserBaseInfo;

public class StUserInfoModify extends ModifyMyInfoRequest {

  public StUserInfoModify(UserBaseInfo info) {
    super(info);
  }

  public StUserInfoModify() {
    this(null);
  }

  @NotEmpty(field = "type", value = UcenterModelConstant.IMPROVE_INFO)
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

  @Override
  public String getMoudelInfo() {
    StUserInfo userInfo = new StUserInfo();
    userInfo.setMajor(getMajor());
    userInfo.setSign(getSign());
    userInfo.setPicList(getPicList());
    /* 勋章 */
    StMedal stMedal = new StMedal();
    // if (StringUtils.isNotBlank(getMedalId())) stMedal.setMedalId(getMedalId());
    // if (StringUtils.isNotBlank(getMedalName())) stMedal.setMedalName(getMedalName());
    // if (StringUtils.isNotBlank(getMedalImg())) stMedal.setMedalImg(getMedalImg());
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
    return FastJsonUtil.toJson(userInfo);
  }

  @Override
  public String setMoudelInfo(Map<String, String> moudelInfoMap) {
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
  };

  @Override
  @JSONField(serialize = false)
  public ModifyMyInfoRequest getModifyAccountInfo() {
    // 通过构造方法设置基础属性
    StUserInfoModify info = new StUserInfoModify(this);
    // 模块独有属性需要单独设置
    info.setMajor(getMajor());
    info.setSign(getSign());
    info.setPicList(getPicList());
    info.setMedalId(getMedalId());
    info.setMedalName(getMedalName());
    info.setMedalImg(getMedalImg());
    return info;
  }

}
