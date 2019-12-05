package com.xiaodou.mission.engine.model;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Data;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.UserCollectDataModel;
import com.xiaodou.mission.engine.event.BaseEvent;

/**
 * @name @see com.xiaodou.mission.engine.model.UserCollectDataInstance.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户数据实体
 * @version 1.0
 */
@Data
public class UserCollectDataInstance {

  /** 默认构造方法 */
  public UserCollectDataInstance() {}

  /**
   * 带参构造方法
   * 
   * @param model 用户数据模型
   */
  public UserCollectDataInstance(UserCollectDataModel model) {
    this.id = model.getId();
    this.userId = model.getUserId();
    this.targetUserId = model.getTargetUserId();
    this.module = model.getModule();
    this.pkTotalTimesForCaculate = model.getPkTotalTimesForCaculate();
    this.pkTotalTimesForStatistic = model.getPkTotalTimesForStatistic();
    this.pkWinTimes = model.getPkWinTimes();
    this.pkFailTimes = model.getPkFailTimes();
    this.notesCount = model.getNotesCount();
    this.hasNote = null != this.notesCount && this.notesCount > 0;
    this.tollgateCourseId = model.getTollgateCourseId();
    this.tollgateTotalTimes = model.getTollgateTotalTimes();
    if (StringUtils.isJsonNotBlank(model.getTollgateSet())) {
      this.tollgateSet =
          FastJsonUtil.fromJsons(model.getTollgateSet(), new TypeReference<Set<String>>() {});
    }

    this.tollgateOnestarCount = model.getTollgateOnestarCount();
    if (StringUtils.isJsonNotBlank(model.getTollgateOnestarSet())) {
      this.tollgateOnestarSet =
          FastJsonUtil
              .fromJsons(model.getTollgateOnestarSet(), new TypeReference<Set<String>>() {});
    }

    this.tollgateTwostarCount = model.getTollgateTwostarCount();
    if (StringUtils.isJsonNotBlank(model.getTollgateTwostarSet())) {
      this.tollgateTwostarSet =
          FastJsonUtil
              .fromJsons(model.getTollgateTwostarSet(), new TypeReference<Set<String>>() {});
    }

    this.tollgateThreestarCount = model.getTollgateThreestarCount();
    if (StringUtils.isJsonNotBlank(model.getTollgateThreestarSet())) {
      this.tollgateThreestarSet =
          FastJsonUtil.fromJsons(model.getTollgateThreestarSet(),
              new TypeReference<Set<String>>() {});
    }

    this.scoreTotal = model.getScoreTotal();
    this.scoreOneday = model.getScoreOneday();
    this.creditTotal = model.getCreditTotal();
    this.creditOneday = model.getCreditOneday();
    this.friendCountTotal = model.getFriendCountTotal();
    this.friendCountOneday = model.getFriendCountOneday();
    this.answerTotal = model.getAnswerTotal();
    this.answerOneday = model.getAnswerOneday();
    this.wrongQuesSolveTotal = model.getWrongQuesSolveTotal();
    this.wrongQuesSolveOneday = model.getWrongQuesSolveOneday();
    this.collectionTotal = model.getCollectionTotal();
    this.collectionOneday = model.getCollectionOneday();
    this.improveProtrait = model.getImproveProtrait();
    this.improveSign = model.getImproveSign();
    this.improveNickname = model.getImproveNickname();
    this.improveComplete =
        MissionConstant.TRUE == model.getImproveProtrait()
            || MissionConstant.TRUE == model.getImproveSign()
            || MissionConstant.TRUE == model.getImproveNickname();
    if (StringUtils.isJsonNotBlank(model.getBuyCourse())) {
      this.buyCourse =
          FastJsonUtil.fromJsons(model.getBuyCourse(), new TypeReference<Set<String>>() {});
    }
    this.followCountTotal = model.getFollowCountTotal();
    this.followCountOneday = model.getFollowCountOneday();
    this.followedCountTotal = model.getFollowedCountTotal();
    this.followedCountOneday = model.getFollowedCountOneday();
    this.activeTotalDays = model.getActiveTotalDays();
    this.activeContinuousDays = model.getActiveContinuousDays();
    this.lastUpdateTime = model.getLastUpdateTime();
    this.leakFillingStatus = Boolean.FALSE;

    this.pkInfo = new UserPkDataDetailInstance(model.getPkInfoWithBuild());
    this.tollgateInfo = new UserTollgateDataDetailInstance(model.getTollgateInfoWithBuild());
  }

