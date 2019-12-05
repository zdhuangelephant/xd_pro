package com.xiaodou.userCenter.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class FriendRankListResponse extends BaseResultInfo {

  public FriendRankListResponse(ResultType type) {
    super(type);
  }

  public FriendRankListResponse(UcenterResType type) {
    super(type);
  }

  /** rankList 排名信息 */
  private List<RankInfo> rankList = Lists.newArrayList();

  public List<RankInfo> getRankList() {
    return rankList;
  }

  public void addRankInfo(RankInfo rankInfo) {
    if (rankList.size() < 10) {
      rankList.add(rankInfo);
    }
  }

  public void setRankList(List<RankInfo> rankList) {
    this.rankList = rankList;
  }

  public void addRankList(RankInfo rank) {
    this.rankList.add(rank);
  }

  public static class RankInfo {

    /** userId 用户ID */
    private String userId = StringUtils.EMPTY;
    /** nickName 昵称 */
    private String nickName = StringUtils.EMPTY;
    /** portrait 头像 */
    private String portrait = StringUtils.EMPTY;
    /** gender 性別 */
    private String gender = StringUtils.EMPTY;

    public RankInfo() {}

    public RankInfo(BaseUserModel userModel) {
      if (StringUtils.isNotBlank(userModel.getId())) this.userId = userModel.getId();
      if (StringUtils.isNotBlank(userModel.getUserId())) this.userId = userModel.getUserId();
      this.nickName = userModel.getNickName();
      this.portrait = userModel.getPortrait();
      this.gender = userModel.getGender();
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
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
  }
}
