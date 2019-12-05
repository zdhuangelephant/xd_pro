package com.xiaodou.jmsg.model;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.model.MessageConsumersConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description cousumer信息
 * @version 1.0
 */
public class MessageConsumersConfig {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
    private String id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String messageName;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String url;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private int timeOut;
	
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MessageConsumersConfig.class, "message_consumers_config",
	            "E:/work1/xiaodou-jmsg/src/main/resources/conf/mybatis/")
	            .buildXml();
	}
}
