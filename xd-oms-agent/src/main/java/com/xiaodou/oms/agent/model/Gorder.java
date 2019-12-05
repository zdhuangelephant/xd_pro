package com.xiaodou.oms.agent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Gorder implements Serializable {

    private static final long serialVersionUID = 8116840113920829034L;

    /**
     * 大订单ID*
     */
    private String id;

    /**
     * 大订单生成时间*
     */
    private Timestamp gorderTime;

    /**
     * 大订单金额*
     */
    private BigDecimal gorderAmount;

    /**
     * 大订单实际金额*
     */
    private BigDecimal gpayAmount;

    /**
     * 大订单原始应付金额*
     */
    private BigDecimal originalGpayAmount;

    /**
     * 大订单活动优惠，节省金额*
     */
    private BigDecimal gsaveAmount;

    /**
     * 大订单手续费/利润*
     */
    private BigDecimal gfee;

    /**
     * 大订单状态*
     */
    private Integer gorderStatus;

    /**
     * 实际支付方式*
     */
    private String realPayMethod;

    /**
     * 购买商品名称<=50字符*
     */
    private String goodsName;

    /**
     * 支付系统订单id*
     */
    private String payOrderId;

    /**
     * 支付url*
     */
    private String payUrl;

    /**
     * 大订单预设的关闭时间*
     */
    private Timestamp preCloseTime;

    /**
     * 成功付款时间*
     */
    private Timestamp paySuccessTime;

    /**
     * 卖家昵称*
     */
    private String sellerNickName;

    /**
     * 用户ID*
     */
    private String buyAccountId;

    /**
     * 下单ip*
     */
    private String gorderIp;

    /**
     * 发票信息*
     */
    private String invoice;

    /**
     * 发货地址*
     */
    private String shippingAddress;

    /**
     * 收货人邮箱*
     */
    private String receiverEmail;

    /**
     * 收货人电话*
     */
    private String receiverPhone;

    /**
     * 送货时间标识（0不限；1工作日；2节假日）*
     */
    private Integer deliveryDateType;

    /**
     * 用户预期的送货时间*
     */
    private Timestamp expectedShippingTime;

    /**
     * 订单外部来源*
     */
    private String outerOrigin;

    /**
     * 订单内部来源*
     */
    private String innerOrigin;

    /**
     * 父大订单ID，用于实现多级订单*
     */
    private String parentGorderId;

    /**
     * 备注*
     */
    private String note;

    /**
     * 大订单最近一次更新时间*
     */
    private Timestamp updateTime;

    /**
     * 客户端类型：10web端；20客户端
     */
    private String clientType;

    /**
     * 大订单关单时间*
     */
    private Timestamp closeTime;

    /**
     * 大订单扩展字段gorderDesc，各个产品自己定义
     */
    private String gorderDesc;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 业务类型/产品类型
     */
    private String productType;

    /**
     * 小订单列表*
     */
    private List<Order> orderList;

    /**
     * 是否需要发票*
     */
    private Integer isNeedInvoice;

    /**
     * 收货人姓名*
     */
    private String receiverName;

    /**
     * 发票信息*
     */
    private WayBill wayBill;

    /**
     * 罚金*
     */
    private BigDecimal mult;
    
    /**
     * 会员卡号,满足DMO需求
     */
    private String uid;
    
    /**
     * 设备号,满足DMO需求
     */
    private String deviceId;
    
    /**
     * 订单来源,满足DMO需求
     */
    private String orderFrom;
    
    /**
     * 是否会员,满足DMO需求
     */
    private Integer isLogin;
    
    /**
     * Gorder订单类型tags
     * */
    private Integer tags;
    

    public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getGorderTime() {
        return gorderTime;
    }

    public void setGorderTime(Timestamp gorderTime) {
        this.gorderTime = gorderTime;
    }

    public BigDecimal getGorderAmount() {
        return gorderAmount;
    }

    public void setGorderAmount(BigDecimal gorderAmount) {
        this.gorderAmount = gorderAmount;
    }

    public BigDecimal getGpayAmount() {
        return gpayAmount;
    }

    public void setGpayAmount(BigDecimal gpayAmount) {
        this.gpayAmount = gpayAmount;
    }

    public BigDecimal getOriginalGpayAmount() {
        return originalGpayAmount;
    }

    public void setOriginalGpayAmount(BigDecimal originalGpayAmount) {
        this.originalGpayAmount = originalGpayAmount;
    }

    public BigDecimal getGsaveAmount() {
        return gsaveAmount;
    }

    public void setGsaveAmount(BigDecimal gsaveAmount) {
        this.gsaveAmount = gsaveAmount;
    }

    public BigDecimal getGfee() {
        return gfee;
    }

    public void setGfee(BigDecimal gfee) {
        this.gfee = gfee;
    }

    public Integer getGorderStatus() {
        return gorderStatus;
    }

    public void setGorderStatus(Integer gorderStatus) {
        this.gorderStatus = gorderStatus;
    }

    public String getRealPayMethod() {
        return realPayMethod;
    }

    public void setRealPayMethod(String realPayMethod) {
        this.realPayMethod = realPayMethod;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public Timestamp getPreCloseTime() {
        return preCloseTime;
    }

    public void setPreCloseTime(Timestamp preCloseTime) {
        this.preCloseTime = preCloseTime;
    }

    public Timestamp getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Timestamp paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public String getSellerNickName() {
        return sellerNickName;
    }

    public void setSellerNickName(String sellerNickName) {
        this.sellerNickName = sellerNickName;
    }

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public String getGorderIp() {
        return gorderIp;
    }

    public void setGorderIp(String gorderIp) {
        this.gorderIp = gorderIp;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Integer getDeliveryDateType() {
        return deliveryDateType;
    }

    public void setDeliveryDateType(Integer deliveryDateType) {
        this.deliveryDateType = deliveryDateType;
    }

    public Timestamp getExpectedShippingTime() {
        return expectedShippingTime;
    }

    public void setExpectedShippingTime(Timestamp expectedShippingTime) {
        this.expectedShippingTime = expectedShippingTime;
    }

    public String getOuterOrigin() {
        return outerOrigin;
    }

    public void setOuterOrigin(String outerOrigin) {
        this.outerOrigin = outerOrigin;
    }

    public String getInnerOrigin() {
        return innerOrigin;
    }

    public void setInnerOrigin(String innerOrigin) {
        this.innerOrigin = innerOrigin;
    }

    public String getParentGorderId() {
        return parentGorderId;
    }

    public void setParentGorderId(String parentGorderId) {
        this.parentGorderId = parentGorderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    public String getGorderDesc() {
        return gorderDesc;
    }

    public void setGorderDesc(String gorderDesc) {
        this.gorderDesc = gorderDesc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public WayBill getWayBill() {
        return wayBill;
    }

    public void setWayBill(WayBill wayBill) {
        this.wayBill = wayBill;
    }

    public BigDecimal getMult() {
        return mult;
    }

    public void setMult(BigDecimal mult) {
        this.mult = mult;
    }

	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

	
}
