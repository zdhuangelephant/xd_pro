package com.xiaodou.resources.response.quesbk;

import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.response.BaseResponse;

public class BaseDetailResponse extends BaseResponse {

  public BaseDetailResponse(ResultType type) {
    super(type);
  }

  /**
   * 练习情况
   */
  private ExamDetail examTotal = new ExamDetail();

  public ExamDetail getExamTotal() {
    return examTotal;
  }

  public void setExamTotal(ExamDetail examTotal) {
    this.examTotal = examTotal;
  }

  /**
   * 题库练习详情
   * 
   * @author Administrator
   */
  public static class ExamDetail {
    /**
     * 我的练习总题数
     */
    private String myQues = "0";
    /**
     * 我的正确率
     */
    private String myRightPercent = "-";
    /**
     * 我的答题排名
     */
    private String myQuesRank = "-";
    /**
     * 我的正确率排名
     */
    private String myRightRank = "-";
    /**
     * 全站答题人数
     */
    private String totalMembers = "-";
    /**
     * 我的正确总题数
     */
    private String myRightQues = "-";

    public String getMyQues() {
      return myQues;
    }

    public void setMyQues(String myQues) {
      this.myQues = myQues;
    }

    public String getMyRightPercent() {
      return myRightPercent;
    }

    public void setMyRightPercent(String myRightPercent) {
      this.myRightPercent = myRightPercent;
    }

    public String getMyQuesRank() {
      return myQuesRank;
    }

    public void setMyQuesRank(String myQuesRank) {
      this.myQuesRank = myQuesRank;
    }

    public String getMyRightRank() {
      return myRightRank;
    }

    public void setMyRightRank(String myRightRank) {
      this.myRightRank = myRightRank;
    }

    public String getTotalMembers() {
      return totalMembers;
    }

    public void setTotalMembers(String totalMembers) {
      this.totalMembers = totalMembers;
    }

    public String getMyRightQues() {
      return myRightQues;
    }

    public void setMyRightQues(String myRightQues) {
      this.myRightQues = myRightQues;
    }

  }

}
