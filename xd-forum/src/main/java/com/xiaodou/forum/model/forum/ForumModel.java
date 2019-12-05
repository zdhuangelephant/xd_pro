package com.xiaodou.forum.model.forum;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * 话题Model－用于数据库操作
 * 
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2015年4月12日 下午12:30:58 weichao.zhai 1.0 初始化创建<br>
 * </p>
 * 
 * @author weichao.zhai
 * @version 1.0
 */
public class ForumModel {
  /**
   * 主键
   */
  @BaseField
  @GeneralField
  private Integer id;
  /**
   * 话题类型
   */
  @BaseField
  @GeneralField
  private Integer digest;
  /**
   * 是否置顶
   */
  @BaseField
  @GeneralField
  private Integer top;
  /**
   * 是否推荐
   */
  @BaseField
  @GeneralField
  private Integer recommend;
  /**
   * 标题
   */
  @BaseField
  @GeneralField
  private String title;
  /**
   * 概括
   */
  @BaseField
  @GeneralField
  private String outline;
  /**
   * 内容
   */
  @BaseField
  @GeneralField
  private String content;
  /**
   * 图片
   */
  @BaseField
  @GeneralField
  private String images;
  /**
   * 分类ID
   */
  @BaseField
  @GeneralField
  private String categoryId;
  /**
   * 分类名称
   */
  @BaseField
  @GeneralField
  private String categoryName;
  /** categoryShortName 分类简称 */
  @BaseField
  @GeneralField
  private String categoryShortName;
  /**
   * 发布人ID
   */
  @BaseField
  @GeneralField
  private Long publisherId;
  /**
   * 回帖数目
   */
  @BaseField
  @GeneralField
  private Integer repliesNumber;
  /**
   * 点赞数目
   */
  @BaseField
  @GeneralField
  private Integer praiseNumber = 0;
  /** sPraiseNumber 点赞数目 */
  @BaseField
  @GeneralField
  private String sPraiseNumber = "0";
  /**
   * 权值
   */
  @BaseField
  @GeneralField
  private Integer assign;
  /**
   * 标签
   */
  @BaseField
  @GeneralField
  private Integer tag;
  /**
   * 操作人
   */
  @BaseField
  private String operator;
  /**
   * 操作ip
   */
  @BaseField
  private String operatorip;
  /**
   * 创建时间
   */
  @BaseField
  @GeneralField
  private Timestamp createTime;
  /**
   * 最后操作时间
   */
  @BaseField
  @GeneralField
  private Timestamp updateTime;
  /**
   * 是否被点赞过
   */
  @BaseField
  @GeneralField
  private String isPraise = "0";

  public String getCategoryShortName() {
    return categoryShortName;
  }

  public void setCategoryShortName(String categoryShortName) {
    this.categoryShortName = categoryShortName;
  }

  public String getsPraiseNumber() {
    return sPraiseNumber;
  }

  public void setsPraiseNumber(String sPraiseNumber) {
    this.sPraiseNumber = sPraiseNumber;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDigest() {
    return digest;
  }

  public void setDigest(Integer digest) {
    this.digest = digest;
  }

  public Integer getTop() {
    return top;
  }

  public void setTop(Integer top) {
    this.top = top;
  }

  public Integer getRecommend() {
    return recommend;
  }

  public void setRecommend(Integer recommend) {
    this.recommend = recommend;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline == null ? null : outline.trim();
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public Long getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Long publisherId) {
    this.publisherId = publisherId;
  }

  public Integer getRepliesNumber() {
    return repliesNumber;
  }

  public void setRepliesNumber(Integer repliesNumber) {
    this.repliesNumber = repliesNumber;
  }

  public Integer getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(Integer praiseNumber) {
    this.praiseNumber = praiseNumber;
  }

  public Integer getAssign() {
    return assign;
  }

  public void setAssign(Integer assign) {
    this.assign = assign;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator == null ? null : operator.trim();
  }

  public String getOperatorip() {
    return operatorip;
  }

  public void setOperatorip(String operatorip) {
    this.operatorip = operatorip == null ? null : operatorip.trim();
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getIsPraise() {
    return isPraise;
  }

  public void setIsPraise(String isPraise) {
    this.isPraise = isPraise;
  }

}
