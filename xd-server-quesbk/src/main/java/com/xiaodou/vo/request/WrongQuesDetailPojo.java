package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.util.QuesbkUtil;

public class WrongQuesDetailPojo extends QuesBasePojo {

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
   * 错题状态 1 未掌握 2 待强化 3 已消灭
   */
  @NotEmpty
  @LegalValueList({"0", "1", "2", "3"})
  private String type;

  public String getCourseId() {
    return QuesbkUtil.trim(courseId);
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return QuesbkUtil.trim(chapterId);
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

}
