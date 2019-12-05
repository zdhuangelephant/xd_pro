package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.domain.CourseKeyword;
import com.xiaodou.server.mapi.domain.QuesbkQues;
import com.xiaodou.server.mapi.domain.QuesbkQues.KeyPoint;
import com.xiaodou.server.mapi.domain.QuesbkQues.Statistic;
import com.xiaodou.server.mapi.enums.ExamSubmitStatus;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.selftaught.QuesDetailResponse.Answer;
import com.xiaodou.summer.vo.out.ResultType;

public class ExamDetailResponse extends BaseResponse {

  public ExamDetailResponse() {}

  public ExamDetailResponse(ResultType type) {
    super(type);
  }

  private ExamDetail examDetail;

  public ExamDetail getExamDetail() {
    return examDetail;
  }

  public void setExamDetail(ExamDetail examDetail) {
    this.examDetail = examDetail;
  }

  public static class ExamDetail {

    public ExamDetail() {}

    public ExamDetail(QuesbkExamPaper paper) {
      setQuesCount(paper.getQuesNum().toString());
      setPaperName(paper.getExamName());
      setPaperId(paper.getId().toString());
    }

    /**
     * 试卷ID
     */
    private String paperId;
    /**
     * 试卷名
     */
    private String paperName;
    /**
     * 课程ID
     */
    private String courseId;
    /**
     * 问题数量
     */
    private String quesCount;
    /**
     * 上次练习耗时(继续练习使用字段)
     */
    private String examCost;
    /**
     * 练习问题列表详情
     */
    private List<Question> questionList = Lists.newArrayList();

    private String status = "0";

    private String credit;

    /** nextDeductCredit 下一关应扣除积分 */
    private String nextDeductCredit = "0";

    /** itemIndex 节序号 */
    private String itemIndex;
    /** itemName 节名称 */
    private String itemName;

