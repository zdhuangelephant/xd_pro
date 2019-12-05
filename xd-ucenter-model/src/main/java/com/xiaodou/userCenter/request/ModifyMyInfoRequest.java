package com.xiaodou.userCenter.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.model.vo.UserModelVo;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"phoneNum", "passWord",
    "confirmPassWord", "checkCode", "publishId", "systemType"})
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public abstract class ModifyMyInfoRequest extends RegistAccountRequest {
  public ModifyMyInfoRequest(UserBaseInfo info) {
    super(info);
  }
  
  public abstract ModifyMyInfoRequest getModifyAccountInfo();

  public void setModifyInfo(UserModelVo model) {
    if (StringUtils.isNotBlank(getNickName())) model.setNickName(getNickName()); // 设置昵称
    if (StringUtils.isNotBlank(getPortrait())) model.setPortrait(getPortrait()); // 设置头像
    if (null != getAge()) model.setAge(getAge()); // 设置年龄
    if (StringUtils.isNotBlank(getNickName())) model.setAddress(getAddress()); // 设置地址
    if (null != getGender()) model.setGender(getGender()); // 设置性别
    if (StringUtils.isNotBlank(getDeviceId())) model.setUsedDeviceId(getDeviceId()); // 设置使用过的设备
    if (StringUtils.isNotBlank(getClientIp())) model.setLatestDeviceIp(getClientIp()); // 设置最近登录设备
    if (StringUtils.isNotBlank(getMoudelInfo())) model.setModuleInfo(getMoudelInfo()); // 设置模块信息
  }
}
