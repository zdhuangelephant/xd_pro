package com.xiaodou.vo.task;

import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserChapterRecord;

import lombok.Data;

/**
 * @name @see com.xiaodou.vo.task.InitCreditRedBonus.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年7月24日
 * @description 初始化积分红包参数
 * @version 1.0
 */
@Data
public class InitCreditRedBonus {

  /** redBonusType 红包类型 */
  private String redBonusType = QuesBaseConstant.CREDIT_RED_BONUS_TYPE;
  /** module 所属模块 */
  private String module;
  /** typeCode 专业码值 */
  private String typeCode;
  /** userId 用户ID */
  private String userId;
  /** missionId 任务ID */
  private String uniqueId;
  /** courseId 课程ID */
  private String courseId = Integer.toString(-1);
  /** chapterId 章ID */
  private String chapterId = Integer.toString(-1);
  /** itemId 节ID */
  private String itemId = Integer.toString(-1);

  public InitCreditRedBonus() {}

  public InitCreditRedBonus(String uniqueId, UserChapterRecord record) {
    this.module = record.getModule();
    this.typeCode = record.getTypeCode();
    this.userId = record.getUserId();
    this.uniqueId = uniqueId;
    if (null != record.getCourseId()) {
      this.courseId = record.getCourseId().toString();
    }
    if (null != record.getChapterId()) {
      this.chapterId = record.getChapterId().toString();
    }
    if (null != record.getItemId()) {
      this.itemId = record.getItemId().toString();
    }
  }

  public String getRedBonusType() {
    return redBonusType;
  }

  public void setRedBonusType(String redBonusType) {
    this.redBonusType = redBonusType;
  }

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

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
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

}
