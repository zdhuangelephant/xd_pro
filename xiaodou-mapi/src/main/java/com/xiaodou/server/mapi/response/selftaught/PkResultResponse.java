package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.PkExam;
import com.xiaodou.server.mapi.response.selftaught.ExamResultResponse.Rank;
import com.xiaodou.summer.vo.out.ResultType;

public class PkResultResponse extends BaseResponse {
  public PkResultResponse() {}

  public PkResultResponse(ResultType type) {
    super(type);
  }

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
  /** creditUpper 经验涨幅 */
  private String creditUpper;
  /** coinUpper 金币涨幅 */
  private String coinUpper;
  /** winner 胜利者 */
  private String winner;
  /** rankList 好友排名列表 */
  private List<Rank> rankList = Lists.newArrayList();
  private PkExam pkExam; 
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

public PkExam getPkExam() {
	return pkExam;
}

public void setPkExam(PkExam pkExam) {
	this.pkExam = pkExam;
}

}
