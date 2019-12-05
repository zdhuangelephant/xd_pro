package com.xiaodou.server.mapi.web.controller.selfTaught;

import static com.xiaodou.server.mapi.constant.SelfTaughtConstant.MODULE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.constant.SelfTaughtConstant;
import com.xiaodou.server.mapi.domain.ItemRecord;
import com.xiaodou.server.mapi.domain.NotesModel;
import com.xiaodou.server.mapi.enums.ProduceResType;
import com.xiaodou.server.mapi.request.ChapterCardListRequest;
import com.xiaodou.server.mapi.request.ChapterResourceRequest;
import com.xiaodou.server.mapi.request.CreateOrderRequest;
import com.xiaodou.server.mapi.request.GetAwardPojo;
import com.xiaodou.server.mapi.request.InitAwardPojo;
import com.xiaodou.server.mapi.request.MajorDetailByIdRequest;
import com.xiaodou.server.mapi.request.MajorDetailRequest;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.server.mapi.request.MedalListRequest;
import com.xiaodou.server.mapi.request.ModifyExamDateRequest;
import com.xiaodou.server.mapi.request.PayOrderRequest;
import com.xiaodou.server.mapi.request.PerformanceDetailPojo;
import com.xiaodou.server.mapi.request.PersonInfoRequest;
import com.xiaodou.server.mapi.request.ProductDetailRequest;
import com.xiaodou.server.mapi.request.UserListRequest;
import com.xiaodou.server.mapi.request.mission.TaskCompleteRequest;
import com.xiaodou.server.mapi.request.mission.TaskListRequest;
import com.xiaodou.server.mapi.request.mission.TaskStatusRequest;
import com.xiaodou.server.mapi.request.notes.NotesAddRequest;
import com.xiaodou.server.mapi.request.notes.NotesDelRequest;
import com.xiaodou.server.mapi.request.notes.NotesListRequest;
import com.xiaodou.server.mapi.request.payment.AliCallbackRequest;
import com.xiaodou.server.mapi.request.payment.AppleCallbackRequest;
import com.xiaodou.server.mapi.request.product.LearnRecordRequest;
import com.xiaodou.server.mapi.request.product.LearnRecordViewRequest;
import com.xiaodou.server.mapi.request.product.UserLearnExamDateRequest;
import com.xiaodou.server.mapi.request.product.UserLearnScoreDateRequest;
import com.xiaodou.server.mapi.request.product.UserNotValidCourseRequest;
import com.xiaodou.server.mapi.request.quesbk.ChallengeRequest;
import com.xiaodou.server.mapi.request.quesbk.DailyExamPojo;
import com.xiaodou.server.mapi.request.quesbk.ExamDetailRequest;
import com.xiaodou.server.mapi.request.quesbk.ExamResultRequest;
import com.xiaodou.server.mapi.request.quesbk.FaceRecognitionRequest;
import com.xiaodou.server.mapi.request.quesbk.PkListRequest;
import com.xiaodou.server.mapi.request.quesbk.PkResultRequest;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogDetailPojo;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogListPojo;
import com.xiaodou.server.mapi.request.quesbk.QuesAudioLogPojo;
import com.xiaodou.server.mapi.request.quesbk.QuesDetailRequest;
import com.xiaodou.server.mapi.request.quesbk.QuesListRequest;
import com.xiaodou.server.mapi.request.quesbk.RaiseWrongQuesRequest;
import com.xiaodou.server.mapi.request.quesbk.ScorePointRuleRequest;
import com.xiaodou.server.mapi.request.quesbk.StoreQuesRequest;
import com.xiaodou.server.mapi.request.quesbk.UnQuesRequest;
import com.xiaodou.server.mapi.request.trade.TradeDetailRequest;
import com.xiaodou.server.mapi.request.ucenter.UserRankRequest;
import com.xiaodou.server.mapi.response.forum.ForumListResponse;
import com.xiaodou.server.mapi.response.notes.NotesListResponse;
import com.xiaodou.server.mapi.response.product.AddCourseListResponse;
import com.xiaodou.server.mapi.response.product.ChapterCardListResponse;
import com.xiaodou.server.mapi.response.product.LearnRecordViewResponse;
import com.xiaodou.server.mapi.response.product.MajorDetailResponse;
import com.xiaodou.server.mapi.response.product.ProductDetailResponse;
import com.xiaodou.server.mapi.response.product.ProductExamDetailResponse;
import com.xiaodou.server.mapi.response.product.RecordDetailResponse;
import com.xiaodou.server.mapi.response.product.UserChapterRecordResponse;
import com.xiaodou.server.mapi.response.product.UserLearnRecordDataResponse;
import com.xiaodou.server.mapi.response.product.UserLearnRecordDataResponse.LearnRecordData;
import com.xiaodou.server.mapi.response.product.UserTaskCompleteResponse;
import com.xiaodou.server.mapi.response.quesbk.ChallengeInfo;
import com.xiaodou.server.mapi.response.quesbk.CourseStatisticsInfo;
import com.xiaodou.server.mapi.response.quesbk.DailyExamResponse;
import com.xiaodou.server.mapi.response.quesbk.ScorePointRuleResponse;
import com.xiaodou.server.mapi.response.quesbk.StoreQuesDetailResponse;
import com.xiaodou.server.mapi.response.quesbk.StoreQuesListResponse;
import com.xiaodou.server.mapi.response.quesbk.WrongQuesDetailResponse;
import com.xiaodou.server.mapi.response.quesbk.WrongQuesListResponse;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.selftaught.ChallengeResponse;
import com.xiaodou.server.mapi.response.selftaught.ExamResultResponse;
import com.xiaodou.server.mapi.response.selftaught.ExamResultResponse.Rank;
import com.xiaodou.server.mapi.response.selftaught.HomeResponse;
import com.xiaodou.server.mapi.response.selftaught.MedalListResponse;
import com.xiaodou.server.mapi.response.selftaught.MedalListResponse.MedalDetail;
import com.xiaodou.server.mapi.response.selftaught.PkListResponse;
import com.xiaodou.server.mapi.response.selftaught.PkListResponse.PkRecord;
import com.xiaodou.server.mapi.response.selftaught.PkResultResponse;
import com.xiaodou.server.mapi.response.selftaught.UserNotValidCourseResponse;
import com.xiaodou.server.mapi.response.ucenter.PersonInfoResponse;
import com.xiaodou.server.mapi.response.ucenter.RetentionResponse;
import com.xiaodou.server.mapi.response.ucenter.UserListResponse;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.service.selftaught.SelfTaughtService;
import com.xiaodou.server.mapi.util.HtmlRegexpUtil;
import com.xiaodou.server.mapi.vo.request.ForumListRequest;
import com.xiaodou.server.mapi.web.controller.BaseMapiController;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.service.WalletService;

