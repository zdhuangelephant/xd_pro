package com.xiaodou.resources.model.forum;

import com.xiaodou.ucenter.model.UserModel;

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
