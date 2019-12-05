package com.xiaodou.vo.response;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.CourseKeyword;
import com.xiaodou.domain.QuesbkAnswer;
import com.xiaodou.domain.QuesbkAnswerList;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.domain.product.QuesbkQues.KeyPoint;
import com.xiaodou.domain.product.QuesbkQues.Statistic;
import com.xiaodou.enums.ExamSubmitStatus;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.StaticInfoProp;
import com.xiaodou.vo.response.QuesDetailResponse.Answer;

public class ExamDetailResponse extends BaseResponse {

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
      setCourseId(paper.getCourseId().toString());
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
    /** itemIndex 节序号 */
    private String itemIndex;
    /** itemName 节名称 */
    private String itemName;
    /** nextChapterId 下一节所属章ID */
    private String nextChapterId = "-1";
    /** nextItemId 下一节ID */
    private String nextItemId = "-1";
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

    /** credit 用户剩余积分 */
    private Integer credit = 0;

    /** nextDeductCredit 下一关应扣除积分 */
    private String nextDeductCredit = "0";

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


    public void setItemInfo(CourseProductItem item) {
      if (null == item) return;
      this.itemIndex = item.getChapterId();
      this.itemName = item.getName();
    }

    public void setNextItemInfo(CourseProductItem nextItem) {
      if (null == nextItem) {
        this.nextChapterId = "-1";
        this.nextItemId = "-1";
      } else {
        this.nextChapterId = nextItem.getParentId().toString();
        this.nextItemId = nextItem.getId().toString();
      }

    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public Integer getCredit() {
      return credit;
    }

    public void setCredit(Integer credit) {
      this.credit = credit;
    }

    public String getNextDeductCredit() {
      return nextDeductCredit;
    }

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
      if (null == myAnswerIds)
        this.myAnswerIds = Lists.newArrayList();
      else
        this.myAnswerIds = myAnswerIds;
    }

    public List<String> getOpponentsIds() {
      return opponentsIds;
    }

    public void setOpponentsIds(List<String> opponentsIds) {
      if (null == opponentsIds)
        this.opponentsIds = Lists.newArrayList();
      else
        this.opponentsIds = opponentsIds;
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

    public Question(String desc) {
      setQuesType(StaticInfoProp.getParams(QuesBaseConstant.QUES_TYPE_DIRECTIONS));
      String[] namePair = desc.split(QuesBaseConstant.QUES_TYPE_SPLIT);
      setQuesDesc(namePair[0]);
      if (namePair.length > 1) {
        setQuesAnaly(namePair[1]);
      }
    }

    public Question(QuesbkQues quesbkQues) {
      setQuesId(quesbkQues.getId().toString()); // 问题ID
      setQuesType(quesbkQues.getQuestionType() > StaticInfoProp
          .getInt(QuesBaseConstant.QUES_TYPE_MULTIPLE_CHOICE) ? StaticInfoProp
          .getParams(QuesBaseConstant.QUES_TYPE_SUBJECTIVE) : quesbkQues.getQuestionType()
          .toString()); // TODO
      if (null != quesbkQues.getQuestionTypeScore())
        setQuesTypeScore(QuesBaseConstant.D_FORMAT.format(quesbkQues.getQuestionTypeScore()));
      // 问题类型
      if (StringUtils.isNotBlank(quesbkQues.getQuestionTypeName()))
        setQuesTypeName(quesbkQues.getQuestionTypeName()); // 问题类型展示名称
      setQuesSrc(quesbkQues.getQuestionSrc());// 问题来源
      setItemId(quesbkQues.getChapterId().toString()); // 所属节
      setChapterId(quesbkQues.getParentChapter()); // 所属章
      setCognition(QuesbkUtil.getFormat().format(quesbkQues.getCognitionLevel())); // 认知程度
      setDiffculty(QuesbkUtil.getFormat().format(quesbkQues.getDiffcultLevel())); // 难度等级
      setQuesDesc(quesbkQues.getMdesc()); // 问题说明(题干内容)
      if (StringUtils.isNotBlank(quesbkQues.getQuesImgUrl())) {
        setQuesImgUrl(Lists.newArrayList(quesbkQues.getQuesImgUrl()));// 问题图片Url列表
      }
      setQuesAnaly(quesbkQues.getManalyze()); // 问题解析
      if (StringUtils.isJsonNotBlank(quesbkQues.getAnswerIds())) {
        setRightAnswerIds(FastJsonUtil.fromJsons(quesbkQues.getAnswerIds(),
            new TypeReference<List<String>>() {})); // 正确答案ID
      }
      String _myAnswerIds =
          quesbkQues.getMyRightTimes() == 0 ? quesbkQues.getMyAnswerIds() : quesbkQues
              .getAnswerIds();// 判断正答次数 : > 0 我的答案ID = 正确答案ID ; = 0 我的答案ID = 错误答案ID
      if (StringUtils.isJsonNotBlank(_myAnswerIds)) { // 错误ID不为空,证明该题目回答过
        setMyAnswerIds(FastJsonUtil.fromJsons(_myAnswerIds, new TypeReference<List<String>>() {}));
      }
      setAnswerList(quesbkQues.getAnswerList());// 答案列表
      setKeyPointList(quesbkQues.getKeyPoint());// 考点列表
      Statistics statistics = new Statistics(quesbkQues.getStatistic());// 设置统计情况
      setStatistics(statistics);// 设置统计情况
      // setStoreStatus(StringUtils.isNotBlank(quesbkQues.getStoreId())); // 设置收藏状态
      setStoreStatus(quesbkQues.getStoreStatus());
      setWrongStatus(quesbkQues.getWrongStatus());
    }

    /**
     * 设置考点列表
     * 
     * @param answerList
     */
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
    private void setAnswerList(String answerList) {
      if (StringUtils.isJsonBlank(answerList)) return;
      QuesbkAnswerList answerLists = FastJsonUtil.fromJson(answerList, QuesbkAnswerList.class);
      if (null == answerLists || null == answerLists.getQuestionSelectionList()
          || answerLists.getQuestionSelectionList().size() == 0) return;
      answerLists.shuffleAnswer();
      for (QuesbkAnswer qanswer : answerLists.getQuestionSelectionList()) {
        getAnswerList().add(new Answer(qanswer)); // 答案列表,只记录正常答案,废弃答案不做记录
      }
    }
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