@Controller("selfTaughtController")
@RequestMapping("selftaught")
public class SelfTaughtController extends BaseMapiController {

  @Resource
  SelfTaughtService selfTaughtService;
  @Resource
  WalletService walletService;

  /**
   * 1 获取个人详情信息。 · url /selftaught/person_info
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("person_info")
  public String personInfo(PersonInfoRequest pojo) {
    PersonInfoResponse response = new PersonInfoResponse(ResultType.SUCCESS);
    // 低版本
    // 如果是低版本并且之前选好的地域不是北京
    if (pojo.getVersion().compareTo("1.5.0") < 0) {
      if (StringUtils.isNotEmpty(pojo.getRegionFromUser())
          && !MODULE.equals(pojo.getRegionFromUser())) {
        response = new PersonInfoResponse(UcenterResType.UnCompleteInfo);
      }
    }
    // 获取学习耗时/连续学习天数/我的课程列表信息
    response = selfTaughtService.personProductInfo(pojo);
    // 1、获取用户信息
    response.setPersonInfo(pojo.getUserFromWrapper());
    response.setAudioCount(selfTaughtService.getAudioCount(pojo));
    // 4、获取用户未读消息数
    Map<String, Object> param = Maps.newHashMap();
    param.putAll(pojo.getUserBaseParams());
    HttpResult result = router("ucenter", "unreadMesCount", param);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      PersonInfoResponse unreadMesCount =
          FastJsonUtil.fromJson(result.getContent(), PersonInfoResponse.class);
      response.setNoticeCount(unreadMesCount.getNoticeCount());
      response.setSysMesCount(unreadMesCount.getSysMesCount());
    }
    return response.toString();
  }

  @RequestMapping("/list_not_valid_course")
  @ResponseBody
  public String listNotValidCourse(UserNotValidCourseRequest pojo) {
    UserNotValidCourseResponse response = new UserNotValidCourseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("upperId", pojo.getUpperId());
    HttpResult result = router("product", "listNotValidCourse", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), UserNotValidCourseResponse.class);
    }
    return response.toJsonString();
  }

  /**
   * 2、章节卡片接口 获取指定章的节卡片列表。 · url /product/chapter_card_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("chapter_card_list")
  public String chapterCardList(ChapterCardListRequest pojo) {

    // 请求章节卡片
    ChapterCardListResponse response = selfTaughtService.chapterCardList(pojo);
    if (response.isRetOk()) {
      // 请求闯关汇总情况
      UserListRequest userListReq = new UserListRequest();
      for (ItemRecord itemRecord : response.getItemList())
        userListReq.getUserIdList().addAll(itemRecord.getTopUserList());
      Map<String, Map<String, Object>> userMap = Maps.newHashMap();
      UserListResponse userList = selfTaughtService.queryUserListInfo(userListReq);
      for (Map<String, Object> userModel : userList.getUserModelList())
        userMap.put(userModel.get("id").toString(), userModel);
      for (ItemRecord itemRecord : response.getItemList()) {
        List<String> portraitList = Lists.newArrayList();
        for (String userId : itemRecord.getTopUserList())
          if (null != userMap.get(userId) && null != userMap.get(userId).get("portrait"))
            portraitList.add(userMap.get(userId).get("portrait").toString());
        itemRecord.setTopUserList(portraitList);
      }
      response.setCredit(pojo.getUserFromWrapper().getCredit());
    }
    return response.toString();
  }


  /**
   * 6、章节修炼接口 获取本课程视频和评论列表 。 · url /selftaught/chapter_resource
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("chapter_resource")
  public String chapterResource(ChapterResourceRequest pojo) {
    return selfTaughtService.chappterResource(pojo).toString0();
  }

  @ResponseBody
  @RequestMapping("chapter_audio")
  public String chapterAudio(ChapterResourceRequest pojo) {
    return selfTaughtService.chapterAudio(pojo).toString0();
  }

  /**
   * 8、 获取PK记录列表。 · url /selftaught/pk_list
   * 
   * @param pojo
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("pk_list")
  public String pkList(PkListRequest pojo) throws Exception {
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("typeCode", pojo.getTypeCode());
    param.put("courseId", pojo.getCourseId());
    HttpResult examResult = router("quesbk", "pkList", param);
    if (null != examResult && examResult.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(examResult.getContent())) {
      PkListResponse pkList = FastJsonUtil.fromJson(examResult.getContent(), PkListResponse.class);
      if (pkList.isRetOk()) {
        UserListRequest userIdListReq = new UserListRequest();
        Map<String, List<PkRecord>> recordMap = Maps.newHashMap();
        for (PkRecord record : pkList.getList()) {
          userIdListReq.getUserIdList().add(record.getUserId());
          List<PkRecord> recordList = recordMap.get(record.getUserId());
          if (null == recordList) {
            recordList = Lists.newArrayList();
            recordMap.put(record.getUserId(), recordList);
          }
          recordList.add(record);
        }
        UserListResponse userListResponse = selfTaughtService.queryUserListInfo(userIdListReq);
        for (Map<String, Object> userInfo : userListResponse.getUserModelList()) {
          List<PkRecord> recordList = recordMap.get(userInfo.get("userId").toString());
          if (null == recordList || recordList.size() == 0) continue;
          for (PkRecord record : recordList) {
            record.setUserInfo(userInfo);
          }
        }
        return pkList.toString();
      } else {
        return pkList.toString();
      }
    } else {
      return new ExamResultResponse(ResultType.SYSFAIL).toString();
    }
  }

  /**
   * 9、 获取pk结果。 · url /selftaught/pk_result
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("pk_result")
  public String pkResult(PkResultRequest pojo) {
    PkResultResponse response = new PkResultResponse(ResultType.SUCCESS);
    Map<String, Object> param = Maps.newHashMap();
    param.put("uid", pojo.getUid());
    param.put("module", pojo.getModule());
    param.put("typeCode", pojo.getTypeCode());
    param.put("recordId", pojo.getRecordId());
    HttpResult pkResult = router("quesbk", "pkResult", param);
    if (null != pkResult && pkResult.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(pkResult.getContent())) {
      PkResultResponse exam = FastJsonUtil.fromJson(pkResult.getContent(), PkResultResponse.class);
      if (exam.isRetOk()) {
        response.setScore(exam.getScore());
        response.setOpponentsScore(exam.getOpponentsScore());
        response.setTotalCount(exam.getTotalCount());
        response.setRightCount(exam.getRightCount());
        response.setCoinUpper(exam.getCoinUpper());
        response.setCreditUpper(exam.getCreditUpper());
        response.setStarLevel(exam.getStarLevel());
        response.setWinner(exam.getWinner());
        response.setRankList(exam.getRankList());
        response.setPkExam(exam.getPkExam());
      } else {
        return exam.toString();
      }
    } else {
      response = new PkResultResponse(ResultType.SYSFAIL);
    }

    // 2 获取排名用戶信息
    UserListRequest request = new UserListRequest();
    for (Rank rank : response.getRankList()) {
      request.getUserIdList().add(rank.getUserId());
    }
    UserListResponse userList = selfTaughtService.queryUserListInfo(request);
    if (userList.isRetOk() && userList != null && userList.getUserModelList() != null
        && userList.getUserModelList().size() > 0) {
      Map<String, Map<String, Object>> userMap = Maps.newHashMap();
      for (Map<String, Object> user : userList.getUserModelList()) {
        userMap.put(user.get("id").toString(), user);
      }
      for (Rank rank : response.getRankList()) {
        rank.setInfo(userMap.get(rank.getUserId()));
      }
    }
    return response.toString();
  }

  /**
   * 10、 发起挑战。 · url /selftaught/challenge
   * 
   * @param pojo
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  @ResponseBody
  @RequestMapping("challenge")
  public String challenge(ChallengeRequest pojo) throws Exception {
    ChallengeResponse response = new ChallengeResponse(ResultType.SUCCESS);
    ChallengeInfo challengeInfo = selfTaughtService.challenge(pojo);
    if (!challengeInfo.isRetOk()) return challengeInfo.toString();
    response.setChallengeInfo(challengeInfo);
    if (StringUtils.isNotBlank(challengeInfo.getTargetUserId())) {
      UserListRequest request = new UserListRequest();
      request.getUserIdList().add(challengeInfo.getTargetUserId());
      UserListResponse userList = selfTaughtService.queryUserListInfo(request);
      if (userList.isRetOk() && userList != null && userList.getUserModelList() != null
          && userList.getUserModelList().size() > 0) {
        response.setTargetUserInfo(userList.getUserModelList().get(0));
      }
    }
    return response.toString();
  }

  /**
   * 12、 获取任务列表。 · url /selftaught/task_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("task_list")
  public String taskList(TaskListRequest pojo) {
    ProductExamDetailResponse courseDetail = selfTaughtService.productExamDetail(pojo);
    if (courseDetail.isRetOk())
      return selfTaughtService.queryTaskList(pojo, courseDetail).toString();
    return courseDetail.toString();
  }

  /**
   * 13、 获取任务状态。 · url /selftaught/task_status
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("task_status")
  public String taskStatus(TaskStatusRequest pojo) {
    return selfTaughtService.queryTaskStatus(pojo).toString();
  }

  /**
   * 26、 首页。 · url /selftaught/home
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("home")
  public String home(MapiBaseRequest pojo) {
    HomeResponse response = new HomeResponse(ResultType.SUCCESS);
    // 低版本
    // 如果是低版本并且之前选好的地域不是北京
    if (pojo.getVersion().compareTo("1.5.0") < 0) {
      if (StringUtils.isNotEmpty(pojo.getRegionFromUser())
          && !MODULE.equals(pojo.getRegionFromUser())) {
        response = new HomeResponse(UcenterResType.UnCompleteInfo);
      }
    }
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    HttpResult result = router("product", "home", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), HomeResponse.class);
    } else {
      response = new HomeResponse(ResultType.SYSFAIL);
    }
    response.setUserInfo(pojo.getUserFromWrapper());
    response.getUserInfo().setMajorName(response.getMajorName());
    // 2.获取倒计时
    // ProductExamDetailResponse courseDetail = selfTaughtService.productExamDetail(pojo);
    // if (courseDetail.isRetOk() && null != courseDetail.getAwayNextExam()) {
    // response.setCountDownTime(courseDetail.getAwayNextExam().toString());
    // }
    // 3、获取未完成任务量
    // ProductExamDetailResponse courseDetail = selfTaughtService.productExamDetail(pojo);
    // if (courseDetail.isRetOk()) {
    // UnReachedTaskCountResponse res = selfTaughtService.unReachTask(pojo, courseDetail);
    // if (res.isRetOk()) response.setUnReachedCount(res.getUnReachedMissionCount());
    // }
    return response.toString();
  }

  /**
   * 专业知识分享列表
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("cat_forum_list")
  public String forumList(ForumListRequest pojo) {
    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("forumId", pojo.getForumId());
    HttpResult result = router("product", "forumList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), ForumListResponse.class);
    } else {
      response = new ForumListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 打卡
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("sign")
  public String sign(MapiBaseRequest pojo) {
    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    HttpResult result = router("product", "sign", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), ForumListResponse.class);
    } else {
      response = new ForumListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 章节列表
   */
  @ResponseBody
  @RequestMapping("chapter_list")
  public String chapterList(ProductDetailRequest pojo) {
    UserChapterRecordResponse response = new UserChapterRecordResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("product", "chapterList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), UserChapterRecordResponse.class);
    } else {
      response = new UserChapterRecordResponse(ResultType.SYSFAIL);
    }
    if (!response.isRetOk()) return response.toString();
    // 查询练习情况
    CourseStatisticsInfo statistics = selfTaughtService.courseStatistics(params);
    if (statistics.isRetOk()) {
      response.setStatistics(statistics);
    } else {
      return statistics.toString();
    }
    return response.toString();
  }

