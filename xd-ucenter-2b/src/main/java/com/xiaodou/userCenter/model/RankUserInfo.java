package com.xiaodou.userCenter.model;

import lombok.Data;

@Data
public class RankUserInfo {
  /** rank 排名 */
  private String rank;
  /**userId 用户Id*/
  private String userId;
  /**gender 性别*/
  private String gender;
  /**portrait 头像*/
  private String portrait;
  /** nickName 昵称 */
  private String nickName;
  /**credit 积分*/
  private String credit;
}
