package com.xiaodou.oms.vo;

import com.xiaodou.oms.entity.product.Merchant;


public class OrderItemVo {
    
	/**购买数量*/
	private Integer buyCount;
	
	/**商家信息*/
	private Merchant merchant;

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerhcant(Merchant merchant) {
		this.merchant = merchant;
	}

}
