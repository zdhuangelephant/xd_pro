package com.xiaodou.course.web.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.enums.FriendShip;
import com.xiaodou.ucerCenter.agent.model.RankUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.ucerCenter.agent.prop.InitMedalProp;

public class UserRankResponse extends BaseResponse {
  public UserRankResponse(ResultType type) {
    super(type);
  }

  public UserRankResponse(ProductResType resultType) {
    super(resultType);
  }

  private RankInfo myRankInfo = new RankInfo();

  private List<RankInfo> rankInfoList = Lists.newArrayList();

  public RankInfo getMyRankInfo() {
    return myRankInfo;
  }

  public PraiseRankInfo getPraiseRankInfo(StUserInfo sourseInfo, RankUserInfo rankUserInfo,
      String userId) {
    PraiseRankInfo praiseRankInfo = new PraiseRankInfo();
    if (null != sourseInfo && StringUtils.isNotBlank(sourseInfo.getMedal())) {
      StMedal stMedal = FastJsonUtil.fromJson(sourseInfo.getMedal(), StMedal.class);
      praiseRankInfo.setMedalName(StringUtils.isNotBlank(stMedal.getMedalName()) ? stMedal
          .getMedalName() : InitMedalProp.getParams("init.medalName"));
      praiseRankInfo.setMedalImg(StringUtils.isNotBlank(stMedal.getMedalImg()) ? stMedal
          .getMedalImg() : InitMedalProp.getParams("init.medalImg"));
    }
    if (null != rankUserInfo) {
      if (userId.equals(rankUserInfo.getUserId()))
        praiseRankInfo.setIsFriend(FriendShip.ONESELF.getCode().toString());
      else if (StringUtils.isNotBlank(rankUserInfo.getIsFriend()))
        praiseRankInfo.setIsFriend(rankUserInfo.getIsFriend());
      else
        praiseRankInfo.setIsFriend(FriendShip.STRENGER.getCode().toString());
      if (StringUtils.isNotBlank(rankUserInfo.getNickName()))
        praiseRankInfo.setNickName(rankUserInfo.getNickName());
      if (StringUtils.isNotBlank(rankUserInfo.getPortrait()))
        praiseRankInfo.setPortrait(rankUserInfo.getPortrait());
      if (StringUtils.isNotBlank(rankUserInfo.getRank()))
        praiseRankInfo.setRank(rankUserInfo.getRank());
      if (StringUtils.isNotBlank(rankUserInfo.getUserId()))
        praiseRankInfo.setUserId(rankUserInfo.getUserId());
      if (StringUtils.isNotBlank(rankUserInfo.getBePraiseNum()))
        praiseRankInfo.setBePraiseNum(rankUserInfo.getBePraiseNum());
      if (StringUtils.isNotBlank(rankUserInfo.getGender()))
        praiseRankInfo.setGender(rankUserInfo.getGender());
    }
    return praiseRankInfo;
  }

  public CreditRankInfo getCreditRankInfo(StUserInfo sourseInfo, RankUserInfo rankUserInfo,
      String userId) {
    CreditRankInfo creditRankInfo = new CreditRankInfo();
    if (null != sourseInfo && StringUtils.isNotBlank(sourseInfo.getMedal())) {
      StMedal stMedal = FastJsonUtil.fromJson(sourseInfo.getMedal(), StMedal.class);
      creditRankInfo.setMedalName(StringUtils.isNotBlank(stMedal.getMedalName()) ? stMedal
          .getMedalName() : InitMedalProp.getParams("init.medalName"));
      creditRankInfo.setMedalImg(StringUtils.isNotBlank(stMedal.getMedalImg()) ? stMedal
          .getMedalImg() : InitMedalProp.getParams("init.medalImg"));
    }
    if (null != rankUserInfo) {
      if (userId.equals(rankUserInfo.getUserId()))
        creditRankInfo.setIsFriend(FriendShip.ONESELF.getCode().toString());
      else if (StringUtils.isNotBlank(rankUserInfo.getIsFriend()))
        creditRankInfo.setIsFriend(rankUserInfo.getIsFriend());
      else
        creditRankInfo.setIsFriend(FriendShip.STRENGER.getCode().toString());
      if (StringUtils.isNotBlank(rankUserInfo.getNickName()))
        creditRankInfo.setNickName(rankUserInfo.getNickName());
      if (StringUtils.isNotBlank(rankUserInfo.getPortrait()))
        creditRankInfo.setPortrait(rankUserInfo.getPortrait());
      if (StringUtils.isNotBlank(rankUserInfo.getRank()))
        creditRankInfo.setRank(rankUserInfo.getRank());
      if (StringUtils.isNotBlank(rankUserInfo.getUserId()))
        creditRankInfo.setUserId(rankUserInfo.getUserId());
      if (StringUtils.isNotBlank(rankUserInfo.getCredit()))
        creditRankInfo.setCredit(rankUserInfo.getCredit());
      if (StringUtils.isNotBlank(rankUserInfo.getGender()))
        creditRankInfo.setGender(rankUserInfo.getGender());
    }
    return creditRankInfo;
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
    private String starLevel = StringUtils.EMPTY;// 星星数目

    public String getStarLevel() {
      return starLevel;
    }

    public void setStarLevel(String starLevel) {
      this.starLevel = starLevel;
    }

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
