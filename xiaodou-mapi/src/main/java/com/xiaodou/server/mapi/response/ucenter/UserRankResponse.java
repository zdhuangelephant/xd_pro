package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserRankResponse extends BaseResponse {

  public UserRankResponse() {}

  public UserRankResponse(ResultType type) {
    super(type);
  }

  public UserRankResponse(UcenterResType resType) {
    super(resType);
  }

  private RankInfo myRankInfo = new RankInfo();
  private List<RankInfo> rankInfoList = Lists.newArrayList();

  public RankInfo getMyRankInfo() {
    return myRankInfo;
  }

  public void setMyRankInfo(RankInfo myRankInfo) {
    this.myRankInfo = myRankInfo;
  }

  public List<RankInfo> getRankInfoList() {
    return rankInfoList;
  }

  public void setRankInfoList(List<RankInfo> rankInfoList) {
    this.rankInfoList = rankInfoList;
  }

  /* 给客户端返回有效参数用 */
  public static class RankInfo {
    private String userId = StringUtils.EMPTY;// 用户Id
    private String isFriend = StringUtils.EMPTY;//  1 是 0 否
    private String rank = StringUtils.EMPTY;// 排名
    private String medalName = StringUtils.EMPTY;// 勋章名称
    private String medalImg = StringUtils.EMPTY;// 勋章图片
    private String portrait = StringUtils.EMPTY;// 头像
    private String nickName = StringUtils.EMPTY;// 昵称
    private String gender = StringUtils.EMPTY;

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getIsFriend() {
      return isFriend;
    }

    public void setIsFriend(String isFriend) {
      this.isFriend = isFriend;
    }

    public String getRank() {
      return rank;
    }

    public void setRank(String rank) {
      this.rank = rank;
    }

    public String getMedalName() {
      return medalName;
    }

    public void setMedalName(String medalName) {
      this.medalName = medalName;
    }

    public String getMedalImg() {
      return medalImg;
    }

    public void setMedalImg(String medalImg) {
      this.medalImg = medalImg;
    }

    public String getPortrait() {
      return portrait;
    }

    public void setPortrait(String portrait) {
      this.portrait = portrait;
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

  }

  public class PraiseRankInfo extends RankInfo {
    private String bePraiseNum = Integer.valueOf(0).toString();// 被赞次数

    public String getBePraiseNum() {
      return bePraiseNum;
    }

    public void setBePraiseNum(String bePraiseNum) {
      this.bePraiseNum = bePraiseNum;
    }

  }

  public class CreditRankInfo extends RankInfo {
    private String credit = Integer.valueOf(0).toString();// 积分

    public String getCredit() {
      return credit;
    }

    public void setCredit(String credit) {
      this.credit = credit;
    }
  }

}
