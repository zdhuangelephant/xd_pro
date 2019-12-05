package com.xiaodou.ms.web.request.sms;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * 短信模板添加请求类
 * @author huangtao
 *
 */
public class TemplateCreateRequest extends BaseRequest { 
	//短信模板id
	private Integer id;
	private Integer typeId;
	 
	//短信模板内容
	private String messageContent;
	
	//短信模板描述
	private String description;

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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

}
