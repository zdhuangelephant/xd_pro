package com.xiaodou.resources.service.quesbk;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.quesbk.UserCourseStatisticDayModel;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.model.quesbk.UserExamRecord.ExamDate;
import com.xiaodou.resources.model.quesbk.UserExamRecord.ScoreDate;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.model.quesbk.UserExamTotal.ChapterScore;
import com.xiaodou.resources.request.quesbk.DailyExamPojo;
import com.xiaodou.resources.request.quesbk.LearnRecordViewRequest;
import com.xiaodou.resources.request.quesbk.UserLearnExamDateRequest;
import com.xiaodou.resources.request.quesbk.UserLearnScoreDateRequest;
import com.xiaodou.resources.response.quesbk.DailyExamResponse;
import com.xiaodou.resources.response.quesbk.LearnRecordViewResponse;
import com.xiaodou.resources.response.quesbk.UserLearnExamDateResponse;
import com.xiaodou.resources.response.quesbk.UserLearnScoreDateResponse;
import com.xiaodou.resources.util.DateUtils;
import com.xiaodou.resources.vo.mq.ModifyScoreDateVo;

@Service("quesRecordService")
public class QuesRecordService extends AbstractQuesService {
  private static final int LEARN_RECORD_DAYS = -7;

  private static final int PAGENUM = 20;

