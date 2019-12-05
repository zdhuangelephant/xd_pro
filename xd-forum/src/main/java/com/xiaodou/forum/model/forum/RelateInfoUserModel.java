package com.xiaodou.forum.model.forum;

import com.xiaodou.userCenter.model.UserModel;

/**
 * @name @see com.xiaodou.forum.model.forum.RelateInfoUserModel.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月19日
 * @description 消息发布用户模型
 * @version 1.0
 */
public class RelateInfoUserModel extends ForumRelateInfoModel {

  /**
   * 用户信息
   */
  private UserModel user;

  /** targetUser 被回复者 */
  private UserModel targetUser;

  public UserModel getTargetUser() {
    return targetUser;
  }

  public void setTargetUser(UserModel targetUser) {
    this.targetUser = targetUser;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

}
