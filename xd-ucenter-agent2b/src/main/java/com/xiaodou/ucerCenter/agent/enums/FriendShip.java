package com.xiaodou.ucerCenter.agent.enums;

public enum FriendShip {
  /** FRIEND 好友 */
  FRIEND((short) 0),
  /** DELETED 已删除 */
  DELETED((short) 1),
  /** BLACKLIST 黑名单 */
  BLACKLIST((short) 2),
  /** STRENGER 陌生人 */
  STRENGER((short) 3),
  /** ONESELF 自己 */
  ONESELF((short) 4);
  FriendShip(Short code) {
    this.code = code;
  }

  private Short code;

  public Short getCode() {
    return code;
  }

}
