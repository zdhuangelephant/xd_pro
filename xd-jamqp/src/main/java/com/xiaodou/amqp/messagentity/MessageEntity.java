package com.xiaodou.amqp.messagentity;

import java.io.Serializable;
import java.util.UUID;

import com.xiaodou.amqp.util.timehelper.AMQPTimer;

public class MessageEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID messageID;                      // 消息ID
	private String messageType;                 // 消息类型
	private Object messageBody;                // 消息实体
	private long sendTime;                         // 发送时间
	public MessageEntity(){
		this.messageID = UUID.randomUUID();
		this.sendTime = AMQPTimer.getNowTime();
	}
	public UUID getMessageID() {
		return messageID;
	}
	public void setMessageID(UUID messageID) {
		this.messageID = messageID;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Object getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(Object messageBody) {
		this.messageBody = messageBody;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
}
