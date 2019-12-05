package com.xiaodou.standard.protocol;

/**
 * @name @see com.xiaodou.standard.protocol.TargetSocket.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 可达Socket
 * @version 1.0
 */
public class TargetSocket {

  /** TO_STRING_TMP toString模板 */
  private static final String TO_STRING_TMP = "{Host:%s,Port:%s}";

  public TargetSocket() {}

  public TargetSocket(String host, Integer port) {
    this.host = host;
    this.port = port;
  }

  /** host 地址 */
  private String host;
  /** port 端口 */
  private Integer port;

  public String toString() {
    return String.format(TO_STRING_TMP, this.host, this.port);
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }
  
}
