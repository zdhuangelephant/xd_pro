package com.xiaodou.oms.statemachine.engine.model.api;

import com.xiaodou.oms.statemachine.engine.model.Template;

/**
 * <p>RemoteAPI组件模型</p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
public class RemoteAPI extends BaseAPI {
  
  /**
   * 远程API地址
   */
  private String url;
  
  /**
   * 远程协议
   */
  private String protocol;
  
  /**
   * 入参模板
   */
  private Template template;

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

}
