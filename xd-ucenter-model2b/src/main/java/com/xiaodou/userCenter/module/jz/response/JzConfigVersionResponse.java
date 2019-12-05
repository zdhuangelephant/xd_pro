package com.xiaodou.userCenter.module.jz.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

/**
 * @name @see com.xiaodou.userCenter.moudle.jz.response.JzConfigVersionResponse.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月21日
 * @description 教師資格配置文件版本接口返回值
 * @version 1.0
 * @waste
 */
public class JzConfigVersionResponse
    extends ConfigResponse<JzConfigVersionResponse.ConfigVersionData> {

  public JzConfigVersionResponse(ResultType type) {
    super(type);
  }

  public JzConfigVersionResponse(UcenterResType type) {
    super(type);
  }

  /**
   * @name @see com.xiaodou.userCenter.module.jz.response.ConfigVersionData.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月31日
   * @description 教师资格配置文件版本数据类
   * @version 1.0
   */
  public static class ConfigVersionData {
    /** city 城市信息 */
    private String city;
    /** examDate 考期 */
    private String examDate;
    /** advertisement 广告 */
    private String advertisement;
    /** iosVersion IOS版本号 */
    private String iosVersion;
    /** iosDownloadUrl ios下载地址 */
    private String iosDownloadUrl;
    /** androidVersion Android版本号 */
    private String androidVersion;
    /** androidDownloadUrl android下载地址 */
    private String androidDownloadUrl;
    /** thirdlogin 三方登录平台 */
    private String thirdlogin;
    /** shareplateform 分享平台 */
    private String shareplateform;
    /** blankNotice 系统公告和活动*/
    private String blankNotice;

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

    public String getIosDownloadUrl() {
      return iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
      this.iosDownloadUrl = iosDownloadUrl;
    }

    public String getAndroidDownloadUrl() {
      return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
      this.androidDownloadUrl = androidDownloadUrl;
    }

    public String getIosVersion() {
      return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
      this.iosVersion = iosVersion;
    }

    public String getAndroidVersion() {
      return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
      this.androidVersion = androidVersion;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      this.examDate = examDate;
    }

    public String getAdvertisement() {
      return advertisement;
    }

    public void setAdvertisement(String advertisement) {
      this.advertisement = advertisement;
    }

  }

}
