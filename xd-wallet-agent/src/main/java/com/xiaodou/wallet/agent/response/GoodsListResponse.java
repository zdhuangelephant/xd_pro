package com.xiaodou.wallet.agent.response;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @name @see com.xiaodou.wallet.response.GoodsListResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 产品列表响应
 * @version 1.0
 */
public class GoodsListResponse extends ResponseBase {

  private Double accountAmount = 0d;

  private List<Good> goodList = Lists.newArrayList();

  public Double getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(Double accountAmount) {
    this.accountAmount = accountAmount;
  }

  public List<Good> getGoodList() {
    return goodList;
  }

  public void setGoodList(List<Good> goodList) {
    this.goodList = goodList;
  }

  /**
   * @name @see com.xiaodou.wallet.response.wallet.GoodsListResponse.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年6月3日
   * @description 商品信息
   * @version 1.0
   */
  public static class Good {
    /** id 商品主键 */
    private Long id;
    /** productLine 产品线 */
    private String productLine;
    /** goodsName 商品名称 */
    private String goodsName;
    /** goodsPrice 商品价格 */
    private Double goodsPrice;

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
  }
}
