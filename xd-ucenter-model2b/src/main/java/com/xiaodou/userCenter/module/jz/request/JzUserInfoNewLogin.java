package com.xiaodou.userCenter.module.jz.request;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.userCenter.module.jz.model.JzUserInfo;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;

/**
 * @name JzUserInfoNewLogin CopyRright (c) 2015 by <a
 *       href="mailto:lidehong@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月19日
 * @description 教资用户特殊信息
 * @version 1.0
 * @waste
 */
public class JzUserInfoNewLogin extends NewLoginRequest {

  public JzUserInfoNewLogin() {
    this(null);
  }

  public JzUserInfoNewLogin(UserBaseInfo info) {
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
  private Integer city;

  /**
   * 考期
   */
  private String examDate;

  /**
   * 学位
   */
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
    examDate = null != examDate ? examDate : "其它";
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
    if(null != getTypeCode())userInfo.setType(Integer.parseInt(getTypeCode()));
    return FastJsonUtil.toJson(userInfo);
  }

}
