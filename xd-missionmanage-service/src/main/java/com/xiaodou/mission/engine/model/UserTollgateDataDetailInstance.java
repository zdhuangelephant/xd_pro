package com.xiaodou.mission.engine.model;

import java.util.Set;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.domain.UserCollectDataDetailModel;

/**
 * @name @see com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户闯关数据明细实体
 * @version 1.0
 */
public class UserTollgateDataDetailInstance {

  /** 默认构造方法 */
  public UserTollgateDataDetailInstance() {}

  /**
   * 带参构造方法
   * 
   * @param model 用户数据明细模型
   */
  public UserTollgateDataDetailInstance(UserCollectDataDetailModel model) {
    if (null == model) {
      return;
    }
    this.id = model.getId();
    this.module = model.getModule();
    this.userId = model.getUserId();
    this.mainId = model.getMainId();
    this.courseId = model.getForeignId();
    if (null != model && StringUtils.isJsonNotBlank(model.getDataDetail())) {
      UserTollgateDataDetailInstance instance =
          FastJsonUtil.fromJson(model.getDataDetail(), UserTollgateDataDetailInstance.class);
      this.tollgateTotalTimesSamecourse = instance.tollgateTotalTimesSamecourse;
      this.tollgateSetSamecourse = instance.tollgateSetSamecourse;
      this.tollgateOnestarCountSamecourse = instance.tollgateOnestarCountSamecourse;
      this.tollgateOnestarSetSamecourse = instance.tollgateOnestarSetSamecourse;
      this.tollgateTwostarCountSamecourse = instance.tollgateTwostarCountSamecourse;
      this.tollgateTwostarSetSamecourse = instance.tollgateTwostarSetSamecourse;
      this.tollgateThreestarCountSamecourse = instance.tollgateThreestarCountSamecourse;
      this.tollgateThreestarSetSamecourse = instance.tollgateThreestarSetSamecourse;
      this.wrongQuesNum = instance.wrongQuesNum;
      this.lastestTollgateChapterId = instance.lastestTollgateChapterId;
      this.lastestTollgateItemId = instance.lastestTollgateItemId;
      this.dailyPracticeTime = instance.dailyPracticeTime;
      this.continDailyPractice = instance.continDailyPractice;
      this.totalDailyPractice = instance.totalDailyPractice;
    }
  }

  /** id 主键 */
  private String id;
  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** mainId 数据主表主键 */
  private String mainId;
  /** foreignId 核心外键 */
  private String courseId;
  /** tollgateTotalTimesSamecourse 同一门课程的闯关数 */
  private Integer tollgateTotalTimesSamecourse = 0;
  /** tollgateSetSamecourse 同一门课程已闯关集合 */
  private Set<String> tollgateSetSamecourse = Sets.newHashSet();

  /** dailyPracticeTime 最近一次完成每日一练日期 */
  private Long dailyPracticeTime;
  /** continDailyPractice 每日一练连续练习天数 */
  private Integer continDailyPractice = 0;
  /** totalDailyPractice 每日一练练习总天数 */
  private Integer totalDailyPractice = 0;
  /** lastestTollgateChapterId 最新闯关章ID */
  private String lastestTollgateChapterId;
  /** lastestTollgateItemId 最新闯关节ID */
  private String lastestTollgateItemId;
  /** tollgateOnestarCount 一星闯关数量 */
  private Integer tollgateOnestarCountSamecourse = 0;
  /** tollgateOnestarSet 一星闯关集合 */
  private Set<String> tollgateOnestarSetSamecourse = Sets.newHashSet();
  /** tollgateTwostarCount 两星闯关数量 */
  private Integer tollgateTwostarCountSamecourse = 0;
  /** tollgateTwostarSet 两星闯关集合 */
  private Set<String> tollgateTwostarSetSamecourse = Sets.newHashSet();
  /** tollgateThreestarCount 三星闯关数量 */
  private Integer tollgateThreestarCountSamecourse = 0;
  /** tollgateThreestarSet 三星闯关集合 */
  private Set<String> tollgateThreestarSetSamecourse = Sets.newHashSet();
  /** wrongQuesNum 错题数量 */
  private Integer wrongQuesNum = 0;