  /**
   * 14、出题接口 （包括闯关 挑战 排位赛）。 · url /selftaught/exam_detailc
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("exam_detail")
  public String examDetail(ExamDetailRequest pojo) {
    return selfTaughtService.examDetail(pojo).toJsonString();
  }

  /**
   * 15、提交答案接口 上传用户做题详情返回给后台处理接口。 · url /selftaught/exam_result
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("exam_result")
  public String examResult(ExamResultRequest pojo) {
    ExamResultResponse response = new ExamResultResponse(ResultType.SUCCESS);
    // 1 提交答题情况
    Map<String, Object> params = Maps.newHashMap();
    params.put("traceId", pojo.getTraceId());
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("userCredit", pojo.getUserFromWrapper().getCredit());
    params.put("courseId", pojo.getCourseId());
    params.put("paperId", pojo.getPaperId());
    params.put("examStatus", pojo.getExamStatus());
    params.put("examTime", pojo.getExamTime());
    params.put("examDetail", FastJsonUtil.toJson(pojo.getExamDetail()));
    params.put("recordId", pojo.getRecordId());
    params.put("nextChapterSummery", pojo.getNextChapterSummery());
    HttpResult examResult = router("quesbk", "examResult", params);
    if (null != examResult && examResult.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(examResult.getContent())) {
      ExamResultResponse exam =
          FastJsonUtil.fromJson(examResult.getContent(), ExamResultResponse.class);
      if (exam.isRetOk()) {
        response.setScore(exam.getScore());
        response.setCoinUpper(exam.getCoinUpper());
        response.setCreditUpper(exam.getCreditUpper());
        response.setStarLevel(exam.getStarLevel());
        response.setWinner(exam.getWinner());
        response.setRankList(exam.getRankList());
        response.setPkExam(exam.getPkExam());
        response.setCompleteCount(exam.getCompleteCount());
        response.setUniqueId(exam.getUniqueId());
        response.setLock(exam.getLock());
        response.setUserCredit(exam.getUserCredit());
        response.setUniqueId(exam.getUniqueId());
        response.setOpponentsScore(exam.getOpponentsScore());
      } else {
        return exam.toString();
      }
    } else {
      response = new ExamResultResponse(ResultType.SYSFAIL);
    }

    // 2 获取排名用戶信息
    UserListRequest request = new UserListRequest();
    for (Rank rank : response.getRankList()) {
      request.getUserIdList().add(rank.getUserId());
    }
    UserListResponse userList = selfTaughtService.queryUserListInfo(request);
    if (userList.isRetOk() && userList != null && userList.getUserModelList() != null
        && userList.getUserModelList().size() > 0) {
      Map<String, Map<String, Object>> userMap = Maps.newHashMap();
      for (Map<String, Object> user : userList.getUserModelList()) {
        userMap.put(user.get("id").toString(), user);
      }
      for (Rank rank : response.getRankList()) {
        rank.setInfo(userMap.get(rank.getUserId()));
      }
    }
    return response.toString();
  }

  /**
   * 16、错题集列表 获取用户错题记录集列表。。 · url /selftaught/wrong_ques_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("wrong_ques_list")
  public String wrongQuesList(QuesListRequest pojo) {
    WrongQuesListResponse response = new WrongQuesListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("quesbk", "wrongQuesList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), WrongQuesListResponse.class);
    } else {
      response = new WrongQuesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 错题集详情 获取用户错题记录集详情。 · url /selftaught/wrong_ques_detail
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("wrong_ques_detail")
  public String wrongQuesDetail(QuesDetailRequest pojo) {
    WrongQuesDetailResponse response = new WrongQuesDetailResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("itemId", pojo.getItemId());
    params.put("chapterId", pojo.getChapterId());
    params.put("type", pojo.getType());
    HttpResult result = router("quesbk", "wrongQuesDetail", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), WrongQuesDetailResponse.class);
    } else {
      response = new WrongQuesDetailResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 19、修改错题状态。。 · url /selftaught/unwrong_ques
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("change_wrong_ques")
  public String changeWrongQues(UnQuesRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("majorId", pojo.getUserFromWrapper().getMajorId());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("quesId", pojo.getQuesId());
    params.put("type", pojo.getType());
    HttpResult result = router("quesbk", "changeWrongQues", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), WrongQuesListResponse.class);
    } else {
      response = new WrongQuesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 举报错题题目 用户举报错误题目。。 · url /selftaught/raise_wrong_ques
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("raise_wrong_ques")
  public String raiseWrongQues(RaiseWrongQuesRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("chapterId", pojo.getChapterId());
    params.put("quesId", pojo.getQuesId());
    params.put("wrongType", pojo.getWrongType());
    params.put("wrongMsg", pojo.getWrongMsg());
    HttpResult result = router("quesbk", "raiseWrongQues", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new WrongQuesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 收藏题目 收藏题目到个人收藏列表。 · url /selftaught/store_ques
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("store_ques")
  public String storeQues(StoreQuesRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("quesId", pojo.getQuesId());
    params.put("type", pojo.getType());
    HttpResult result = router("quesbk", "storeQues", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), WrongQuesListResponse.class);
    } else {
      response = new WrongQuesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 获取收藏列表 根据用户ID和课程ID获取个人收藏列表。。 · url /selftaught/store_ques_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("store_ques_list")
  public String storeQuesList(QuesListRequest pojo) {
    StoreQuesListResponse response = new StoreQuesListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("quesbk", "storeQuesList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), StoreQuesListResponse.class);
    } else {
      response = new StoreQuesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 收藏详情 获取用户收藏记录集详情。。 · url /selftaught/store_ques_detail
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("store_ques_detail")
  public String storeQuesDetail(QuesDetailRequest pojo) {
    StoreQuesDetailResponse response = new StoreQuesDetailResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("chapterId", pojo.getChapterId());
    params.put("itemId", pojo.getItemId());
    params.put("type", pojo.getType());
    HttpResult result = router("quesbk", "storeQuesDetail", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), StoreQuesDetailResponse.class);
    } else {
      response = new StoreQuesDetailResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 18、更改收藏题目 更改收藏题目到个人收藏列表。。 · url /selftaught/change_store_ques
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("change_store_ques")
  public String changeStoreQues(StoreQuesRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("quesId", pojo.getQuesId());
    params.put("type", pojo.getType());
    HttpResult result = router("quesbk", "changeStoreQues", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 13 获取今日做题情况 · 说明 获取用户今天的做题情况统计。 · url /quesbk/daily_exam
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_record")
  public String dailyExam(DailyExamPojo pojo) {
    DailyExamResponse response = new DailyExamResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("quesbk", "learnRecord", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), DailyExamResponse.class);
    } else {
      response = new DailyExamResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 19、勋章列表
   * 
   */
  @ResponseBody
  @RequestMapping("medal_list")
  public String medalList(MedalListRequest pojo) {
    MedalListResponse response = new MedalListResponse(ResultType.SUCCESS);
    response = selfTaughtService.queryMedalList(pojo);
    if (response.isRetOk()) {
      UserModelResponse user = pojo.getUserFromWrapper();
      for (MedalDetail medal : response.getMedalList()) {
        if (StringUtils.isNotBlank(medal.getMedalId())
            && medal.getMedalId().equals(user.getMedalId()))
          medal.setIsUsed(SelfTaughtConstant.TRUE.toString());
      }
    }
    return response.toString();
  }

