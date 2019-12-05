package com.xiaodou.sms.model;

import java.util.Date;

/**
 * 短信渠道表的javabean
 * @author wuyunkuo
 *
 */
public class SmsChannelModel {
	//主键id
	private int id;
	//渠道对应的供应商id
	private int merchantId;
	//通道名称
	private String name;
	//通道状态（0 无效，1 有效）
	private int status;
	//短信通道优先级
	private int priority;
	//用户名
	private String userName;
	//秘钥
	private String secretKey;
	//通道地址
	private String channelUrl;
	//通道端口号
	private int port;
	//响应超时时间
	private int timeOut;
	//单通道1秒内最大短信发送数量
	private int controlMaxCount;
	//通道余额
	private double balance;
	//创建时间
	private Date createTime;
	
	private int merchant_id;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getChannelUrl() {
		return channelUrl;
	}

	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getControlMaxCount() {
		return controlMaxCount;
	}

	public void setControlMaxCount(int controlMaxCount) {
		this.controlMaxCount = controlMaxCount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}
	
	

}
