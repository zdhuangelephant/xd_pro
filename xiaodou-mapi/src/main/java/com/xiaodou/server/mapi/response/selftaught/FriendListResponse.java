package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.mapi.response.FriendListResponse.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月26日
 * @description 获取好友列表response
 * @version 1.0
 */
public class FriendListResponse extends BaseResponse {
  public FriendListResponse(ResultType type) {
    super(type);
  }

  /** friendList 好友列表 */
  private List<Friend> friendList = Lists.newArrayList(new Friend());

  public List<Friend> getFriendList() {
    return friendList;
  }

  public void setFriendList(List<Friend> friendList) {
    this.friendList = friendList;
  }

  /**
   * @name @see com.xiaodou.server.mapi.response.Friend.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年1月26日
   * @description 好友实体类
   * @version 1.0
   */
  public static class Friend {
    /** userId 好友ID */
    private String userId = "111";
    /** nickName 好友昵称 */
    private String nickName = "测试";
    /** fortait 好友头像地址 */
    private String portrait =
        "http://7xigj3.com1.z0.glb.clouddn.com/image_2015_09_19_16_21_29e43a5c01-c4b4-41ad-a027-7e0f09c33c8d";
    /** gender 好友性别 */
    private String gender = "1";
    /** title 好友称号 */
    private String title = "东方不败";
    /** pkWins 好友胜场 */
    private String pkWins = "12";
    /** pkLost 好友败场 */
    private String pkLost = "0";

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
}
