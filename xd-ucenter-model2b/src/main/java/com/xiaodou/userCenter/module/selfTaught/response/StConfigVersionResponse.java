package com.xiaodou.userCenter.module.selfTaught.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class StConfigVersionResponse
    extends ConfigResponse<StConfigVersionResponse.ConfigVersionData> {

  public StConfigVersionResponse(ResultType type) {
    super(type);
  }

  public StConfigVersionResponse(UcenterResType type) {
    super(type);
  }

  /**
   * @description 小逗自考配置文件版本数据类
   * @version 1.0
   */
  public static class ConfigVersionData {
    /** major 专业信息 */
    private String major = StringUtils.EMPTY;
    /** thirdlogin 三方登录平台 */
    private String thirdlogin = StringUtils.EMPTY;
    /** shareplateform 分享平台 */
    private String shareplateform = StringUtils.EMPTY;
    /** blankNotice 系统公告和活动 */
    private String blankNotice = StringUtils.EMPTY;
    /** androidDownloadUrl 安卓下载地址 */
    private String androidDownloadUrl = StringUtils.EMPTY;
    /** iosDownloadUrl IOS下载地址 */
    private String iosDownloadUrl = StringUtils.EMPTY;

    public String getMajor() {
      return major;
    }

    public void setMajor(String major) {
      this.major = major;
    }

    public String getBlankNotice() {
      return blankNotice;
    }

    public void setBlankNotice(String blankNotice) {
      this.blankNotice = blankNotice;
    }

    public String getThirdlogin() {
      return thirdlogin;
    }

    public void setThirdlogin(String thirdlogin) {
      this.thirdlogin = thirdlogin;
    }

    public String getShareplateform() {
      return shareplateform;
    }

    public void setShareplateform(String shareplateform) {
      this.shareplateform = shareplateform;
    }

    public String getAndroidDownloadUrl() {
      return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
      this.androidDownloadUrl = androidDownloadUrl;
    }

    public String getIosDownloadUrl() {
      return iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
      this.iosDownloadUrl = iosDownloadUrl;
    }

  }
}
