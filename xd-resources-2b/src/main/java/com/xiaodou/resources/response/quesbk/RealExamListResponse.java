package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.response.BaseResponse;

public class RealExamListResponse extends BaseResponse {

  public RealExamListResponse(ResultType type) {
    super(type);
  }

  /**
   * 真题试卷列表
   */
  private List<RealExam> realExamList = Lists.newArrayList();

  public List<RealExam> getRealExamList() {
    return realExamList;
  }

  public void setRealExamList(List<RealExam> realExamList) {
    this.realExamList = realExamList;
  }

  /**
   * 真题试卷
   *
   * @author Administrator
   */
  public static class RealExam {
    /**
     * 试卷ID
     */
    private String paperId;
    /**
     * 试卷名
     */
    private String paperName;
    /**
     * 题目数量
     */
    private String quesCount;
    /**
     * 难度
     */
    private String diffculty;

    public String getPaperId() {
      return paperId;
    }

    public void setPaperId(String paperId) {
      this.paperId = paperId;
    }

    public String getPaperName() {
      return paperName;
    }

    public void setPaperName(String paperName) {
      this.paperName = paperName;
    }

    public String getQuesCount() {
      return quesCount;
    }

    public void setQuesCount(String quesCount) {
      this.quesCount = quesCount;
    }

    public String getDiffculty() {
      return diffculty;
    }

    public void setDiffculty(String diffculty) {
      this.diffculty = diffculty;
    }
  }
}