  /**
   * 获取学籍信息接口。 · url /selftaught/retention
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("retention")
  public String retention(MapiBaseRequest pojo) {
    UserModelResponse user = pojo.getUserFromWrapper();
    RetentionResponse response = new RetentionResponse(ResultType.SUCCESS, user);
    return response.toString();
  }

  /**
   * 课程详情
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("course_detail")
  public String courseDetail(ProductDetailRequest pojo) {
    ProductDetailResponse response = new ProductDetailResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("product", "courseDetail", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), ProductDetailResponse.class);
    } else {
      response = new ProductDetailResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 专业介绍接口。 · url /selftaught/major_detail
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("major_detail")
  public String majorDetail(MajorDetailRequest pojo) {
    MajorDetailResponse response = new MajorDetailResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("majorId", pojo.getMajorId());
    HttpResult result = router("product", "majorDetail", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), MajorDetailResponse.class);

    } else {
      response = new MajorDetailResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 修改课程考期接口。 · url /selftaught/modify_exam_date
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("modify_exam_date")
  public String modifyExamDate(ModifyExamDateRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("moveType", pojo.getMoveType());
    HttpResult result = router("product", "modifyExamDate", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 课程页课程列表展示  添加课程页面中展示的课程为本专业中未开通过的课程
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("course_list")
  public String courseList(MapiBaseRequest pojo) {
    AddCourseListResponse response = new AddCourseListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    HttpResult result = router("product", "addCourseList", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), AddCourseListResponse.class);
    } else {
      response = new AddCourseListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 学习记录/计时
   * 
   * @param recordRequest
   * @return
   */
  @RequestMapping("/study_record_time")
  @ResponseBody
  public String studyRecordTime(LearnRecordRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("chapterId", pojo.getChapterId());
    params.put("itemId", pojo.getItemId());
    params.put("learnType", pojo.getLearnType());
    params.put("learnTime", pojo.getLearnTime());
    params.put("points", pojo.getPoints());
    HttpResult result = router("product", "studyRecordTime", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 学习记录,学习统计
   * 
   * @param LearnRecordViewRequest pojo
   * @return
   */
  @RequestMapping("/learn_record_view")
  @ResponseBody
  public String learnRecordView(LearnRecordViewRequest pojo) {
    LearnRecordViewResponse response = new LearnRecordViewResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    HttpResult result = router("quesbk", "learnRecordView", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), LearnRecordViewResponse.class);
      if (response.isRetOk()) {
        HttpResult result_ = router("product", "learnRecordTimeStatisticList", params);
        if (null != result_ && result_.getStatusCode() == HttpResultStatus.OK.getCode()
            && StringUtils.isJsonNotBlank(result_.getContent())) {
          UserLearnRecordDataResponse response_ =
              FastJsonUtil.fromJson(result_.getContent(), UserLearnRecordDataResponse.class);
          if (response_.isRetOk()) {
            List<LearnRecordData> learnList = response_.getLearnList();
            for (LearnRecordData learnRecordData : learnList) {
              if (StringUtils.isNotBlank(learnRecordData.getRecordTime())
                  && learnRecordData.getRecordTime().length() >= 5)
                learnRecordData.setRecordTime(learnRecordData.getRecordTime().substring(5));
            }
            response.setLearnList(learnList);
          }
        }
        HttpResult _result = router("product", "recordDetail", params);
        if (null != _result && _result.getStatusCode() == HttpResultStatus.OK.getCode()
            && StringUtils.isJsonNotBlank(_result.getContent())) {
          RecordDetailResponse _response =
              FastJsonUtil.fromJson(_result.getContent(), RecordDetailResponse.class);
          if (_response.isRetOk()) {
            response.setAchieveStarCount(_response.getAchieveStarCount());
            response.setTotalStarCount(_response.getTotalStarCount());
            response.setChallengeLevelCount(_response.getChallengeLevelCount());
            response.setTotalLevelCount(_response.getTotalLevelCount());
          }
        }
      }
    } else {
      response = new LearnRecordViewResponse(ResultType.SYSFAIL);
    }
    UserModelResponse user = pojo.getUserFromWrapper();
    response.setAdmissionCardCode(user.getAdmissionCardCode());
    String realName = user.getRealName();
    if (StringUtils.isBlank(realName)) realName = user.getNickName();
    response.setRealName(realName);
    return response.toString();
  }

