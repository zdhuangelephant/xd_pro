package com.xiaodou.forum.model.forum;

import com.xiaodou.userCenter.model.UserModel;

public class CommentUserModel extends ForumCommentModel{
  /**
   * 用户信息
   */
  private UserModel user;

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

}
