package com.xiaodou.userCenter.module.selfTaught.model;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.model.FriendModel;
import com.xiaodou.userCenter.response.FriendListResponse.Friend;

public class StFriend extends Friend {
  /** userId 好友ID */
  private String userId;
  /** nickName 好友昵称 */
  private String nickName;
  /** fortait 好友头像地址 */
  private String portrait;
  /** gender 好友性别 */
  private String gender;
  /** title 好友称号 */
  private String title;
  /** pkWins 好友胜场 */
  private String pkWins;
  /** pkLost 好友败场 */
  private String pkLost;

  public Friend init(FriendModel friendModel) {
    if (null != friendModel) {
      this.userId = friendModel.getTargetUserId();
      this.nickName = friendModel.getNickName();
      this.portrait = friendModel.getPortrait();
      if (null != friendModel.getGender()) this.gender = friendModel.getGender().toString();
      if (StringUtils.isJsonNotBlank(friendModel.getModuleInfo())) {
        StUserInfo userInfo =
            FastJsonUtil.fromJsons(friendModel.getModuleInfo(), new TypeReference<StUserInfo>() {});
        this.title = userInfo.getTitle();
      }
    }
    return this;
  }

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPkWins() {
    return pkWins;
  }

  public void setPkWins(String pkWins) {
    this.pkWins = pkWins;
  }

  public String getPkLost() {
    return pkLost;
  }

  public void setPkLost(String pkLost) {
    this.pkLost = pkLost;
  }
}
