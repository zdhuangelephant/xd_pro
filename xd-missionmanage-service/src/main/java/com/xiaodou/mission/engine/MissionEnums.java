package com.xiaodou.mission.engine;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.AddNoteEvent;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.event.BuyCourseEvent;
import com.xiaodou.mission.engine.event.CollectionEvent;
import com.xiaodou.mission.engine.event.DailyPracticeEvent;
import com.xiaodou.mission.engine.event.EliminateWrongQuesEvent;
import com.xiaodou.mission.engine.event.FollowEvent;
import com.xiaodou.mission.engine.event.FriendAddEvent;
import com.xiaodou.mission.engine.event.FriendPkEvent;
import com.xiaodou.mission.engine.event.ImproveInfoEvent;
import com.xiaodou.mission.engine.event.LeakFillingEvent;
import com.xiaodou.mission.engine.event.RandomPkEvent;
import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.mission.engine.event.WrongQuesCountEvent;
import com.xiaodou.mission.engine.listener.IEventListener;
import com.xiaodou.mission.engine.listener.impl.ActiveContinuousDaysListener;
import com.xiaodou.mission.engine.listener.impl.ActiveTotalDaysListener;
import com.xiaodou.mission.engine.listener.impl.AnswerOnedayListener;
import com.xiaodou.mission.engine.listener.impl.AnswerTotalListener;
import com.xiaodou.mission.engine.listener.impl.CollectionOnedayListener;
import com.xiaodou.mission.engine.listener.impl.CollectionTotalListener;
import com.xiaodou.mission.engine.listener.impl.CompleteFriendPkListener;
import com.xiaodou.mission.engine.listener.impl.CompleteRandomPkListener;
import com.xiaodou.mission.engine.listener.impl.CreditOnedayListener;
import com.xiaodou.mission.engine.listener.impl.CreditTotalListener;
import com.xiaodou.mission.engine.listener.impl.DailyPracticeCompleteListener;
import com.xiaodou.mission.engine.listener.impl.DailyPracticeContinListener;
import com.xiaodou.mission.engine.listener.impl.DailyPracticeTotalListener;
import com.xiaodou.mission.engine.listener.impl.FollowCountOnedayListener;
import com.xiaodou.mission.engine.listener.impl.FollowCountTotalListener;
import com.xiaodou.mission.engine.listener.impl.FollowedCountOnedayListener;
import com.xiaodou.mission.engine.listener.impl.FollowedCountTotalListener;
import com.xiaodou.mission.engine.listener.impl.FriendCountOnedayListener;
import com.xiaodou.mission.engine.listener.impl.FriendCountTotalListener;
import com.xiaodou.mission.engine.listener.impl.InfoBuyCourseCountListener;
import com.xiaodou.mission.engine.listener.impl.InfoBuyCourseListener;
import com.xiaodou.mission.engine.listener.impl.InfoImproveListener;
import com.xiaodou.mission.engine.listener.impl.LeakFillingCompleteListener;
import com.xiaodou.mission.engine.listener.impl.NotesCountListener;
import com.xiaodou.mission.engine.listener.impl.PkFailPercentListener;
import com.xiaodou.mission.engine.listener.impl.PkFailPercentSamepersonListener;
import com.xiaodou.mission.engine.listener.impl.PkFailTimesListener;
import com.xiaodou.mission.engine.listener.impl.PkFailTimesSamepersonListener;
import com.xiaodou.mission.engine.listener.impl.PkTotalTimesListener;
import com.xiaodou.mission.engine.listener.impl.PkTotalTimesSamepersonListener;
import com.xiaodou.mission.engine.listener.impl.PkWinPercentListener;
import com.xiaodou.mission.engine.listener.impl.PkWinPercentSamepersonListener;
import com.xiaodou.mission.engine.listener.impl.PkWinTimesListener;
import com.xiaodou.mission.engine.listener.impl.PkWinTimesSamepersonListener;
import com.xiaodou.mission.engine.listener.impl.ScoreOnedayListener;
import com.xiaodou.mission.engine.listener.impl.ScoreTotalListener;
import com.xiaodou.mission.engine.listener.impl.TollgateCompleteDesignatedListener;
import com.xiaodou.mission.engine.listener.impl.TollgateDesignatedListener;
import com.xiaodou.mission.engine.listener.impl.TollgateOnestarListener;
import com.xiaodou.mission.engine.listener.impl.TollgateOnestarSamecourseListener;
import com.xiaodou.mission.engine.listener.impl.TollgateThreestarListener;
import com.xiaodou.mission.engine.listener.impl.TollgateThreestarSamecourseListener;
import com.xiaodou.mission.engine.listener.impl.TollgateTotalTimesListener;
import com.xiaodou.mission.engine.listener.impl.TollgateTotalTimesSamecourseListener;
import com.xiaodou.mission.engine.listener.impl.TollgateTwostarListener;
import com.xiaodou.mission.engine.listener.impl.TollgateTwostarSamecourseListener;
import com.xiaodou.mission.engine.listener.impl.WrongquesSolveAllListener;
import com.xiaodou.mission.engine.listener.impl.WrongquesSolveOnedayListener;
import com.xiaodou.mission.engine.listener.impl.WrongquesSolveTotalListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.server.mapi.enums.MissionEnums.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月30日
 * @description 枚举类
 * @version 1.0
 */
