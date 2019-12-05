package com.xiaodou.webfetch.plugin.jsoup;

import java.util.List;

import lombok.Data;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;

import com.google.common.collect.Lists;

/**
 * @name @see com.xiaodou.webfetch.plugin.jsoup.JsoupPojo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月14日
 * @description Jsoup插件入参模型
 * @version 1.0
 */
@Data
public class JsoupPojo {
  /** url 请求地址 */
  private String url;
  /** headerList header列表 */      
  private List<Header> headerList = Lists.newArrayList();
  /** cookieList cookie列表 */
  private List<Cookie> cookieList = Lists.newArrayList();
}
