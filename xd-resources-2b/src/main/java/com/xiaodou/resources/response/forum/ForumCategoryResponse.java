package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.model.forum.ForumCategoryModel;

/**
 * 发现--话题分类返回模型
 * 
 * @author bing.cheng
 * 
 */
public class ForumCategoryResponse {

  public ForumCategoryResponse() {}

  public ForumCategoryResponse(ForumCategoryModel model) {
    setForumCategoryId(String.valueOf(model.getId()));
    setImage(model.getImages());
    setOutline(model.getOutline());
    setTitle(model.getTitle());
  }

  /** 分类ID */
  private String forumCategoryId;
  /** 标题 */
  private String title;
  /** 概括 */
  private String outline;
  /** 图片url 列表 */
  private String image;
  public String getForumCategoryId() {
    return forumCategoryId;
  }

  public void setForumCategoryId(String forumCategoryId) {
    this.forumCategoryId = forumCategoryId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline;
  }



  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
