package com.xiaodou.dashboard.event;

import lombok.Data;

import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;

/**
 * @name DataCleanEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017=08-08 * @description 重发消息
 * @version 1.0
 */
@Data
public class ReSendEvent<T> {


	private final static String routeName = "";

	
	private T dataModel;

	/**
	 * 鍙戦�娓呮礂鏁版嵁浜嬩欢
	 */
	public final void send() {
		AbstractMessagePojo pojo = new AbstractMessagePojo(routeName);
		pojo.setTransferObject(this);
		RabbitMQSender.getInstance().send(pojo);
	}


	public T getDataModel() {
		return dataModel;
	}

	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}

	public static String getRoutename() {
		return routeName;
	}

}
