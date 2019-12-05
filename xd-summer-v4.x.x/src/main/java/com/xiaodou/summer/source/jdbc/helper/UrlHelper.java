package com.xiaodou.summer.source.jdbc.helper;

import java.util.ArrayList;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.source.jdbc.enums.DbType;

/**
 * @name UrlHelper CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年12月10日
 * @description url处理
 * @version 1.0
 */
public class UrlHelper {

  private static String URL_START = "jdbc";
  private static String URL_SEQ = ":";

  public static ArrayList<String> handle(String url) throws Exception {
    LoggerUtil.debug("url ---> " + url);
    String[] urlCluster = url.split(URL_SEQ);
    if (urlCluster.length < 3) {
      throw new IllegalArgumentException("JDBC Url has wrong format." + url);
    }
    if (!urlCluster[0].startsWith(URL_START)) {
      throw new IllegalArgumentException("UnSupport url." + url);
    }
    return DbType.getDbType(urlCluster[1]).getAllUrl(url);
  }
}
