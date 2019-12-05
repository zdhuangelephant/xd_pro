package com.xiaodou.userCenter.model;

/**
 * @name @see com.xiaodou.userCenter.model.FriendModel.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月1日
 * @description 好友模型
 * @version 1.0
 */
public class FriendModel extends UserModel {

  /** userId 用户ID */
  private String userId;

  /** targetUserId 好友ID */
  private String targetUserId;

  /** relationShip 状态 */
  private Short relationShip;

  /** moduleInfo */
  private String moduleInfo;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Short getRelationShip() {
    return relationShip;
  }

  public void setRelationShip(Short relationShip) {
    this.relationShip = relationShip;
  }

  public String getModuleInfo() {
    return moduleInfo;
  }

  public void setModuleInfo(String moduleInfo) {
    this.moduleInfo = moduleInfo;
  }

}
