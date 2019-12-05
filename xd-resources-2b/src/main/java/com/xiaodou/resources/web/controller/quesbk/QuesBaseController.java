package com.xiaodou.resources.web.controller.quesbk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.resources.request.quesbk.ExamDetailPojo;
import com.xiaodou.resources.request.quesbk.ExamHistoryPojo;
import com.xiaodou.resources.request.quesbk.ExamResultPojo;
import com.xiaodou.resources.request.quesbk.PerformanceDetailPojo;
import com.xiaodou.resources.service.quesbk.QuesBaseServices;
import com.xiaodou.summer.web.BaseController;

@Controller("quesBaseController")
@RequestMapping("/quesbk")
public class QuesBaseController extends BaseController {

  @Resource
  QuesBaseServices quesBaseServices;

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

  @ResponseBody
  @RequestMapping("exam_history")
  public String examHistory(ExamHistoryPojo pojo) throws Exception {
    return quesBaseServices.examHistory(pojo).toString();
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

}
