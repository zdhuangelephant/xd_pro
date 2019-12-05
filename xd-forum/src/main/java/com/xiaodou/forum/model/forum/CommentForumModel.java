package com.xiaodou.forum.model.forum;

public class CommentForumModel extends ForumCommentModel{
  //话题model
  private ForumModel forum;

  public ForumModel getForum() {
    return forum;
  }

  public void setForum(ForumModel forum) {
    this.forum = forum;
  }
}
