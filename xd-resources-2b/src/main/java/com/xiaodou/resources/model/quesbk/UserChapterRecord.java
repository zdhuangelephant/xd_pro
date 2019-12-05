package com.xiaodou.resources.model.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.StaticInfoProp;


/**
 * @name @see com.xiaodou.domain.UserChapterRecord.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月10日
 * @description 用户闯关记录实体
 * @version 1.0
 */
public class UserChapterRecord extends BaseEntity {

  /** id 主键 */
  private Long id;
  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** courseId 课程ID */
  private Long courseId;
  /** chapterId 章节ID */
  private Long chapterId;
  /** itemId 节ID */
  private Long itemId;
  /** starLevel 星级 */
  private String starLevel;
  /** status 状态 */
  private Integer status = QuesBaseConstant.QUES_USER_CHAPTER_STATUS_UNLOCK;
  /** score 得分 */
  private Double score;
  /** robotIdList 机器人ID列表 */
  private List<String> robotIdList = Lists.newArrayList();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(String starLevel) {
    if (StringUtils.isNotBlank(this.starLevel) && StringUtils.isNotBlank(starLevel)
        && this.starLevel.compareTo(starLevel) > 0) return;
    if ((StringUtils.isBlank(this.starLevel) || this.starLevel.compareTo(StaticInfoProp
        .getPassStarLevel()) < 0)
        && StringUtils.isNotBlank(starLevel)
        && starLevel.compareTo(StaticInfoProp.getPassStarLevel()) >= 0)
      setStatus(QuesBaseConstant.QUES_USER_CHAPTER_STATUS_HASTHROUGHED);
    if ((StringUtils.isBlank(this.starLevel) || this.starLevel.compareTo(StaticInfoProp
        .getFinishStarLevel()) < 0)
        && StringUtils.isNotBlank(starLevel)
        && starLevel.compareTo(StaticInfoProp.getFinishStarLevel()) >= 0)
      setStatus(QuesBaseConstant.QUES_USER_CHAPTER_STATUS_HASFINISHED);
    this.starLevel = starLevel;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    if (null != this.score && null != score && this.score > score) return;
    if (null != score) this.score = score;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    if (this.status != null && null != status && this.status > status) return;
    this.status = status;
  }

  public List<String> getRobotIdList() {
    return robotIdList;
  }

  public void setRobotIdList(List<String> robotIdList) {
    this.robotIdList = robotIdList;
  }

}