  /**
   * 由学习统计页面 进入的每日学习时长数据
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_record_time_detail_list")
  public String learnExamDateList(UserLearnExamDateRequest pojo) {
    return selfTaughtService.learnExamDateList(pojo).toString();
  }

  /**
   * 由学习统计页面 进入的每日得分（成绩）
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_score_date_list")
  public String learnScoreDateList(UserLearnScoreDateRequest pojo) {
    return selfTaughtService.learnScoreDateList(pojo).toString();
  }

  /**
   * 1、创建课程产品订单
   * 
   * @param request
   * @return
   */
  @RequestMapping("/create_order")
  @ResponseBody
  public String createOrder(CreateOrderRequest request) {
    return selfTaughtService.createOrder(request).toString();
  }

  /**
   * 2、支付课程产品订单
   * 
   * @param request
   * @return
   */
  @RequestMapping("/pay")
  @ResponseBody
  public String pay(PayOrderRequest request) {
    return selfTaughtService.pay(request).toString();
  }


  /**
   * 17 获取成绩详情
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("performance_detail")
  public String performanceDetail(PerformanceDetailPojo pojo) {
    return selfTaughtService.performanceDetail(pojo).toString();
  }

  /**
   * 18 查看红包奖励
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("init_award")
  @ResponseBody
  public String initAward(InitAwardPojo pojo) {
    return selfTaughtService.initAward(pojo).toString();
  }

  /**
   * 19 获取红包奖励
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("get_award")
  @ResponseBody
  public String getAward(GetAwardPojo pojo) {
    return selfTaughtService.getAward(pojo).toString();
  }

  /**
   * 20 苹果支付结果回调
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("apple_call_back")
  @ResponseBody
  public String appleCallBack(AppleCallbackRequest pojo) {
    return selfTaughtService.appleCallBack(pojo).toString();
  }

  /**
   * 20 支付宝支付结果回调（notify_url）
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("ali_call_back")
  @ResponseBody
  public String alipayCallBack(AliCallbackRequest pojo) {
    return selfTaughtService.aliCallBack(pojo).toString();
  }

  @RequestMapping("/user_protocol")
  public ModelAndView userProtocol() {
    ModelAndView view = new ModelAndView();
    view.setViewName("/user_protocol");
    return view;
  }

  @RequestMapping("/help")
  public ModelAndView help() {
    ModelAndView mv = new ModelAndView("/help");
    ScorePointRuleRequest request = new ScorePointRuleRequest();
    // 旧版本兼容,旧版本没有传入header头
    if (null == request.getVersion() || (request.getVersion().compareTo("1.5.0")) < 0) {
      return mv;
    }
    ScorePointRuleResponse ruleResponse = selfTaughtService.scorePointRule(request);
    if (null != ruleResponse && null != ruleResponse.getScorePointRule()
        && StringUtils.isNotBlank(ruleResponse.getScorePointRule().getRuleDesc())) {
      mv.addObject("scoreRuleDesc", ruleResponse.getScorePointRule().getRuleDesc());
    }
    return mv;
  }

  /**
   * 28、排行榜 排位赛列表接口。 · url /selftaught/rank_list
   * 
   * @param pojo
   */
  @RequestMapping("/rank_list")
  @ResponseBody
  public String rankList(UserRankRequest pojo) {
    // RankListResponse response = new RankListResponse(ResultType.SUCCESS);
    return selfTaughtService.rankList(pojo).toString();
  }

