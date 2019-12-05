package com.xiaodou.st.dataclean.web.batch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.st.dataclean.crontab.CalculateEverydaySummaryJob;
import com.xiaodou.st.dataclean.crontab.CategorySessionPercentJob;
import com.xiaodou.st.dataclean.crontab.FinishMissionJob;
import com.xiaodou.st.dataclean.crontab.LastWeekLearnTimeRankJob;
import com.xiaodou.st.dataclean.crontab.LastWeekMissionPercentRankJob;
import com.xiaodou.st.dataclean.crontab.UserExamTotalEventJob;
import com.xiaodou.st.dataclean.crontab.UserLearnRecordJob;
import com.xiaodou.st.dataclean.crontab.UserLearnTrackerJob;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
/**
 * @name @see com.xiaodou.st.dataclean.web.controller.BatchController.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月10日
 * @description 批处理Controller
 * @version 1.0
 */
@Controller("batchController")
@RequestMapping("/batch")
public class BatchController extends BaseController {

  /**
   * 上周学习时长统计
   */
  @RequestMapping("/learn_time_rank")
  @ResponseBody
  public String learnTimeRank() {
    new LastWeekLearnTimeRankJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * 上周任务完成度统计
   */
  @RequestMapping("/mission_percent_rank")
  @ResponseBody
  public String missionPercentRank() {
    new LastWeekMissionPercentRankJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * 上周任务完成度统计
   */
  @RequestMapping("/category_session_percent")
  @ResponseBody
  public String categorySessionPercent() {
    new CategorySessionPercentJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  
  /**
   * 每小时统计learnrecord
   */
  @RequestMapping("/user_learn_record_job")
  @ResponseBody
  public String UserLearnRecordJob() {
    new UserLearnRecordJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  
  /**
   * 每小时统计userexamtotal
   */
  @RequestMapping("/user_examtotal_job")
  @ResponseBody
  public String UserExamTotalJob() {
    new UserExamTotalEventJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  /**
   * 每小时统计finishMission
   */
  @RequestMapping("/finish_mission_job")
  @ResponseBody
  public String FinishMissionJob() {
    new FinishMissionJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  
  /**
   * 每日凌晨1点统计无学习行为记录
   */
  @RequestMapping("/user_learn_tracker_job")
  @ResponseBody
  public String userLearnActionJob(@PathVariable String module) {
    new UserLearnTrackerJob(module).work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  
  
  /**
   * report补充 首页-每日概览 -> 更新频率可为考期内每日两次，上午10点、下午16点各一次。
   */
  @RequestMapping("/calculate_everyday_summary_job")
  @ResponseBody
  public String calculateEverydaySummaryActionJob() {
    new CalculateEverydaySummaryJob().work();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }
  
}
