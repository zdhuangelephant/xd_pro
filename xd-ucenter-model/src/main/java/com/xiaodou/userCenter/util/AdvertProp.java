package com.xiaodou.userCenter.util;

import java.util.List;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.Advertisement;

/**
 * @name @see com.xiaodou.userCenter.util.AdvertProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月31日
 * @description 广告配置文件类
 * @version 1.0
 */
public class AdvertProp {

  private static String key_code = "keys";

  private static String image_temp = "image.%s";

  private static String dubbing_temp = "dubbing.%s";

  private static FileUtil advert = FileUtil
      .getInstance("/conf/custom/env/advertisement.properties");

  public static List<String> getKeys() {
    String keys = advert.getProperties(key_code);
    if (StringUtils.isBlank(keys)) return null;
    return Lists.newArrayList(keys.split(","));
  }

  public static Advertisement getAd(String key) {
    if (StringUtils.isBlank(key)) return null;
    String imageUrl = advert.getProperties(String.format(image_temp, key));
    if (StringUtils.isBlank(imageUrl)) return null;
    String dubbingUrl = advert.getProperties(String.format(dubbing_temp, key));
    if (StringUtils.isBlank(dubbingUrl)) return null;
    return new Advertisement(imageUrl, dubbingUrl);
  }

  public static List<Advertisement> getAds(List<String> keyList) {
    List<Advertisement> adList = Lists.newArrayList();
    for (String key : keyList) {
      Advertisement ad = getAd(key);
      if (null != ad) adList.add(ad);
    }
    return adList;
  }

  public static List<Advertisement> getAds() {
    return getAds(getKeys());
  }
}
