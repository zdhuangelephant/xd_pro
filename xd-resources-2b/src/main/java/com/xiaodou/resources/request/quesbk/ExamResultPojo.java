package com.xiaodou.resources.request.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ExamResultPojo extends BaseRequest {

  /** productId 产品ID */
  @NotEmpty
  private String productId;
  /** productItemId 节ID */
  @NotEmpty
  private String productItemId;
  /** examType 测验类型 */
  private String examType;
  /** paperId 试卷编号 */
  @NotEmpty
  private String paperId;
  /** examTime 答题时间 */
  @NotEmpty
  private String examTime;
  /** examDetail 练习详情 */
  @NotEmpty
  private String examDetail;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductItemId() {
    return productItemId;
  }

  public void setProductItemId(String productItemId) {
    this.productItemId = productItemId;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(String examDetail) {
    this.examDetail = examDetail;
  }
  public static void main(String[] args) {
    List<AnswerItem> answerList = Lists.newArrayList();
    {
      AnswerItem item = new AnswerItem();
      item.setQuesId(119l);
      item.getAnswerIdList().add("119_2");
      answerList.add(item);
    }
    {
      AnswerItem item = new AnswerItem();
      item.setQuesId(120l);
      item.getAnswerIdList().add("120_1");
      answerList.add(item);
    }
    {
      AnswerItem item = new AnswerItem();
      item.setQuesId(121l);
      item.getAnswerIdList().add("121_3");
      answerList.add(item);
    }
    {
      AnswerItem item = new AnswerItem();
      item.setQuesId(122l);
      item.getAnswerIdList().add("122_2");
      answerList.add(item);
    }
    {
      AnswerItem item = new AnswerItem();
      item.setQuesId(340l);
      item.getAnswerIdList().add("340_2");
      answerList.add(item);
    }
    System.out.println(FastJsonUtil.toJson(answerList));
  }
  public static class AnswerItem {
    /**
     * 问题ID
     */
    @NotEmpty
    private Long quesId;
    /**
     * 答案ID列表
     */
    private List<String> answerIdList = Lists.newArrayList();

    private String examStatus;

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }

    public Long getQuesId() {
      return quesId;
    }

    public void setQuesId(Long quesId) {
      this.quesId = quesId;
    }

    public List<String> getAnswerIdList() {
      return answerIdList;
    }

    public void setAnswerIdList(List<String> answerIdList) {
      this.answerIdList = answerIdList;
    }
  }
}
