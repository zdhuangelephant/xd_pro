package com.xiaodou.ms.web.request.sms;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * 编辑请求类
 * @author tao.huang
 *
 */
public class ChannelEditRequest extends BaseRequest {
	//通道id
			private Integer id;
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

}
