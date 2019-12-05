package com.xiaodou.sms.cache;

import java.util.Map;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.sms.common.constant.Constant;
import com.xiaodou.sms.common.constant.Constant.ConstantUtil;
import com.xiaodou.sms.model.SmsChannelModel;

/**
 * @name @see com.xiaodou.sms.cache.SmsChannelCache.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 短信通道缓存
 * @version 1.0
 */
public class SmsChannelCache {

  /**
   * 根据channelID获取有效短信通道
   * 
   * @param channelId
   * @return
   */
  public SmsChannelModel getSmsChannel(String channelId) {
    Map<String, String> channelMap =
        JedisUtil.getAllMapValueFromJedis(ConstantUtil.append(Constant.SMS_CHANNEL, channelId));
    if (null != channelMap && channelMap.size() > 0) {
      String sChannel = channelMap.values().toArray()[0].toString();
      if (StringUtils.isJsonNotBlank(sChannel))
        return FastJsonUtil.fromJson(sChannel, SmsChannelModel.class);
    }
    return null;
  }

}
