package com.xiaodou.forum.vo.forum;


/**
 * @name @see com.xiaodou.forum.vo.forum.RelateComment.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月18日
 * @description 与我相关的评论
 * @version 1.0
 */
public class RelateComment extends Comment {
  /** id */
  private String cid;
  /** 话题标题 */
  private String title;
  /** 发帖人昵称 */
  private String publisherNickName;
  /** publisherPortrait 发帖人头像 */
  private String publisherPortrait;
  /** status 状态 */
  private String status;
  /** 话题分类ID */
  private String forumCategoryId;
  /** forumCategoryName 话题分类 */
  private String forumCategoryName;

  public String getForumCategoryName() {
    return forumCategoryName;
  }

  public void setForumCategoryName(String forumCategoryName) {
    this.forumCategoryName = forumCategoryName;
  }

  public String getForumCategoryId() {
    return forumCategoryId;
  }

  public void setForumCategoryId(String forumCategoryId) {
    this.forumCategoryId = forumCategoryId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public String getPublisherPortrait() {
    return publisherPortrait;
  }

  public void setPublisherPortrait(String publisherPortrait) {
    this.publisherPortrait = publisherPortrait;
  }

  public String getPublisherNickName() {
    return publisherNickName;
  }

  public void setPublisherNickName(String publisherNickName) {
    this.publisherNickName = publisherNickName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
