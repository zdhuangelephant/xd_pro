package com.xiaodou.live.vo.model;

/**
 * @name LiveOwner 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月24日
 * @description 直播主讲人
 * @version 1.0
 */
public class LiveOwner {
  
  /** nickName 主讲人 */
  private String nickName;
  /** portrait 主讲人头像 */
  private String portrait;
  /** introduction 主讲人介绍 */
  private String introduction;
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
  public String getIntroduction() {
    return introduction;
  }
  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

}
