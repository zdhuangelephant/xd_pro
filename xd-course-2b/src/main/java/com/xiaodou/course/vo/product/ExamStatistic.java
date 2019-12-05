package com.xiaodou.course.vo.product;

import java.text.DecimalFormat;

/**
 * Created by zyp on 15/8/31.
 */
public class ExamStatistic {

  private static final DecimalFormat _FORMAT = new DecimalFormat("#0.00");

  // 今日做题数
  private String todayTotalNum = "0";

  // 今日答对数
  private String todayRightNum = "0";

  // 累计做题数
  private String grandTotalNum = "0";

  // 累计正确率
  private String grandRightPercent = "0";

  public ExamStatistic() {}

  public ExamStatistic(DailyExamResponse quesbkRes) {
    this.todayTotalNum = quesbkRes.dailyExamDetail.totalQues;
    this.todayRightNum = quesbkRes.dailyExamDetail.rightQues;
    this.grandTotalNum = quesbkRes.totalExamDetail.totalQues;
    double totalQues = Double.parseDouble(quesbkRes.totalExamDetail.totalQues);
    double rightQues = Double.parseDouble(quesbkRes.totalExamDetail.rightQues);
    if (totalQues == 0)
      this.grandRightPercent = "0";
    else
      this.grandRightPercent = _FORMAT.format(rightQues / totalQues);
  }

  public String getTodayTotalNum() {
    return todayTotalNum;
  }

  public void setTodayTotalNum(String todayTotalNum) {
    this.todayTotalNum = todayTotalNum;
  }

  public String getTodayRightNum() {
    return todayRightNum;
  }

  public void setTodayRightNum(String todayRightNum) {
    this.todayRightNum = todayRightNum;
  }

  public String getGrandTotalNum() {
    return grandTotalNum;
  }

  public void setGrandTotalNum(String grandTotalNum) {
    this.grandTotalNum = grandTotalNum;
  }

  public String getGrandRightPercent() {
    return grandRightPercent;
  }

  public void setGrandRightPercent(String grandRightPercent) {
    this.grandRightPercent = grandRightPercent;
  }

  /**
   * @name @see com.xiaodou.vo.response.DailyExamResponse.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年8月27日
   * @description 每日做题统计响应
   * @version 1.0
   */
  public static class DailyExamResponse {


    /** examDetail 每日做题详情 */
    private ExamDetail dailyExamDetail = new ExamDetail();

    /** totalExamDetail 总做题详情 */
    private ExamDetail totalExamDetail = new ExamDetail();


    public ExamDetail getDailyExamDetail() {
      return dailyExamDetail;
    }


    public void setDailyExamDetail(ExamDetail dailyExamDetail) {
      this.dailyExamDetail = dailyExamDetail;
    }


    public ExamDetail getTotalExamDetail() {
      return totalExamDetail;
    }


    public void setTotalExamDetail(ExamDetail totalExamDetail) {
      this.totalExamDetail = totalExamDetail;
    }


    /**
     * @name @see com.xiaodou.vo.response.DailyExamDetail.java
     * @CopyRright (c) 2015 by XiaoDou NetWork Technology
     * 
     * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
     * @date 2015年8月27日
     * @description 每日做题统计信息
     * @version 1.0
     */
    public static class ExamDetail {
      /** totalQues 每天总做题数 */
      private String totalQues = "0";
      /** rightQues 每天正确题数 */
      private String rightQues = "0";

      public String getTotalQues() {
        return totalQues;
      }

      public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
      }

      public String getRightQues() {
        return rightQues;
      }

      public void setRightQues(String rightQues) {
        this.rightQues = rightQues;
      }

    }

  }

}
