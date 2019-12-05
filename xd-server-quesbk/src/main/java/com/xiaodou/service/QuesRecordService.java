package com.xiaodou.service;

import static com.xiaodou.constant.BehaviorConstants.LEARN_RECORD_DAYS;
import static com.xiaodou.constant.BehaviorConstants.PAGENUM;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

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
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.behavior.UserCourseStatisticDayModel;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.domain.behavior.UserExamRecord.ExamDate;
import com.xiaodou.domain.behavior.UserExamRecord.ScoreDate;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserExamTotal.ChapterScore;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.ProductScorePointRule;
import com.xiaodou.domain.product.ProductScorePointRule.RuleInfo;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.util.DateUtils;
import com.xiaodou.vo.request.DailyExamPojo;
import com.xiaodou.vo.request.LearnRecordViewRequest;
import com.xiaodou.vo.request.QuesBasePojo;
import com.xiaodou.vo.request.UserLearnExamDateRequest;
import com.xiaodou.vo.request.UserLearnScoreDateRequest;
import com.xiaodou.vo.response.DailyExamResponse;
import com.xiaodou.vo.response.LearnRecordViewResponse;
import com.xiaodou.vo.response.PkListResponse;
import com.xiaodou.vo.response.PkListResponse.PkAbstractResult;
import com.xiaodou.vo.response.UserLearnExamDateResponse;
import com.xiaodou.vo.response.UserLearnScoreDateResponse;

@Service("quesRecordService")
public class QuesRecordService extends AbstractQuesService {

  @Resource
  PageManager pageManager;

