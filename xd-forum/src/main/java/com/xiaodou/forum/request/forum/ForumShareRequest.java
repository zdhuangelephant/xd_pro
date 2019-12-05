package com.xiaodou.forum.request.forum;

import org.springframework.validation.Errors;

import com.xiaodou.share.request.ShareRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.forum.request.forum.ForumShareRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 话题分享参数pojo
 * @version 1.0
 */
public class ForumShareRequest extends ShareRequest {

  /** forumId 话题ID */
  @NotEmpty
  private String forumId;
  /** categoryId 话题类型ID */
  @NotEmpty
  private String categoryId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    if (!"4".equals(getShareType()))
      errors.rejectValue("shareType", null, "shareType has a unvalid value, plz check it.");
    return errors;
  }

}
