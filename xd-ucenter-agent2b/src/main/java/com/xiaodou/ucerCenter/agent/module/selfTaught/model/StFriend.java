package com.xiaodou.ucerCenter.agent.module.selfTaught.model;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ucerCenter.agent.model.FriendModel;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.ucerCenter.agent.response.FriendListResponse.Friend;

public class StFriend extends Friend {
  /** userId 好友ID */
  private String userId;
  /** userName 账号 */
  private String userName;
  /** nickName 好友昵称 */
  private String nickName;
  /** fortait 好友头像地址 */
  private String portrait;
  /** gender 好友性别 */
  private String gender;
  /** title 好友称号 */
  private String medalName;
  /** medalUrl 称号图片地址 */
  private String medalUrl;
  /** pkWins 好友胜场 */
  private String pkWins;
  /** pkLost 好友败场 */
  private String pkLost;

  public Friend init(FriendModel friendModel) {
    if (null != friendModel) {
      this.userId = friendModel.getTargetUserId();
      this.userName = friendModel.getUserName();
      this.nickName = friendModel.getNickName();
      this.portrait = friendModel.getPortrait();
      if (null != friendModel.getGender()) this.gender = friendModel.getGender().toString();
      if (StringUtils.isJsonNotBlank(friendModel.getModuleInfo())) {
        StUserInfo userInfo =
            FastJsonUtil.fromJsons(friendModel.getModuleInfo(), new TypeReference<StUserInfo>() {});
        if (StringUtils.isJsonNotBlank(userInfo.getMedal())) {
          StMedal medal = FastJsonUtil.fromJson(userInfo.getMedal(), StMedal.class);
          this.medalName = medal.getMedalName();
          this.medalUrl = medal.getMedalImg();
        }
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getMedalName() {
    return medalName;
  }

  public void setMedalName(String medalName) {
    this.medalName = medalName;
  }

  public String getMedalUrl() {
    return medalUrl;
  }

  public void setMedalUrl(String medalUrl) {
    this.medalUrl = medalUrl;
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
