package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class QuesDetailRequest extends MapiBaseRequest {

  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;
  /**
   * 章ID
   */
  @NotEmpty
  private String chapterId;
  
  private String itemId;
  /**
   *   错题： 1 未掌握 2 待强化 3 已消灭 0 全部
   *   收藏： 1 好题 2 我不会 3 需要记忆 0 全部
   */
  @NotEmpty
  @LegalValueList({"0", "1", "2", "3"})
  private String type;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

}
