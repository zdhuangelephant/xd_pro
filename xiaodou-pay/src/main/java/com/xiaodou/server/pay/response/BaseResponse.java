package com.xiaodou.server.pay.response;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 所有给app返回对象的基类
 * <p/>
 * Date: 2014/8/6 Time: 14:23
 * 
 * @author Tian.Dong
 */
public abstract class BaseResponse extends ResultInfo {
  /**
   * 返回码
   */
  protected String retcode;
  /**
   * 返回结果
   */
  protected String retdesc;
  /**
   * 错误描述
   */
  protected String message = "";

  /**
   * 服务器Ip
   */
  protected String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public BaseResponse() {

  }

  public BaseResponse(ResultType resultType) {
    this.retcode = resultType.getCode();
    this.retdesc = resultType.getMsg();
    this.serverIp = resultType.getServerIp();
  }

  public BaseResponse(PaymentResType resultType) {
    this.retcode = resultType.getCode();
    this.retdesc = resultType.getMsg();
    this.serverIp = resultType.getServerIp();
  }

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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isRetOk() {
    return ResultType.SUCCESS.getCode().equals(retcode);
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public String toString0() {
    return String.format("{%s=%s}", this.getClass().getSimpleName(), FastJsonUtil.toJson(this));
  }
}
