package com.xiaodou.async.model;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;

public class PkMessage extends AbstractAsyncMessage {

  public PkMessage() {
    setNeedCallBack(Boolean.TRUE);
    setType(AsyncMessageConst.POJO_TYPE_OTHERMES);
    setStatus(AsyncMessageConst.POJO_STATUS_UNREAD);
    setClassify(AsyncMessageConst.POJO_CLASSIFY_PK);
    setMessageBody(AsyncResInfo.PK.getMesTmp());
    setRetCode(AsyncResInfo.PK.getRetCode());
    setRetDesc(AsyncResInfo.PK.getRetDesc());
  }

}
