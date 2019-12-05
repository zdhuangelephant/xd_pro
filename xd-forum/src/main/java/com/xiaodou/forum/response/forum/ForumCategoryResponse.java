package com.xiaodou.forum.response.forum;

import com.xiaodou.forum.model.forum.ForumCategoryModel;

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
    setForumCount(String.valueOf(model.getForumNumber()));
    setImage(model.getImages());
    setShortName(model.getShortName());
    setOutline(model.getOutline());
    setParticipationCount(String.valueOf(model.getPeopleNumber()));
    setTitle(model.getTitle());
  }

  /** 分类ID */
  private String forumCategoryId;
  /** 标题 */
  private String title;
  /** 概括 */
  private String outline;
  /** 话题数量 */
  private String forumCount;
  /** 参与人数 */
  private String participationCount;
  /** 图片url 列表 */
  private String image;
  /** shortNameString 简称 */
  private String shortName;

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

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

  public String getForumCount() {
    return forumCount;
  }

  public void setForumCount(String forumCount) {
    this.forumCount = forumCount;
  }

  public String getParticipationCount() {
    return participationCount;
  }

  public void setParticipationCount(String participationCount) {
    this.participationCount = participationCount;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
