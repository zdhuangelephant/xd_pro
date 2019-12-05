package com.xiaodou.ms.model.course;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @ClassName: KeywordResourceModel
 * @Description: 关键词与资源绑定Model
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午10:32:35
 */
@Data
public class CourseKeywordResourceModel {

	// 关联ID
	private Long id;

	// 对应资源的ID(比如，视频，文档，题库)
	private Long resourceId;

	// 关键知识点的ID
	private Long keywordId;

	// 关联资源类型：(比如文档，视频，题库)
	private Integer resourceType;

	// 创建时间
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
