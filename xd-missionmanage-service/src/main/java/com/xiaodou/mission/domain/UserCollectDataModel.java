package com.xiaodou.mission.domain;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;
import com.xiaodou.mission.engine.model.UserPkDataDetailInstance;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;

/**
 * @name @see com.xiaodou.mission.domain.UserCollectDataModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户数据模型
 * @version 1.0
 */
@Data
public class UserCollectDataModel {

  /** 默认构造方法 */
  public UserCollectDataModel() {}

  /**
   * 带参构造方法
   * 
   * @param instance 用户数据模型实体
   */
  public UserCollectDataModel(UserCollectDataInstance instance) {
    if (StringUtils.isNotBlank(instance.getId())) {
      this.id = instance.getId();
    }
    this.userId = instance.getUserId();
    this.targetUserId = instance.getTargetUserId();
    this.module = instance.getModule();
    this.pkTotalTimesForCaculate = instance.getPkTotalTimesForCaculate();
    this.pkTotalTimesForStatistic = instance.getPkTotalTimesForStatistic();
    this.pkWinTimes = instance.getPkWinTimes();
    this.pkFailTimes = instance.getPkFailTimes();
    this.notesCount = instance.getNotesCount();
    this.tollgateCourseId = instance.getTollgateCourseId();
    this.tollgateTotalTimes = instance.getTollgateTotalTimes();
    if (null != instance.getTollgateSet()) {
      this.tollgateSet = FastJsonUtil.toJson(instance.getTollgateSet());
    }

    this.tollgateOnestarCount = instance.getTollgateOnestarCount();
    if (null != instance.getTollgateOnestarSet()) {
      this.tollgateOnestarSet = FastJsonUtil.toJson(instance.getTollgateOnestarSet());
    }

    this.tollgateTwostarCount = instance.getTollgateTwostarCount();
    if (null != instance.getTollgateTwostarSet()) {
      this.tollgateTwostarSet = FastJsonUtil.toJson(instance.getTollgateTwostarSet());
    }

    this.tollgateThreestarCount = instance.getTollgateThreestarCount();
    if (null != instance.getTollgateThreestarSet()) {
      this.tollgateThreestarSet = FastJsonUtil.toJson(instance.getTollgateThreestarSet());
    }

    this.scoreTotal = instance.getScoreTotal();
    this.scoreOneday = instance.getScoreOneday();
    this.creditTotal = instance.getCreditTotal();
    this.creditOneday = instance.getCreditOneday();
    this.friendCountTotal = instance.getFriendCountTotal();
    this.friendCountOneday = instance.getFriendCountOneday();
    this.answerTotal = instance.getAnswerTotal();
    this.answerOneday = instance.getAnswerOneday();
    this.wrongQuesSolveTotal = instance.getWrongQuesSolveTotal();
    this.wrongQuesSolveOneday = instance.getWrongQuesSolveOneday();
    this.collectionTotal = instance.getCollectionTotal();
    this.collectionOneday = instance.getCollectionOneday();
    this.improveProtrait = instance.getImproveProtrait();
    this.improveSign = instance.getImproveSign();
    this.improveNickname = instance.getImproveNickname();
    this.buyCourse = FastJsonUtil.toJson(instance.getBuyCourse());
    this.followCountTotal = instance.getFollowCountTotal();
    this.followCountOneday = instance.getFollowCountOneday();
    this.followedCountTotal = instance.getFollowedCountTotal();
    this.followedCountOneday = instance.getFollowedCountOneday();
    this.activeTotalDays = instance.getActiveTotalDays();
    this.activeContinuousDays = instance.getActiveContinuousDays();
    this.lastUpdateTime = instance.getLastUpdateTime();

    if (null != instance.getPkInfo()) {
      this.pkInfo = new UserCollectDataDetailModel(instance.getPkInfo());
    }
    if (null != instance.getTollgateInfo()) {
      this.tollgateInfo = new UserCollectDataDetailModel(instance.getTollgateInfo());
    }
  }

  /** id 主键ID */
  private String id;
  /** userId 用户ID */
  private String userId;
  /** targetUserId 目标用户ID */
  private String targetUserId;
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
  /** tollgateCourseId 闯关课程ID */
  private String tollgateCourseId;
  /** tollgateTotalTimes 闯关总数 */
  private Integer tollgateTotalTimes = 0;
  /** tollgateSet 已闯关集合 */
  private String tollgateSet;
  /** tollgateOnestarCount 一星闯关数量 */
  private Integer tollgateOnestarCount = 0;
  /** tollgateOnestarSet 一星闯关集合 */
  private String tollgateOnestarSet;
  /** tollgateTwostarCount 两星闯关数量 */
  private Integer tollgateTwostarCount = 0;
  /** tollgateTwostarSet 两星闯关集合 */
  private String tollgateTwostarSet;
  /** tollgateThreestarCount 三星闯关数量 */
  private Integer tollgateThreestarCount = 0;
  /** tollgateThreestarSet 三星闯关集合 */
  private String tollgateThreestarSet;
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
  /** buyCourse 购买课程 */
  private String buyCourse;
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
  private Timestamp lastUpdateTime = new Timestamp(System.currentTimeMillis());

  /** pkInfo PK信息 */
  private UserCollectDataDetailModel pkInfo;
  /** tollgateInfo 闯关信息 */
  private UserCollectDataDetailModel tollgateInfo;

  public UserCollectDataDetailModel getPkInfoWithBuild() {
    if (StringUtils.isNotBlank(targetUserId) && null == pkInfo) {
      pkInfo = new UserCollectDataDetailModel(new UserPkDataDetailInstance());
      pkInfo.setUserId(userId);
      pkInfo.setDataType(MissionConstant.USER_DATA_TYPE_PK);
      pkInfo.setModule(module);
      pkInfo.setForeignId(targetUserId);
      pkInfo.setMainId(id);
    }
    return pkInfo;
  }

  public UserCollectDataDetailModel getTollgateInfoWithBuild() {
    if (StringUtils.isNotBlank(tollgateCourseId) && null == tollgateInfo) {
      tollgateInfo = new UserCollectDataDetailModel(new UserTollgateDataDetailInstance());
      tollgateInfo.setUserId(userId);
      tollgateInfo.setDataType(MissionConstant.USER_DATA_TYPE_TOLLGATE);
      tollgateInfo.setModule(module);
      tollgateInfo.setForeignId(tollgateCourseId);
      tollgateInfo.setMainId(id);
    }
    return tollgateInfo;
  }

  public <T extends BaseEvent> void reset(boolean continueActive, Class<T> event) {
    this.activeTotalDays += 1;
    if (continueActive) {
      this.activeContinuousDays += 1;
    } else {
      this.activeContinuousDays = 0;
    }
    this.answerOneday = 0;
    this.collectionOneday = 0;
    this.collectionOneday = 0;
    this.creditOneday = 0;
    this.followCountOneday = 0;
    this.followedCountOneday = 0;
    this.friendCountOneday = 0;
    this.scoreOneday = 0;
    this.wrongQuesSolveOneday = 0;
    if (!event.equals(BeFollowedEvent.class)) {
      this.lastUpdateTime = new Timestamp(System.currentTimeMillis());
    }
  }

}
