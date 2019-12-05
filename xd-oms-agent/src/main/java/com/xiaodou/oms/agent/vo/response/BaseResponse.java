package com.xiaodou.oms.agent.vo.response;

/**
 * Created by zyp on 14-6-24.
 */
public class BaseResponse {

  /**
   * 返回结果编码
   */
  private int retCode;

  /**
   * 返回错误结果描述
   */
  private String retDesc;

  /**
   * 服务器ip
   */
  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  /**
   * 追加错误描述
   * 
   * @param retdesc
   */
  public void appendRetdesc(String retdesc) {
    this.retDesc = this.retDesc + ";" + retdesc;
  }

  public boolean isRetOk() {
    return 0 == retCode;
  }
}
