package com.xiaodou.resources.response.quesbk;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.UserExamRecordDetail;
import com.xiaodou.resources.response.BaseResponse;

public class PkResultResponse extends BaseResponse {

  public PkResultResponse(ResultType type) {
    super(type);
  }

  private Integer maxSize = 20;
  private Set<String> userSets = Sets.newHashSet();
  /** score 得分 */
  private String score;
  /** opponentsScore 对手得分 */
  private String opponentsScore;
  /** rightCount 正确数量 */
  private String rightCount;
  /** totalCount 总体量 */
  private String totalCount;
  /** starLevel 星级 */
  private String starLevel;
  /** creditUpper 积分涨幅 */
  private String creditUpper;
  /** coinUpper 金币涨幅 */
  private String coinUpper;
  /** winner 胜利者 */
  private String winner;
  private List<Rank> rankList = Lists.newArrayList();

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

  public String getRightCount() {
    return rightCount;
  }

  public void setRightCount(String rightCount) {
    this.rightCount = rightCount;
  }

  public String getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(String totalCount) {
    this.totalCount = totalCount;
  }

  public String getStarLevel() {
    return starLevel;
  }

  public void setStarLevel(String starLevel) {
    this.starLevel = starLevel;
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
    this.winner = winner;
  }

  public List<Rank> getRankList() {
    return rankList;
  }

  public void setRankList(List<Rank> rankList) {
    this.rankList = rankList;
  }

  public void setDetail(UserExamRecordDetail detail) {
    if (null != detail) {
      if (null != detail.getCreditUpper()) setCreditUpper(detail.getCreditUpper().toString());
      if (null != detail.getStarLevel()) setStarLevel(detail.getStarLevel().toString());
      if (null != detail.getQuesCount()) setTotalCount(detail.getQuesCount().toString());
      if (null != detail.getRightCount()) setRightCount(detail.getRightCount().toString());
    }
  }

  public void setUserIdList(List<String> userIdList) {
    for (String userId : userIdList) {
      if (this.rankList.size() > maxSize) return;
      if (userSets.contains(userId)) continue;
      userSets.add(userId);
      Rank rank = new Rank();
      rank.setUserId(userId);
      this.rankList.add(rank);
    }
  }

}
