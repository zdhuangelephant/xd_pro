package com.xiaodou.im.agent.qq.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * {"retcode":0,"result":"ok"}
 * <p/>
 * Date: 2014/12/11
 * Time: 11:04
 *
 * @author Tian.Dong
 */
public class SendBuddyMsgResponse extends BaseResponse {

  @JSONField(name = "retcode")
  private int retCode;

  private String result;

  @JSONField(name = "errmsg")
  private String errMsg;

  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }
}
