package com.xiaodou.server.mapi.util;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.constant.UcenterConstant;

/**
 * @name PlatformUtil CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月28日
 * @description 三方登录操作工具类
 * @version 1.0
 */
public class PlatformUtil {

  /**
   * 判断登录用户是否为三方登录
   * 
   * @param platform 平台类型
   * @return 是否为三方用户
   */
  public static boolean isThirdPartAccount(String platform) {
    if (StringUtils.isBlank(platform)) return false;
    return UcenterConstant.PLATFORM_QQ.equals(platform)
        || UcenterConstant.PLATFORM_WEIBO.equals(platform)
        || UcenterConstant.PLATFORM_WEIXIN.equals(platform);
  }

}
