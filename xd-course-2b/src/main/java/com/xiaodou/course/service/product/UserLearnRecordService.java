package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.user.UserCourseHourProgress;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.service.queue.QueueService.Message;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.util.FileUtils;
import com.xiaodou.course.util.HttpWrapper;
import com.xiaodou.course.vo.product.ExamStatistic;
import com.xiaodou.course.vo.product.ExamStatistic.DailyExamResponse;
import com.xiaodou.course.vo.product.StudyStatistic;
import com.xiaodou.course.vo.product.StudyTime;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.product.BaseLearnRecordRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest.PointVo;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.product.LearnStaticsResponse;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @name UserLearnRecordService CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */
@Service("userLearnRecordService")
public class UserLearnRecordService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  QueueService queueService;

  public UserLearnRecordModel findLearnRecordById(String learnRecordId) {
    UserLearnRecordModel learnRecord = new UserLearnRecordModel();
    learnRecord.setId(Long.valueOf(learnRecordId));
    return productServiceFacade.queryUserLearnRecordById(learnRecord);
  }

  public boolean updateLearnRecord(String learnRecordId, UserLearnRecordModel userLearnRecordModel) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", learnRecordId);
    return productServiceFacade.updateUserLearnRecord(cond, userLearnRecordModel);
  }

  public List<UserLearnRecordModel> queryLearnRecord(Map<String, Object> cond) {
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("recordTime", "");
    output.put("learnTime", "");
    Page<UserLearnRecordModel> recordList =
        productServiceFacade.queryUserLearnRecordListByCondWithOutPage(cond, output);
    return null != recordList ? recordList.getResult() : null;
  }

  /**
   * 新增学习记录
   * 
   * @param request
   * @return
   * @throws ParseException
   */
  public BaseResponse addLearnRecord(BaseLearnRecordRequest request) throws ParseException {
    if (request.getLearnTime() <= 3) {
      BaseResponse response = new BaseResponse(ResultType.SUCCESS);
      response.setRetdesc("学习时间超过3秒才会计入有效学习时长");
      return response;
    }
    if (request.getLearnTime() > 86400) {
      BaseResponse response = new BaseResponse(ResultType.SUCCESS);
      response.setRetdesc("学习时间超过正常学习时长");
      return response;
    }
    CheckResult result = super.checkIsValidCourseId((BaseRequest) request, request.getCourseId());
    if (!result.isRetOk()) {
      return new BaseResponse(result.getResType());
    }

    // 构建消息盒
    MessageBox box = new MessageBox();

    UserLearnRecordModel learnRecord = new UserLearnRecordModel();
    learnRecord.setRecordTime(new Timestamp(System.currentTimeMillis()));
    learnRecord.setUserId(Long.parseLong(request.getUid()));
    learnRecord.setModuleId(Integer.parseInt(request.getModule()));
    learnRecord.setTypeCode(request.getTypeCode());
    learnRecord.setProductId(Long.valueOf(request.getCourseId()));
    learnRecord.setLearnTime(request.getLearnTime());
    learnRecord.setLearnType(Short.valueOf(request.getLearnType()));
    Long chapterId = 0L, itemId = 0L;
    String chapterContent = StringUtils.EMPTY, itemContent = StringUtils.EMPTY;
    if (StringUtils.isNotBlank(request.getChapterId())) {
      chapterId = Long.valueOf(request.getChapterId());
      ProductItemModel chapter = new ProductItemModel();
      chapter.setId(chapterId);
      chapter = productServiceFacade.queryProductItemById(chapter);
      if (null != chapter) chapterContent = chapter.getChapterId();
    }
    if (StringUtils.isNotBlank(request.getItemId())) {
      itemId = Long.valueOf(request.getItemId());
      ProductItemModel item = new ProductItemModel();
      item.setId(itemId);
      item = productServiceFacade.queryProductItemById(item);
      if (null != item) itemContent = item.getChapterId();
    }
    learnRecord.setChapterId(chapterId);
    learnRecord.setItemId(itemId);
    this.evalLearnContent(request, learnRecord, chapterContent, itemContent);
    // 发送 记录学习时长纪录 消息
    box.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.AddLearnRecord.toString(),
        learnRecord);
    // 发送 记录每天学习时长 消息
    box.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.RecordLearnTimeForDay.toString(),
        learnRecord);
    // 记录音频， 视频， 微课、的学习进度 Mofify by zdhuang at 2018-7-10 13:20:00
    if (request.getLearnType().trim().equals(CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_AUDIO)
        || request.getLearnType().trim().equals(CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_VIDEO)
        || request.getLearnType().trim()
            .equals(CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO)) {
      UserCourseHourProgress info = new UserCourseHourProgress();
      info.setChapterId(request.getChapterId());
      info.setModule(request.getModule());
      info.setCourseId(request.getCourseId());
      info.setItemId(request.getItemId());
      info.setUserId(request.getUid());
      info.setResourceType(resolveResourceType(request));
      List<PointVo> points =
          FastJsonUtil.fromJsons(((LearnRecordRequest) request).getPoints(),
              new TypeReference<List<PointVo>>() {});
      info.setPoints(points);
      box.addTargetLevelMessage(MessageBox.FIRST_LEVEL,
          Message.UpdateUserCourseHourProgress.toString(), info);
      // Modify By zhaodan 将计算课件学习成绩移入计算课件学习完成度的逻辑中处理
      box.addTargetLevelMessage(MessageBox.SECOND_LEVEL,
          Message.CaculateCourseWareScore.toString(), info);
    }
    // 更新用户产品信息
    box.addTargetLevelMessage(MessageBox.SECOND_LEVEL, Message.UpdatePersonProductInfo.toString(),
        request);
    queueService.sendMessageBox(box);
    return new BaseResponse(ResultType.SUCCESS);
  }

  private Integer resolveResourceType(BaseLearnRecordRequest request) {
    if (CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_VIDEO.equals(request.getLearnType())) {
      return CourseConstant.RESOURCE_TYPE_VIDEO.intValue();
    } else if (CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_AUDIO.equals(request.getLearnType())) {
      return CourseConstant.RESOURCE_TYPE_AUDIO.intValue();
    } else if (CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO
        .equals(request.getLearnType())) {
      return CourseConstant.RESOURCE_TYPE_MICRO.intValue();
    }
    return null;
  }

  private void evalLearnContent(BaseLearnRecordRequest request, UserLearnRecordModel learnRecord,
      String chapterContent, String itemContent) {
    switch (request.getLearnType()) {
    // case CourseConstant.LEARN_RECORD_TYPE_TASK_TYPE_MICRO_VIDEO:
    // learnRecord.setLearnContent("微课考点");
    // break;
      case CourseConstant.LEARN_RECORD_TYPE_PK_EXAM:
        learnRecord.setLearnContent("随机PK答题");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_PK_ANALYZE:
        learnRecord.setLearnContent("随机PK解析");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_WRONGQUES:
        learnRecord.setLearnContent("错题");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_DAILY:
        learnRecord.setLearnContent("每日一练答题");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_DAILY_ANALYZE:
        learnRecord.setLearnContent("每日一练解析");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_FINAL:
        FinalExamModel examModel = null;
        if (StringUtils.isNotEmpty(request.getItemId())) {
          examModel = productServiceFacade.queryFinalExamById(Long.parseLong(request.getItemId()));
        }
        if (null != examModel)
          learnRecord.setLearnContent(examModel.getExamName());
        else
          learnRecord.setLearnContent("期末考试答题");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_FINAL_ANALYZE:
        learnRecord.setLearnContent("期末测试解析");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_SUPPLEMENT:
        learnRecord.setLearnContent("查漏补缺答题");
        break;
      case CourseConstant.LEARN_RECORD_TYPE_SUPPLEMENT_ANALYZE:
        learnRecord.setLearnContent("查漏补缺解析");
        break;
      default:
        learnRecord.setLearnType(Short.valueOf(CourseConstant.LEARN_RECORD_TYPE_TRAINING));
        if (StringUtils.isAllNotBlank(chapterContent, itemContent)
            && !itemContent.equals(chapterContent))
          learnRecord.setLearnContent(String.format("%s%s", chapterContent, itemContent));
        else if (StringUtils.isNotBlank(chapterContent) && StringUtils.isBlank(itemContent))
          learnRecord.setLearnContent(String.format("%s-章总结", chapterContent));
        else if (StringUtils.isAllNotBlank(chapterContent, itemContent)
            && itemContent.equals(chapterContent))
          learnRecord.setLearnContent(String.format("%s-章总结", chapterContent));
        else
          learnRecord.setLearnContent("未知章节");
        break;
    }
  }

  public Integer getTodayLearnTime1(Map<String, Object> cond) {
    Integer totalLearnTime = 0;
    Integer todayLearnTime = 0;
    List<UserLearnRecordModel> recordList = this.queryLearnRecord(cond);
    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
    String today = formatter.format(new Timestamp(System.currentTimeMillis()));
    Map<String, Integer> dayMap = new TreeMap<>();
    if (null != recordList && recordList.size() > 0)
      for (UserLearnRecordModel learnRecord : recordList) {
        String data = formatter.format(learnRecord.getRecordTime());
        totalLearnTime = totalLearnTime + learnRecord.getLearnTime();
        if (dayMap.containsKey(data)) {
          dayMap.put(data, dayMap.get(data) + learnRecord.getLearnTime());
        } else {
          dayMap.put(data, learnRecord.getLearnTime());
        }
      }
    if (dayMap.containsKey(today)) {
      todayLearnTime = dayMap.get(today);
    }
    return todayLearnTime;
  }

  /**
   * 学习时间统计
   * 
   * @param baseRequest
   * @return
   * @throws Exception
   */
  public LearnStaticsResponse statics(BaseRequest baseRequest) {

    LearnStaticsResponse response = new LearnStaticsResponse(ResultType.SUCCESS);

    Integer userId = Integer.parseInt(baseRequest.getUid());
    Integer moduleId = Integer.parseInt(baseRequest.getModule());
    Integer totalLearnTime = 0;
    Integer todayLearnTime = 0;

    Map<String, Object> cond = new HashMap<>();
    cond.put("userId", userId);
    cond.put("moduleId", moduleId);

    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("recordTime", "");
    output.put("learnTime", "");
    Page<UserLearnRecordModel> recordList =
        productServiceFacade.queryUserLearnRecordListByCondWithOutPage(cond, output);

    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
    String today = formatter.format(new Timestamp(System.currentTimeMillis()));
    Map<String, Integer> dayMap = new TreeMap<>();
    for (UserLearnRecordModel learnRecord : recordList.getResult()) {
      String data = formatter.format(learnRecord.getRecordTime());
      totalLearnTime = totalLearnTime + learnRecord.getLearnTime();
      if (dayMap.containsKey(data)) {
        dayMap.put(data, dayMap.get(data) + learnRecord.getLearnTime());
      } else {
        dayMap.put(data, learnRecord.getLearnTime());
      }
    }

    if (dayMap.containsKey(today)) {
      todayLearnTime = dayMap.get(today);
    }

    StudyStatistic studyStatistic = new StudyStatistic();
    studyStatistic.setTodayStudyTime(todayLearnTime);
    studyStatistic.setTotalTime(totalLearnTime);
    List<StudyTime> studyTimeList = new ArrayList<>();
    for (int i = 6; i >= 0; i--) {
      StudyTime studyTime = new StudyTime();
      String date =
          formatter.format(new Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1000 * i));
      studyTime.setDate(date);
      if (dayMap.containsKey(date)) {
        studyTime.setTime(dayMap.get(date));
      } else {
        studyTime.setTime(0);
      }
      studyTimeList.add(studyTime);
    }
    studyStatistic.setLastStudyTime(studyTimeList);
    response.setStudyStatistic(studyStatistic);

    // 做题统计
    ExamStatistic examStatistic = new ExamStatistic();
    String dailyQuesStatistic = StringUtils.EMPTY;
    try {
      dailyQuesStatistic =
          HttpWrapper.send(FileUtils.QUESBK_PROPERTIES.getProperties("quesbk.host"),
              FileUtils.QUESBK_PROPERTIES.getPropertiesInt("quesbk.port"),
              FileUtils.QUESBK_PROPERTIES.getProperties("quesbk.dailystatistic.url"),
              FileUtils.QUESBK_PROPERTIES.getPropertiesInt("quesbk.dailystatistic.retryTime"),
              FileUtils.QUESBK_PROPERTIES.getPropertiesInt("quesbk.dailystatistic.timeout"),
              StringUtils.EMPTY, FileUtils.initHeader());
    } catch (Exception e) {
      LoggerUtil.error("获取做题详情失败.", e);
    }
    if (StringUtils.isNotBlank(dailyQuesStatistic)) {
      DailyExamResponse quesbkRes =
          FastJsonUtil.fromJson(dailyQuesStatistic, DailyExamResponse.class);
      if (null != quesbkRes && null != quesbkRes.getDailyExamDetail()
          && null != quesbkRes.getTotalExamDetail()) examStatistic = new ExamStatistic(quesbkRes);
    }
    response.setExamStatistic(examStatistic);
    return response;
  }

  /**
   * 获取用户总学习时长
   * 
   * @param pojo
   */
  public String sumLearnTime(BaseRequest pojo) {
    String learnTime = "0";
    try {
      Map<String, Object> input = Maps.newHashMap();
      input.put("userId", pojo.getUid());
      Integer time = productServiceFacade.sumLearnTimeByCond(input);
      if (null != time) {
        learnTime = DateUtil.getLongStringValue(Double.valueOf(time) / 60);
      }
    } catch (Exception e) {
      LoggerUtil.error("获取用户总学习时长异常", e);
    }
    return learnTime;
  }

}
