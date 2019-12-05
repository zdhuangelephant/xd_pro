package com.xiaodou.server.mapi.service.selftaught;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.constant.SelfTaughtConstant;
import com.xiaodou.server.mapi.domain.OfficialInfo;
import com.xiaodou.server.mapi.domain.UserLearnRecordDayModel;
import com.xiaodou.server.mapi.request.AcceptTaskRequest;
import com.xiaodou.server.mapi.request.ChapterCardListRequest;
import com.xiaodou.server.mapi.request.ChapterResourceRequest;
import com.xiaodou.server.mapi.request.CreateOrderRequest;
import com.xiaodou.server.mapi.request.GetAwardPojo;
import com.xiaodou.server.mapi.request.InitAwardPojo;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.server.mapi.request.MedalListRequest;
import com.xiaodou.server.mapi.request.PayOrderRequest;
import com.xiaodou.server.mapi.request.PerformanceDetailPojo;
import com.xiaodou.server.mapi.request.PersonInfoRequest;
import com.xiaodou.server.mapi.request.UserListRequest;
import com.xiaodou.server.mapi.request.mission.TaskCompleteRequest;
import com.xiaodou.server.mapi.request.mission.TaskListRequest;
import com.xiaodou.server.mapi.request.mission.TaskStatusRequest;
import com.xiaodou.server.mapi.request.payment.AliCallbackRequest;
import com.xiaodou.server.mapi.request.payment.AppleCallbackRequest;
import com.xiaodou.server.mapi.request.product.UserLearnExamDateRequest;
import com.xiaodou.server.mapi.request.product.UserLearnScoreDateRequest;
import com.xiaodou.server.mapi.request.quesbk.ChallengeRequest;
import com.xiaodou.server.mapi.request.quesbk.ExamDetailRequest;
import com.xiaodou.server.mapi.request.quesbk.FaceRecognitionRequest;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogDetailPojo;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogListPojo;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogPojo;
import com.xiaodou.server.mapi.request.quesbk.ScorePointRuleRequest;
import com.xiaodou.server.mapi.request.trade.TradeDetailRequest;
import com.xiaodou.server.mapi.request.ucenter.UserRankRequest;
import com.xiaodou.server.mapi.response.mission.TaskListResponse;
import com.xiaodou.server.mapi.response.mission.TaskStatusResponse;
import com.xiaodou.server.mapi.response.mission.UnReachedTaskCountResponse;
import com.xiaodou.server.mapi.response.payment.PayResponse;
import com.xiaodou.server.mapi.response.product.ChapterAudioResponse;
import com.xiaodou.server.mapi.response.product.ChapterCardListResponse;
import com.xiaodou.server.mapi.response.product.PayOrderResponse;
import com.xiaodou.server.mapi.response.product.ProductExamDetailResponse;
import com.xiaodou.server.mapi.response.product.ProductExamDetailResponse.ExamDateDetail;
import com.xiaodou.server.mapi.response.product.ProductOrderResponse;
import com.xiaodou.server.mapi.response.product.UserLearnRecordDataResponse;
import com.xiaodou.server.mapi.response.product.UserLearnRecordResponse;
import com.xiaodou.server.mapi.response.product.UserProductOrderResponse;
import com.xiaodou.server.mapi.response.product.UserTaskCompleteResponse;
import com.xiaodou.server.mapi.response.quesbk.ChallengeInfo;
import com.xiaodou.server.mapi.response.quesbk.CourseStatisticsInfo;
import com.xiaodou.server.mapi.response.quesbk.ExamDetailResponse;
import com.xiaodou.server.mapi.response.quesbk.QuesAudioLogCountResponse;
import com.xiaodou.server.mapi.response.quesbk.QuesAudioLogListResponse;
import com.xiaodou.server.mapi.response.quesbk.QuesAudioLogResponse;
import com.xiaodou.server.mapi.response.quesbk.ScorePointRuleResponse;
import com.xiaodou.server.mapi.response.quesbk.UserLearnScoreDateResponse;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.selftaught.ChapterResourceResponse;
import com.xiaodou.server.mapi.response.selftaught.MedalListResponse;
import com.xiaodou.server.mapi.response.selftaught.RankListResponse;
import com.xiaodou.server.mapi.response.trade.TradeDetailResponse;
import com.xiaodou.server.mapi.response.ucenter.FirstLoginTimeResponse;
import com.xiaodou.server.mapi.response.ucenter.PersonInfoResponse;
import com.xiaodou.server.mapi.response.ucenter.UserListResponse;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.service.BaseMapiService;
import com.xiaodou.server.mapi.util.DateUtil;
import com.xiaodou.summer.vo.out.ResultType;

