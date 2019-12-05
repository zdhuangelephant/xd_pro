package com.xiaodou.userCenter.model;

import com.xiaodou.userCenter.util.TimeUtil;

public class HelpDocModel {
	private String id;
	private String title;
	private String content;
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return TimeUtil.format_yyyy_MM_dd_HH_mm_ss(TimeUtil
				.parse_yyyy_MM_dd_HH_mm_ss(createTime));
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "HelpDocModel [id=" + id + ", title=" + title + ", content="
				+ content + ", createTime=" + createTime + "]";
	}

}
