package com.xiaodou.ms.web.request.sms;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * 短信模板修改请求类
 * @author Administrator
 *
 */
public class TemplateEditRequest extends BaseRequest {
	//短信模板id
		private Integer id;
		 
		//短信模板内容
		private String messageContent;
		
		//短信模板描述
		private String description;
		//短信模板状态
		private Integer status;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getMessageContent() {
			return messageContent;
		}

		public void setMessageContent(String messageContent) {
			this.messageContent = messageContent;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

}
