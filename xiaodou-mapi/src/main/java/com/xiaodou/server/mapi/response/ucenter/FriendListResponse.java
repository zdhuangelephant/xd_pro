package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.vo.ucenter.model.FriendModel;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.response.FriendListResponse.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月26日
 * @description 获取好友列表response
 * @version 1.0
 */
public class FriendListResponse extends BaseResponse {

  public FriendListResponse() {}

  public FriendListResponse(ResultType type) {
    super(type);
  }

  public FriendListResponse(UcenterResType type) {
    super(type);
  }

  /** friendList 好友列表 */
  private List<Friend> friendList = Lists.newArrayList();

  public List<Friend> getFriendList() {
    return friendList;
  }

  public void setFriendList(List<Friend> friendList) {
    this.friendList = friendList;
  }

  /**
   * @name @see com.xiaodou.server.mapi.response.Friend.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年1月26日
   * @description 好友实体类
   * @version 1.0
   */
  public static abstract class Friend {
    public abstract Friend init(FriendModel model);
  }
}
