package com.xiaodou.server.mapi.request.ucenter;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.constant.UcenterConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

public class NewLoginRequest extends UserBaseInfo {
  /*
   * 第三方登录必填项
   */

  // local/qq/weibo/weixin;所属平台; device;游客模式登录
  @NotEmpty
  @LegalValueList({UcenterConstant.PLATFORM_TELEPHONE, UcenterConstant.PLATFORM_QQ,
      UcenterConstant.PLATFORM_WEIBO, UcenterConstant.PLATFORM_WEIXIN,
      UcenterConstant.PLATFORM_TOURIST, UcenterConstant.PLATFORM_DEVICE, UcenterConstant.PLATFORM_LOCAL})
  private String platform;

  // 唯一标识，第三方
  @OrNotEmptyList({@NotEmpty(field = "platform", value = UcenterConstant.PLATFORM_QQ),
      @NotEmpty(field = "platform", value = UcenterConstant.PLATFORM_WEIBO),
      @NotEmpty(field = "platform", value = UcenterConstant.PLATFORM_WEIXIN)})
  private String uniqueId;

  /*
   * local登录必填项
   */

  /** 手机号 */
  @NotEmpty(field = "platform", value = UcenterConstant.PLATFORM_TELEPHONE)
  private String phoneNum;

  /** 密码 */
  @NotEmpty(field = "platform", value = UcenterConstant.PLATFORM_TELEPHONE)
  @JSONField(serialize=false)
  private String pwd;

  /** 个推id */
  private String registrationId;


  /** salt 注册时系统自动生成掩码 */
  // private String salt = RandomNumberUtil.getRandomString(4, 6);
  private String major;// 用户已选专业
  private String sign; // 签名
  private String picList;// 图片
  private String medalId;// 勋章id
  private String medalName;// 勋章名称
  private String medalImg;// 勋章图片
  private String lat;
  private String lng;

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

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getPicList() {
    return picList;
  }

  public void setPicList(String picList) {
    this.picList = picList;
  }

  public String getMedalId() {
    return medalId;
  }

  public void setMedalId(String medalId) {
    this.medalId = medalId;
  }

  public String getMedalName() {
    return medalName;
  }

  public void setMedalName(String medalName) {
    this.medalName = medalName;
  }

  public String getMedalImg() {
    return medalImg;
  }

  public void setMedalImg(String medalImg) {
    this.medalImg = medalImg;
  }

  public NewLoginRequest() {}

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

  public static String randCode(int n) {
    char[] arrChar =
        new char[] {'a', 'b', 'd', 'c', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'r', 'q',
            's', 't', 'u', 'v', 'w', 'z', 'y', 'x', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'Q', 'P', 'R',
            'S', 'V', 'U', 'W', 'X', 'Y', 'Z'};
    StringBuilder num = new StringBuilder();
    Random rnd = new Random();
    for (int i = 0; i < n; i++) {
      num.append(String.valueOf(arrChar[rnd.nextInt(arrChar.length - 1)]));
    }
    return num.toString();
  }

  public boolean canRegist() {
    return StringUtils.isNotBlank(platform)
        && !platform.equals(UcenterConstant.PLATFORM_TELEPHONE);
  }

}
