package com.xiaodou.vo.response;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.PkExam;
import com.xiaodou.domain.behavior.UserExamRecordDetail;

public class SubmitResultResponse extends BaseResponse {
  public SubmitResultResponse(ResultType type) {
    super(type);
  }

  private Integer maxSize = 20;
  private Set<String> userSets = Sets.newHashSet();
  /** score 得分 */
  private String score;
  /** opponentsScore 对手得分 */
  private String opponentsScore;
  /** starLevel 星级 */
  private String starLevel;
  /** userCredit 用户积分 */
  private Integer userCredit = 0;
  /** creditUpper 积分涨幅 */
  private String creditUpper;
  /** coinUpper 金币涨幅 */
  private String coinUpper;
  /** winner 胜利者 */
  private String winner;
  /** completeCount 闯关完成者数量 */
  private Integer completeCount = 0;
  /** uniqueId 红包唯一ID */
  private String uniqueId = StringUtils.EMPTY;
  private List<Rank> rankList = Lists.newArrayList();

  private PkExam pkExam;

  private String lock = "0";

  public Integer getUserCredit() {
    return userCredit;
  }

  public void setUserCredit(Integer userCredit) {
    this.userCredit = userCredit;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getOpponentsScore() {
    return opponentsScore;
  }

  public void setOpponentsScore(String opponentsScore) {
    this.opponentsScore = opponentsScore;
  }

  public String getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(String starLevel) {
    this.starLevel = starLevel;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getCreditUpper() {
    return creditUpper;
  }

  public void setCreditUpper(String creditUpper) {
    this.creditUpper = creditUpper;
  }

  public String getCoinUpper() {
    return coinUpper;
  }

  public void setCoinUpper(String coinUpper) {
    this.coinUpper = coinUpper;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    if (!winner.equals(QuesBaseConstant.QUES_CHALLENGE_RESULT_UNKNOWN)) {
      this.winner = winner;
    }
  }

  public Integer getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(Integer completeCount) {
    this.completeCount = completeCount;
  }

  public List<Rank> getRankList() {
    return rankList;
  }

  public void setRankList(List<Rank> rankList) {
    this.rankList = rankList;
  }

  public void setUserIdList(List<String> userIdList) {
    for (String userId : userIdList) {
      if (userSets.contains(userId)) continue;
      userSets.add(userId);
      if (this.rankList.size() > maxSize) return;
      Rank rank = new Rank();
      rank.setUserId(userId);
      this.rankList.add(rank);
    }
  }

  public void markCompleteUser(String userId) {
    if (userSets.contains(userId)) return;
    userSets.add(userId);
  }

  public void setDetail(UserExamRecordDetail detail) {
    if (null != detail) {
      if (null != detail.getCreditUpper()) {
        setCreditUpper(detail.getCreditUpper().toString());
        userCredit += detail.getCreditUpper();
      }
      if (null != detail.getScore()) {
        setScore(MathUtil.getIntStringValue(detail.getScore()));
        if (detail.getScore() >= 60) setSelf(detail.getUserId());
      }
      if (null != detail.getStarLevel()) setStarLevel(detail.getStarLevel().toString());
    }
  }

  private void setSelf(String uid) {
    if (userSets.contains(uid)) return;
    userSets.add(uid);
    this.completeCount++;
    if (this.rankList.size() > maxSize) return;
    Rank rank = new Rank();
    rank.setUserId(uid);
    this.rankList.add(rank);
  }

  public PkExam getPkExam() {
    return pkExam;
  }

  public void setPkExam(PkExam pkExam) {
    this.pkExam = pkExam;
  }

  public String getLock() {
    return lock;
  }

  public void setLock(String lock) {
    this.lock = lock;
  }


}
