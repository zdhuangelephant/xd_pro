package com.xiaodou.autotest.web.request;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;

public class HeaderGroupReq {
	private Integer id;
	private String  headerGroupName;
	private Timestamp createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHeaderGroupName() {
		return headerGroupName;
	}
	public void setHeaderGroupName(String headerGroupName) {
		this.headerGroupName = headerGroupName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
