package com.xiaodou.autotest.core.vo;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;

/**
 * @name @see com.xiaodou.autotest.core.vo.ApiRequest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月25日
 * @description API请求参数
 * @version 1.0
 */
public class ApiRequest {

  /** url 请求地址 */
  private String url;
  /** header 请求头 */
  private Map<String, String> header = Maps.newHashMap();
  /** format 参数格式 */
  private DataFormat format = ActionConstant.DEFAULT_DATA_TYPE;
  /** body 请求参数 */
  private Object body;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public DataFormat getFormat() {
    return format;
  }

  public void setFormat(DataFormat format) {
    this.format = format;
  }

  public Map<String, String> getHeader() {
    return header;
  }

  public void setHeader(Map<String, String> header) {
    this.header = header;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

}
