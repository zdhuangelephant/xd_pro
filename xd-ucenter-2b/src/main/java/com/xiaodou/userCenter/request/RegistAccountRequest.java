package com.xiaodou.userCenter.request;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.UserModel;

public abstract class RegistAccountRequest extends UserBaseInfo {

    /**
     * 手机号
     */
    @NotEmpty
    private String phoneNum;

    /**
     * 密码
     */
    @NotEmpty
    private String password;

    /**
     * 确认密码
     */
    @NotEmpty
    private String confirmPassword;

    /**
     * 验证码
     */
    @NotEmpty
    private String checkCode;

    public RegistAccountRequest(UserBaseInfo info) {
        super(info);
    }

    public abstract String getMoudelInfo();

    public abstract String setMoudelInfo(Map<String, Object> moudelInfoMap);

    public void setRegistUserInfo(UserModel model) {
        model.setNickName(StringUtils.isNotBlank(getNickName())
                ? getNickName()
                : UcenterModelConstant.DEFAULT_NAME + getPhoneNum().substring(6)); // 设置昵称
        model.setPortrait(StringUtils.isNotBlank(getPortrait())
                ? getPortrait()
                : UcenterModelConstant.PORTRAIT); // 设置头像
        model.setAge(getAge()); // 设置年龄
        model.setAddress(getAddress()); // 设置地址
        model.setGender(null != getGender() ? getGender() : Gender.MAN.getCode()); // 设置性别
        model.setCreateTime(getCreateTime()); // 设置创建时间
        model.setToken(getToken()); // 设置令牌
        model.setTokenTime(getTokenTime()); // 设置令牌创建时间
        model.setUsedDeviceId(getDeviceId()); // 设置设备号
        model.setLatestDeviceIp(getClientIp()); // 设置当前IP
        model.setModuleInfo(getMoudelInfo()); // 设置模块信息
        model.setPackageTag(getPackageTag());
    }

    public Timestamp getTokenTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    // public String getPasswd() {
    // return UcenterUtil.getPasswd(getPassword() + getSalt());
    // }

    public Timestamp getCreateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String getToken() {
        return UUID.randomUUID().toString();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public abstract String getMajor();
}
