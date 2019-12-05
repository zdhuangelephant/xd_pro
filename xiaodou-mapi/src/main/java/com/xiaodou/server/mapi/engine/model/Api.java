package com.xiaodou.server.mapi.engine.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.engine.ActionConstant;
import com.xiaodou.server.mapi.engine.ActionEnum.Format;
import com.xiaodou.server.mapi.engine.ActionEnum.Method;
import com.xiaodou.server.mapi.engine.ActionEnum.Protocol;
import com.xiaodou.server.mapi.engine.ActionTool;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Api.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description API组件模型, 代表了业务系统注册的一个服务
 * @version 1.0
 */
public class Api {

  /** protocol 协议类型 */
  private Protocol protocol = ActionConstant.DEFAULT_PROTOCOL;
  /** format 数据传输格式 */
  private Format format = ActionConstant.DEFAULT_FORMAT;
  /** host 主机域名 */
  private String url;
  /** method 方法类型 */
  private Method method = ActionConstant.DEFAULT_METHOD;
  /** params 参数列表 */
  private List<Param> params = Lists.newArrayList();

  public List<Param> getParams() {
    return params;
  }

  public void registParam(Param param) {
    if (null != param) params.add(param);
  }

  public void registParam(String name) {
    if (StringUtils.isBlank(name)) return;
    registParam(new Param(name));
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Format getFormat() {
    return format;
  }

  public void setFormat(String formatName) {
    Format format = ActionTool.getEnumValue(Format.class, formatName);
    if (null != format) this.format = format;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocolName) {
    Protocol protocol = ActionTool.getEnumValue(Protocol.class, protocolName);
    if (null != protocol) this.protocol = protocol;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(String methodName) {
    Method method = ActionTool.getEnumValue(Method.class, methodName);
    if (null != method) this.method = method;
  }

}
