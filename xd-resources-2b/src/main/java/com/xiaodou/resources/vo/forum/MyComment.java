package com.xiaodou.resources.vo.forum;


/**
 * @name @see com.xiaodou.forum.vo.forum.MyComment.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月28日
 * @description 我参与的评论
 * @version 1.0
 */
public class MyComment extends Comment {
  /** 话题标题 */
  private String title;
  /** 发帖人昵称 */
  private String publisherNickName;

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
