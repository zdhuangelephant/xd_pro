package com.xiaodou.ms.web.request.sms;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 通道请求类
 * @author tao.huang
 *
 */
public class ChannelCreateRequest extends BaseRequest {
	//通道id
		private Integer id;
		@NotEmpty
		private Integer merchantId;
		//通道名称
		private String name;
		private String userName;
		//密钥
		private String secretKey;
		//通道URL
		private String channelURL;
		//通道端口号
		private Integer port;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public Integer getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(Integer merchantId) {
			this.merchantId = merchantId;
		}
		
		
}
