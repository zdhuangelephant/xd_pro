package com.xiaodou.oms.vo.result;

public class ResultInfo {

  private String retcode;

  private String retdesc;

  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public ResultInfo() {

  }

  public ResultInfo(String retcode, String retdesc, String serverIp) {
    this.retcode = retcode;
    this.retdesc = retdesc;
    this.serverIp = serverIp;
  }

  public String getRetcode() {

    return this.retcode;
  }

  public void setRetcode(String retcode) {

    this.retcode = retcode;
  }

  public String getRetdesc() {

    return this.retdesc;
  }

  public void setRetdesc(String retdesc) {

    this.retdesc = retdesc;
  }

  public void appendRetdesc(String retdesc) {
    this.retdesc += retdesc;
  }

  public ResultInfo(ResultType type) {
    this.retcode = type.getCode();
    this.retdesc = type.getMsg();
    this.serverIp = type.getServerIp();
  }

}
