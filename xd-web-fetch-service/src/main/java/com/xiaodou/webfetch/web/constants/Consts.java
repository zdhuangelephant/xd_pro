package com.xiaodou.webfetch.web.constants;

import java.text.SimpleDateFormat;

public interface Consts {
  
  /** sdf 知乎发布时间格式化模版 */
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
  
  /** CHARSET 抓取系统默认编码 */
  public static final String CHARSET = "UTF-8";
  
  /** ZH_ZHUANLAN param1: 文章组 ; param2: 可选<0-100>; param3: 偏移量 */
  public static final String ZH_ZHUANLAN = "https://zhuanlan.zhihu.com/api/columns/%s/posts?limit=%s&offset=%s";
  
  /** DEFAULT_PAGENO 默认页码 */
  public static final int DEFAULT_PAGENO = 0;
  /** DEFAULT_PAGSIZE 默认PS */
  public static final int DEFAULT_PAGSIZE = 20;
  
  /** DEFAULT_BODYSIZE jsoup堆外缓存大小 */
  public static final int BODYSIZE = 5 * 1024 * 1024;
  
  public static final String PORTRAIT_ZHIHU = "https://pic4.zhimg.com/";
  public static final String PORTRAIT_SIZE_ZHIHU = "xl.jpg";
  public static final String PORTRAIT_UNDERLINE_ZHIHU = "_";
  
  
  
  
}
