package com.xiaodou.moofficial.entity;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name ForumModel CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月7日
 * @description 知识分享模型
 * @version 1.0
 */
@Data
public class ForumModel {
	/** id 主键 */
	@Column(isMajor = true)
	private String forumId;
	private Short status;
	@Column(persistent = false)
	private String statusDesc;
	@GeneralField
	@Column(canUpdate = true, sortBy = false, listValue = true)
	private String forumType;
	@Column(persistent = false)
	private String forumTypeDesc;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumClassify;
	@Column(persistent = false)
	private String forumClassifyDesc;
	@Column(canUpdate = true, sortBy = false)
	private String forumCover;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumTitle;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumContent;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumMedia;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Integer forumPraiserNum;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Integer forumReaderNum;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String authorId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumTag;
	@Column(persistent = false)
	private String forumTagName;
	@GeneralField
	@Column(canUpdate = true, sortBy = true, betweenScope = true)
	private Timestamp createTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = true, betweenScope = true)
	private Timestamp updateTime;
	@Column(persistent = false)
	private AuthorModel author;

	private String htmlContent;
	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		this.forumId = forumId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getForumType() {
		return forumType;
	}

	public void setForumType(String forumType) {
		this.forumType = forumType;
	}

	public String getForumTypeDesc() {
		return forumTypeDesc;
	}

	public void setForumTypeDesc(String forumTypeDesc) {
		this.forumTypeDesc = forumTypeDesc;
	}

	public String getForumClassify() {
		return forumClassify;
	}

	public void setForumClassify(String forumClassify) {
		this.forumClassify = forumClassify;
	}

	public String getForumClassifyDesc() {
		return forumClassifyDesc;
	}

	public void setForumClassifyDesc(String forumClassifyDesc) {
		this.forumClassifyDesc = forumClassifyDesc;
	}

	public String getForumCover() {
		return forumCover;
	}

	public void setForumCover(String forumCover) {
		this.forumCover = forumCover;
	}

	public String getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String forumTitle) {
		this.forumTitle = forumTitle;
	}

	public String getForumContent() {
		return forumContent;
	}

	public void setForumContent(String forumContent) {
		this.forumContent = forumContent;
	}

	public String getForumMedia() {
		return forumMedia;
	}

	public void setForumMedia(String forumMedia) {
		this.forumMedia = forumMedia;
	}

	public Integer getForumPraiserNum() {
		return forumPraiserNum;
	}

	public void setForumPraiserNum(Integer forumPraiserNum) {
		this.forumPraiserNum = forumPraiserNum;
	}

	public Integer getForumReaderNum() {
		return forumReaderNum;
	}

	public void setForumReaderNum(Integer forumReaderNum) {
		this.forumReaderNum = forumReaderNum;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getForumTag() {
		return forumTag;
	}

	public void setForumTag(String forumTag) {
		this.forumTag = forumTag;
	}

	public String getForumTagName() {
		return forumTagName;
	}

	public void setForumTagName(String forumTagName) {
		this.forumTagName = forumTagName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public AuthorModel getAuthor() {
		return author;
	}

	public void setAuthor(AuthorModel author) {
		this.author = author;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(ForumModel.class, "xd_resource_list", "src/main/resources/conf/mybatis").buildXml();
	}
}
