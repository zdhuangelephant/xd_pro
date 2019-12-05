package com.xiaodou.jmsg.server.model;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.server.model.Relation.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description queue-group关联信息
 * @version 1.0
 */
public class Relation {
	/** id 主键 */
	@Column(isMajor = true)
	@GeneralField
	private String id;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String groupId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String queueId;


	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(Relation.class, "message_queue_server_mapper",
	            "E:/work3/xd-dashboard/src/main/resources/conf/mybatis/jmsg/")
	            .buildXml();
	}


	public String getQueueId() {
		return queueId;
	}


	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

}
