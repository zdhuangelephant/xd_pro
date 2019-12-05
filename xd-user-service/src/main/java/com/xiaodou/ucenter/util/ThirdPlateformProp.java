package com.xiaodou.ucenter.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.userCenter.util.AdvertProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月31日
 * @description 三方平台类
 * @version 1.0
 */
public class ThirdPlateformProp {

  private static String thirdlogin_key = "thirdlogin";

  private static String shareplateform_key = "shareplateform";

  private static String seq = "#";

  private static FileUtil thirdplateform = FileUtil
      .getInstance("/conf/custom/env/thirdplateform.properties");

  public static List<String> getThirdLogin() {
    String thirdlogin = thirdplateform.getProperties(thirdlogin_key);
    if (StringUtils.isBlank(thirdlogin)) return null;
    return Lists.newArrayList(thirdlogin.split(seq));
  }

  public static List<String> getSharePlateform() {
    String shareplateform = thirdplateform.getProperties(shareplateform_key);
    if (StringUtils.isBlank(shareplateform)) return null;
    return Lists.newArrayList(shareplateform.split(seq));
  }
}
