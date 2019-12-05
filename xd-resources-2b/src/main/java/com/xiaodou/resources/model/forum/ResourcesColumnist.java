package com.xiaodou.resources.model.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;

/**
 * @name ResourcesColumnist CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月31日
 * @description 资源专栏模型
 * @version 1.0
 */
public class ResourcesColumnist {

  /** id 主键 */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** module 模块 */
  @Column(canUpdate = false)
  private String module;
  /** type 专栏类型 1 默认专栏 2 用户专栏 */
  @Column(canUpdate = false)
  private Integer type;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** title 标题 */
  private String title;
  /** cover 封面 */
  private String cover;
  /** info 简介 */
  private String info;
  /** opusNum 作品数 */
  private Integer opusNum;
  /** followerNum 关注数 */
  private Integer followerNum;
  /** columnistHeat 专栏热度 */
  private Integer columnistHeat;
  /** lastedForum 专栏最新文章标题 */
  private String lastedForum;
  /** lastedForumTime 专栏最新文章发布时间 */
  private Timestamp lastedForumTime;
  /** createTime 创建时间 */
  @Column(betweenScope = true, canUpdate = false)
  private Timestamp createTime;
  /** isFollower 是否关注 */
  private String isFollower;
  /** lastVisit 最后访问时间 */
  private Timestamp lastVisit;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Integer getOpusNum() {
    return opusNum;
  }

  public void setOpusNum(Integer opusNum) {
    this.opusNum = opusNum;
  }

  public Integer getFollowerNum() {
    return followerNum;
  }

  public void setFollowerNum(Integer followerNum) {
    this.followerNum = followerNum;
  }

  public Integer getColumnistHeat() {
    return columnistHeat;
  }

  public void setColumnistHeat(Integer columnistHeat) {
    this.columnistHeat = columnistHeat;
  }

  public String getLastedForum() {
    return lastedForum;
  }

  public void setLastedForum(String lastedForum) {
    this.lastedForum = lastedForum;
  }

  public Timestamp getLastedForumTime() {
    return lastedForumTime;
  }

  public void setLastedForumTime(Timestamp lastedForumTime) {
    this.lastedForumTime = lastedForumTime;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getIsFollower() {
    return isFollower;
  }

  public void setIsFollower(String isFollower) {
    this.isFollower = isFollower;
  }

  public Timestamp getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(Timestamp lastVisit) {
    this.lastVisit = lastVisit;
  }

}