  public DailyExamResponse dailyExam(DailyExamPojo pojo) {
    DailyExamResponse response = new DailyExamResponse(ResultType.SUCCESS);
    try {
      List<String> courseIdList = getCourseIdList(pojo);
      // 做题数据
      Integer quesCount = quesOperationFacade.queryTodayQuesCount(pojo.getUid(), courseIdList);
      if (quesCount != null) {
        response.setTodayQues(quesCount.toString());
      }
      List<String> quesTotalIdList =
          quesOperationFacade.queryTotalQuesIdList(pojo.getUid(), courseIdList);
      if (null != quesTotalIdList) response.setTotalQues(quesTotalIdList.size() + "");
      // pk数据
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("challengerUid", pojo.getUid());
      cond.put("courseId", pojo.getCourseId());
      List<ChallengeRecord> totalRecord = quesOperationFacade.queryChallengeRecord(cond);
      if (null != totalRecord) response.setTotalPkQues(totalRecord.size() + "");
      cond.put("createTimeLower", new Timestamp(DateUtil.getTimesmorning(0)));
      cond.put("createTimeUpper", new Timestamp(DateUtil.getTimesnight(0)));
      List<ChallengeRecord> todayRecord = quesOperationFacade.queryChallengeRecord(cond);
      if (null != todayRecord) response.setTodayPkQues(todayRecord.size() + "");
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
              quesOperationFacade.queryProductList(pojo.getModule(), pojo.getTypeCode()),
              new Function<CourseProduct, String>() {
                @Override
                public String apply(CourseProduct input) {
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
    LearnRecordViewResponse response = pageManager.getPage(pojo.getCacheKey());
    if (null != response) {
      return response;
    }
    response = learnRecordView0(pojo);
    pageManager.addPage(pojo.getCacheKey(), response, QuesBaseConstant.MONGO_CACHE_TIME_DAYS, true,
        TimeUnit.DAYS);
    return response;
  }

  /**
   * 学习统计页面
   * 
   * @param pojo
   * @return
   */
  public LearnRecordViewResponse learnRecordView0(LearnRecordViewRequest pojo) {
    if (!checkOrderCourseIdOverTypeCode(pojo, pojo.getCourseId())) {
      return new LearnRecordViewResponse(ResultType.UNVALIDCOURSEID4TARGETUSER);
    }
    LearnRecordViewResponse response = new LearnRecordViewResponse(ResultType.SUCCESS);
    try {
      List<String> courseIdList = Lists.newArrayList();
      courseIdList.add(pojo.getCourseId());
      // 做题数据
      Integer quesCount = quesOperationFacade.queryTodayQuesCount(pojo.getUid(), courseIdList);
      if (quesCount != null) {
        response.setTodayQues(quesCount.toString());
      }
      UserExamTotal userExamTotal =
          quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
      if (null != userExamTotal) {
        if (null != userExamTotal.getRightQues())
          response.setTotalRightQues(userExamTotal.getRightQues().toString());
        if (null != userExamTotal.getTotalQues())
          response.setTotalQues(userExamTotal.getTotalQues().toString());
        response.setRightPercent(userExamTotal.getRightPercent());
      }
      QuesBasePojo pojo1 = new QuesBasePojo();
      pojo1.setCourseId(pojo.getCourseId());
      pojo1.setUid(pojo.getUid());
      PkListResponse response1 = quesChallengeService.pkList(pojo1);
      if (null != response1 && response1.isRetOk()) {
        List<PkAbstractResult> pkResultList = response1.getList();
        Integer totalPkNum = 0;
        Integer totalPkRightNum = 0;
        if (null != pkResultList && pkResultList.size() > 0) {
          totalPkNum = pkResultList.size();
          for (PkAbstractResult resule : pkResultList) {
            if (StringUtils.isNotEmpty(resule.getResult())
                && QuesBaseConstant.QUES_CHALLENGE_RESULT_SUCCESS == Short.valueOf(resule
                    .getResult())) totalPkRightNum++;
          }
        }
        response.setTotalPkNum(totalPkNum.toString());
        response.setTotalPkRightNum(totalPkRightNum.toString());
      }
      CourseProduct product = quesOperationFacade.queryProduct(pojo.getCourseId());
      if (null != product && null != product.getBeginApplyTime()
          && null != product.getEndApplyTime()) {
        response.setDeadline(DateUtil.SDF_YMD.format(product.getEndApplyTime()));
        if (DateUtils.ifIsExp(product.getBeginApplyTime(), product.getEndApplyTime()))
          response.setLearnStatus("0");
        else
          response.setLearnStatus("1");
      }
      UserExamTotal myExamTotal =
          quesOperationFacade.queryExamTotal(pojo.getUid(), pojo.getCourseId());
      if (null != myExamTotal && null != myExamTotal.getScore()) {
        response.setTotalScore(MathUtil.getIntStringValue(myExamTotal.getScore()));
      }
      /* 章数据 */
      List<ChapterScore> chapterScoreList =
          this.getChapterScoreList(myExamTotal, pojo.getModule(), pojo.getCourseId(), pojo.getUid());
      response.setChapterScoreList(chapterScoreList);
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
   * 章数据
   * 
   * @param userId
   * @param courseId
   * @return
   */
  private List<ChapterScore> getChapterScoreList(UserExamTotal myExamTotal, String module,
      String courseId, String userId) {
    List<ChapterScore> chapterScoreList = Lists.newArrayList();
    if (null != myExamTotal && StringUtils.isJsonNotBlank(myExamTotal.getChapterScore())) {
      chapterScoreList =
          FastJsonUtil.fromJsons(myExamTotal.getChapterScore(),
              new TypeReference<List<ChapterScore>>() {});
    }
    if (null == chapterScoreList || chapterScoreList.size() <= 0) {
      chapterScoreList = Lists.newArrayList();
      List<CourseProductItem> chapterList = quesOperationFacade.queryChapterList(courseId);
      Collections.sort(chapterList, new Comparator<CourseProductItem>() {
        @Override
        public int compare(CourseProductItem o1, CourseProductItem o2) {
          if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
            return 1;
          else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
            return -1;
          else
            return (int) (o1.getListOrder() - o2.getListOrder());
        }
      });
      for (CourseProductItem chapter : chapterList) {
        if (chapter.getParentId() == 0) {
          ChapterScore chapterScore =
              new ChapterScore(chapter.getId().toString(), chapter.getChapterId(),
                  chapter.getName(), 0.00d, chapter.getWeight());
          chapterScoreList.add(chapterScore);
        }
      }
    }
    CourseProduct productModel = quesOperationFacade.queryProduct(courseId);
    if (null == productModel || StringUtils.isBlank(productModel.getRuleId())) {
      return chapterScoreList;
    }
    ProductScorePointRule rule =
        quesOperationFacade.queryProductScorePointRuleById(productModel.getRuleId());
    if (null == rule || StringUtils.isJsonBlank(rule.getRuleDetail())) {
      return chapterScoreList;
    }
    List<RuleInfo> ruleInfoList =
        FastJsonUtil.fromJsons(rule.getRuleDetail(), new TypeReference<List<RuleInfo>>() {});
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("productId", courseId);
    param.addOutputs(UserScorePointRecord.class);
    Page<UserScorePointRecord> userScorePointRecordPage =
        quesOperationFacade.queryUserScorePointRecord(param);
    buildUserScorePointRecord(ruleInfoList, userScorePointRecordPage.getResult());
    for (UserScorePointRecord record : userScorePointRecordPage.getResult()) {
      if (null == record || ProductConstants.RULE_TYPE_ITEM_PRACTICE.equals(record.getRuleType())
          || ProductConstants.RULE_TYPE_CHAPTER_PRACTICE.equals(record.getRuleType())) {
        continue;
      }
      if (null != record.getRuleInfo() && record.getRuleInfo().getWeight() > 0) {
        ChapterScore chapterRecord =
            new ChapterScore(String.valueOf(0), record.getRuleInfo().getAbtractInfo(), record
                .getRuleInfo().getAbtractInfo(), record.getScore(), record.getRuleInfo()
                .getWeight());
        chapterScoreList.add(chapterRecord);

      }
    }
    return chapterScoreList;
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



  private void buildUserScorePointRecord(List<RuleInfo> ruleInfoList,
      List<UserScorePointRecord> recordList) {
    Map<Short, UserScorePointRecord> recordMap = Maps.newHashMap();
    for (UserScorePointRecord record : recordList) {
      recordMap.put(record.getRuleType(), record);
    }
    for (RuleInfo ruleInfo : ruleInfoList) {
      UserScorePointRecord record = recordMap.get(ruleInfo.getCode());
      if (null == record) {
        record = new UserScorePointRecord();
        record.setRuleType(ruleInfo.getCode());
        recordList.add(record);
      }
      record.setRuleInfo(ruleInfo);
    }
    Collections.sort(recordList, new Comparator<UserScorePointRecord>() {
      @Override
      public int compare(UserScorePointRecord o1, UserScorePointRecord o2) {
        if (null == o1 || null == o1.getRuleInfo() || null == o1.getRuleInfo().getOrder()) {
          return -1;
        } else if (null == o2 || null == o2.getRuleInfo() || null == o2.getRuleInfo().getOrder()) {
          return 1;
        } else {
          return o1.getRuleInfo().getOrder() - o2.getRuleInfo().getOrder();
        }
      }
    });
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
    Date date = new Date();
    CourseProduct product = super.queryProduct(courseId);
    if (null != product && null != product.getEndApplyTime()) date = product.getEndApplyTime();
    if (type == QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA) {
      _examStrList = this.getExamStrList(date, LEARN_RECORD_DAYS);
    } else if (type == QuesBaseConstant.LEARN_RECORD_DETAIL_DATA) {
      _examStrList = this.getExamStrList(date, page, PAGENUM, firstLoginTime);
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
  private List<String> getExamStrList(Date date, int days) {
    List<String> _examStrList = Lists.newArrayList();
    for (int i = days + 1; i <= 0; i++) {
      days++;
      _examStrList.add(DateUtils.getDateForDateAndDays(date, 0, 0, days));
    }
    return _examStrList;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   * @throws ParseException
   */
  private List<String> getExamStrList(Date date, int page, int pageNum, String firstLoginTime)
      throws ParseException {
    if (page <= 0) page = 1;
    if (pageNum <= 0) pageNum = PAGENUM;
    int beforeCount = (page - 1) * pageNum;
    int afterCount = page * pageNum;
    List<String> examStrList = Lists.newArrayList();
    // List<String> _examStrList = Lists.newArrayList();
    // 获取用户首次登录日期
    for (int i = beforeCount; i <= afterCount - 1; i++) {
      if (DateUtil.SDF_YMD.parse(DateUtils.getDateForDateAndDays(date, 0, 0, -i)).before(
          DateUtil.SDF_YMD.parse(firstLoginTime))) break;
      examStrList.add(DateUtils.getDateForDateAndDays(date, 0, 0, -i));
    }
    // for (int i = 0; i <= beforeCount - 1; i++) {
    // _examStrList.add(DateUtils.getDateForDateAndDays(date,0, 0, -i));
    // }
    // examStrList.removeAll(_examStrList);
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
    Date date = new Date();
    CourseProduct product = super.queryProduct(courseId);
    if (null != product && null != product.getEndApplyTime()
        && product.getEndApplyTime().before(date)) date = product.getEndApplyTime();
    if (type == QuesBaseConstant.LEARN_RECORD_STATISTIC_DATA) {
      _examStrList = this.getExamStrList(date, LEARN_RECORD_DAYS);
    } else if (type == QuesBaseConstant.LEARN_RECORD_DETAIL_DATA) {
      _examStrList = this.getExamStrList(date, page, PAGENUM, firstLoginTime);
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

}
