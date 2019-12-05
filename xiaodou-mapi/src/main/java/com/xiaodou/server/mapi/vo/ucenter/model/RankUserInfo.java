package com.xiaodou.server.mapi.vo.ucenter.model;

import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;

public class RankUserInfo extends UserModelResponse {
  private String userId;// 用户Id
  private String isFriend;//  1 是 0 否
  private String moduleInfo;// 模块信息
  private String rank;// 排名
  private String credit;// 积分

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

  public String getModuleInfo() {
    return moduleInfo;
  }

  public void setModuleInfo(String moduleInfo) {
    this.moduleInfo = moduleInfo;
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

}
