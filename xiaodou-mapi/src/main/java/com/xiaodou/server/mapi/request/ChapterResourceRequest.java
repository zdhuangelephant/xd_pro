package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ChapterResourceRequest extends MapiBaseRequest {

  /** courseId 课程ID */
  @NotEmpty
  private String courseId;

  /** chapterId 章ID */
  @NotEmpty
  private String chapterId;

  /** itemId 节ID */
  @NotEmpty
  private String itemId;

  private String resourceType;

  private String commentIdUpper;

  private Integer size;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getCommentIdUpper() {
    return commentIdUpper;
  }

  public void setCommentIdUpper(String commentIdUpper) {
    this.commentIdUpper = commentIdUpper;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