@Service("selfTaughtService")
public class SelfTaughtService extends BaseMapiService {

  public ScorePointRuleResponse scorePointRule(ScorePointRuleRequest pojo) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("traceId", pojo.getTraceId());
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    HttpResult httpResult = router("quesbk", "scorePointRule", params);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ScorePointRuleResponse.class);
    return new ScorePointRuleResponse(ResultType.SYSFAIL);
  }

  public ExamDetailResponse examDetail(ExamDetailRequest pojo) {
    UserModelResponse user = pojo.getUserFromWrapper();
    Map<String, Object> params = Maps.newHashMap();
    params.put("traceId", pojo.getTraceId());
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("chapterId", pojo.getChapterId());
    params.put("itemId", pojo.getItemId());
    params.put("examType", pojo.getExamType());
    params.put("recordId", pojo.getRecordId());
    params.put("userType", user.getUserType());
    params.put("userCredit", user.getCredit());
    params.put("srcFaceId", user.getBenchmarkFace());
    HttpResult httpResult = router("quesbk", "examDetail", params);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ExamDetailResponse.class);
    return new ExamDetailResponse(ResultType.SYSFAIL);
  }

  public ChallengeInfo challenge(ChallengeRequest pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("typeCode", pojo.getTypeCode());
    param.put("type", pojo.getType());
    param.put("courseId", pojo.getCourseId());
    param.put("targetUserId", pojo.getUserId());
    HttpResult httpResult = router("quesbk", "challenge", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ChallengeInfo.class);
    return new ChallengeInfo();
  }

  /**
   * 根据id List 查询用户
   * 
   * @param userIdListReq
   * @return
   */
  public UserListResponse queryUserListInfo(UserListRequest pojo) {
    Map<String, Object> params = Maps.newHashMap();
    params.putAll(pojo.getUserBaseParams());
    List<String> userIdList = pojo.getUserIdList();
    String ids = "";
    for (String id : userIdList) {
      ids = id + "," + ids;
    }
    if (StringUtils.isNotBlank(ids) && ids.length() > 1)
      params.put("userIdList", ids.substring(0, ids.length() - 1));
    HttpResult userList = router("ucenter", "userList", params);
    if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(userList.getContent())) {
      return FastJsonUtil.fromJson(userList.getContent(), UserListResponse.class);
    }
    return new UserListResponse(ResultType.SYSFAIL);
  }

  public MedalListResponse queryMedalList(MedalListRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    HttpResult httpResult = router("mission", "medalList", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), MedalListResponse.class);
    return new MedalListResponse(ResultType.SYSFAIL);
  }

  public ProductExamDetailResponse productExamDetail(MapiBaseRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("majorId", request.getUserFromWrapper().getMajorId());
    HttpResult httpResult = router("product", "examDetail", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ProductExamDetailResponse.class);
    return new ProductExamDetailResponse(ResultType.SYSFAIL);
  }

  public UnReachedTaskCountResponse unReachTask(MapiBaseRequest request,
      ProductExamDetailResponse examDetail) {
    List<ExamDateDetail> courseList = examDetail.getExamDateDetail();
    Map<String, String> courseMap = Maps.newHashMap();
    if (null != courseList && courseList.size() > 0) {
      for (ExamDateDetail course : courseList) {
        courseMap.put(course.getCourseId().toString(), course.getCourseName());
      }
    }
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("majorId", request.getUserFromWrapper().getMajorId());
    param.put("courseMap", FastJsonUtil.toJson(courseMap));
    param.put("surplusDays", examDetail.getAwayNextExam());
    HttpResult httpResult = router("mission", "unReachTask", param);
    if (null != httpResult && httpResult.getStatusCode() == 200) {
      return FastJsonUtil.fromJson(httpResult.getContent(), UnReachedTaskCountResponse.class);
    }
    return new UnReachedTaskCountResponse(ResultType.SYSFAIL);
  }

  public TaskListResponse queryTaskList(TaskListRequest request,
      ProductExamDetailResponse examDetail) {
    TaskListResponse response = new TaskListResponse(ResultType.SUCCESS);
    List<ExamDateDetail> courseList = examDetail.getExamDateDetail();
    Map<String, String> courseMap = Maps.newHashMap();
    if (null != courseList && courseList.size() > 0) {
      for (ExamDateDetail course : courseList) {
        courseMap.put(course.getCourseId().toString(), course.getCourseName());
      }
    }
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("majorId", request.getUserFromWrapper().getMajorId());
    param.put("courseMap", FastJsonUtil.toJson(courseMap));
    param.put("surplusDays", examDetail.getAwayNextExam());
    param.put("lastDate", request.getEarliestDate());
    HttpResult httpResult = router("mission", "taskList", param);
    if (null != httpResult && httpResult.getStatusCode() == 200) {
      response = FastJsonUtil.fromJson(httpResult.getContent(), TaskListResponse.class);
      response
          .setExamPassDays((examDetail.getIntervalsBetweenExam() - examDetail.getAwayNextExam())
              + "");
      response.setExamIntervalsDays(examDetail.getIntervalsBetweenExam().toString());
      return response;
    }
    return new TaskListResponse(ResultType.SYSFAIL);
  }

  public TaskStatusResponse queryTaskStatus(TaskStatusRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("courseId", request.getCourseId());
    param.put("chapterId", request.getChapterId());
    param.put("taskId", request.getTaskId());
    param.put("recordId", request.getRecordId());
    HttpResult httpResult = router("mission", "taskStatus", param);
    if (null != httpResult && httpResult.getStatusCode() == 200) {
      return FastJsonUtil.fromJson(httpResult.getContent(), TaskStatusResponse.class);
    }
    return new TaskStatusResponse(ResultType.SYSFAIL);
  }

  public TaskListResponse acceptTaskList(AcceptTaskRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("taskId", request.getTaskId());
    HttpResult httpResult = router("mission", "acceptTask", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), TaskListResponse.class);
    return new TaskListResponse(ResultType.SYSFAIL);
  }

  public ChapterResourceResponse chappterResource(ChapterResourceRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("courseId", request.getCourseId());
    param.put("chapterId", request.getChapterId());
    param.put("itemId", request.getItemId());
    param.put("commentIdUpper", request.getCommentIdUpper());
    param.put("version", request.getVersion());
    param.put("size", request.getSize());
    HttpResult httpResult = router("product", "chapterResource", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ChapterResourceResponse.class);
    return new ChapterResourceResponse(ResultType.SYSFAIL);
  }

  public ChapterAudioResponse chapterAudio(ChapterResourceRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("courseId", request.getCourseId());
    param.put("chapterId", request.getChapterId());
    param.put("itemId", request.getItemId());
    param.put("commentIdUpper", request.getCommentIdUpper());
    param.put("size", request.getSize());
    HttpResult httpResult = router("product", "chapterAudio", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ChapterAudioResponse.class);
    return new ChapterAudioResponse(ResultType.SYSFAIL);
  }

  public CourseStatisticsInfo courseStatistics(Map<String, Object> params) {
    HttpResult httpResult = router("quesbk", "courseStatistics", params);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), CourseStatisticsInfo.class);
    return new CourseStatisticsInfo(ResultType.SYSFAIL);
  }

  public ChapterCardListResponse chapterCardList(ChapterCardListRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("courseId", request.getCourseId());
    param.put("chapterId", request.getChapterId());
    param.put("itemId", request.getItemId());
    HttpResult httpResult = router("product", "chapterCardList", param);
    if (null != httpResult && httpResult.getStatusCode() == 200)
      return FastJsonUtil.fromJson(httpResult.getContent(), ChapterCardListResponse.class);
    return new ChapterCardListResponse(ResultType.SYSFAIL);
  }

  /**
   * 开通课程
   * 
   * @param params
   * @return
   */
  public BaseResponse openProduct(Map<String, Object> params) {
    // 购买课程
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    HttpResult result = router("product", "openProduct", params);
    if (null == result || result.getStatusCode() != HttpResultStatus.OK.getCode()
        || StringUtils.isJsonBlank(result.getContent()))
      return new BaseResponse(ResultType.SYSFAIL);
    response = FastJsonUtil.fromJson(result.getContent(), UserProductOrderResponse.class);
    return response;
  }

  /**
   * 对导入数据做验证1、对象验证 2、参数验证
   * 
   * @param officialInfo
   * @return
   */
  public boolean verifyOfficialInfo(OfficialInfo officialInfo) {
    return true;
  }

  public ProductOrderResponse createOrder(CreateOrderRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("productId", request.getCourseId());
    param.put("deviceId", request.getDeviceId());
    param.put("clientType", request.getClientType());
    param.put("clientIp", request.getClientIp());
    HttpResult httpResult = router("product", "createOrder", param);
    if (null != httpResult && httpResult.isResultOk())
      return FastJsonUtil.fromJson(httpResult.getContent(), ProductOrderResponse.class);
    return new ProductOrderResponse(ResultType.SYSFAIL);
  }

  public PayOrderResponse pay(PayOrderRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("gorderId", request.getGorderId());
    param.put("clientType", request.getClientType());
    HttpResult httpResult = router("product", "pay", param);
    if (null != httpResult && httpResult.isResultOk())
      return FastJsonUtil.fromJson(httpResult.getContent(), PayOrderResponse.class);
    return new PayOrderResponse(ResultType.SYSFAIL);
  }

  public Object performanceDetail(PerformanceDetailPojo request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("courseId", request.getCourseId());
    param.put("bonusId", request.getBonusId());
    HttpResult httpResult = router("quesbk", "performanceDetail", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new PayOrderResponse(ResultType.SYSFAIL);
  }

  public Object initAward(InitAwardPojo request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("missionId", request.getMissionId());
    HttpResult httpResult = router("quesbk", "initAward", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new BaseResponse(ResultType.SYSFAIL);
  }

  public Object getAward(GetAwardPojo request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    param.put("typeCode", request.getTypeCode());
    param.put("bonusId", request.getBonusId());
    HttpResult httpResult = router("quesbk", "getAward", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new BaseResponse(ResultType.SYSFAIL);
  }


  /**
   * 获取字符串类型整型
   * 
   * @param dValue 双精度浮点类型
   * @return 字符串整型
   */
  public String getLongStringValue(Double dValue) {
    try {
      return Long.toString(new Double(Math.ceil(dValue)).longValue());
    } catch (Exception e) {
      return "0";
    }
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年10月11日
   * @param pojo
   * @return
   */
  public String getTotalLearnTime(PersonInfoRequest pojo) {
    Integer learnTime = 0;
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    HttpResult _result = router("product", "learnRecordTimeList", params);
    if (null != _result && _result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(_result.getContent())) {
      UserLearnRecordResponse learnTimeResponse =
          FastJsonUtil.fromJson(_result.getContent(), UserLearnRecordResponse.class);
      if (learnTimeResponse.isRetOk() && null != learnTimeResponse.getLearnList()) {
        for (UserLearnRecordDayModel learnModel : learnTimeResponse.getLearnList()) {
          if (null != learnModel) learnTime += learnModel.getLearnTime();
        }
      }
    }
    String time = "0";
    if (0 != learnTime) time = this.getLongStringValue(Double.valueOf(learnTime) / 60);
    return time;
  }

  public PersonInfoResponse personProductInfo(PersonInfoRequest pojo) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("traceId", pojo.getTraceId());
    HttpResult _result = router("product", "personProductInfo", params);
    if (null != _result && _result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(_result.getContent())) {
      return FastJsonUtil.fromJson(_result.getContent(), PersonInfoResponse.class);
    } else {
      return new PersonInfoResponse(ResultType.SYSFAIL);
    }
  }

  public UserLearnRecordDataResponse learnExamDateList(UserLearnExamDateRequest pojo) {
    UserLearnRecordDataResponse response = new UserLearnRecordDataResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("page", pojo.getPage());
    String firstLoginTime = this.getUserFirstLoginTime(pojo);
    if (StringUtils.isBlank(firstLoginTime)) {
      return new UserLearnRecordDataResponse(ResultType.NOTLOGIN);
    }
    params.put("firstLoginTime", firstLoginTime);
    HttpResult result = router("product", "learnRecordTimeDetailList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), UserLearnRecordDataResponse.class);
    } else {
      response = new UserLearnRecordDataResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public String getUserFirstLoginTime(MapiBaseRequest pojo) {
    Map<String, Object> params = Maps.newHashMap();
    params.putAll(pojo.getUserBaseParams());
    params.put("userId", pojo.getUid());
    String firstLoginTimeResponseStr = getRouter("ucenter", "userFirstLoginTime", params);
    String firstLoginTime = StringUtils.EMPTY;
    if (StringUtils.isJsonNotBlank(firstLoginTimeResponseStr)) {
      FirstLoginTimeResponse firstLoginTimeResponse =
          FastJsonUtil.fromJson(firstLoginTimeResponseStr, FirstLoginTimeResponse.class);
      firstLoginTime = firstLoginTimeResponse.getFristLoginTime();
    }
    return firstLoginTime;
  }

  public UserLearnScoreDateResponse learnScoreDateList(UserLearnScoreDateRequest pojo) {
    UserLearnScoreDateResponse response = new UserLearnScoreDateResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("page", pojo.getPage());
    String firstLoginTime = this.getUserFirstLoginTime(pojo);
    if (StringUtils.isBlank(firstLoginTime)) {
      return new UserLearnScoreDateResponse(ResultType.NOTLOGIN);
    }
    params.put("firstLoginTime", firstLoginTime);
    HttpResult result = router("quesbk", "learnScoreDateList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), UserLearnScoreDateResponse.class);
    } else {
      response = new UserLearnScoreDateResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public PayResponse appleCallBack(AppleCallbackRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("tradeNo", request.getTradeNo());
    param.put("thirdTositenumero", request.getThirdTositenumero());
    HttpResult httpResult = router("payment", "appleCallBack", param);
    if (null != httpResult && httpResult.isResultOk())
      return FastJsonUtil.fromJson(httpResult.getContent(), PayResponse.class);
    return new PayResponse(ResultType.SYSFAIL);
  }

  public PayResponse aliCallBack(AliCallbackRequest request) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("notify_id", request.getNotify_id());
    param.put("out_trade_no", request.getOut_trade_no());
    HttpResult httpResult = router("payment", "aliCallBack", param);
    if (null != httpResult && httpResult.isResultOk())
      return FastJsonUtil.fromJson(httpResult.getContent(), PayResponse.class);
    return new PayResponse(ResultType.SYSFAIL);
  }


  public String tradeDetail(TradeDetailRequest pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("typeCode", pojo.getTypeCode());
    param.put("clientType", pojo.getClientType());
    param.put("pageNo", pojo.getPageNo());
    param.put("size", pojo.getSize());
    HttpResult httpResult = router("product", "tradeDetail", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new TradeDetailResponse(ResultType.SYSFAIL).toString0();
  }

  /**
   * 排位赛列表接口
   * 
   * @param pojo
   * @return
   */
  public RankListResponse rankList(UserRankRequest pojo) {
    Map<String, Object> param = Maps.newHashMap();
    if (SelfTaughtConstant.STAR_RANK.equals(pojo.getType())) {
      param.put("uid", pojo.getUid());
      param.put("module", pojo.getModule());
      param.put("typeCode", pojo.getTypeCode());
      param.put("type", pojo.getType());
      HttpResult httpResult = router("product", "starRankList", param);
      if (null != httpResult && httpResult.getStatusCode() == 200) {
        RankListResponse response =
            FastJsonUtil.fromJson(httpResult.getContent(), RankListResponse.class);
        if (StringUtils.isBlank(response.getMyRankInfo().getUserId())) {
          UserModelResponse baseUserModel = pojo.getUserFromWrapper();
          response.getMyRankInfo().setUserId(baseUserModel.getUserId());
          response.getMyRankInfo().setGender(baseUserModel.getGender());
          response.getMyRankInfo().setNickName(baseUserModel.getNickName());
          response.getMyRankInfo().setPortrait(baseUserModel.getPortrait());
        }
        return response;
      }
    } else if (SelfTaughtConstant.CREDIT_RANK.equals(pojo.getType())) {
      param.putAll(pojo.getUserBaseParams());
      param.put("type", pojo.getType());
      HttpResult httpResult = router("ucenter", "rankList", param);
      if (null != httpResult && httpResult.getStatusCode() == 200) {
        return FastJsonUtil.fromJson(httpResult.getContent(), RankListResponse.class);
      }
    }
    return new RankListResponse(ResultType.SYSFAIL);
  }

  public String faceRecognition(FaceRecognitionRequest pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("deviceId", pojo.getDeviceId());
    param.put("typeCode", pojo.getTypeCode());
    param.put("type", pojo.getType());
    param.put("courseId", pojo.getCourseId());
    param.put("paperId", pojo.getPaperId());
    param.put("faceUrl", pojo.getFaceUrl());
    param.put("srcFaceId", pojo.getUserFromWrapper().getBenchmarkFace());
    HttpResult httpResult = router("quesbk", "faceRecognition", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new BaseResponse(ResultType.SYSFAIL).toString();
  }

  public String quesAudioLogAdd(QuesAudioLogPojo pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("deviceId", pojo.getDeviceId());
    param.put("typeCode", pojo.getTypeCode());
    param.put("quesId", pojo.getQuesId());
    param.put("quesVideoUrl", pojo.getQuesVideoUrl());
    param.put("courseId", pojo.getCourseId());
    param.put("traceId", pojo.getTraceId());
    HttpResult httpResult = router("quesbk", "quesAudioLogAdd", param);
    if (null != httpResult && httpResult.isResultOk()) return httpResult.getContent();
    return new BaseResponse(ResultType.SYSFAIL).toString();
  }

  public String getQuesAudioLogList(QuesAudioLogListPojo pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("deviceId", pojo.getDeviceId());
    param.put("typeCode", pojo.getTypeCode());
    param.put("quesId", pojo.getQuesLogId());
    param.put("traceId", pojo.getTraceId());
    HttpResult httpResult = router("quesbk", "getQuesAudioLogList", param);
    if (null != httpResult && httpResult.isResultOk()) {
      return FastJsonUtil.fromJson(httpResult.getContent(), QuesAudioLogListResponse.class)
          .toString();
    }
    return new QuesAudioLogListResponse(ResultType.SYSFAIL).toString();
  }

  public String getQuesAudioLogDetail(QuesAudioLogDetailPojo pojo) {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("deviceId", pojo.getDeviceId());
    param.put("typeCode", pojo.getTypeCode());
    param.put("quesId", pojo.getQuesLogId());
    param.put("traceId", pojo.getTraceId());
    HttpResult httpResult = router("quesbk", "getQuesAudioLogDetail", param);
    if (null != httpResult && httpResult.isResultOk()) {
      return FastJsonUtil.fromJson(httpResult.getContent(), QuesAudioLogResponse.class).toString();
    }
    return new QuesAudioLogResponse(ResultType.SYSFAIL).toString();
  }

  public String getAudioCount(PersonInfoRequest pojo) {
    String count = "0";
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("deviceId", pojo.getDeviceId());
    param.put("typeCode", pojo.getTypeCode());
    param.put("traceId", pojo.getTraceId());
    HttpResult httpResult = router("quesbk", "getAudioCount", param);
    if (null != httpResult && httpResult.isResultOk()) {
      QuesAudioLogCountResponse response =
          FastJsonUtil.fromJson(httpResult.getContent(), QuesAudioLogCountResponse.class);
      return response.getAudioCount();
    }
    return count;
  }


  public UserTaskCompleteResponse queryCompleteTaskList(TaskCompleteRequest request,
      ProductExamDetailResponse examDetail) {
    List<ExamDateDetail> courseList = examDetail.getExamDateDetail();
    Map<String, String> courseMap = Maps.newHashMap();
    if (null != courseList && courseList.size() > 0) {
      for (ExamDateDetail course : courseList) {
        courseMap.put(course.getCourseId().toString(), course.getCourseName());
      }
    }
    long examPassDays = examDetail.getIntervalsBetweenExam() - examDetail.getAwayNextExam();
    long startTime = 0l, deadDate = 0l;
    if (examPassDays >= 0l) startTime = DateUtil.MILLIS_PER_DAY * examPassDays;
    if (examPassDays >= 0l) deadDate = DateUtil.MILLIS_PER_DAY * examDetail.getAwayNextExam();
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", request.getUid());
    param.put("module", request.getModule());
    if(request.getUserFromWrapper().getMajorId() != null && StringUtils.isNotBlank(request.getUserFromWrapper().getMajorId())) {
      param.put("majorId", request.getUserFromWrapper().getMajorId());
    }
    param.put("courseMap", FastJsonUtil.toJson(courseMap));
    param.put("beginTime", System.currentTimeMillis() - startTime);
    param.put("endTime", deadDate + System.currentTimeMillis());
    HttpResult httpResult = router("mission", "taskCompleteList", param);
    if (null != httpResult && httpResult.getStatusCode() == 200) {
      return FastJsonUtil.fromJson(httpResult.getContent(), UserTaskCompleteResponse.class);
    }
    // 查询该用户的所有的课程是否完成称必做任务
    return new UserTaskCompleteResponse(ResultType.SYSFAIL);
  }
}
