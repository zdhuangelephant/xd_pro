package com.xiaodou.vo.task;


/**
 * @name @see com.xiaodou.vo.task.UseBonusToChapterItem.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月20日
 * @description 使用红包为章节加分
 * @version 1.0
 */
public class UseBonusToChapterItem {

  /** module 所属模块 */
  private String module;
  /** typeCode 专业码值 */
  private String typeCode;
  /** userId 用户ID */
  private String userId;
  /** courseId 课程ID */
  private String courseId;
  /** chapterId 章ID */
  private String chapterId;
  /** itemId 节ID */
  private String itemId;
  /** bonusScore 红包分 */
  private Long bonusScore;

  public UseBonusToChapterItem() {}

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

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

  public Long getBonusScore() {
    return bonusScore;
  }

  public void setBonusScore(Long bonusScore) {
    this.bonusScore = bonusScore;
  }

}
