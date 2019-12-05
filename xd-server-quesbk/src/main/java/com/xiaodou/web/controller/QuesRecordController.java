package com.xiaodou.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.service.QuesRecordService;
import com.xiaodou.vo.request.DailyExamPojo;
import com.xiaodou.vo.request.LearnRecordViewRequest;
import com.xiaodou.vo.request.UserLearnExamDateRequest;
import com.xiaodou.vo.request.UserLearnScoreDateRequest;

@Controller("quesRecordController")
@RequestMapping("/quesbk")
public class QuesRecordController {

  @Resource
  QuesRecordService QuesRecordService;

  /**
   * 每日天学习记录 url /quesbk/daily_exam
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("daily_exam")
  public String dailyExam(DailyExamPojo pojo) {
    return QuesRecordService.dailyExam(pojo).toString();
  }

  /**
   * 学习统计页面 url /quesbk/learn_record_view
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_record_view")
  public String learnRecordView(LearnRecordViewRequest pojo) {
    return QuesRecordService.learnRecordView(pojo).toString();
  }

  /**
   * 由学习统计页面 进入的每日学习时长数据 url /quesbk/learn_exam_date_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_exam_date_list")
  public String learnExamDateList(UserLearnExamDateRequest pojo) {
    return QuesRecordService.learnExamDateList(pojo).toString();
  }

  /**
   * 由学习统计页面 进入的每日得分（成绩） url /quesbk/learn_score_date_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("learn_score_date_list")
  public String learnScoreDateList(UserLearnScoreDateRequest pojo) {
    return QuesRecordService.learnScoreDateList(pojo).toString();
  }

}
