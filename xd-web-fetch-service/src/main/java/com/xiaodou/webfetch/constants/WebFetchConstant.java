package com.xiaodou.webfetch.constants;

/**
 * @name @see com.xiaodou.webfetch.constants.WebFetchConstant.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 常量类
 * @version 1.0
 */
public interface WebFetchConstant {

  /** URL_REG Url正则 */
  public static final String URL_REG =
      "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
  
}
