package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.domain.PkExam;
import com.xiaodou.summer.vo.out.ResultType;

public class ExamResultResponse extends BaseResponse {
  public ExamResultResponse() {}

  public ExamResultResponse(ResultType type) {
    super(type);
  }

  /** score 得分 */
  private String score;
  /** opponentsScore 对手得分 */
  private String opponentsScore;
  /** starLevel 星级 */
  private String starLevel;
  /** creditUpper 经验涨幅 */
  private String creditUpper;
  /** coinUpper 金币涨幅 */
  private String coinUpper;
  /** winner 胜利者 */
  private String winner;
  /** completeCount 闯关完成者数量 */
  private String completeCount;
  /** uniqueId 红包唯一ID */
  private String uniqueId = StringUtils.EMPTY;
  /** rankList 好友排名列表 */
  private List<Rank> rankList = Lists.newArrayList();

  private PkExam pkExam;

  private String lock = "0";

  private String userCredit;

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

  public String getCompleteCount() {
    return completeCount;
  }

  public void setCompleteCount(String completeCount) {
    this.completeCount = completeCount;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
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

  public String getLock() {
    return lock;
  }

  public void setLock(String lock) {
    this.lock = lock;
  }

  public String getUserCredit() {
    return userCredit;
  }

  public void setUserCredit(String userCredit) {
    this.userCredit = userCredit;
  }

  /**
   * @name @see com.xiaodou.server.mapi.response.Rank.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年1月26日
   * @description 排名
   * @version 1.0
   */
  public static class Rank {
    /** userId 用户ID */
    private String userId;
    /** nickName 用户昵称 */
    private String nickName;
    /** portait 用户头像 */
    private String portrait;
    /** gender 用户性别 */
    private String gender;

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public String getPortrait() {
      return portrait;
    }

    public void setPortrait(String portrait) {
      this.portrait = portrait;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public void setInfo(Map<String, Object> map) {
      if (null == map) {
        this.gender = "";
        this.nickName = "";
        this.portrait = "";
        return;
      }
      if (null != map.get("gender")) this.gender = map.get("gender").toString();
      if (null != map.get("nickName")) this.nickName = map.get("nickName").toString();
      if (null != map.get("portrait")) this.portrait = map.get("portrait").toString();
    }

  }

}
