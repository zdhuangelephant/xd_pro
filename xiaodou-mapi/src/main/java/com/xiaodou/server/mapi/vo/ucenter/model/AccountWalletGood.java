package com.xiaodou.server.mapi.vo.ucenter.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.wallet.model.AccountWalletGood.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 账户钱包商品模型
 * @version 1.0
 */
public class AccountWalletGood {

  /** id 主键ID */
  @Column(isMajor = true)
  private Long id;
  /** productLine 产品线 */
  @Column(canUpdate = false)
  private String productLine;
  /** goodsName 商品名 */
  private String goodsName;
  /** goodsPrice 商品价格 */
  private Double goodsPrice;
  /** goodsCount 商品数量 */
  private Double goodsCount;
  public Double getGoodsCount() {
	return goodsCount;
}

public void setGoodsCount(Double goodsCount) {
	this.goodsCount = goodsCount;
}

/** discountRule 折扣规则 */
  private String discountRule;
  /** promotionRule 促销规则 */
  private String promotionRule;
  /** goodsStock 商品库存 */
  private Integer goodsStock;
  /** goodsStatus 商品状态 */
  private Short goodsStatus;
  /** createTime 入库时间 */
  @Column(betweenScope = true, canUpdate = false)
  private Timestamp createTime;
  /** upTime 上架时间 */
  @Column(betweenScope = true)
  private Timestamp upTime;
  /** downTime 下架时间 */
  @Column(betweenScope = true)
  private Timestamp downTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Double getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(Double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  public String getDiscountRule() {
    return discountRule;
  }

  public void setDiscountRule(String discountRule) {
    this.discountRule = discountRule;
  }

  public String getPromotionRule() {
    return promotionRule;
  }

  public void setPromotionRule(String promotionRule) {
    this.promotionRule = promotionRule;
  }

  public Integer getGoodsStock() {
    return goodsStock;
  }

  public void setGoodsStock(Integer goodsStock) {
    this.goodsStock = goodsStock;
  }

  public Short getGoodsStatus() {
    return goodsStatus;
  }

  public void setGoodsStatus(Short goodsStatus) {
    this.goodsStatus = goodsStatus;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpTime() {
    return upTime;
  }

  public void setUpTime(Timestamp upTime) {
    this.upTime = upTime;
  }

  public Timestamp getDownTime() {
    return downTime;
  }

  public void setDownTime(Timestamp downTime) {
    this.downTime = downTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AccountWalletGood.class, "xd_account_wallet_goods",
        "D:/MyWorkSpace/MyEclipse8.5/xd-walllet-2b/src/main/resources/conf/mybatis/").buildXml();
  }

}
