package com.xiaodou.async.model;

import com.xiaodou.async.constant.AsyncMessageConst;
import com.xiaodou.async.enums.AsyncMessageEnums.AsyncResInfo;

public class AddFriendMessage extends AbstractAsyncMessage {

  public AddFriendMessage() {
    setNeedCallBack(Boolean.TRUE);
    setType(AsyncMessageConst.POJO_TYPE_OTHERMES);
    setStatus(AsyncMessageConst.POJO_STATUS_UNREAD);
    setClassify(AsyncMessageConst.POJO_CLASSIFY_ADDFRIEND);
    setMessageBody(AsyncResInfo.AddFriend.getMesTmp());
    setRetCode(AsyncResInfo.AddFriend.getRetCode());
    setRetDesc(AsyncResInfo.AddFriend.getRetDesc());
  }

}
