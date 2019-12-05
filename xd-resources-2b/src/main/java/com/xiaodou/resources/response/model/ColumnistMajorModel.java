package com.xiaodou.resources.response.model;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.model.forum.ResourcesColumnist;

/**
 * @name ColumnistListModel CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 专栏列表实体模型
 * @version 1.0
 */
public class ColumnistMajorModel {

  /** columnistId 专栏ID */
  private String columnistId;
  /** columnType 专栏类型 0:默认专栏 1:用户专栏 */
  private Integer columnistType;
  /** columnistTitle 专栏标题 */
  private String columnistTitle;
  /** columnistCover 专栏封面 */
  private String columnistCover;
  /** columnistInfo 专栏简介 */
  private String columnistInfo;
  /** columnistLastedForum 专栏最新文章标题 */
  private String columnistLastedForum;
  /** columnistUnreadForumCount 专栏是否有未读文章 1 有 0 没有 */
  private String columnistUnread;
  /** columnistUserId 专栏作者 */
  private String columnistUserId;
  /** columnistOpusNum 专栏文章数 */
  private String columnistOpusNum;
  /** columnistFollowerNum 专栏关注数 */
  private String columnistFollowerNum;
  /** canFollow 能否关注 */
  private String canFollow;

  public ColumnistMajorModel(ResourcesColumnist columnist) {
    this.columnistId = columnist.getId();
    this.setColumnistType(columnist.getType());
    this.columnistUserId = columnist.getUserId();
    this.columnistTitle = columnist.getTitle();
    this.columnistCover = columnist.getCover();
    this.columnistInfo = columnist.getInfo();
    this.columnistLastedForum = columnist.getLastedForum();
    if (null == columnist.getLastedForumTime()
        || (null != columnist.getLastedForumTime() && columnist.getLastedForumTime().after(
            columnist.getLastedForumTime())))
      this.columnistUnread = Constant.NO;
    else
      this.columnistUnread = Constant.YES;
    this.columnistOpusNum = Integer.toString(columnist.getOpusNum());
    this.columnistFollowerNum = Integer.toString(columnist.getFollowerNum());
    this.canFollow = StringUtils.isNotBlank(columnist.getIsFollower()) ? Constant.NO : Constant.YES;
  }

  public String getColumnistId() {
    return columnistId;
  }

  public void setColumnistId(String columnistId) {
    this.columnistId = columnistId;
  }



  public String getColumnistTitle() {
    return columnistTitle;
  }

  public void setColumnistTitle(String columnistTitle) {
    this.columnistTitle = columnistTitle;
  }

  public String getColumnistCover() {
    return columnistCover;
  }

  public void setColumnistCover(String columnistCover) {
    this.columnistCover = columnistCover;
  }

  public String getColumnistInfo() {
    return columnistInfo;
  }

  public void setColumnistInfo(String columnistInfo) {
    this.columnistInfo = columnistInfo;
  }

  public String getColumnistLastedForum() {
    return columnistLastedForum;
  }

  public void setColumnistLastedForum(String columnistLastedForum) {
    this.columnistLastedForum = columnistLastedForum;
  }

  public String getColumnistUnread() {
    return columnistUnread;
  }

  public void setColumnistUnread(String columnistUnread) {
    this.columnistUnread = columnistUnread;
  }

  public String getColumnistUserId() {
    return columnistUserId;
  }

  public void setColumnistUserId(String columnistUserId) {
    this.columnistUserId = columnistUserId;
  }

  public String getColumnistOpusNum() {
    return columnistOpusNum;
  }

  public void setColumnistOpusNum(String columnistOpusNum) {
    this.columnistOpusNum = columnistOpusNum;
  }

  public String getColumnistFollowerNum() {
    return columnistFollowerNum;
  }

  public void setColumnistFollowerNum(String columnistFollowerNum) {
    this.columnistFollowerNum = columnistFollowerNum;
  }

  public String getCanFollow() {
    return canFollow;
  }

  public void setCanFollow(String canFollow) {
    this.canFollow = canFollow;
  }

  public Integer getColumnistType() {
    return columnistType;
  }

  public void setColumnistType(Integer columnistType) {
    this.columnistType = columnistType;
  }
}
