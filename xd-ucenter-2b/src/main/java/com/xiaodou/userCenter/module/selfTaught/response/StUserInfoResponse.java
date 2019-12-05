package com.xiaodou.userCenter.module.selfTaught.response;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.module.selfTaught.model.StOfficialInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.userCenter.response.BaseUserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description 小逗自考用户响应信息
 * @version 1.0
 * @waste
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StUserInfoResponse extends BaseUserModel {

  public StUserInfoResponse() {}

  /**major 用户已选专业专业(typeCode)*/
  private String major = StringUtils.EMPTY;
  private String sign = StringUtils.EMPTY; // 签名
  private List<String> picList = Lists.newArrayList();// 图片
  private String medalId = StringUtils.EMPTY;// 勋章id
  private String medalName = StringUtils.EMPTY;// 勋章名称
  private String medalImg = StringUtils.EMPTY;// 勋章图片

  /* 导入用户数据 start */
  private String realName = StringUtils.EMPTY;// 真实姓名
  private String officialGender = StringUtils.EMPTY;// 官方导入性别
  private String identificationCardCode = StringUtils.EMPTY;// 身份证号
  private String admissionCardCode = StringUtils.EMPTY; // 准考证号
  private String majorName = StringUtils.EMPTY; // 专业名称
  private String degreeLevel = StringUtils.EMPTY; // 学习层次
  private String merchant = StringUtils.EMPTY; // 学习机构
  private String benchmarkFace = StringUtils.EMPTY; // 基准面容
  private String officialStatus = StringUtils.EMPTY;// 0：初次导入用户，1：完成密码修改，2：完成上传基准面容
  /* 导入用户数据 end */
  
  private String region;
  private String regionName;
  private String majorId;

  @Override
  public void initModuleInfo(String moduleInfo) {
    if (StringUtils.isJsonNotBlank(moduleInfo)) {
      StUserInfo userInfo = FastJsonUtil.fromJson(moduleInfo, StUserInfo.class);
      if (null != userInfo) {
        if (StringUtils.isNotBlank(userInfo.getMajor())) {
          this.major = userInfo.getMajor();
        }
        if (StringUtils.isNotBlank(userInfo.getSign())) {
          sign = userInfo.getSign(); // 签名
        }
        if (StringUtils.isNotBlank(userInfo.getMedal())) {// 勋章
          StMedal stMedal = FastJsonUtil.fromJson(userInfo.getMedal(), StMedal.class);
          medalId = stMedal.getMedalId();
          medalName = stMedal.getMedalName();
          medalImg = stMedal.getMedalImg();
        }
        if (StringUtils.isNotBlank(userInfo.getPicList())) {
          picList =
            FastJsonUtil.fromJsons(userInfo.getPicList(), new TypeReference<List<String>>() {});// 图片
        }
        if(StringUtils.isNotBlank(userInfo.getRegion())) {
          this.region = userInfo.getRegion();
        }
        if(StringUtils.isNotBlank(userInfo.getRegionName())) {
          this.regionName = userInfo.getRegionName();
        }
        if(StringUtils.isNotBlank(userInfo.getMajorId())) {
          this.majorId = userInfo.getMajorId();
        }
        if(StringUtils.isNotBlank(userInfo.getMajorName())) {
          this.majorName = userInfo.getMajorName();
        }
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
        if (StringUtils.isNotBlank(stOfficialInfo.getOfficialStatus())) {
          officialStatus = stOfficialInfo.getOfficialStatus();
        }
        if (StringUtils.isBlank(stOfficialInfo.getOfficialStatus()))
          officialStatus = UcenterModelConstant.OFFICIALINFO_IMPORT;
      }
    } else {
      officialStatus = UcenterModelConstant.OFFICIALINFO_IMPORT;
    }
  }

}
