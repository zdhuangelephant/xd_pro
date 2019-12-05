package com.xiaodou.server.pay.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.server.pay.model.PayToken.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 支付Token模型
 * @version 1.0
 */
public class PayToken {

  /** id 主键ID */
  @Column(isMajor = true, sortBy = true)
  private String id;
  /** businessType 业务类型 */
  private Integer businessType;
  /** tradeNo 交易流水号 */
  private String tradeNo;
  /** outTradeNo 业务订单号 */
  private String outTradeNo;
  /** businessStatus 业务状态 */
  private Integer businessStatus;
  /** createTime 创建时间 */
  @Column(sortBy = true)
  private Timestamp createTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public Integer getBusinessStatus() {
    return businessStatus;
  }

  public void setBusinessStatus(Integer businessStatus) {
    this.businessStatus = businessStatus;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(PayToken.class, "xd_pay_token",
        "D:\\MyWorkSpace\\MyEclipse8.5\\xiaodou-pay\\src\\main\\resources\\conf\\mybatis\\pay")
        .buildXml();
  }
}
