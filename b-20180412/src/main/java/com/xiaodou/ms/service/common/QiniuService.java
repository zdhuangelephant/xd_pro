package com.xiaodou.ms.service.common;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.prop.SevenCowProp;
import com.xiaodou.ms.util.UpTokenUtil;
import com.xiaodou.ms.web.request.common.UpTokenPojo;

/**
 * @name @see com.xiaodou.ms.service.common.QiniuService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月5日
 * @description 七牛操作service
 * @version 1.0
 */
@Service("qiniuService")
public class QiniuService {
  public String getUpToken(String scope) {
    UpTokenPojo pojo = new UpTokenPojo();
    pojo.setScope(scope);
    String upToken = UpTokenCache.getUpToken(pojo);
    if (StringUtils.isBlank(upToken)) {
      // 获取accessKey
      String accessKey = SevenCowProp.getParams("sevencow.uptoken.init.accessKey");
      // 获取secretKey
      String secretKey = SevenCowProp.getParams("sevencow.uptoken.init.secretKey");
      // 获取scope对应prefix_path
      String prefixPath =
          SevenCowProp.getParams(String.format("sevencow.uptoken.scope.prefixpath.%s", scope));
      if (StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)) {
        return StringUtils.EMPTY;
      }
      upToken = UpTokenUtil.getUpToken(accessKey, secretKey, scope, prefixPath);
      UpTokenCache.setUpToken(pojo, upToken);
    }
    return upToken;
  }

  public String relaseUpToken(String scope) {
    UpTokenPojo pojo = new UpTokenPojo();
    pojo.setScope(scope);
    // 获取accessKey
    String accessKey = SevenCowProp.getParams("sevencow.uptoken.init.accessKey");
    // 获取secretKey
    String secretKey = SevenCowProp.getParams("sevencow.uptoken.init.secretKey");
    // 获取scope对应prefix_path
    String prefixPath =
        SevenCowProp.getParams(String.format("sevencow.uptoken.scope.prefixpath.%s", scope));
    if (StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)) {
      return StringUtils.EMPTY;
    }
    String upToken = UpTokenUtil.getUpToken(accessKey, secretKey, scope, prefixPath);
    UpTokenCache.releaseUpToken(pojo, upToken);
    return upToken;
  }

  /**
   * @name @see com.xiaodou.ms.service.common.QiniuService.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月6日
   * @description 上传凭证缓存
   * @version 1.0
   */
  private static class UpTokenCache {

    private static AtomicBoolean lock = new AtomicBoolean(false);

    private static Long _deadLine = System.currentTimeMillis() / 1000;

    /**
     * 缓存验证码
     * 
     * @param model
     * @throws Exception
     */
    public static void setUpToken(UpTokenPojo pojo, String uptoken) {
      if (lock.compareAndSet(false, true)) {
        if (null != pojo.getDeadline() && pojo.getDeadline() > _deadLine) {
          String key = XdmsConstant.UP_TOKEN + pojo.getScope();
          JedisUtil.addStringToJedis(key, uptoken, 50 * 60);
          _deadLine = pojo.getDeadline();
        }
        lock.compareAndSet(true, false);
      }
    }

    /**
     * 释放验证码
     * 
     * @param pojo
     * @param uptoken
     */
    public static void releaseUpToken(UpTokenPojo pojo, String uptoken) {
      if (lock.compareAndSet(false, true)) {
        String key = XdmsConstant.UP_TOKEN + pojo.getScope();
        JedisUtil.addStringToJedis(key, uptoken, 50 * 60);
        _deadLine = pojo.getDeadline();
        lock.compareAndSet(true, false);
      }
    }

    /**
     * 
     * 从缓存获取验证码
     * 
     * @param pojo
     * @return
     */
    public static String getUpToken(UpTokenPojo pojo) {
      String key = XdmsConstant.UP_TOKEN + pojo.getScope();
      return JedisUtil.getStringFromJedis(key);
    }

  }

}
