package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.util.QuesbkUtil;

public class StoreQuesDetailPojo extends QuesBasePojo {

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
   * 收藏状态  0 全部 1 好题(默认) 2 我不会的  3 需要记忆
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
