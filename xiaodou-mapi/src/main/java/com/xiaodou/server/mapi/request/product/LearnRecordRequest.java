package com.xiaodou.server.mapi.request.product;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

/**
 * 
 * 51xiaodou.com (c) 2016 by 李德洪
 * 
 * @name LearnRecordRequest
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */
public class LearnRecordRequest extends MapiBaseRequest {
  private Integer learnTime;// 学习时长(秒为单位)
  private String courseId; // 课程ID
  private String chapterId;// 章ID
  private String itemId;// 节ID
  private String learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题,51每日一练,61期末测试,101 / 102, #视频学习时长 / 音频学习时长）
  /* 用不到这个入参 */
  private String learnScope;// 范围（courseId,chapterId,itemId的非空组合范围）
  private String points;


  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getLearnType() {
    return learnType;
  }

  public void setLearnType(String learnType) {
    this.learnType = learnType;
  }

  public String getLearnScope() {
    return learnScope;
  }

  public void setLearnScope(String learnScope) {
    this.learnScope = learnScope;
  }

  public String getPoints() {
    return points;
  }

  public void setPoints(String points) {
    this.points = points;
  }

}
