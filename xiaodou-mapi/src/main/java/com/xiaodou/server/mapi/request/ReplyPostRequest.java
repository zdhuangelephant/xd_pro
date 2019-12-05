package com.xiaodou.server.mapi.request;

import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复帖子request
 * 
 * @author bing.cheng
 * 
 */
public class ReplyPostRequest extends MapiBaseRequest {
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;

  /** chapterId 章ID */
  @NotEmpty
  private String chapterId;

  /** itemId 节ID */
  @NotEmpty
  private String itemId;

  /** 评论内容 */
  @NotEmpty
  private String content;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getClientIp() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientIp();
    }

}
