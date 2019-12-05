package com.xiaodou.ucerCenter.agent.module.selfTaught.response;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.annotation.ShowField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StOfficialInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.ucerCenter.agent.response.BaseUserModel;

/**
 * @description 小逗自考用户响应信息
 * @version 1.0
 * @waste
 */
public class StUserInfoResponse extends BaseUserModel {

  public StUserInfoResponse() {}

  @ShowField
  private String major = StringUtils.EMPTY;// 用户已选专业专业(typeCode)
  @ShowField
  private String sign = StringUtils.EMPTY; // 签名
  @ShowField
  private List<String> picList = Lists.newArrayList();// 图片
  @ShowField
  private String medalId = StringUtils.EMPTY;// 勋章id
  @ShowField
  private String medalName = StringUtils.EMPTY;// 勋章名称
  @ShowField
  private String medalImg = StringUtils.EMPTY;// 勋章图片

  /* 导入用户数据 start */
  @ShowField
  private String realName = StringUtils.EMPTY;// 真实姓名
  @ShowField
  private String officialGender = StringUtils.EMPTY;// 官方导入性别
  @ShowField
  private String identificationCardCode = StringUtils.EMPTY;// 身份证号
  @ShowField
  private String admissionCardCode = StringUtils.EMPTY; // 准考证号
  @ShowField
  private String majorName = StringUtils.EMPTY; // 专业名称
  @ShowField
  private String degreeLevel = StringUtils.EMPTY; // 学习层次
  @ShowField
  private String merchant = StringUtils.EMPTY; // 学习机构
  @ShowField
  private String benchmarkFace = StringUtils.EMPTY; // 基准面容

  /* 导入用户数据 end */

  public String getMajor() {
    return major;
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

  public void setMajor(String major) {
    this.major = major;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
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

  public List<String> getPicList() {
    return picList;
  }

  public void setPicList(List<String> picList) {
    this.picList = picList;
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
  public void initModuleInfo(String moduleInfo) {
    if (StringUtils.isJsonNotBlank(moduleInfo)) {
      StUserInfo userInfo = FastJsonUtil.fromJson(moduleInfo, StUserInfo.class);
      if (null != userInfo) {
        if (StringUtils.isNotBlank(userInfo.getMajor())) major = userInfo.getMajor();
        if (StringUtils.isNotBlank(userInfo.getSign())) sign = userInfo.getSign(); // 签名
        if (StringUtils.isNotBlank(userInfo.getMedal())) {// 勋章
          StMedal stMedal = FastJsonUtil.fromJson(userInfo.getMedal(), StMedal.class);
          medalId = stMedal.getMedalId();
          medalName = stMedal.getMedalName();
          medalImg = stMedal.getMedalImg();
        }
        if (StringUtils.isNotBlank(userInfo.getPicList()))
          picList =
              FastJsonUtil.fromJsons(userInfo.getPicList(), new TypeReference<List<String>>() {});// 图片
      }
    }
  }

  @Override
  public boolean checkInfo() {
    if (StringUtils.isNotBlank(major)) return true;
    return false;
  }

  @Override
  protected void initOfficialInfo(String officialInfo) {
    if (StringUtils.isJsonNotBlank(officialInfo)) {
      StOfficialInfo stOfficialInfo = FastJsonUtil.fromJson(officialInfo, StOfficialInfo.class);
      if (null != stOfficialInfo) {
        if (StringUtils.isNotBlank(stOfficialInfo.getIdentificationCardCode()))
          identificationCardCode = stOfficialInfo.getIdentificationCardCode();
        if (StringUtils.isNotBlank(stOfficialInfo.getAdmissionCardCode()))
          admissionCardCode = stOfficialInfo.getAdmissionCardCode();
        if (StringUtils.isNotBlank(stOfficialInfo.getMajorName()))
          majorName = stOfficialInfo.getMajorName();
        if (StringUtils.isNotBlank(stOfficialInfo.getDegreeLevel()))
          degreeLevel = stOfficialInfo.getDegreeLevel();
        if (StringUtils.isNotBlank(stOfficialInfo.getMerchant()))
          merchant = stOfficialInfo.getMerchant();
        if (StringUtils.isNotBlank(stOfficialInfo.getRealName()))
          realName = stOfficialInfo.getRealName();
        if (StringUtils.isNotBlank(stOfficialInfo.getOfficialGender()))
          officialGender = stOfficialInfo.getOfficialGender();
        if (StringUtils.isNotBlank(stOfficialInfo.getBenchmarkFace()))
          benchmarkFace = stOfficialInfo.getBenchmarkFace();
      }
    }
  }

}
