package com.xiaodou.esagent;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.esagent.enums.CommonEnum.DumpMethod;
import com.xiaodou.esagent.model.DumpType;

public class ResourcesForEs extends DumpType {
	public ResourcesForEs() {
		super();
	}
	public ResourcesForEs(DumpMethod method, String id, ResourcesForEs resourcesForEs) {
		super(method, id,resourcesForEs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 主键
	 */
	@BaseField
	@GeneralField
	private String id;
	/**
	 * 话题类型
	 */
	@BaseField
	@GeneralField
	private Integer digest;
	/**
	 * 标题
	 */
	@BaseField
	@GeneralField
	private String title;
	/**
	 * 概括
	 */
	@BaseField
	@GeneralField
	private String outline;
	/**
	 * 内容
	 */
	@BaseField
	@GeneralField
	private String content;

	/**
	 * 发布人ID
	 */
	@BaseField
	@GeneralField
	private Long publisherId;
	/**
	 * 创建时间
	 */
	@BaseField
	@GeneralField
	private Timestamp createTime;

	public Integer getDigest() {
		return digest;
	}

	public void setDigest(Integer digest) {
		this.digest = digest;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline == null ? null : outline.trim();
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}

}
