package com.xiaodou.im;

import com.xiaodou.im.request.AddFriendPojo;
import com.xiaodou.im.request.BlacklistFriendPojo;
import com.xiaodou.im.request.ChangeFriendGroupPojo;
import com.xiaodou.im.request.CommentFriendPojo;
import com.xiaodou.im.request.DelFriendPojo;
import com.xiaodou.im.request.GetStatusPojo;
import com.xiaodou.im.request.ListFriendGroupPojo;
import com.xiaodou.im.request.ListFriendPojo;
import com.xiaodou.im.request.SpecialFriendPojo;
import com.xiaodou.im.response.IMResponse;

/**
 * @name @see com.xiaodou.im.IMFriendManager.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月2日
 * @description 好友关系管理interface
 * @version 1.0
 */
public interface IMFriendManager {

  /**
   * 添加好友
   */
  public IMResponse addFriend(AddFriendPojo pojo);

  /**
   * 删除好友
   */
  public IMResponse delFriend(DelFriendPojo pojo);

  /**
   * 获取好友状态
   */
  public IMResponse getFriendStatus(GetStatusPojo pojo);

  /**
   * 拉黑好友
   */
  public IMResponse blackListFriend(BlacklistFriendPojo pojo);

  /**
   * 特别关注
   */
  public IMResponse specialFriend(SpecialFriendPojo pojo);

  /**
   * 备注好友
   */
  public IMResponse commentFriend(CommentFriendPojo pojo);

  /**
   * 好友分组列表
   */
  public IMResponse listFriendGroup(ListFriendGroupPojo pojo);

  /**
   * 好友列表
   */
  public IMResponse listFriend(ListFriendPojo pojo);

  /**
   * 修改好友分组
   */
  public IMResponse changeFriendGroup(ChangeFriendGroupPojo pojo);

}
