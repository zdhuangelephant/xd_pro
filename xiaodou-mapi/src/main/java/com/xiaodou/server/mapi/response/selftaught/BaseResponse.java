package com.xiaodou.server.mapi.response.selftaught;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.enums.ProduceResType;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 所有给app返回对象的基类
 * <p/>
 * Date: 2014/8/6 Time: 14:23
 * 
 * @author Tian.Dong
 */
public class BaseResponse extends ResultInfo {
  /**
   * 错误描述
   */
  private String message = "";

  /**
   * 服务器Ip
   */
  private String serverIp;

  /* (non-Javadoc)
   * @see com.xiaodou.summer.vo.out.ResultInfo#getServerIp()
   */
  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public BaseResponse() {}

  public BaseResponse(ResultType resultType) {
    super(resultType);
  }
  
  public BaseResponse(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
    this.serverIp = resType.getServerIp();
  }

  public BaseResponse(ProduceResType resultType) {
    setRetcode(resultType.getCode());
    setRetdesc(resultType.getMsg());
    this.serverIp = resultType.getServerIp();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }

  public boolean isRetOk() {
    return ResultType.SUCCESS.getCode().equals(getRetcode());
  }
  
  public String toJsonString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }
}
