package com.xiaodou.course.model.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name @see com.xiaodou.course.model.forum.UserScanRecordModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月10日
 * @description 用户查看知识分享资源列表记录模型
 * @version 1.0
 */
@Data
public class UserScanRecordModel {
	@Column(isMajor = true, autoIncrement = true)
	private Long id;
	@Column(canUpdate = false)
	private String userId;
	@Column(canUpdate = false)
	private String module;
	@Column(canUpdate = false)
	private String typeCode;
	@Column(canUpdate = false, betweenScope = true)
	private Timestamp recordDate;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						UserScanRecordModel.class,
						"xd_user_resource_scan_record",
						"D:\\MyWorkSpace\\MyEclipse8.5\\xd-course-2b\\src\\main\\resources\\conf\\mybatis\\forum")
				.buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

}
