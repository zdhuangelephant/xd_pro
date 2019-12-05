package com.xiaodou.ucenter.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.ucenter.model.UserModel;

// @OverComeField(annotiation = AnnotationType.NotEmpty, field = {"phoneNum", "passWord",
// "checkCode",
// "nickName"})
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public class ModifyUserRequest extends BaseUserInfo {
  public ModifyUserRequest() {}

  public ModifyUserRequest(BaseUserInfo info) {
    super(info);
  }



  public void setModifyInfo(UserModel model) {
    if (StringUtils.isNotBlank(getNickName())) model.setNickName(getNickName()); // 设置昵称
    if (StringUtils.isNotBlank(getPortrait())) model.setPortrait(getPortrait()); // 设置头像
    if (null != getAge()) model.setAge(getAge()); // 设置年龄
    if (StringUtils.isNotBlank(getAddress())) model.setAddress(getAddress()); // 设置地址
    if (null != getGender()) model.setGender(getGender()); // 设置性别
    if (StringUtils.isNotBlank(getDeviceId())) model.setDeviceId(getDeviceId()); // 设置设备id
    if (StringUtils.isNotBlank(getClientIp())) model.setLatestDeviceIp(getClientIp()); // 设置最近登录设备
    if (StringUtils.isNotBlank(getSign())) model.setSign(getSign());// 设置签名
  }

}
