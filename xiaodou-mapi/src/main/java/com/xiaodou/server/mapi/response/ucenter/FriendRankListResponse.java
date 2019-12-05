package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;

public class FriendRankListResponse extends BaseResponse {

  public FriendRankListResponse() {}

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
  
  @Data
  public static class RankInfo {
    /** userId 用户ID */
    private String userId = StringUtils.EMPTY;
    /** nickName 昵称 */
    private String nickName = StringUtils.EMPTY;
    /** portrait 头像 */
    private String portrait = StringUtils.EMPTY;
    /** gender 性別 */
    private String gender = StringUtils.EMPTY;
  }
}
