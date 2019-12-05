package com.xiaodou.server.mapi.vo.ucenter.model;

public class UserRelateNoticeModel extends NoticeModel {
	//标识id
	private Long relateId;
	//用户id
	private Long userId;
	//通知公告id
	private Integer noticeId;
	// 状态: 1未读 2已读
	private Short status;

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
}
