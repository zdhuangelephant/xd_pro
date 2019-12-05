package com.xiaodou.ms.web.request.sms;

import java.sql.Timestamp;

import com.xiaodou.ms.model.sms.SmsLogModel;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SmsLogRequest extends BaseRequest {
	private Long id;
	private String message;
	private int sendStatus;
	private int channelId;
	private int templateId;
	private String channelSendResult;
	private String mobile;
	private Timestamp createTime;
	private int typeId;

	private String beginDate;
	private String endDate;
	
	public SmsLogModel initModel() {
		SmsLogModel model = new SmsLogModel();
		model.setId(id);
		model.setBeginDate(beginDate);
		model.setChannelId(channelId);
		model.setChannelSendResult(channelSendResult);
		model.setCreateTime(createTime);
		model.setEndDate(endDate);
		model.setMessage(message);
		model.setSendStatus(sendStatus);
		model.setMobile(mobile);
		model.setTemplateId(templateId);
		model.setTypeId(typeId);
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getChannelSendResult() {
		return channelSendResult;
	}

	public void setChannelSendResult(String channelSendResult) {
		this.channelSendResult = channelSendResult;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
