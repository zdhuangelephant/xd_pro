package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.util.FileUtils;
import com.xiaodou.course.util.HttpWrapper;
import com.xiaodou.course.vo.product.ExamStatistic;
import com.xiaodou.course.vo.product.ExamStatistic.DailyExamResponse;
import com.xiaodou.course.vo.product.StudyStatistic;
import com.xiaodou.course.vo.product.StudyTime;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.product.LearnStaticsResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;
import com.xiaodou.userCenter.enums.LoginPar;

/**
 * Created by zyp on 15/8/9.
 */
@Service("userLearnRecordService")
public class UserLearnRecordService {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 新增学习记录
   * 
   * @param learnRecord
   * @return
   */
  public UserLearnRecordModel addLearnRecord(UserLearnRecordModel learnRecord) {
    return productServiceFacade.insertUserLearnRecord(learnRecord);
  }


  /**
   * 新增学习记录
   * 
   * @param request
   * @return
   */
  public BaseResponse addLearnRecord(LearnRecordRequest request) {
    if(request.getTime()<=0){
      return new BaseResponse(ResultType.SUCCESS);
    }
    UserLearnRecordModel learnRecord = new UserLearnRecordModel();
    learnRecord.setLearnTime(request.getTime());
    learnRecord.setRecordTime(new Timestamp(System.currentTimeMillis()));
    learnRecord.setUserId(Integer.parseInt(request.getUid()));
    learnRecord.setModuleId(Integer.parseInt(request.getModule()));
    this.addLearnRecord(learnRecord);
    return new BaseResponse(ResultType.SUCCESS);
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
              FileUtils.QUESBK_PROPERTIES.getPropertiesInt("quesbk.dailystatistic.timeout"), StringUtils.EMPTY,
              initHeader());
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

  private Map<String, String> initHeader() {
    Map<String, String> header = Maps.newHashMap();
    header.put(LoginPar.clientIp.toString(), UserTokenWrapper.getWrapper().getUserModel()
        .getLatestDeviceIp());
    header.put(LoginPar.clientType.toString(), "other");
    header.put(LoginPar.deviceId.toString(), UserTokenWrapper.getWrapper().getUserModel()
        .getUsedDeviceId());
    header
        .put(LoginPar.module.toString(), UserTokenWrapper.getWrapper().getUserModel().getModule());
    header.put(LoginPar.sessionToken.toString(), UserTokenWrapper.getWrapper().getUserToken());
    header.put(LoginPar.version.toString(), "1.0.0");
    return header;
  }

}
