package com.xiaodou.async.model;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;

public class SystemMessage extends AbstractAsyncMessage {

  public SystemMessage() {
    setSrcUid("-1");
    setType(AsyncMessageConst.POJO_TYPE_SYSTEMMES);
    setStatus(AsyncMessageConst.POJO_STATUS_UNREAD);
    setClassify(AsyncMessageConst.POJO_CLASSIFY_SYSMES);
    setMessageBody(AsyncResInfo.SystemMessage.getMesTmp());
    setRetCode(AsyncResInfo.SystemMessage.getRetCode());
    setRetDesc(AsyncResInfo.SystemMessage.getRetDesc());
    setDealResult(AsyncMessageConst.DOMAIN_DEALRESULT_STATIC);
  }

}
