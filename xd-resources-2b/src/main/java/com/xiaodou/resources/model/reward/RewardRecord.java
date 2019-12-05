package com.xiaodou.resources.model.reward;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.forum.model.reward.RewardRecord.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description 赞赏记录
 * @version 1.0
 */
public class RewardRecord {

  /** id 主键ID */
  @Column(isMajor = true)
  private String id;
  /** module 模块号 */
  @Column(canUpdate = false)
  private String module;
  /** recordId 资源ID */
  @Column(canUpdate = false)
  private String recordId;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /* targetUserId 目标用户id */
  @Column(canUpdate = false)
  private String targetUserId;
  /* giftType 赞赏类型 */
  @Column(canUpdate = false)
  private Integer giftType;
  /* gift_money 赞赏金额 */
  @Column(canUpdate = false)
  private Double giftMoney;
  /** createTime 账户创建时间 */
  @Column(canUpdate = false, betweenScope = true)
  private Timestamp createTime;
  /** operateType 操作类型 */
  private Integer operateType;
  /** operateDesc 操作类型明细 */
  private String operateDesc;
  /* h5Type 网页支付类型 */
  private String h5Type;
  /* remark 网页支付备注 */
  private String remark;
  /* payType 支付類型 */
  private String payType;
  /* rate 汇率 */
  private String rate;
  /*poundage 手续费*/
  private Double poundage;

  public Double getPoundage() {
    return poundage;
  }

  public void setPoundage(Double poundage) {
    this.poundage = poundage;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getH5Type() {
    return h5Type;
  }

  public void setH5Type(String h5Type) {
    this.h5Type = h5Type;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

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

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

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

  public Integer getGiftType() {
    return giftType;
  }

  public void setGiftType(Integer giftType) {
    this.giftType = giftType;
  }

  public Double getGiftMoney() {
    return giftMoney;
  }

  public void setGiftMoney(Double giftMoney) {
    this.giftMoney = giftMoney;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Integer getOperateType() {
    return operateType;
  }

  public void setOperateType(Integer operateType) {
    this.operateType = operateType;
  }

  public String getOperateDesc() {
    return operateDesc;
  }

  public void setOperateDesc(String operateDesc) {
    this.operateDesc = operateDesc;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RewardRecord.class, "xd_resources_reward_record",
        "D:/MyWorkSpace/MyEclipse8.5/xd-resource/src/main/resources/conf/mybatis/reward")
        .buildXml();
  }
}
