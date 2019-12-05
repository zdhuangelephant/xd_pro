package com.xiaodou.course.web.response;

import com.xiaodou.summer.vo.out.ResultType;

/**
 * 返回APP的基础信息
 * 
 * @author bing.cheng
 * 
 */
public class BaseResponse {

  /** 返回结果编码 */
  private String retcode;

  /** 返回错误结果描述 */
  private String retdesc;

  /** 服务器IP */
  private String serverIp;

  public String getRetcode() {
    return retcode;
  }

  public void setRetcode(String retcode) {
    this.retcode = retcode;
  }

  public String getRetdesc() {
    return retdesc;
  }

  public void setRetdesc(String retdesc) {
    this.retdesc = retdesc;
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public BaseResponse(ResultType resultType) {
    retcode = resultType.getCode();
    retdesc = resultType.getMsg();
  }
}
