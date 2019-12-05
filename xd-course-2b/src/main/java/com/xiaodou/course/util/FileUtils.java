package com.xiaodou.course.util;

import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ucerCenter.agent.module.mapping.ModuleMapping;

/**
 * 读取配置文件
 * 
 * @author bing.cheng
 * 
 */
public class FileUtils {
  /** 不需要登录，也能访问的url */
  public static FileUtil NO_LOGIN_ACCESS_FILTER_URL = FileUtil
      .getInstance("/conf/custom/env/access_url.properties");
  /** QUESBK_PROPERTIES 题库相关参数 */
  public static FileUtil QUESBK_PROPERTIES = FileUtil
      .getInstance("/conf/custom/env/quesbk.properties");



  public static Map<String, String> initHeader() {
    Map<String, String> header = Maps.newHashMap();
    return header;
  }

  /**
   * 构造request请求内容
   * 
   * @param obj
   * @param clazzReq
   * @return
   * @throws Exception
   */
  public static String createContent(Object obj) throws Exception {
    if (obj == null) return StringUtils.EMPTY;
    StringBuilder content = new StringBuilder();
    String value = JSON.toJSONString(obj);
    content.append("&").append("req=").append(URLEncoder.encode(value, "utf-8"));
    return content.substring(1);

  }

  public static <T> T parseResponse(String result, Class<T> clazzRes) {
    return null == result ? null : JSON.parseObject(result, clazzRes);
  }

  /**
   * 
   * 获取用户访问模块
   * 
   * @param module
   * @return
   */
  public static ModuleMapping getModule(String module) {
    return ModuleMapping.getByCode(module);
  }

}
