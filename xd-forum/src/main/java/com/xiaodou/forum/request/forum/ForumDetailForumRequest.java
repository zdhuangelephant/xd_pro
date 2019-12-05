package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 话题详情请求－话题部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:12:07
 */
public class ForumDetailForumRequest extends BaseRequest {

  /** 话题ID */
  @NotEmpty
  private String forumId;

  /** relateCommentId 相关评论信息ID */
  private String relateCommentId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getRelateCommentId() {
    return relateCommentId;
  }

  public void setRelateCommentId(String relateCommentId) {
    this.relateCommentId = relateCommentId;
  }

}
