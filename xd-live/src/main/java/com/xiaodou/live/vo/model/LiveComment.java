package com.xiaodou.live.vo.model;

/**
 * @name LiveComment 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播评论
 * @version 1.0
 */
public class LiveComment {

  /** nickName 用户名 */
  private String nickName;
  /** portrait 用户头像 */
  private String portrait;
  /** comment 用户评论 */
  private String comment;
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
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  
}
