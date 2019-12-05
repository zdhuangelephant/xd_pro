package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class SysMessageRequest extends MapiBaseRequest {

  /**
   * 消息ID
   * 
   */
  @NotEmpty
  private String messageId;
  /**
   * 结果 0 不同意 1 同意
   * 
   */
  @NotEmpty
  @LegalValueList({AsyncMessageConst.DOMAIN_DEALRESULT_UNAGREE,
      AsyncMessageConst.DOMAIN_DEALRESULT_AGREE})
  private String result;

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
