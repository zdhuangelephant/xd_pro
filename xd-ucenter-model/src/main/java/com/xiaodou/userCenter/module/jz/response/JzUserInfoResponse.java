package com.xiaodou.userCenter.module.jz.response;

import com.xiaodou.common.city.CityUtil;
import com.xiaodou.common.city.model.City;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.constant.UcneterModelConstant;
import com.xiaodou.userCenter.enums.Degree;
import com.xiaodou.userCenter.enums.ExamType;
import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.module.jz.model.JzUserInfo;
import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * @name JzUserInfoResponse CopyRright (c) 2015 by <a
 *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月11日
 * @description 教资用户响应信息
 * @version 1.0
 * @waste
 */
public class JzUserInfoResponse extends BaseUserModel {

  public JzUserInfoResponse() {}

  /**
   * 报考类型
   */
  private String type;
  /** typeCode 报考类型码值 */
  private String typeCode;
  /**
   * 学历
   */
  private String degree;
  
  /** degreeCode 学历码值 */
  private String degreeCode;
  /**
   * 考期
   */
  private String examDate;
  /**
   * 报考城市
   */
  private String city;

  /** cityCode 城市码值 */
  private String cityCode;
  
  /** genderCode 性别码值 */
  private String genderCode;


  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getDegreeCode() {
    return degreeCode;
  }

  public void setDegreeCode(String degreeCode) {
    this.degreeCode = degreeCode;
  }

  public String getGenderCode() {
    return genderCode;
  }

  public void setGenderCode(String genderCode) {
    this.genderCode = genderCode;
  }

  @Override
  public void initModuleInfo(UserModelVo model) {
    if (null != model) {
      if (null != model.getGender()) genderCode = model.getGender().toString();
      if (StringUtils.isJsonNotBlank(model.getModuleInfo())) {
        JzUserInfo userInfo = FastJsonUtil.fromJson(model.getModuleInfo(), JzUserInfo.class);
        if (null != userInfo) {
          if (null != userInfo.getCity()) {
            City cityInfo = CityUtil.getCityInfoByCode(userInfo.getCity());
            if (null != cityInfo) {
              city = cityInfo.getName();
              cityCode = cityInfo.getId().toString();
            } else {
              city = UcneterModelConstant.UNKNOWN_INFO;
              cityCode = StringUtils.EMPTY;
            }
          }
          degree = Degree.getByCode(userInfo.getDegree()).getName();
          if (null != userInfo.getDegree()) degreeCode = userInfo.getDegree().toString();
          examDate = userInfo.getExamDate();
          type = ExamType.getByCode(userInfo.getType()).getName();
          if (null != userInfo.getType()) typeCode = userInfo.getType().toString();
        }
      }
    }
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

}
