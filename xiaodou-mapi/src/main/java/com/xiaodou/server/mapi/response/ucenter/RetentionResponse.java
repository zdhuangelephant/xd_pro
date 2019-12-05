package com.xiaodou.server.mapi.response.ucenter;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.server.mapi.constant.SelfTaughtConstant;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.vo.out.ResultType;

public class RetentionResponse extends BaseResponse {

  public RetentionResponse() {}

  public RetentionResponse(ResultType resultType) {
    super(resultType);
  }

  public RetentionResponse(ResultType resultType, UserModelResponse user) {
    super(resultType);
    this.realName = user.getRealName();
    this.officialGender = user.getOfficialGender();
    this.identificationCardCode = user.getIdentificationCardCode();
    this.admissionCardCode = user.getAdmissionCardCode();
    this.majorName = user.getMajorName();
    this.degreeLevel = user.getDegreeLevel();
    this.merchant = user.getMerchant();
  }

  private String realName = StringUtils.EMPTY;// 真实姓名
  private String officialGender = StringUtils.EMPTY;// 官方导入性别
  private String identificationCardCode = StringUtils.EMPTY;// 身份证号
  private String admissionCardCode = StringUtils.EMPTY; // 准考证号
  private String majorName = StringUtils.EMPTY; // 专业名称
  private String degreeLevel = StringUtils.EMPTY; // 学习层次
  private String merchant = StringUtils.EMPTY; // 学习机构

  /* 基本信息 */
  public String getBaseInfo() {
    String gender = Gender.UNKNOW.getName();
    if (SelfTaughtConstant.MAN.equals(this.officialGender)) {
      gender = Gender.MAN.getName();
    } else if (SelfTaughtConstant.WOMAN.equals(this.officialGender)) {
      gender = Gender.WOMAM.getName();
    }
    return String.format("考生姓名:%s<br/>考生性别:%s<br/>身份证号:%s", this.realName, gender,
        this.identificationCardCode);
  }

  /* 学籍信息 */
  public String getRetentionInfo() {
    return String.format("准考证号:%s<br/>专业名称:%s<br/>学习层次:%s<br/>学习机构:%s", this.admissionCardCode,
        this.majorName, this.degreeLevel, this.merchant);
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public void setOfficialGender(String officialGender) {
    this.officialGender = officialGender;
  }


  public void setIdentificationCardCode(String identificationCardCode) {
    this.identificationCardCode = identificationCardCode;
  }


  public void setAdmissionCardCode(String admissionCardCode) {
    this.admissionCardCode = admissionCardCode;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public void setDegreeLevel(String degreeLevel) {
    this.degreeLevel = degreeLevel;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

}