  public DailyExamResponse dailyExam(DailyExamPojo pojo) {
    DailyExamResponse response = new DailyExamResponse(ResultType.SUCCESS);
    try {
      List<String> courseIdList = getCourseIdList(pojo);
      // 做题数据
      List<String> quesIdList =
          quesOperationFacade.queryTodayQuesIdList(pojo.getUid(), courseIdList);
      if (quesIdList != null) response.setTodayQues(quesIdList.size() + "");
      List<String> quesTotalIdList =
          quesOperationFacade.queryTotalQuesIdList(pojo.getUid(), courseIdList);
      if (null != quesTotalIdList) response.setTotalQues(quesTotalIdList.size() + "");
      /* 章数据 */
      List<ChapterScore> chapterScoreList = Lists.newArrayList();
      UserExamTotal myExamTotal =
          quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
      if (null != myExamTotal && StringUtils.isNotBlank(myExamTotal.getChapterScore()))
        FILL_CHAPTER_SCORE: {
          List<ChapterScore> _chapterScoreList =
              FastJsonUtil.fromJsons(myExamTotal.getChapterScore(),
                  new TypeReference<List<ChapterScore>>() {});
          if (null == _chapterScoreList || _chapterScoreList.size() == 0) break FILL_CHAPTER_SCORE;
          for (ChapterScore chapterScore : _chapterScoreList) {
            chapterScoreList.add(chapterScore);
          }
          Collections.sort(chapterScoreList, new Comparator<ChapterScore>() {
            @Override
            public int compare(ChapterScore o1, ChapterScore o2) {
              if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
                return 1;
              else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
                return -1;
              else
                return (int) (o1.getListOrder() - o2.getListOrder());
            }
          });
          response.setChapterScoreList(chapterScoreList);
          response.setTotalScore(myExamTotal.getScore().toString());
        }
      /* 每日学习时长数据 */
      List<ExamDate> examDateList =
          this.getExamDateList(pojo.getUid(), pojo.getCourseId(),
              QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA, LEARN_RECORD_DAYS, 0, null);
      response.setExamDateList(examDateList);
    } catch (Exception e) {
      LoggerUtil.error("获取每日学习数据异常", e);
      response = new DailyExamResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  private List<String> getCourseIdList(DailyExamPojo pojo) {
    List<String> courseIdList = Lists.newArrayList();
    if (StringUtils.isBlank(pojo.getCourseId())) {
      courseIdList =
          Lists.transform(
            quesOperationFacade.queryBuyCourseByCond(new HashMap<String, Object>()),
              new Function<ProductModel, String>() {
                @Override
                public String apply(ProductModel input) {
                  if (null != input && null != input.getId()) return input.getId().toString();
                  return StringUtils.EMPTY;
                }
              });
    } else {
      courseIdList.add(pojo.getCourseId());
    }
    return courseIdList;
  }

  /**
   * 学习统计页面
   * 
   * @param pojo
   * @return
   */
  public LearnRecordViewResponse learnRecordView(LearnRecordViewRequest pojo) {
    LearnRecordViewResponse response = new LearnRecordViewResponse(ResultType.SUCCESS);
    try {
      List<String> courseIdList = Lists.newArrayList();
      courseIdList.add(pojo.getCourseId());
      // 做题数据
      List<String> quesIdList =
          quesOperationFacade.queryTodayQuesIdList(pojo.getUid(), courseIdList);
      if (quesIdList != null) response.setTodayQues(quesIdList.size() + "");
      List<String> quesTotalIdList =
          quesOperationFacade.queryTotalQuesIdList(pojo.getUid(), courseIdList);
      if (null != quesTotalIdList) response.setTotalQues(quesTotalIdList.size() + "");
      UserExamTotal userExamTotal =
          quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
      if (null != userExamTotal) {
        if (null != userExamTotal.getRightQues())
          response.setTotalRightQues(userExamTotal.getRightQues().toString());
        // if (null != userExamTotal.getTotalQues())
        // response.setTotalQues(userExamTotal.getTotalQues().toString());
        response.setRightPercent(userExamTotal.getRightPercent());
      }
      /* 章数据 */
      UserExamTotal myExamTotal =
          quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
      if (null != myExamTotal && null != myExamTotal.getScore()) {
        response.setTotalScore(MathUtil.getIntStringValue(myExamTotal.getScore()));
      }
      List<ChapterScore> chapterScoreList =
          this.getChapterScoreList(myExamTotal, pojo.getCourseId());
      response.setChapterScoreList(chapterScoreList);
      // /* 每日学习时长数据 */
      // List<ExamDate> examDateList =
      // this.getExamDateList(pojo.getUid(), pojo.getCourseId(),
      // QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA, LEARN_RECORD_DAYS, 0, null);
      // response.setExamDateList(examDateList);
      /** 每日得分（成绩） */
      List<ScoreDate> scoreDateList =
          this.getScoreDateList(pojo.getUid(), pojo.getModule(), pojo.getCourseId(),
              QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA, LEARN_RECORD_DAYS, 0, null);
      response.setScoreDateList(scoreDateList);
    } catch (Exception e) {
      LoggerUtil.error("学习统计页面 异常", e);
      response = new LearnRecordViewResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 每日学习时长数据
   * 
   * @param pojo
   */
  public UserLearnExamDateResponse learnExamDateList(UserLearnExamDateRequest pojo) {
    UserLearnExamDateResponse response = new UserLearnExamDateResponse(ResultType.SUCCESS);
    try {
      List<ExamDate> examDateList =
          this.getExamDateList(pojo.getUid(), pojo.getCourseId(),
              QuesBaseConstant.LEARN_RECORD_DETAIL_DATA, PAGENUM, Integer.valueOf(pojo.getPage()),
              pojo.getFirstLoginTime());
      response.setExamDateList(examDateList);
    } catch (Exception e) {
      LoggerUtil.error("获取每日学习时长数据异常", e);
      response = new UserLearnExamDateResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 每日得分（成绩）
   * 
   * @param pojo
   * @return
   */
  public UserLearnScoreDateResponse learnScoreDateList(UserLearnScoreDateRequest pojo) {
    UserLearnScoreDateResponse response = new UserLearnScoreDateResponse(ResultType.SUCCESS);
    try {
      List<ScoreDate> scoreDateList =
          this.getScoreDateList(pojo.getUid(), pojo.getModule(), pojo.getCourseId(),
              QuesBaseConstant.LEARN_RECORD_DETAIL_DATA, PAGENUM, Integer.valueOf(pojo.getPage()),
              pojo.getFirstLoginTime());
      response.setScoreDateList(scoreDateList);
    } catch (Exception e) {
      LoggerUtil.error("获取每日得分（成绩）数据异常", e);
      response = new UserLearnScoreDateResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 章数据
   * 
   * @param userId
   * @param courseId
   * @return
   */
  private List<ChapterScore> getChapterScoreList(UserExamTotal myExamTotal, String courseId) {
    List<ChapterScore> chapterScoreList = Lists.newArrayList();
    if (null != myExamTotal && StringUtils.isJsonNotBlank(myExamTotal.getChapterScore())) {
      chapterScoreList =
          FastJsonUtil.fromJsons(myExamTotal.getChapterScore(),
              new TypeReference<List<ChapterScore>>() {});
    }
    if (null == chapterScoreList || chapterScoreList.size() <= 0) {
      List<ProductItemModel> chapterList = quesOperationFacade.queryChapterList(courseId);
      Collections.sort(chapterList, new Comparator<ProductItemModel>() {
        @Override
        public int compare(ProductItemModel o1, ProductItemModel o2) {
          if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
            return 1;
          else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
            return -1;
          else
            return (int) (o1.getListOrder() - o2.getListOrder());
        }
      });
      for (ProductItemModel chapter : chapterList) {
        if (chapter.getParentId() == 0) {
          ChapterScore chapterScore =
              new ChapterScore(chapter.getId().toString(), chapter.getChapterId(),
                  chapter.getName(), MathUtil.getIntStringValue("0.00"));
          chapterScoreList.add(chapterScore);
        }
      }
    }
    return chapterScoreList;
  }

  /**
   * 每日学习时长数据
   * 
   * @param userId 用户id
   * @param courseId 课程id
   * @param count 数据数量
   * @param type 类型 1：统计页面数据 2：详情列表数据
   * @return
   * @throws ParseException
   */
  private List<ExamDate> getExamDateList(String userId, String courseId, int type, int count,
      int page, String firstLoginTime) throws ParseException {
    List<ExamDate> examDateList = new ArrayList<ExamDate>(Math.abs(count));
    List<String> _examStrList = Lists.newArrayList();
    if (type == QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA) {
      _examStrList = this.getExamStrList(LEARN_RECORD_DAYS);
    } else if (type == QuesBaseConstant.LEARN_RECORD_DETAIL_DATA) {
      _examStrList = this.getExamStrList(page, PAGENUM, firstLoginTime);
    } else
      return null;
    Map<String, Object> params = Maps.newHashMap();
    params.put("userId", userId);
    params.put("courseId", courseId);
    List<UserExamRecord> userExamRecordList = quesOperationFacade.selectExamCostByExamCost(params);
    int i = 0;
    Map<String, ExamDate> examMap = Maps.newHashMap();
    if (null != userExamRecordList && userExamRecordList.size() > 0) {
      for (UserExamRecord userExamRecord : userExamRecordList) {
        examMap.put(userExamRecord.getExamDate().getExamDate(), userExamRecord.getExamDate());
      }
    }
    if (null == _examStrList || _examStrList.size() == 0) return examDateList;
    for (String examStr : _examStrList) {
      if (i >= Math.abs(count)) break;
      if (examMap.containsKey(examStr)) {
        examDateList.add(examMap.get(examStr));
      } else {
        examDateList.add(new ExamDate(examStr, "0"));
      }
      ++i;
    }
    return examDateList;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   */
  private List<String> getExamStrList(int days) {
    List<String> _examStrList = Lists.newArrayList();
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -6));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -5));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -4));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -3));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -2));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -1));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, 0));
    for (int i = days + 1; i <= 0; i++) {
      days++;
      _examStrList.add(DateUtils.getDateForDays(0, 0, days));
    }
    return _examStrList;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   * @throws ParseException
   */
  private List<String> getExamStrList(int page, int pageNum, String firstLoginTime)
      throws ParseException {
    if (page <= 0) page = 1;
    if (pageNum <= 0) pageNum = PAGENUM;
    int beforeCount = (page - 1) * pageNum;
    int afterCount = page * pageNum;
    List<String> examStrList = Lists.newArrayList();
    List<String> _examStrList = Lists.newArrayList();
    // 获取用户首次登录日期
    for (int i = 0; i <= afterCount - 1; i++) {
      if (DateUtil.SDF_YMD.parse(DateUtils.getDateForDays(0, 0, -i)).before(
          DateUtil.SDF_YMD.parse(firstLoginTime))) break;
      examStrList.add(DateUtils.getDateForDays(0, 0, -i));
    }
    for (int i = 0; i <= beforeCount - 1; i++) {
      _examStrList.add(DateUtils.getDateForDays(0, 0, -i));
    }
    examStrList.removeAll(_examStrList);
    return examStrList;
  }

  /**
   * 获取每日得分数据
   * 
   * @param userId
   * @param moduleId
   * @param courseId
   * @return
   * @throws ParseException
   */
  private List<ScoreDate> getScoreDateList(String userId, String moduleId, String courseId,
      int type, int count, int page, String firstLoginTime) throws ParseException {
    List<ScoreDate> scoreDateList = new ArrayList<ScoreDate>(Math.abs(count));
    List<String> _examStrList = Lists.newArrayList();
    if (type == QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA) {
      _examStrList = this.getExamStrList(LEARN_RECORD_DAYS);
    } else if (type == QuesBaseConstant.LEARN_RECORD_DETAIL_DATA) {
      _examStrList = this.getExamStrList(page, PAGENUM, firstLoginTime);
    } else
      return null;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("moduleId", moduleId);
    cond.put("productId", courseId);
    List<UserCourseStatisticDayModel> statisticDayList =
        quesOperationFacade.queryStatisticByDay(cond,
            CommUtil.getAllField(UserCourseStatisticDayModel.class));
    int i = 0;
    Map<String, ScoreDate> examMap = Maps.newHashMap();
    if (null != statisticDayList && statisticDayList.size() > 0) {
      for (UserCourseStatisticDayModel statisticDay : statisticDayList) {
        ScoreDate scoreDate = new ScoreDate();
        scoreDate.setExamDate(statisticDay.getRecordTime().toString());
        if (null != statisticDay.getScore())
          scoreDate.setDateCourseScore(statisticDay.getScore().toString());
        examMap.put(scoreDate.getExamDate(), scoreDate);
      }
    }
    for (String examStr : _examStrList) {
      if (i >= Math.abs(count)) break;
      if (examMap.containsKey(examStr)) {
        scoreDateList.add(examMap.get(examStr));
      } else {
        // if (i == 0)
        // scoreDateList.add(new ScoreDate(examStr, String.valueOf(0)));
        // else {
        // scoreDateList
        // .add(new ScoreDate(examStr, scoreDateList.get(i - 1).getDateCourseScore()));
        // }
        /* 取近期取得的分数 */
        cond.put("recordTimeUpper", examStr);
        List<UserCourseStatisticDayModel> _statisticDayList =
            quesOperationFacade.queryStatisticByDay(cond,
                CommUtil.getAllField(UserCourseStatisticDayModel.class));
        if (null != _statisticDayList && _statisticDayList.size() > 0) {
          if (null != _statisticDayList.get(0) && null != _statisticDayList.get(0).getScore())
            scoreDateList
                .add(new ScoreDate(examStr, _statisticDayList.get(0).getScore().toString()));
        } else {
          scoreDateList.add(new ScoreDate(examStr, String.valueOf(0)));
        }
      }
      ++i;
    }

    return scoreDateList;
  }

  public boolean modifyScoreByDate(ModifyScoreDateVo pojo) {
    boolean flag = true;
    try {
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", pojo.getUserId());
      cond.put("moduleId", pojo.getModuleId());
      cond.put("productId", pojo.getCourseId());
      cond.put("recordTime", pojo.getRecordTime());
      List<UserCourseStatisticDayModel> statisticDayList =
          quesOperationFacade.queryStatisticByDay(cond,
              CommUtil.getAllField(UserCourseStatisticDayModel.class));
      if (null != statisticDayList && statisticDayList.size() > 0) {
        // update
        UserCourseStatisticDayModel entity = new UserCourseStatisticDayModel();
        entity.setScore(Integer.valueOf(pojo.getScore()));
        if (!quesOperationFacade.updateStatisticByDay(cond, entity)) flag = false;
      } else {
        // add
        UserCourseStatisticDayModel entity = new UserCourseStatisticDayModel();
        entity.setModuleId(Integer.valueOf(pojo.getModuleId()));
        entity.setProductId(Integer.valueOf(pojo.getCourseId()));
        entity.setUserId(Integer.valueOf(pojo.getUserId()));
        entity.setRecordTime(pojo.getRecordTime());
        entity.setScore(Integer.valueOf(pojo.getScore()));
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if (!quesOperationFacade.insertStatisticByDay(entity)) flag = false;
      }
    } catch (Exception e) {
      flag = false;
      LoggerUtil.error("更新每日学习记录异常", e);
    }
    return flag;
  }

}
