package com.xiaodou.forum.response.forum;

import com.xiaodou.forum.enums.ForumResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:52:13
 */
public class BaseResponse {

  /**
   * 如果错误，返回错误结果描述
   */
  private String retdesc;

  /**
   * 如果错误，返回错误代码
   */
  private String retcode;

  public String getRetcode() {
    return retcode;
  }

  public void setRetcode(String retcode) {
    this.retcode = retcode;
  }

  public Long getTimestamp() {
    return System.currentTimeMillis();
  }

  public String getRetdesc() {
    return retdesc;
  }

  public void setRetdesc(String retdesc) {
    this.retdesc = retdesc;
  }

  public BaseResponse(ResultType type) {
    this.retcode = type.getCode();
    this.retdesc = type.getMsg();
  }

  public BaseResponse(ForumResType type) {
    this.retcode = type.getCode();
    this.retdesc = type.getMsg();
  }

  public void appendErrorMessage(String errorMsg) {
    this.retdesc += errorMsg;
  }

}
