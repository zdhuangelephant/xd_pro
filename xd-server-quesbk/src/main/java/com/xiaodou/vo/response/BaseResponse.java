package com.xiaodou.vo.response;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.ResultType;
import com.xiaodou.summer.vo.out.ResultInfo;

/**
 * 所有给app返回对象的基类
 * <p/>
 * Date: 2014/8/6 Time: 14:23
 * 
 * @author Tian.Dong
 */
public class BaseResponse extends ResultInfo {
  /**
   * 返回码
   */
  private String retcode;
  /**
   * 返回结果
   */
  private String retdesc;
  /**
   * 错误描述
   */
  private String message = "";

  /**
   * 服务器Ip
   */
  private String serverIp;

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
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }
}