  public String getId() {
    return id;
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

  public String getMainId() {
    return mainId;
  }

  public void setMainId(String mainId) {
    this.mainId = mainId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public Integer getTollgateTotalTimesSamecourse() {
    return tollgateTotalTimesSamecourse;
  }

  public void setTollgateTotalTimesSamecourse(Integer tollgateTotalTimesSamecourse) {
    this.tollgateTotalTimesSamecourse = tollgateTotalTimesSamecourse;
  }

  public Set<String> getTollgateSetSamecourse() {
    return tollgateSetSamecourse;
  }

  public void setTollgateSetSamecourse(Set<String> tollgateSetSamecourse) {
    this.tollgateSetSamecourse = tollgateSetSamecourse;
  }

  public Integer getTollgateOnestarCountSamecourse() {
    return tollgateOnestarCountSamecourse;
  }

  public void setTollgateOnestarCountSamecourse(Integer tollgateOnestarCountSamecourse) {
    this.tollgateOnestarCountSamecourse = tollgateOnestarCountSamecourse;
  }

  public Set<String> getTollgateOnestarSetSamecourse() {
    return tollgateOnestarSetSamecourse;
  }

  public void setTollgateOnestarSetSamecourse(Set<String> tollgateOnestarSetSamecourse) {
    this.tollgateOnestarSetSamecourse = tollgateOnestarSetSamecourse;
  }

  public Integer getTollgateTwostarCountSamecourse() {
    return tollgateTwostarCountSamecourse;
  }

  public void setTollgateTwostarCountSamecourse(Integer tollgateTwostarCountSamecourse) {
    this.tollgateTwostarCountSamecourse = tollgateTwostarCountSamecourse;
  }

  public Set<String> getTollgateTwostarSetSamecourse() {
    return tollgateTwostarSetSamecourse;
  }

  public void setTollgateTwostarSetSamecourse(Set<String> tollgateTwostarSetSamecourse) {
    this.tollgateTwostarSetSamecourse = tollgateTwostarSetSamecourse;
  }

  public Integer getTollgateThreestarCountSamecourse() {
    return tollgateThreestarCountSamecourse;
  }

  public void setTollgateThreestarCountSamecourse(Integer tollgateThreestarCountSamecourse) {
    this.tollgateThreestarCountSamecourse = tollgateThreestarCountSamecourse;
  }

  public Set<String> getTollgateThreestarSetSamecourse() {
    return tollgateThreestarSetSamecourse;
  }

  public void setTollgateThreestarSetSamecourse(Set<String> tollgateThreestarSetSamecourse) {
    this.tollgateThreestarSetSamecourse = tollgateThreestarSetSamecourse;
  }

  public Integer getWrongQuesNum() {
    return wrongQuesNum;
  }

  public void setWrongQuesNum(Integer wrongQuesNum) {
    this.wrongQuesNum = wrongQuesNum;
  }

  public String getLastestTollgateChapterId() {
    return lastestTollgateChapterId;
  }

  public void setLastestTollgateChapterId(String lastestTollgateChapterId) {
    this.lastestTollgateChapterId = lastestTollgateChapterId;
  }

  public String getLastestTollgateItemId() {
    return lastestTollgateItemId;
  }

  public void setLastestTollgateItemId(String lastestTollgateItemId) {
    this.lastestTollgateItemId = lastestTollgateItemId;
  }

  public Long getDailyPracticeTime() {
    return dailyPracticeTime;
  }

  public void setDailyPracticeTime(Long dailyPracticeTime) {
    this.dailyPracticeTime = dailyPracticeTime;
  }

  public Integer getContinDailyPractice() {
    return continDailyPractice;
  }

  public void setContinDailyPractice(Integer continDailyPractice) {
    this.continDailyPractice = continDailyPractice;
  }

  public Integer getTotalDailyPractice() {
    return totalDailyPractice;
  }

  public void setTotalDailyPractice(Integer totalDailyPractice) {
    this.totalDailyPractice = totalDailyPractice;
  }

}
