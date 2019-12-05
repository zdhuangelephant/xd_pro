package com.xiaodou.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.service.QuesBaseServices;
import com.xiaodou.service.QuesChallengeService;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.vo.request.ChallengePojo;
import com.xiaodou.vo.request.CourseStatisticsPojo;
import com.xiaodou.vo.request.ExamDetailPojo;
import com.xiaodou.vo.request.ExamDetailPojo_v1_3_8;
import com.xiaodou.vo.request.ExamHisList;
import com.xiaodou.vo.request.ExamResultPojo;
import com.xiaodou.vo.request.PerformanceDetailPojo;
import com.xiaodou.vo.request.PkResultPojo;
import com.xiaodou.vo.request.QuesAudioLogDetailPojo;
import com.xiaodou.vo.request.QuesAudioLogListPojo;
import com.xiaodou.vo.request.QuesAudioLogPojo;
import com.xiaodou.vo.request.QuesBasePojo;
import com.xiaodou.vo.request.RealExamListPojo;
import com.xiaodou.vo.request.ScorePointRulePojo;

@Controller("quesBaseController")
@RequestMapping("/quesbk")
public class QuesBaseController extends BaseController {

  @Resource
  QuesBaseServices quesBaseServices;

  @Resource
  QuesChallengeService quesChallengeService;

  /**
   * 3 获取课程学习情况统计接口
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("course_statistics")
  public String courseStatistics(CourseStatisticsPojo pojo) {
    return quesBaseServices.courseStatistics(pojo).toString();
  }

  /**
   * 4 真题模考列表接口 · 说明 点击历年真题模拟接口进入真题模考列表。 · url /quesbk/real_exam_list · 参数 uid subjectId
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("real_exam_list")
  public String realExamList(RealExamListPojo pojo) {
    return quesBaseServices.realExamList(pojo).toString();
  }

  /**
   * 2017/11/13 兼容1.3.8版本以前接口 5 出题接口（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题；
   * 历年真题返回真题卷。 · url /quesbk/exam_detail · 参数 uid subjectId chapterId examType paperId
   * 
   * @param pojo
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("exam_detail_v1_3_8")
  public String examDetail_v1_3_8(ExamDetailPojo_v1_3_8 pojo) throws Exception {
    return quesBaseServices.examDetail_v1_3_8(pojo).toString();
  }

  /**
   * 5 出题接口（包括智能练习 组卷模考 章节练习） · 说明 智能练习、组卷模式、章节练习根据所选模式，触发相关组卷规则进行出题； 历年真题返回真题卷。 · url
   * /quesbk/exam_detail · 参数 uid subjectId chapterId examType paperId
   * 
   * @param pojo
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("exam_detail")
  public String examDetail(ExamDetailPojo pojo) throws Exception {
    return quesBaseServices.examDetail(pojo).toString();
  }

  /**
   * 6 提交答案接口 · 说明 上传用户做题详情返回给后台处理接口。 · url /quesbk/exam_result
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("exam_result")
  public String submitExamResult(ExamResultPojo pojo) {
    return quesBaseServices.submitExamResult(pojo).toString();
  }

  /**
   * 8 练习记录 · 说明 获取用户练习列表。 · url /quesbk/exam_his_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("exam_his_list")
  public String examHisList(ExamHisList pojo) {
    return quesBaseServices.examHisList(pojo).toString();
  }

  /**
   * 13 获取今日做题情况 · 说明 获取用户今天的做题情况统计。 · url /quesbk/daily_exam
   * 
   * @param pojo
   */
  /*
   * @ResponseBody
   * 
   * @RequestMapping("daily_exam") public String dailyExam(DailyExamPojo pojo) { return
   * quesBaseServices.dailyExam(pojo).toString(); }
   */

  /**
   * 14 发起挑战接口 · 説明 發起挑戰 · url /quesbk/challenge
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("challenge")
  public String challenge(ChallengePojo pojo) throws Exception {
    return quesChallengeService.challenge(pojo).toString();
  }

  /**
   * 15 获取挑战列表 · 説明 獲取個人挑戰列表 · url /quesbk/pk_list
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("pk_list")
  public String pkList(QuesBasePojo pojo) {
    return quesChallengeService.pkList(pojo).toString();
  }

  /**
   * 16 获取挑战结果 · 説明 獲取單次挑戰結果 · url /quesbk/pk_result
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("pk_result")
  public String pkResult(PkResultPojo pojo) {
    return quesChallengeService.pkResult(pojo).toString();
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
    return quesBaseServices.performanceDetail(pojo).toString();
  }

  /**
   * 14 提交主观题音频日志 · url /quesbk/quesVideoLogAdd
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_insert")
  public String quesAudioLogAdd(QuesAudioLogPojo pojo) throws Exception {
    return quesBaseServices.quesAudioLogAdd(pojo).toString();
  }

  /**
   * 15 查询主观题音频日志 · url /quesbk/ques_audio_log_list
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_list")
  public String getQuesAudioLogList(QuesAudioLogListPojo pojo) throws Exception {
    return quesBaseServices.getQuesAudioLogList(pojo).toString();
  }

  /**
   * 16 查询主观题音频日志 · url /quesbk/ques_audio_log_detail
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("ques_audio_log_detail")
  public String getQuesAudioLogDetail(QuesAudioLogDetailPojo pojo) throws Exception {
    return quesBaseServices.getQuesAudioLogDetail(pojo).toString();
  }

  /**
   * 16 查询日志数量· url /quesbk/ques_audio_log_detail
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("get_audio_count")
  public String getAudioCount(QuesAudioLogListPojo pojo) throws Exception {
    return quesBaseServices.getAudioCount(pojo).toString();
  }

  /**
   * 17 获取计分点规则详情· url /quesbk/score_point_rule
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("score_point_rule")
  public String scorePointRule(ScorePointRulePojo pojo) throws Exception {
    return quesBaseServices.scorePointRule(pojo).toString0();
  }
}
