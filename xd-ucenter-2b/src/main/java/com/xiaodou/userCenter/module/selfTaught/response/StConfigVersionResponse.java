package com.xiaodou.userCenter.module.selfTaught.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

import lombok.Data;

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
  @Data
  public static class ConfigVersionData {
    /** major 专业信息 */
    private String major = StringUtils.EMPTY;
    /** thirdlogin 三方登录平台 */
    private String thirdlogin = StringUtils.EMPTY;
    /** shareplateform 分享平台 */
    private String shareplateform = StringUtils.EMPTY;
    /** blankNotice 系统公告和活动 */
    private String blankNotice = StringUtils.EMPTY;
    /*android版本号*/
    private String androidVersion = StringUtils.EMPTY;
    /*ios版本号*/
    private String iosVersion = StringUtils.EMPTY;
    /*Android下载地址*/
    private String androidDownLoadUrl = StringUtils.EMPTY;
    /*IOS下载地址*/
    private String iosDownLoadUrl = StringUtils.EMPTY;
    /*Android版本描述*/
    private String androidVersionDesc = StringUtils.EMPTY;
    /*ios版本描述*/
    private String iosVersionDesc = StringUtils.EMPTY;
    /*Android最小可用版本号*/
    private String androidMinUsableVersion = StringUtils.EMPTY;
    /*IOS最小可用版本号*/
    private String iosMinUsableVersion = StringUtils.EMPTY;
    /*Android更新包大小*/
    private String androidUpdatePackSize = StringUtils.EMPTY;
    /*IOS更新包大小*/
    private String iosUpdatePackSize = StringUtils.EMPTY;
    /*是否可以注册*/
    private String isCanRegister = StringUtils.EMPTY;
    /**touristSwitch 0为关闭学习，1为开启学习*/
    private String touristSwitch = StringUtils.EMPTY;
  }
  
}
