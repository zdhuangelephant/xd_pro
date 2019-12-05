package com.xiaodou.userCenter.module.jz.request;

import java.util.Map;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.userCenter.module.jz.model.JzUserInfo;
import com.xiaodou.userCenter.request.RegistAccountRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

/**
 * @name JzUserInfoRegist CopyRright (c) 2015 by <a
 *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月11日
 * @description 教资用户特殊信息
 * @version 1.0
 * @waste
 */
// @AddValidField(annotiation = AnnotationType.NotEmpty, field = {"nickName", "gender"})
public class JzUserInfoRegist extends RegistAccountRequest {

  public JzUserInfoRegist() {
    this(null);
  }

  public JzUserInfoRegist(UserBaseInfo info) {
    super(info);
  }

  /**
   * 报考类型
   */
  @LegalValueList({"1", "2", "3"})
  private String typeCode;


  /**
   * 城市ID
   */
  // @NotEmpty
  private Integer city;

  /**
   * 考期
   */
  private String examDate;

  /**
   * 学位
   */
  // @NotEmpty
  @LegalValueList({"1", "2", "3", "4"})
  private String degree;

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public Integer getCity() {
    return city;
  }

  public void setCity(Integer city) {
    this.city = city;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  @Override
  public String getMoudelInfo() {
    JzUserInfo userInfo = new JzUserInfo();
    userInfo.setCity(getCity());
    if (null != getDegree()) userInfo.setDegree(Integer.parseInt(getDegree()));
    userInfo.setExamDate(getExamDate());
    if (null != getTypeCode()) userInfo.setType(Integer.parseInt(getTypeCode()));
    return FastJsonUtil.toJson(userInfo);
  }

  @Override
  public String setMoudelInfo(Map<String, String> moudelInfoMap) {
    JzUserInfo userInfo = new JzUserInfo();
    if (null != moudelInfoMap.get("city"))
      userInfo.setCity(Integer.valueOf(moudelInfoMap.get("city")));
    if (null != moudelInfoMap.get("degree"))
      userInfo.setDegree(Integer.valueOf(moudelInfoMap.get("degree")));
    if (null != moudelInfoMap.get("examDate"))
      userInfo.setExamDate((String) moudelInfoMap.get("examDate"));
    if (null != moudelInfoMap.get("type"))
      userInfo.setType(Integer.valueOf(moudelInfoMap.get("type")));
    if (null != getCity()) userInfo.setCity(getCity());
    if (null != getDegree()) userInfo.setDegree(Integer.parseInt(getDegree()));
    if (null != getExamDate()) userInfo.setExamDate(getExamDate());
    if (null != getTypeCode()) userInfo.setType(Integer.parseInt(getTypeCode()));
    return FastJsonUtil.toJson(userInfo);
  }

  @Override
  public String getMajor() {
    return null == typeCode ? StringUtils.EMPTY : typeCode.toString();
  };
}
