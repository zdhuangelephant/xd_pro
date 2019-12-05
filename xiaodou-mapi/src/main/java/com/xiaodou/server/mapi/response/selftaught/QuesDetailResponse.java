package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class QuesDetailResponse extends BaseResponse {

  public QuesDetailResponse(ResultType type) {
    super(type);
  }

  private List<Question> questionList = Lists.newArrayList(new Question(), new Question(),
      new Question(), new Question());

  public List<Question> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
  }

  public static class Question {
    private String quesId; // 问题ID
    private String quesType; // 问题类型
    private String quesSrc; // 题目来源
    private String chapterId; // 章ID
    private String itemId; // 节ID
    private String quesDesc; // 题干
    private String diffculty; // 难度
    private List<KeyPoint> keyPointList = Lists.newArrayList(new KeyPoint(), new KeyPoint());
    private List<String> rightAnswerIds = Lists.newArrayList(); // 正确答案ID列表
    private List<String> myAnswerIds = Lists.newArrayList(); // 我的答案ID列表
    private List<Answer> answerList = Lists.newArrayList(new Answer(), new Answer(), new Answer(),
        new Answer()); // 答案列表
    private String quesAnaly;// 问题解析
    private Statistic statistics = new Statistic();
    private String storeStatus; // 收藏状态 0 未收藏 1 已收藏

    public String getQuesId() {
      return quesId;
    }

    public void setQuesId(String quesId) {
      this.quesId = quesId;
    }

    public String getQuesType() {
      return quesType;
    }

    public void setQuesType(String quesType) {
      this.quesType = quesType;
    }

    public String getQuesSrc() {
      return quesSrc;
    }

    public void setQuesSrc(String quesSrc) {
      this.quesSrc = quesSrc;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getQuesDesc() {
      return quesDesc;
    }

    public void setQuesDesc(String quesDesc) {
      this.quesDesc = quesDesc;
    }

    public String getDiffculty() {
      return diffculty;
    }

    public void setDiffculty(String diffculty) {
      this.diffculty = diffculty;
    }

    public List<KeyPoint> getKeyPointList() {
      return keyPointList;
    }

    public void setKeyPointList(List<KeyPoint> keyPointList) {
      this.keyPointList = keyPointList;
    }

    public List<String> getRightAnswerIds() {
      return rightAnswerIds;
    }

    public void setRightAnswerIds(List<String> rightAnswerIds) {
      this.rightAnswerIds = rightAnswerIds;
    }

    public List<String> getMyAnswerIds() {
      return myAnswerIds;
    }

    public void setMyAnswerIds(List<String> myAnswerIds) {
      this.myAnswerIds = myAnswerIds;
    }

    public List<Answer> getAnswerList() {
      return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
      this.answerList = answerList;
    }

    public String getQuesAnaly() {
      return quesAnaly;
    }

    public void setQuesAnaly(String quesAnaly) {
      this.quesAnaly = quesAnaly;
    }

    public Statistic getStatistics() {
      return statistics;
    }

    public void setStatistics(Statistic statistics) {
      this.statistics = statistics;
    }

    public String getStoreStatus() {
      return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
      this.storeStatus = storeStatus;
    }
  }

  public static class KeyPoint {
    private String poindId; // 考点ID
    private String name;

    public String getPoindId() {
      return poindId;
    }

    public void setPoindId(String poindId) {
      this.poindId = poindId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class Answer {
    /**
     * 答案ID
     */
    private String answerId;
    /**
     * 答案详情
     */
    private String answerDesc;
    /**
     * 答案图片Url
     */
    private String answerImgUrl;

    public String getAnswerId() {
      return answerId;
    }

    public void setAnswerId(String answerId) {
      this.answerId = answerId;
    }

    public String getAnswerDesc() {
      return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
      this.answerDesc = answerDesc;
    }

    public String getAnswerImgUrl() {
      return answerImgUrl;
    }

    public void setAnswerImgUrl(String answerImgUrl) {
      this.answerImgUrl = answerImgUrl;
    }

  }

  public static class Statistic {
    private String myExamTimes; // 我的作答次数
    private String myWrongTimes; // 我的做错次数
    private String totalExamTimes; // 全站作答次数
    private String totalRightTimes; // 全站正答次数
    private String totalRightPercent; // 全站正确率

    public String getMyExamTimes() {
      return myExamTimes;
    }

    public void setMyExamTimes(String myExamTimes) {
      this.myExamTimes = myExamTimes;
    }

    public String getMyWrongTimes() {
      return myWrongTimes;
    }

    public void setMyWrongTimes(String myWrongTimes) {
      this.myWrongTimes = myWrongTimes;
    }

    public String getTotalExamTimes() {
      return totalExamTimes;
    }

    public void setTotalExamTimes(String totalExamTimes) {
      this.totalExamTimes = totalExamTimes;
    }

    public String getTotalRightTimes() {
      return totalRightTimes;
    }

    public void setTotalRightTimes(String totalRightTimes) {
      this.totalRightTimes = totalRightTimes;
    }

    public String getTotalRightPercent() {
      return totalRightPercent;
    }

    public void setTotalRightPercent(String totalRightPercent) {
      this.totalRightPercent = totalRightPercent;
    }
  }

}
