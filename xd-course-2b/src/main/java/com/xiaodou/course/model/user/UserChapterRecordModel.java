package com.xiaodou.course.model.user;

import java.util.List;

import com.google.common.collect.Lists;

public class UserChapterRecordModel {
  private Long id;
  private Long userId;
  private Long courseId;// 课程id
  private Long chapterId;// 章id
  private Long itemId; // 节id
  private String starLevel; // 星级 0 0颗 1 一星 2 两心 3 三星
  private Integer score; // 闯关得分
  private String status;// 状态 0 未解锁 1 已解锁 2 已完成（数据库针对一个用户该字段为1的值有且只能为1）
  /** robotIdList 机器人ID列表 */
  private List<String> robotIdList = Lists.newArrayList();

 // private String rank;
  private String nickName;
  private String portrait;
  private String gender;

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
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
    this.starLevel = starLevel;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<String> getRobotIdList() {
    return robotIdList;
  }

  public void setRobotIdList(List<String> robotIdList) {
    this.robotIdList = robotIdList;
  }

}