  public UserCollectDataInstance(BaseEvent event) {
    this.module = event.getModule();
    this.userId = event.getUserId();
    this.targetUserId = event.getTargetUserId();
    this.tollgateCourseId = event.getCourseId();
  }

  /** id 主键ID */
  private String id;
  /** userId 用户ID */
  private String userId;
  /** targetUserId 目标用户ID */
  private String targetUserId;
  /** courseId 课程ID */
  private String courseId;
  /** module 所属模块 */
  private String module;
  /** pkTotalTimesForStatistic PK次数(统计用) */
  private Integer pkTotalTimesForStatistic = 0;
  /** pkTotalTimesForCaculate PK次数(计算用) */
  private Integer pkTotalTimesForCaculate = 0;
  /** pkWinTimes PK胜利次数 */
  private Integer pkWinTimes = 0;
  /** pkFailTimes PK失败次数 */
  private Integer pkFailTimes = 0;
  /** notesCount 添加笔记数量 */
  private Integer notesCount = 0;
  /** hasNote 添加过笔记 */
  private Boolean hasNote = Boolean.FALSE;
  /** tollgateCourseId 闯关课程ID */
  private String tollgateCourseId;
  /** tollgateTotalTimes 闯关总数 */
  private Integer tollgateTotalTimes = 0;
  /** tollgateList 已闯关集合 */
  private Set<String> tollgateSet = Sets.newHashSet();
  /** tollgateOnestarCount 一星闯关数量 */
  private Integer tollgateOnestarCount = 0;
  /** tollgateOnestarSet 一星闯关集合 */
  private Set<String> tollgateOnestarSet = Sets.newHashSet();
  /** tollgateTwostarCount 两星闯关数量 */
  private Integer tollgateTwostarCount = 0;
  /** tollgateTwostarSet 两星闯关集合 */
  private Set<String> tollgateTwostarSet = Sets.newHashSet();
  /** tollgateThreestarCount 三星闯关数量 */
  private Integer tollgateThreestarCount = 0;
  /** tollgateThreestarSet 三星闯关集合 */
  private Set<String> tollgateThreestarSet = Sets.newHashSet();
  /** scoreTotal 总分 */
  private Integer scoreTotal = 0;
  /** scoreOneday 单日得分 */
  private Integer scoreOneday = 0;
  /** creditTotal 总积分 */
  private Integer creditTotal = 0;
  /** creditOneday 单日积分 */
  private Integer creditOneday = 0;
  /** friendCountTotal 好友总数 */
  private Integer friendCountTotal = 0;
  /** friendCountOneday 单日加好友数 */
  private Integer friendCountOneday = 0;
  /** answerTotal 总答题数 */
  private Integer answerTotal = 0;
  /** answerOneday 单日答题数 */
  private Integer answerOneday = 0;
  /** wrongQuesSolveTotal 解决错题总数 */
  private Integer wrongQuesSolveTotal = 0;
  /** wrongQuesSolveOneday 单日解决错题数 */
  private Integer wrongQuesSolveOneday = 0;
  /** collectionTotal 收藏题总数 */
  private Integer collectionTotal = 0;
  /** collectionOneday 单日收藏题数 */
  private Integer collectionOneday = 0;
  /** improveProtrait 完善头像 */
  private Integer improveProtrait = 0;
  /** improveSign 完善签名 */
  private Integer improveSign = 0;
  /** improveNickname 完善昵称 */
  private Integer improveNickname = 0;
  /** improveComplete 已完成完善个人信息 */
  private Boolean improveComplete = Boolean.FALSE;
  /** buyCourse 购买课程 */
  private Set<String> buyCourse = Sets.newHashSet();
  /** followCountTotal 关注总数 */
  private Integer followCountTotal = 0;
  /** followCountOneday 单日关注数 */
  private Integer followCountOneday = 0;
  /** followedCountTotal 被关注总数 */
  private Integer followedCountTotal = 0;
  /** followedCountOneday 单日被关注数 */
  private Integer followedCountOneday = 0;
  /** activeTotalDays 总活跃天数 */
  private Integer activeTotalDays = 0;
  /** activeContinuousDays 连续活跃天数 */
  private Integer activeContinuousDays = 0;
  /** lastUpdateTime 最近更新时间 */
  private Timestamp lastUpdateTime;
  /** leakFillingStatus 查漏补缺状态 */
  private Boolean leakFillingStatus;
  /** pkInfo 用户PK信息 */
  private UserPkDataDetailInstance pkInfo;
  /** tollgateInfo 用户挑战信息 */
  private UserTollgateDataDetailInstance tollgateInfo;

}
