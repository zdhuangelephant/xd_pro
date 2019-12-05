package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.ucerCenter.agent.constant.UcenterModelConstant;
import com.xiaodou.ucerCenter.agent.model.UserModel;
import com.xiaodou.ucerCenter.agent.util.ModuleMappingWrapper;

@OverComeField(annotiation = AnnotationType.NotEmpty, field = {"confirmPassWord", "checkCode"})
public abstract class OfficialRegistRequest extends RegistAccountRequest {

  public OfficialRegistRequest(UserBaseInfo info) {
    super(info);
  }

  public abstract String getOfficialInfo();

  public void setRegistOfficialInfo(UserModel model) {
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getPhoneNum()); // 设置用户名
    model.setSalt(getSalt()); // 设置掩码
    model.setNickName(StringUtils.isNotBlank(getNickName()) ? getNickName() : "小逗"
        + getPhoneNum().substring(6)); // 设置昵称
    model.setPortrait(getPortrait()); // 设置头像
    model.setAge(getAge()); // 设置年龄
    model.setAddress(getAddress()); // 设置地址
    model.setGender(null != getGender() ? getGender() : Gender.MAN.getCode()); // 设置性别
    model.setPassword(getPasswd()); // 设置密码
    model.setCreateTime(getCreateTime()); // 设置创建时间
    model.setToken(getToken()); // 设置令牌
    model.setTokenTime(getTokenTime()); // 设置令牌创建时间
    model.setUsedDeviceId(getDeviceId()); // 设置设备号
    model.setLatestDeviceIp(getClientIp()); // 设置当前IP
    if (StringUtils.isBlank(model.getUserName())) model.setUserName(getPhoneNum()); // 设置用户名
    model.setPlatform(UcenterModelConstant.PLATFORM_LOCAL);
    model.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode()); // 设置模块ID
    model.setModuleInfo(getMoudelInfo()); // 设置模块信息
    model.setOfficialInfo(getOfficialInfo());// 设置官方导入用户数据
    model.setUserType(2);
  }

}