  /**
   * 查询交易记录列表
   * 
   * @param pojo 查询交易记录列表参数类
   * @return 交易记录列表
   */
  @RequestMapping("/account_detail")
  @ResponseBody
  public String tradeDetail(TradeDetailRequest pojo) {
    return selfTaughtService.tradeDetail(pojo);
  }

  /**
   * 笔记增加
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("notes_add")
  public String notesAdd(NotesAddRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("courseId", pojo.getCourseId());
    params.put("chapterId", pojo.getChapterId());
    params.put("itemId", pojo.getItemId());
    params.put("content", pojo.getContent());
    params.put("source", pojo.getSource());
    params.put("resourcesName", pojo.getResourcesName());
    HttpResult result = router("product", "notes_add", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 查询笔记
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("notes_list")
  public String notesList(NotesListRequest pojo) {
    NotesListResponse response = new NotesListResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("notesId", pojo.getNotesId());
    params.put("courseStatus", pojo.getCourseStatus());
    HttpResult result = router("product", "notes_list", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), NotesListResponse.class);
      for (NotesModel notes : response.getNotes()) {
        if (null == notes || null == notes.getCreateTime()
            || StringUtils.isBlank(notes.getContent())) continue;
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        notes.setTime(sdf.format(notes.getCreateTime()));
        notes.setContent(HtmlRegexpUtil.filterHtml(notes.getContent()));
      }
    } else {
      response = new NotesListResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 删除笔记
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("notes_del")
  public String notesDel(NotesDelRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("uid", pojo.getUid());
    params.put("module", pojo.getModule());
    params.put("typeCode", pojo.getTypeCode());
    params.put("notesId", pojo.getNotesId());
    HttpResult result = router("product", "notes_del", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), BaseResponse.class);
    } else {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 专业介绍接口。 · url /selftaught/major_detail_by_id
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("major_detail_by_id")
  public String majorDetailById(MajorDetailByIdRequest pojo) {
    MajorDetailResponse response = new MajorDetailResponse(ResultType.SUCCESS);
    Map<String, Object> params = Maps.newHashMap();
    params.put("majorId", pojo.getMajorId());
    params.put("module", pojo.getModule());
    HttpResult result = router("product", "majorDetailById", params);
    if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
        && StringUtils.isJsonNotBlank(result.getContent())) {
      response = FastJsonUtil.fromJson(result.getContent(), MajorDetailResponse.class);

    } else {
      response = new MajorDetailResponse(ResultType.SYSFAIL);
    }
    return response.toString();
  }

  /**
   * 人脸识别验证接口
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("/face_recognition")
  public String faceRecognition(FaceRecognitionRequest pojo) {
    return selfTaughtService.faceRecognition(pojo);
  }

  /**
   * 提交主观题音频日志 ·
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_insert")
  public String quesAudioLogAdd(QuesAudioLogPojo pojo) throws Exception {
    return selfTaughtService.quesAudioLogAdd(pojo);
  }

  /**
   * 查询主观题音频日志 ·
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_list")
  public String getQuesAudioLogList(QuesAudioLogListPojo pojo) throws Exception {
    return selfTaughtService.getQuesAudioLogList(pojo);
  }

  /**
   * 查询主观题音频日志 ·
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_detail")
  public String getQuesAudioLogDetail(QuesAudioLogDetailPojo pojo) throws Exception {
    return selfTaughtService.getQuesAudioLogDetail(pojo);
  }

  @RequestMapping(value = "/learning_weekly")
  public ModelAndView learningWeekly() {
    ModelAndView mv = new ModelAndView("/weekly/index");
    return mv;
  }


  /**
   * 查看一个考期内必做任务完成日期。 · url /selftaught/complete_task_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("task_complete_list")
  public String taskCompleteList(TaskCompleteRequest pojo) {
    UserTaskCompleteResponse response = new UserTaskCompleteResponse(ResultType.SYSFAIL);
    ProductExamDetailResponse taskDetails = selfTaughtService.productExamDetail(pojo);
    if(null == taskDetails || taskDetails.getExamDateDetail().isEmpty()) {
      return new UserTaskCompleteResponse(ProduceResType.NothingOwnProduct).toString();
    }
    if (taskDetails.isRetOk())
      return selfTaughtService.queryCompleteTaskList(pojo, taskDetails).toString();
    return response.toString();
  }

}