    public String getItemIndex() {
      return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
      this.itemIndex = itemIndex;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getNextChapterId() {
      return nextChapterId;
    }

    public void setNextChapterId(String nextChapterId) {
      this.nextChapterId = nextChapterId;
    }

    public String getNextItemId() {
      return nextItemId;
    }

    public void setNextItemId(String nextItemId) {
      this.nextItemId = nextItemId;
    }

    /** nextChapterId 下一节所属章ID */
    private String nextChapterId = "-1";
    /** nextItemId 下一节ID */
    private String nextItemId = "-1";

    public String getExamCost() {
      return examCost;
    }

    public void setExamCost(String examCost) {
      this.examCost = examCost;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

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

    public List<Question> getQuestionList() {
      return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
      this.questionList = questionList;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCredit() {
      return credit;
    }

    public void setCredit(String credit) {
      this.credit = credit;
    }

    @JSONField(name = "deductPoint")
    public String getNextDeductCredit() {
      return nextDeductCredit;
    }

    @JSONField(name = "nextDeductCredit")
    public void setNextDeductCredit(String nextDeductCredit) {
      this.nextDeductCredit = nextDeductCredit;
    }

  }

  public static class Question {
    /**
     * 问题ID
     */
    private String quesId;
    /**
     * 题目来源
     */
    private String quesSrc;
    /**
     * 问题类型
     */
    private String quesType;
    /** quesTypeName 问题类型展示名称 */
    private String quesTypeName;
    /** quesTypeScore 问题类型分值 */
    private String quesTypeScore = "0";
    /**
     * 所属章
     */
    private String chapterId;
    /**
     * 所属节
     */
    private String itemId;
    /**
     * 问题描述
     */
    private String quesDesc;
    /**
     * 题干图片列表
     */
    private List<String> quesImgUrl = Lists.newArrayList();
    /**
     * 难度
     */
    private String diffculty;
    /**
     * 认知程度
     */
    private String cognition;
    /**
     * 考点列表
     */
    private List<KeyPoint> keyPointList = Lists.newArrayList();
    /**
     * 正确答案ID列表
     */
    private List<String> rightAnswerIds = Lists.newArrayList();
    /**
     * 我的答案ID列表
     */
    private List<String> myAnswerIds = Lists.newArrayList();
    /** opponentsIds 对手的答案ID列表 */
    private List<String> opponentsIds = Lists.newArrayList();
    /**
     * 问题答案列表
     */
    private List<Answer> answerList = Lists.newArrayList();
    /**
     * 问题解析
     */
    private String quesAnaly;
    /**
     * 统计信息
     */
    private Statistics statistics;
    /**
     * 用户收藏状态 0 未收藏 1 好题(默认) 2 我不会的  3 需要记忆  4 取消收藏(未收藏)
     */
    private String storeStatus = "0";
    /**
     * 错题状态 0 未练习 1 未掌握 2 待强化 3 已消灭 4 已移除(默认)
     */
    private String wrongStatus = "0";
    /** examStatus 上次提交状态 */
    private String examStatus = ExamSubmitStatus.SAVE.getCode();

    public String getQuesTypeScore() {
      return quesTypeScore;
    }

    public void setQuesTypeScore(String quesTypeScore) {
      this.quesTypeScore = quesTypeScore;
    }

    public String getExamStatus() {
      return examStatus;
    }

    public void setExamStatus(String examStatus) {
      this.examStatus = examStatus;
    }

    public String getQuesTypeName() {
      return quesTypeName;
    }

    public void setQuesTypeName(String quesTypeName) {
      this.quesTypeName = quesTypeName;
    }

    public String getCognition() {
      return cognition;
    }

    public void setCognition(String cognition) {
      this.cognition = cognition;
    }

    public String getQuesId() {
      return quesId;
    }

    public void setQuesId(String quesId) {
      this.quesId = quesId;
    }

    public String getQuesSrc() {
      return quesSrc;
    }

    public void setQuesSrc(String quesSrc) {
      this.quesSrc = quesSrc;
    }

    public String getQuesType() {
      return quesType;
    }

    public void setQuesType(String quesType) {
      this.quesType = quesType;
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

    public List<String> getQuesImgUrl() {
      return quesImgUrl;
    }

    public void setQuesImgUrl(List<String> quesImgUrl) {
      this.quesImgUrl = quesImgUrl;
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
      if (null == myAnswerIds) {
        this.myAnswerIds = Lists.newArrayList();
      } else {
        this.myAnswerIds = myAnswerIds;
      }
    }

    public List<String> getOpponentsIds() {
      return opponentsIds;
    }

    public void setOpponentsIds(List<String> opponentsIds) {
      if (null == opponentsIds) {
        this.opponentsIds = Lists.newArrayList();
      } else {
        this.opponentsIds = opponentsIds;
      }
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

    public Statistics getStatistics() {
      return statistics;
    }

    public void setStatistics(Statistics statistics) {
      this.statistics = statistics;
    }

    // 先注释，待用
    // public String getStoreStatus() {
    // return storeStatus ? "1" : "0";
    // }
    //
    // public void setStoreStatus(Boolean storeStatus) {
    // this.storeStatus = storeStatus;
    // }
    //
    // public void setStoreStatus(String storeList) {
    // if (StringUtils.isNotBlank(this.getQuesId(), storeList)) {
    // if (QuesbkUtil.match(storeList, this.getQuesId(), QuesBaseConstant.STORE_QUES_SPLIT)) {
    // this.storeStatus = true;
    // return;
    // }
    // }
    // this.storeStatus = false;
    // }

    public String getStoreStatus() {
      return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
      this.storeStatus = storeStatus;
    }

    public String getWrongStatus() {
      return wrongStatus;
    }

    public void setWrongStatus(String wrongStatus) {
      this.wrongStatus = wrongStatus;
    }

    public Question() {}

    public Question(String desc) {}

    public Question(QuesbkQues quesbkQues) {}

    /**
     * 设置考点列表
     * 
     * @param answerList
     */
    @SuppressWarnings("unused")
    private void setKeyPointList(String keyPointList) {
      if (StringUtils.isJsonBlank(keyPointList)) return;
      List<CourseKeyword> qkeyPointList =
          FastJsonUtil.fromJsons(keyPointList, new TypeReference<List<CourseKeyword>>() {});
      for (CourseKeyword keyPoint : qkeyPointList) {
        getKeyPointList().add(new KeyPoint(keyPoint)); // 考点列表
      }
    }

    /**
     * 设置答案列表
     * 
     * @param answerList
     */
    @SuppressWarnings("unused")
    private void setAnswerList(String answerList) {}
  }

  /**
   * 统计信息
   * 
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   */
  public static class Statistics {
    /**
     * 我的作答次数
     */
    private String myExamTimes;
    /**
     * 我的做错次数
     */
    private String myWrongTimes;
    /**
     * 全站作答次数
     */
    private String totalExamTimes;
    /**
     * 全站正答次数
     */
    private String totalRightTimes;
    /**
     * 全站正确率
     */
    private String totalRightPercent;

    public Statistics() {}

    public Statistics(Statistic statistic) {
      if (null != statistic) {
        setMyExamTimes(statistic.getMyExamTimes().toString());// 设置我的练习次数
        setMyWrongTimes(statistic.getMyWrongTimes().toString());// 设置我的错误次数
        setTotalExamTimes(statistic.getTotalExamTimes().toString());// 设置全站练习次数
        setTotalRightTimes(String.valueOf((statistic.getTotalExamTimes() - statistic
            .getTotalWrongTimes())));// 设置全站正答次数
        setTotalRightPercent(statistic.getTotalRightPercent());// 设置全站正确率
      }
    }

    public String getMyExamTimes() {
      return myExamTimes;
    }

    public void setMyExamTimes(String myExamTimes) {
      this.myExamTimes = myExamTimes;
    }

    public String getTotalRightTimes() {
      return totalRightTimes;
    }

    public void setTotalRightTimes(String totalRightTimes) {
      this.totalRightTimes = totalRightTimes;
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

    public String getTotalRightPercent() {
      return totalRightPercent;
    }

    public void setTotalRightPercent(String totalRightPercent) {
      this.totalRightPercent = totalRightPercent;
    }
  }

  @Override
  public String toString0() {
    return String.format("[paperId:%s,paperName:%s,courseId:%s,quesCount:%s,examCost:%s]",
        this.examDetail.paperId, this.examDetail.paperName, this.examDetail.courseId,
        this.examDetail.quesCount, this.examDetail.examCost);
  }

}
