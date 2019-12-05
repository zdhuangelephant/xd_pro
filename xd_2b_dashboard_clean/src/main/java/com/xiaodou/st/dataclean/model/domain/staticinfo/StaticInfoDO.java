package com.xiaodou.st.dataclean.model.domain.staticinfo;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class StaticInfoDO {
	@Column(isMajor = true, sortBy = false)
	private Long id;
	@Column(canUpdate = true, sortBy = false)
	private String examDate;
	@Column(canUpdate = true, sortBy = false)
	private String beginApplyTime;
	@Column(canUpdate = true, sortBy = false)
	private String endApplyTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(StaticInfoDO.class,
				"xd_dashboard_static_info",
				"src/main/resources/conf/mybatis/staticinfo/").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(String beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public String getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(String endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

}