package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;

public class RankListResponse extends BaseResponse {
  public RankListResponse() {}

  public RankListResponse(ResultType type) {
    super(type);
  }

  private MyRankInfo myRankInfo = new MyRankInfo();
  private List<MyRankInfo> rankInfoList = Lists.newArrayList();

  public MyRankInfo getMyRankInfo() {
    return myRankInfo;
  }

  public void setMyRankInfo(MyRankInfo myRankInfo) {
    this.myRankInfo = myRankInfo;
  }

  public List<MyRankInfo> getRankInfoList() {
    return rankInfoList;
  }

  public void setRankInfoList(List<MyRankInfo> rankInfoList) {
    this.rankInfoList = rankInfoList;
  }

  @Data
  public static class MyRankInfo {
    private String userId = StringUtils.EMPTY;
    private String nickName = StringUtils.EMPTY;
    //private String title;// 称号
    private String gender = StringUtils.EMPTY;
    private String portrait =StringUtils.EMPTY;
    private String rank = StringUtils.EMPTY;
    //private String coin = "22";// 金币
    private String credit = StringUtils.EMPTY;// 积分
    private String starLevel = StringUtils.EMPTY;// 星星数目
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}
    
  }

  public static class RankInfo extends MyRankInfo {
    private String isFriend;// 1 是 0 否

    public String getIsFriend() {
      return isFriend;
    }

    public void setIsFriend(String isFriend) {
      this.isFriend = isFriend;
    }

  }
}
