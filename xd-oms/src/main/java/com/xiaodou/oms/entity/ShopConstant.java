package com.xiaodou.oms.entity;

public class ShopConstant implements java.io.Serializable
{
	private String shopPlatformId;
	private String shopSellerId;
	private String shopNotifyUrl;
	private String sellerNickName;

	public String getShopPlatformId()
	{
		return shopPlatformId;
	}

	public void setShopPlatformId(String shopPlatformId)
	{
		this.shopPlatformId = shopPlatformId;
	}

	public String getShopSellerId()
	{
		return shopSellerId;
	}

	public void setShopSellerId(String shopSellerId)
	{
		this.shopSellerId = shopSellerId;
	}

	public String getShopNotifyUrl()
	{
		return shopNotifyUrl;
	}

	public void setShopNotifyUrl(String shopNotifyUrl)
	{
		this.shopNotifyUrl = shopNotifyUrl;
	}

	public String getSellerNickName()
	{
		return sellerNickName;
	}

	public void setSellerNickName(String sellerNickName)
	{
		this.sellerNickName = sellerNickName;
	}
}
