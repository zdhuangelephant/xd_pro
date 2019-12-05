package com.xiaodou.course.web.response;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.common.enums.NotesResType;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

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

  public boolean isRetOk() {
    return ResultType.SUCCESS.getCode().equals(retcode);
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

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public BaseResponse() {}

  public BaseResponse(ResultType resultType) {
    retcode = resultType.getCode();
    retdesc = resultType.getMsg();
  }

  public BaseResponse(ProductResType resultType) {
    retcode = resultType.getCode();
    retdesc = resultType.getMsg();
  }

  public BaseResponse(NotesResType resultType) {
    retcode = resultType.getCode();
    retdesc = resultType.getMsg();
  }

  public BaseResponse(UcenterResType resultType) {
    retcode = resultType.getCode();
    retdesc = resultType.getMsg();
  }

  
  public BaseResponse(ForumResType resultType) {
	  retcode = resultType.getCode();
	    retdesc = resultType.getMsg();
	  }
  
  public String toString0() {
    return FastJsonUtil.toJson(this);
  }
  
  public String toJsonString() {
    return FastJsonUtil.toJson(this);
  }
}
