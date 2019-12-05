package com.xiaodou.ms.model.sms;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 通道类
 * @author tao.huang
 *
 */
public class SmsChannelModel {
	//通道id
	private Integer id;
	//通道对应的供应商id
	private Integer merchantId;
	//通道名称
	private String name;
	//状态
	private Integer status;
	//短信通道优先级
	private Integer priority;
	private String userName;
	//密钥
	private String secretKey;
	//通道URL
	private String channelURL;
	//通道端口号
	private Integer port;
	//响应超出时间
	private Integer timeOut;
	//单通道1秒内最大短信发送数量
	private Integer controlMaxCount;
	//通道余额
	private BigDecimal balance;
	//创建时间
	private Timestamp createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
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
	public String getChannelURL() {
		return channelURL;
	}
	public void setChannelURL(String channelURL) {
		this.channelURL = channelURL;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
	public Integer getControlMaxCount() {
		return controlMaxCount;
	}
	public void setControlMaxCount(Integer controlMaxCount) {
		this.controlMaxCount = controlMaxCount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
