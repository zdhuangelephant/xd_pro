package com.xiaodou.async.model;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;

public class PkCallbackMessage extends AbstractAsyncMessage {

  public PkCallbackMessage(AsyncResInfo info) {
    setType(AsyncMessageConst.POJO_TYPE_OTHERMES);
    setStatus(AsyncMessageConst.POJO_STATUS_UNREAD);
    setClassify(AsyncMessageConst.POJO_CLASSIFY_PK_CALLBACK);
    setMessageBody(info.getMesTmp());
    setRetCode(info.getRetCode());
    setRetDesc(info.getRetDesc());
    setDealResult(AsyncMessageConst.DOMAIN_DEALRESULT_STATIC);
  }

}
