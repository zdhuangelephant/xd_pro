package com.xiaodou.sms.cache;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.sms.common.constant.Constant;
import com.xiaodou.sms.utils.CacheTimeProp;

/**
 * @name @see com.xiaodou.sms.cache.SmsTemplateCache.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 短信模板缓存方法类
 * @version 1.0
 */
@Service("smsTemplateCache")
public class SmsTemplateCache {

  // 缓存失效时间, sms.template.cache.time在properties文件中配置
  private int smsTemplateCacheTime = Integer.parseInt(CacheTimeProp
      .getParams("sms.task.list.cache.time"));



  /**
   * 缓存短信模板信息
   * 
   * @param id 模板ID
   * @param template 模板内容
   */
  public void cacheTemplate(String id, String template) {
    if (StringUtils.isAllNotBlank(id, template)) {
      JedisUtil.addStringToJedis(Constant.SMS_TEMPLATE + id, template, smsTemplateCacheTime);
    }
  }

  /**
   * 获取短信模板信息
   * 
   * @param id 短信模板ID
   * @return
   */
  public String getTemplate(String id) {
    return JedisUtil.getStringFromJedis(Constant.SMS_TEMPLATE + id);
  }

}
