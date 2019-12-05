package com.xiaodou.resources.model.forum;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * 话题评论Model－用于数据库操作
 * 
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2015年4月12日 下午12:21:50 weichao.zhai 1.0 初始化创建<br>
 * </p>
 * 
 * @author weichao.zhai
 * @version 1.0
 */
public class ForumCommentModel {
  /**
   * 主键
   */
  @BaseField
  @GeneralField
  private Long id;

  /**
   * 话题ID
   */
  @BaseField
  @GeneralField
  private Long resourcesId;

  /**
   * 内容
   */
  @BaseField
  @GeneralField
  private String content;

  /**
   * 类型
   */
  @BaseField
  @GeneralField
  private Integer type;

  /**
   * 评论人ID
   */
  @BaseField
  @GeneralField
  private Long replyId;

  @BaseField
  @GeneralField
  private Long targeId;

  @BaseField
  @GeneralField
  private Long targeCommentId;

  @BaseField
  @GeneralField
  private String images;

  @BaseField
  @GeneralField
  private String targeContent;

  /**
   * 点赞数量
   */
  @BaseField
  @GeneralField
  private Integer praiseNumber;

  /**
   * 标签
   */
  @BaseField
  @GeneralField
  private Integer tag;

  /**
   * 创建时间
   */
  @BaseField
  @GeneralField
  private Timestamp createTime;

  /**
   * 操作人
   */
  @BaseField
  private String operator;

  /**
   * 操作IP
   */
  @BaseField
  private String operatorip;


  private Long productId;

  private Long itemId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public Long getTargeId() {
    return targeId;
  }

  public void setTargeId(Long targeId) {
    this.targeId = targeId;
  }

  public Long getTargeCommentId() {
    return targeCommentId;
  }

  public void setTargeCommentId(Long targeCommentId) {
    this.targeCommentId = targeCommentId;
  }

  public Integer getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(Integer praiseNumber) {
    this.praiseNumber = praiseNumber;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
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

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getTargeContent() {
    return targeContent;
  }

  public void setTargeContent(String targeContent) {
    this.targeContent = targeContent;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }


  public Long getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(Long resourcesId) {
    this.resourcesId = resourcesId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
