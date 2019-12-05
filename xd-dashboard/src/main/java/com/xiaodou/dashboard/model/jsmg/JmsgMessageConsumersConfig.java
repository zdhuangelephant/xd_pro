package com.xiaodou.dashboard.model.jsmg;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class JmsgMessageConsumersConfig {
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
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(JmsgMessageConsumersConfig.class, "message_consumers_config",
	            "E:/work3/xd-dashboard/src/main/resources/conf/mybatis/jmsg/")
	            .buildXml();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
