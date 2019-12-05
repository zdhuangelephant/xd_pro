package com.xiaodou.mooccrawler.domain.course;

import java.util.Date;

/**
 * 
 * @ClassName: KeywordResourceModel
 * @Description: 关键词与资源绑定Model
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午10:32:35
 */
public class CourseKeywordResourceModel {

	// 关联ID
	private Integer id;

	// 对应资源的ID(比如，视频，文档，题库)
	private Integer resourceId;

	// 关键知识点的ID
	private Integer keywordId;

	// 关联资源类型：(比如文档，视频，题库)
	private Integer resourceType;

	// 创建时间
	private Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public Integer getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Integer keywordId) {
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
