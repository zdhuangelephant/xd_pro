package com.xiaodou.server.mapi.vo.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class ForumUserModel {
	/** id 主键 */
	@Column(isMajor = true)
	private String forumId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumType;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String forumClassify;
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
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp createTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = true)
	private Timestamp updateTime;
	@GeneralField
	private String authorName;
	@GeneralField
	private String authorPortrait;
	public String getForumId() {
		return forumId;
	}
	
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}


	public String getForumType() {
		return forumType;
	}


	public void setForumType(String forumType) {
		this.forumType = forumType;
	}


	public String getForumClassify() {
		return forumClassify;
	}


	public void setForumClassify(String forumClassify) {
		this.forumClassify = forumClassify;
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


	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(ForumUserModel.class, "xd_resouces_list",
	            "E:/work3/xd-course-branch/src/main/resources/conf/mybatis/forum/")
	            .buildXml();
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getAuthorPortrait() {
		return authorPortrait;
	}


	public void setAuthorPortrait(String authorPortrait) {
		this.authorPortrait = authorPortrait;
	}
}