public class MissionEnums {

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T extends Enum> T getEnumValue(Class<T> clazz, String name) {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    return (T) Enum.valueOf(clazz, name);
  }

  public static List<MissionCondition> getMissionConditionListByType(MissionConditionType type) {
    if (null == type) {
      return null;
    }
    return MissionCondition.MISSION_CONDITION_MAP.get(type.toString());
  }

  /**
   * @name @see com.xiaodou.mission.engine.MissionPreCondition.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年2月16日
   * @description 任务前置条件类型枚举
   * @version 1.0
   */
  public enum MissionPreCondition {
    /** have_wrongques 有错题 */
    have_wrongques {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        Integer wrongQuesNum = userData.getTollgateInfo().getWrongQuesNum();
        return Integer.parseInt(threshold) <= wrongQuesNum;
      }
    },
    /** have_tollgate 有余关 */
    have_tollgate {
      @Override
      public boolean compare(Context context, String threshold) {
        return true;
      }
    },
    /** uncomplete_info 未完善信息 */
    uncomplete_info {
      @Override
      public boolean compare(Context context, String threshold) {
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return !userData.getImproveComplete();
      }
    },
    /** unmark_note 未添加笔记 */
    unmark_note {
      @Override
      public boolean compare(Context context, String threshold) {
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return !userData.getHasNote();
      }
    },
    /** hava_friend 有好友 */
    have_friend {
      @Override
      public boolean compare(Context context, String threshold) {
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return null != userData.getFriendCountTotal() && userData.getFriendCountTotal() > 0;
      }
    },
    /** score_greater_than 积分大于指定值 */
    score_greater_than {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return userData.getCreditTotal() > Integer.parseInt(threshold);
      }
    },
    /** complete_pre_chapter 完成了一课的学习 */
    complete_pre_course {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()
            || StringUtils.isBlank(context.getCurrentThreshold())
            || !context.getCurrentThreshold().equals(threshold)) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return userData.getTollgateOnestarSet().contains(threshold)
            || userData.getTollgateTwostarSet().contains(threshold)
            || userData.getTollgateThreestarSet().contains(threshold);
      }
    },
    /** unperfect_pre_course 不完美的完成了一课的学习 */
    unperfect_pre_course {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()
            || StringUtils.isBlank(context.getCurrentThreshold())
            || !context.getCurrentThreshold().equals(threshold)) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return (userData.getTollgateOnestarSet().contains(threshold) || userData
            .getTollgateTwostarSet().contains(threshold))
            && !userData.getTollgateThreestarSet().contains(threshold);
      }
    },
    /** perfect_pre_course 完美的完成了一课的学习 */
    perfect_pre_course {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()
            || StringUtils.isBlank(context.getCurrentThreshold())
            || !context.getCurrentThreshold().equals(threshold)) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return userData.getTollgateThreestarSet().contains(threshold);
      }
    },
    /** complete_pre_chapter 完成了一章的学习 */
    complete_pre_chapter {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()
            || StringUtils.isBlank(context.getCurrentThreshold())
            || !context.getCurrentThreshold().equals(threshold)) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return userData.getTollgateOnestarSet().contains(threshold)
            || userData.getTollgateTwostarSet().contains(threshold)
            || userData.getTollgateThreestarSet().contains(threshold);
      }
    },
    /** complete_pre_item 完成了一节的学习 */
    complete_pre_item {
      @Override
      public boolean compare(Context context, String threshold) {
        if (StringUtils.isBlank(threshold)) {
          return true;
        }
        if (null == context || null == context.getCoreParam()
            || StringUtils.isBlank(context.getCurrentThreshold())
            || !context.getCurrentThreshold().equals(threshold)) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return userData.getTollgateOnestarSet().contains(threshold)
            || userData.getTollgateTwostarSet().contains(threshold)
            || userData.getTollgateThreestarSet().contains(threshold);
      }
    },
    /** every_daily_practice 每日触发 */
    every_daily_practice {
      @Override
      public boolean compare(Context context, String threshold) {
        if (null == context || null == context.getCoreParam()) {
          return false;
        }
        UserCollectDataInstance userData = context.getCoreParam();
        return null == userData.getTollgateInfo()
            || null == userData.getTollgateInfo().getDailyPracticeTime()
            || new Timestamp(userData.getTollgateInfo().getDailyPracticeTime())
                .before(new Timestamp(System.currentTimeMillis()));
      }
    };

    public abstract boolean compare(Context context, String threshold);
  }

  /**
   * @name @see com.xiaodou.server.mapi.enums.MissionType.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年1月30日
   * @description 类型
   * @version 1.0
   */
  public enum MissionType {
    /** task 任务 */
    task("任务", "恭喜您完成了任务%s。"),
    /** archieve 成就 */
    archieve("成就", "恭喜您达成了成就%s。");
    MissionType(String desc, String info) {
      this.desc = desc;
      this.info = info;
    }

    /** desc 显示信息 */
    private String desc;
    private String info;

    public String getInfo() {
      return info;
    }

    public void setInfo(String info) {
      this.info = info;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

  }

  /**
   * @name @see com.xiaodou.mission.engine.MissionEnums.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年5月16日
   * @description 任务类型明细
   * @version 1.0
   */
  public enum TaskType {
    /** standard 标准 */
    standard(MissionConstant.RECORD_STATUS_AUTO) {
      @Override
      public boolean checkPreCond(IEventListener listener, Context context) {
        return true;
      }
    },
    /** dynamic 动态 */
    dynamic(MissionConstant.RECORD_STATUS_NORMAL) {
      @Override
      public boolean checkPreCond(IEventListener listener, Context context) {
        return listener.getMissionInstance().matchPreCond(context);
      }
    },
    /** statics 静态 */
    statics(MissionConstant.RECORD_STATUS_NORMAL) {
      @Override
      public boolean checkPreCond(IEventListener listener, Context context) {
        return true;
      }
    };
    /**
     * 构造方法
     * 
     * @param recordStatus 记录状态
     */
    TaskType(Integer recordStatus) {
      this.recordStatus = recordStatus;
    }

    /** recordStatus 记录状态 */
    private Integer recordStatus;

    public Integer getRecordStatus() {
      return recordStatus;
    }

    /**
     * 查看是否匹配前置条件
     * 
     * @param listener 事件监听器
     * @param context 事件执行上下文
     * @return 匹配结果
     */
    public abstract boolean checkPreCond(IEventListener listener, Context context);
  }

  /**
   * @name @see com.xiaodou.mission.engine.MissionEnums.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年5月18日
   * @description 任务跳转类型
   * @version 1.0
   */
  public enum MissionJumpType {
    /** tollgate 闯关 */
    tollgate,
    /** dailyPractice 每日练习 */
    dailyPractice,
    /** leakFilling 查漏补缺 */
    leakFilling,
    /** randomPk 随机PK */
    randomPk,
    /** friendPk 好友PK */
    friendPk,
    /** improveInfo 完善信息 */
    improveInfo,
    /** wrongQues 消灭错题 */
    wrongQues,
    /** buyCourse 购买课程 */
    buyCourse,
    /** addNote 添加笔记 */
    addNote
  }

  /**
   * @name @see com.xiaodou.mission.engine.enums.MissionConditionType.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年2月1日
   * @description 任务条件类型枚举
   * @version 1.0
   */
  public enum MissionConditionType {
    /** pk 挑战 */
    pk("挑战", MissionPreCondition.score_greater_than, null),
    /** randomPk 随机挑战 */
    randomPk("随机挑战", MissionPreCondition.complete_pre_chapter, MissionJumpType.randomPk),
    /** friendPk 好友挑战 */
    friendPk("好友挑战", MissionPreCondition.score_greater_than, MissionJumpType.friendPk),
    /** tollgate 闯关 */
    tollgate("闯关", MissionPreCondition.complete_pre_item, MissionJumpType.tollgate),
    /** dailyPractice 每日一练 */
    dailyPractice("每日一练", MissionPreCondition.every_daily_practice, MissionJumpType.dailyPractice),
    /** leakFilling 查漏补缺 */
    leakFilling("查漏补缺", MissionPreCondition.complete_pre_course, MissionJumpType.leakFilling),
    /** score 积分 */
    score("得分", null, null),
    /** credit 积分 */
    credit("积分", null, null),
    /** friend 好友 */
    friend("好友", null, null),
    /** answer 答题 */
    answer("答题", null, null),
    /** info 信息 */
    info("信息", MissionPreCondition.uncomplete_info, MissionJumpType.improveInfo),
    /** wrongques 错题 */
    wrongques("错题", MissionPreCondition.have_wrongques, MissionJumpType.wrongQues),
    /** collection 收藏 */
    collection("收藏", null, null),
    /** follow 关注 */
    follow("关注", null, null),
    /** followed 被关注 */
    followed("被关注", null, null),
    /** addNote 添加笔记 */
    addNote("添加笔记", null, MissionJumpType.addNote),
    /** active 活跃 */
    active("活跃", null, null);
    MissionConditionType(String desc, MissionPreCondition preCondition, MissionJumpType jumpType) {
      this.desc = desc;
      this.preCondition = preCondition;
      this.jumpType = jumpType;
    }

    /** desc 显示信息 */
    private String desc;

    /** preCondition 前置条件 */
    private MissionPreCondition preCondition;

    private MissionJumpType jumpType;

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public MissionPreCondition getPreCondition() {
      return preCondition;
    }

    public void setPreCondition(MissionPreCondition preCondition) {
      this.preCondition = preCondition;
    }

    public MissionJumpType getJumpType() {
      return jumpType;
    }

    public void setJumpType(MissionJumpType jumpType) {
      this.jumpType = jumpType;
    }

  }

  /**
   * @name @see com.xiaodou.mission.engine.enums.MissionCondition.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年2月1日
   * @description 任务条件枚举
   * @version 1.0
   */
  @SuppressWarnings("unchecked")
  public enum MissionCondition {
    /** pk_total_times pk总次数 */
    pk_total_times("pk总次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkTotalTimesListener.class),
    /** pk_total_times_sameperson 对同一个人pk次数 */
    pk_total_times_sameperson("对同一个人pk次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkTotalTimesSamepersonListener.class),
    /** pk_win_times pk胜利次数 */
    pk_win_times("pk胜利次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkWinTimesListener.class),
    /** pk_win_times_sameperson 对同一个人pk胜利次数 */
    pk_win_times_sameperson("对同一个人pk胜利次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkWinTimesSamepersonListener.class),
    /** pk_win_percent pk胜率 */
    pk_win_percent("pk胜率", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkWinPercentListener.class),
    /** pk_win_percent_sameperson 对同一个人pk胜率 */
    pk_win_percent_sameperson("对同一个人pk胜率", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkWinPercentSamepersonListener.class),
    /** pk_fail_times pk失败次数 */
    pk_fail_times("pk失败次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkFailTimesListener.class),
    /** pk_fail_times_sameperson 对同一个人pk失败次数 */
    pk_fail_times_sameperson("对同一个人pk失败次数", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkFailTimesSamepersonListener.class),
    /** pk_fail_percent pk失败率 */
    pk_fail_percent("pk失败率", new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkFailPercentListener.class),
    /** pk_fail_percent_sameperson 对同一个人pk失败率 */
    pk_fail_percent_sameperson("对同一个人pk失败率",
        new Class[] {FriendPkEvent.class, RandomPkEvent.class},
        PkFailPercentSamepersonListener.class),
    /** daily_practice_complete 完成每日一练 */
    daily_practice_complete("完成每日一练", new Class[] {DailyPracticeEvent.class},
        DailyPracticeCompleteListener.class),
    /** daily_practice_total 完成每日一练总天数 */
    daily_practice_total("完成每日一练总天数", new Class[] {DailyPracticeEvent.class},
        DailyPracticeTotalListener.class),
    /** daily_practice_contin 连续完成每日一练天数 */
    daily_practice_contin("连续完成每日一练天数", new Class[] {DailyPracticeEvent.class},
        DailyPracticeContinListener.class),
    /** leak_filling_complete 完成查漏补缺 */
    leak_filling_complete("完成查漏补缺", new Class[] {TollgateEvent.class, LeakFillingEvent.class},
        LeakFillingCompleteListener.class),
    /** compelte_random_pk 发起一次随机PK挑战并取得胜利 */
    complete_random_pk("发起一次随机PK挑战并取得胜利", new Class[] {TollgateEvent.class, RandomPkEvent.class},
        CompleteRandomPkListener.class),
    /** compelte_friend_pk 发起一次好友PK挑战并取得胜利 */
    complete_friend_pk("发起一次好友PK挑战并取得胜利", new Class[] {TollgateEvent.class, FriendPkEvent.class},
        CompleteFriendPkListener.class),
    /** notes_count 添加笔记数量 */
    notes_count("添加笔记数量", new Class[] {AddNoteEvent.class}, NotesCountListener.class),
    /** tollgate_total_times 闯关总数 */
    tollgate_total_times("闯关总数", new Class[] {TollgateEvent.class},
        TollgateTotalTimesListener.class),
    /** tollgate_total_times_samecourse 同一课程闯关总数 */
    tollgate_total_times_samecourse("同一课程闯关总数", new Class[] {TollgateEvent.class},
        TollgateTotalTimesSamecourseListener.class),
    /** tollgate_designated 指定关卡 */
    tollgate_designated("指定关卡", new Class[] {TollgateEvent.class}, TollgateDesignatedListener.class),
    /** tollgate_complete_designated 完成指定关卡 */
    tollgate_complete_designated("完成指定关卡", new Class[] {TollgateEvent.class},
        TollgateCompleteDesignatedListener.class),
    /** tollgate_onestar 一星关卡总数 */
    tollgate_onestar("一星关卡总数", new Class[] {TollgateEvent.class}, TollgateOnestarListener.class),
    /** tollgate_onestar_samecourse 同一课程一星关卡总数 */
    tollgate_onestar_samecourse("同一课程一星关卡总数", new Class[] {TollgateEvent.class},
        TollgateOnestarSamecourseListener.class),
    /** tollgate_twostar 二星关卡总数 */
    tollgate_twostar("二星关卡总数", new Class[] {TollgateEvent.class}, TollgateTwostarListener.class),
    /** tollgate_twostar_samecourse 同一课程二星关卡总数 */
    tollgate_twostar_samecourse("同一课程二星关卡总数", new Class[] {TollgateEvent.class},
        TollgateTwostarSamecourseListener.class),
    /** tollgate_threestar 三星关卡总数 */
    tollgate_threestar("三星关卡总数", new Class[] {TollgateEvent.class}, TollgateThreestarListener.class),
    /** tollgate_threestar_samecourse 同一课程三星关卡总数 */
    tollgate_threestar_samecourse("同一课程三星关卡总数", new Class[] {TollgateEvent.class},
        TollgateThreestarSamecourseListener.class),
    /** score_total 总得分 */
    score_total("总得分", new Class[] {FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        ScoreTotalListener.class),
    /** score_oneday 一天得分 */
    score_oneday("一天得分",
        new Class[] {FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        ScoreOnedayListener.class),
    /** credit_total 总积分 */
    credit_total("总积分",
        new Class[] {FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        CreditTotalListener.class),
    /** credit_oneday 一天获得积分 */
    credit_oneday("一天获得积分", new Class[] {FriendPkEvent.class, RandomPkEvent.class,
        TollgateEvent.class}, CreditOnedayListener.class),
    /** friend_count_total 好友总数 */
    friend_count_total("好友总数", new Class[] {FriendAddEvent.class}, FriendCountTotalListener.class),
    /** friend_count_oneday 单天加好友数 */
    friend_count_oneday("单天加好友数", new Class[] {FriendAddEvent.class},
        FriendCountOnedayListener.class),
    /** answer_total 总答题数 */
    answer_total("总答题数",
        new Class[] {FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        AnswerTotalListener.class),
    /** answer_onetime 一天答题数 */
    answer_oneday("一天答题数", new Class[] {FriendPkEvent.class, RandomPkEvent.class,
        TollgateEvent.class}, AnswerOnedayListener.class),
    /** wrongques_solve_total 消灭错题总数 */
    wrongques_solve_total("消灭错题总数", new Class[] {EliminateWrongQuesEvent.class},
        WrongquesSolveTotalListener.class),
    /** wrongques_solve_oneday 单天消灭错题数 */
    wrongques_solve_oneday("单天消灭错题数", new Class[] {EliminateWrongQuesEvent.class},
        WrongquesSolveOnedayListener.class),
    /** wrongques_solve_all 消灭课程全部错题 */
    wrongques_solve_all("消灭课程全部错题", new Class[] {WrongQuesCountEvent.class},
        WrongquesSolveAllListener.class),
    /** collection_total 总收藏题数 */
    collection_total("总收藏题数", new Class[] {CollectionEvent.class}, CollectionTotalListener.class),
    /** collection_oneday 单天收藏题数 */
    collection_oneday("单天收藏题数", new Class[] {CollectionEvent.class}, CollectionOnedayListener.class),
    /** info_improve 完善信息 */
    info_improve("完善信息", new Class[] {ImproveInfoEvent.class}, InfoImproveListener.class),
    /** info_buy_course 开通指定课程 */
    info_buy_course("开通指定课程", new Class[] {BuyCourseEvent.class}, InfoBuyCourseListener.class),
    /** info_buy_course_count 开通课程数量 */
    info_buy_course_count("开通课程数量", new Class[] {BuyCourseEvent.class},
        InfoBuyCourseCountListener.class),
    /** follow_count_total 关注总数 */
    follow_count_total("关注总数", new Class[] {FollowEvent.class}, FollowCountTotalListener.class),
    /** follow_count_oneday 一天内关注数 */
    follow_count_oneday("一天内关注数", new Class[] {FollowEvent.class}, FollowCountOnedayListener.class),
    /** followed_count_total 被关注总数 */
    followed_count_total("被关注总数", new Class[] {BeFollowedEvent.class},
        FollowedCountTotalListener.class),
    /** followed_count_oneday 一天内被关注数 */
    followed_count_oneday("一天内被关注数", new Class[] {BeFollowedEvent.class},
        FollowedCountOnedayListener.class),
    /** active_total_days 活跃总天数 */
    active_total_days("活跃总天数", new Class[] {BaseEvent.class, CollectionEvent.class,
        EliminateWrongQuesEvent.class, FollowEvent.class, FriendAddEvent.class,
        ImproveInfoEvent.class, FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        ActiveTotalDaysListener.class),
    /** active_continuous_days 连续活跃天数 */
    active_continuous_days("连续活跃天数", new Class[] {BaseEvent.class, CollectionEvent.class,
        EliminateWrongQuesEvent.class, FollowEvent.class, FriendAddEvent.class,
        ImproveInfoEvent.class, FriendPkEvent.class, RandomPkEvent.class, TollgateEvent.class},
        ActiveContinuousDaysListener.class);

    private static final Map<String, List<MissionCondition>> MISSION_CONDITION_MAP = Maps
        .newHashMap();
    static {
      List<MissionCondition> pkList = Lists.newArrayList();
      pkList.add(pk_total_times);
      pkList.add(pk_total_times_sameperson);
      pkList.add(pk_win_times);
      pkList.add(pk_win_times_sameperson);
      pkList.add(pk_win_percent);
      pkList.add(pk_win_percent_sameperson);
      pkList.add(pk_fail_times);
      pkList.add(pk_fail_times_sameperson);
      pkList.add(pk_fail_percent);
      pkList.add(pk_fail_percent_sameperson);
      MISSION_CONDITION_MAP.put(MissionConditionType.pk.toString(), pkList);

      List<MissionCondition> dailyPracticeList = Lists.newArrayList();
      dailyPracticeList.add(daily_practice_complete);
      dailyPracticeList.add(daily_practice_contin);
      dailyPracticeList.add(daily_practice_total);
      MISSION_CONDITION_MAP.put(MissionConditionType.dailyPractice.toString(), dailyPracticeList);

      List<MissionCondition> leakFillingList = Lists.newArrayList();
      dailyPracticeList.add(leak_filling_complete);
      MISSION_CONDITION_MAP.put(MissionConditionType.leakFilling.toString(), leakFillingList);


      List<MissionCondition> tollgateList = Lists.newArrayList();
      tollgateList.add(tollgate_total_times);
      tollgateList.add(tollgate_total_times_samecourse);
      tollgateList.add(tollgate_designated);
      tollgateList.add(tollgate_complete_designated);
      tollgateList.add(tollgate_onestar);
      tollgateList.add(tollgate_onestar_samecourse);
      tollgateList.add(tollgate_twostar);
      tollgateList.add(tollgate_twostar_samecourse);
      tollgateList.add(tollgate_threestar);
      tollgateList.add(tollgate_threestar_samecourse);
      MISSION_CONDITION_MAP.put(MissionConditionType.tollgate.toString(), tollgateList);

      List<MissionCondition> scoreList = Lists.newArrayList();
      scoreList.add(score_total);
      scoreList.add(score_oneday);
      MISSION_CONDITION_MAP.put(MissionConditionType.score.toString(), scoreList);

      List<MissionCondition> coinList = Lists.newArrayList();
      coinList.add(credit_total);
      coinList.add(credit_oneday);
      MISSION_CONDITION_MAP.put(MissionConditionType.credit.toString(), coinList);

      List<MissionCondition> friendList = Lists.newArrayList();
      friendList.add(friend_count_total);
      friendList.add(friend_count_oneday);
      // friendList.add(friend_count_designedperson);
      MISSION_CONDITION_MAP.put(MissionConditionType.friend.toString(), friendList);

      List<MissionCondition> answerList = Lists.newArrayList();
      answerList.add(answer_total);
      answerList.add(answer_oneday);
      MISSION_CONDITION_MAP.put(MissionConditionType.answer.toString(), answerList);

      List<MissionCondition> infoList = Lists.newArrayList();
      infoList.add(info_improve);
      infoList.add(info_buy_course);
      MISSION_CONDITION_MAP.put(MissionConditionType.info.toString(), infoList);

      List<MissionCondition> wrongquesList = Lists.newArrayList();
      wrongquesList.add(wrongques_solve_total);
      wrongquesList.add(wrongques_solve_oneday);
      MISSION_CONDITION_MAP.put(MissionConditionType.wrongques.toString(), wrongquesList);

      List<MissionCondition> collectionList = Lists.newArrayList();
      collectionList.add(collection_total);
      collectionList.add(collection_oneday);
      MISSION_CONDITION_MAP.put(MissionConditionType.collection.toString(), collectionList);

      List<MissionCondition> followList = Lists.newArrayList();
      followList.add(follow_count_total);
      followList.add(follow_count_oneday);
      // followList.add(follow_count_designedperson);
      MISSION_CONDITION_MAP.put(MissionConditionType.follow.toString(), followList);

      List<MissionCondition> followedList = Lists.newArrayList();
      followedList.add(followed_count_total);
      followedList.add(followed_count_oneday);
      // followedList.add(followed_count_designedperson);
      MISSION_CONDITION_MAP.put(MissionConditionType.followed.toString(), followedList);

      List<MissionCondition> addNoteList = Lists.newArrayList();
      addNoteList.add(notes_count);
      MISSION_CONDITION_MAP.put(MissionConditionType.addNote.toString(), addNoteList);

      List<MissionCondition> activeList = Lists.newArrayList();
      activeList.add(active_total_days);
      activeList.add(active_continuous_days);
      MISSION_CONDITION_MAP.put(MissionConditionType.active.toString(), activeList);
    }

    MissionCondition(String desc, Class<? extends BaseEvent>[] events,
        Class<? extends IEventListener> eventListener) {
      this.desc = desc;
      if (null != events && events.length > 0) {
        for (Class<? extends BaseEvent> event : events) {
          eventList.add(event);
        }
      }
      this.eventListener = eventListener;
    }

    /** desc 显示信息 */
    private String desc;

    /** eventList 注册时间列表 */
    private List<Class<? extends BaseEvent>> eventList = Lists.newArrayList();

    private Class<? extends IEventListener> eventListener;

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public List<Class<? extends BaseEvent>> getEventList() {
      return eventList;
    }

    public IEventListener getEventListener() {
      try {
        return eventListener.newInstance();
      } catch (Exception e) {
        LoggerUtil.error("", e);
        return null;
      }
    }

  }
}
