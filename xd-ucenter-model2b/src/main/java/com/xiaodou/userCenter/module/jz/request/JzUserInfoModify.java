package com.xiaodou.userCenter.module.jz.request;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.module.jz.model.JzUserInfo;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
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
@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"type"})
public class JzUserInfoModify extends ModifyMyInfoRequest {

  public JzUserInfoModify() {
    this(null);
  }

  public JzUserInfoModify(UserBaseInfo info) {
    super(info);
  }

  /**
   * 报考类型
   */
  // @NotEmpty
  @LegalValueList({"1", "2", "3"})
  private Integer typeCode;


  /**
   * 城市ID
   */
  // @NotEmpty
  private Integer city;

  /**
   * 考期
   */
  // @NotEmpty
  private String examDate;

  /**
   * 学位
   */
  // @NotEmpty
  @LegalValueList({"1", "2", "3", "4"})
  private Integer degree;

  public Integer getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(Integer typeCode) {
    this.typeCode = typeCode;
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

  public Integer getDegree() {
    return degree;
  }

  public void setDegree(Integer degree) {
    this.degree = degree;
  }

  @Override
  public String getMoudelInfo() {
    JzUserInfo userInfo = new JzUserInfo();
    userInfo.setCity(getCity());
    userInfo.setDegree(getDegree());
    userInfo.setExamDate(getExamDate());
    userInfo.setType(getTypeCode());
    return FastJsonUtil.toJson(userInfo);
  }

  @Override
  public String setMoudelInfo(Map<String, String> moudelInfoMap) {
    JzUserInfo userInfo = new JzUserInfo();
    if (null != moudelInfoMap.get("city"))
      userInfo.setCity(Integer.valueOf(moudelInfoMap.get("city")));
    if (null != moudelInfoMap.get("degree"))
      userInfo.setDegree(Integer.valueOf(moudelInfoMap.get("degree")));
    if (null != moudelInfoMap.get("examDate")) userInfo.setExamDate(moudelInfoMap.get("examDate"));
    if (null != moudelInfoMap.get("type"))
      userInfo.setType(Integer.valueOf(moudelInfoMap.get("type")));
    if (null != getCity()) userInfo.setCity(getCity());
    if (null != getDegree()) userInfo.setDegree(getDegree());
    if (null != getExamDate()) userInfo.setExamDate(getExamDate());
    if (null != getTypeCode()) userInfo.setType(getTypeCode());
    return FastJsonUtil.toJson(userInfo);
  };

  /**
   * 思考:为什么这里要加注释? 答:因为这段代码在我写完之后,逻辑就忘掉了.第二天看到尝试优化,发现做了无用功,又还原回之前的代码.
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @see com.xiaodou.userCenter.request.ModifyStMyInfoRequest.request.ModifyMyInfoRequest#getModifyAccountInfo()
   */
  @Override
  @JSONField(serialize = false)
  public ModifyMyInfoRequest getModifyAccountInfo() {
    // 通过构造方法设置基础属性
    JzUserInfoModify info = new JzUserInfoModify(this);
    // 模块独有属性需要单独设置
    info.setTypeCode(typeCode);
    info.setCity(city);
    info.setExamDate(examDate);
    info.setDegree(degree);
    return info;
  }

  @Override
  public String getMajor() {
    return null == typeCode ? StringUtils.EMPTY : typeCode.toString();
  }

  @Override
  public String getSign() {
    return null;
  }

}
