package com.xiaodou.ms.web.request.knowledge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

/**
 * @name ForumRequest CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月8日
 * @description 话题请求类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ForumRequest extends BaseRequest {

  /** id 主键 */
  private String forumId;
  /** forumType 分类 */
  private String forumType;
  /** forumClassify 资源类型 */
  private String forumClassify;
  /** forumCover 封面 */
  private String forumCover;
  /** forumTitle 标题 */
  private String forumTitle;
  /** forumContent 内容 */
  @NotEmpty(field = "forumClassify", value = "1")
  private String forumContent;
  /** forumMedia 媒体资源地址 */
  @OrNotEmptyList({@NotEmpty(field = "forumClassify", value = "2"),
      @NotEmpty(field = "forumClassify", value = "3")})
  private String forumMedia;
  /** region 所处地域 */
  private String region;
  /** authorId 作者ID */
  private String authorId;
  /** forumTag 课程码标签 */
  private String forumTag;
  /** status 是否显示 0 否 1 是 */
  private Short status;

  public ForumModel initModel() {
    ForumModel forum = new ForumModel();
    forum.setForumId(forumId);
    forum.setRegion(region);
    forum.setAuthorId(authorId);
    forum.setForumClassify(forumClassify);
    forum.setForumContent(forumContent);
    forum.setForumCover(forumCover);
    forum.setForumMedia(forumMedia);
    forum.setForumTag(forumTag);
    forum.setForumTitle(forumTitle);
    forum.setForumType(forumType);
    forum.setStatus(status);
    return forum;
  }

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getForumType() {
    return forumType;
  }

  public void setForumType(String forumType) {
    this.forumType = forumType;
  }

  public String getForumClassify() {
    return forumClassify;
  }

  public void setForumClassify(String forumClassify) {
    this.forumClassify = forumClassify;
  }

  public String getForumCover() {
    return forumCover;
  }

  public void setForumCover(String forumCover) {
    this.forumCover = forumCover;
  }

  public String getForumTitle() {
    return forumTitle;
  }

  public void setForumTitle(String forumTitle) {
    this.forumTitle = forumTitle;
  }

  public String getForumContent() {
    return forumContent;
  }

  public void setForumContent(String forumContent) {
    this.forumContent = forumContent;
  }

  public String getForumMedia() {
    return forumMedia;
  }

  public void setForumMedia(String forumMedia) {
    this.forumMedia = forumMedia;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getForumTag() {
    return forumTag;
  }

  public void setForumTag(String forumTag) {
    this.forumTag = forumTag;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

}
