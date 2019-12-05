package com.xiaodou.userCenter.request;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.enums.Gender;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.UserModel;

@OverComeField(field = {"module"}, annotiation = AnnotationType.NotEmpty)
public abstract class NewLoginRequest extends UserBaseInfo {
    /*
     * 第三方登录必填项
     */

    // local/qq/weibo/weixin;所属平台; device;游客模式登录
    @NotEmpty
    @LegalValueList({UcenterModelConstant.PLATFORM_TELEPHONE, UcenterModelConstant.PLATFORM_QQ,
            UcenterModelConstant.PLATFORM_WEIBO, UcenterModelConstant.PLATFORM_WEIXIN,
            UcenterModelConstant.PLATFORM_TOURIST, UcenterModelConstant.PLATFORM_LOCAL,
            UcenterModelConstant.PLATFORM_DEVICE})
    private String platform;

    // 唯一标识，第三方
    @OrNotEmptyList({@NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_QQ),
            @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIBO),
            @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_WEIXIN)})
    private String uniqueId;

    /*
     * local登录必填项
     */

    /** 手机号 */
    @OrNotEmptyList({@NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE),
            @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_LOCAL)})
    private String phoneNum;

    /** 密码 */
    @OrNotEmptyList({@NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_TELEPHONE),
            @NotEmpty(field = "platform", value = UcenterModelConstant.PLATFORM_LOCAL)})
    private String pwd;

    /** 个推id */
    private String registrationId;

    /* 经度（合作用户不为空） */
    private String lng;
    
    /* 纬度（合作用户不为空） */
    private String lat;

    /** salt 注册时系统自动生成掩码 */
    // private String salt = RandomNumberUtil.getRandomString(4, 6);

    public NewLoginRequest(UserBaseInfo info) {
        super(info);
    }

    public abstract String getMoudelInfo();

    public void setRegistUserInfo(UserModel model) {
        setUserInfo(model);
        switch (platform) {
            case UcenterModelConstant.PLATFORM_TOURIST:
            case UcenterModelConstant.PLATFORM_DEVICE:
                model.setNickName(UcenterModelConstant.NAME_TOURIST); // 设置昵称
                break;
            case UcenterModelConstant.PLATFORM_QQ:
            case UcenterModelConstant.PLATFORM_WEIBO:
            case UcenterModelConstant.PLATFORM_WEIXIN:
                model.setAge(getAge()); // 设置年龄
                model.setNickName(StringUtils.isNotBlank(getNickName())
                        ? getNickName()
                        : UcenterModelConstant.DEFAULT_NAME + randCode(5)); // 设置昵称
                break;
            case UcenterModelConstant.PLATFORM_TELEPHONE:
            case UcenterModelConstant.PLATFORM_LOCAL:
                model.setNickName(UcenterModelConstant.DEFAULT_NAME + randCode(5)); // 设置昵称
                break;
            default:
                break;
        }
    }

    private void setUserInfo(UserModel model) {
        model.setCreateTime(getCreateTime()); // 设置创建时间
        model.setToken(getToken()); // 设置令牌
        model.setTokenTime(getTokenTime()); // 设置令牌创建时间
        model.setGender((null != getGender() ? getGender() : Gender.MAN.getCode())); // 设置性别
        model.setPortrait(StringUtils.isNotBlank(getPortrait())
                ? getPortrait()
                : UcenterModelConstant.PORTRAIT); // 设置头像
        model.setUsedDeviceId(getDeviceId()); // 设置设备号
        model.setLatestDeviceIp(getClientIp()); // 设置当前IP
        model.setModuleInfo(getMoudelInfo()); // 设置模块信息
        model.setUserType(UcenterModelConstant.USER_TYPE_GEN);// 作为普通用户
        model.setPackageTag(getPackageTag());
    }

    public Timestamp getTokenTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String getToken() {
        return UUID.randomUUID().toString();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public static String randCode(int n) {
        char[] arrChar = new char[] {'a', 'b', 'd', 'c', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
                'n', 'p', 'r', 'q', 's', 't', 'u', 'v', 'w', 'z', 'y', 'x', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
                'N', 'Q', 'P', 'R', 'S', 'V', 'U', 'W', 'X', 'Y', 'Z'};
        StringBuilder num = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            num.append(String.valueOf(arrChar[rnd.nextInt(arrChar.length - 1)]));
        }
        return num.toString();
    }

    public boolean canRegist() {
        return StringUtils.isNotBlank(platform)
                && !platform.equals(UcenterModelConstant.PLATFORM_TELEPHONE)
                && !platform.equals(UcenterModelConstant.PLATFORM_LOCAL);
    }
    
    public boolean isNotPush() {
        return platform.equals(UcenterModelConstant.PLATFORM_TOURIST) || platform.equals(UcenterModelConstant.PLATFORM_DEVICE);
    }

    // @Override
    // public String toString() {
    // this.pwd = "******";
    // return FastJsonUtil.toJson(this);
    // }
}
