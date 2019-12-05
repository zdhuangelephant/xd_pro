package com.xiaodou.course.vo.forum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.xiaodou.course.model.forum.ForumModel;

public class Forum {
	private String forumId;
	private String forumType;
	private String forumClassify;
	private String forumTitle;
	private String forumContent;
	private String forumCover;
	private String forumMedia;
	private String forumPraiserNum;
	private String forumReaderNum;
	private String authorId;
	private String forumTag;
	private String createTime;
	private String updateTime;
	private String authorName;
	private String authorPortrait;
	private String isPraised;
	private String forumTagName;
	public Forum(){}
	public Forum(ForumModel forum){
		this.forumId=forum.getForumId();
		this.forumType=forum.getAuthorName();
		this.forumClassify=forum.getForumClassify();
		this.forumTitle=forum.getForumTitle();
		this.forumContent=forum.getForumContent();
		this.forumCover=forum.getForumCover();
		this.forumMedia=forum.getForumMedia();
		this.forumPraiserNum=forum.getForumPraiserNum().toString();
		this.forumReaderNum=forum.getForumReaderNum().toString();
		this.authorId=forum.getAuthorId();
		this.forumTag=forum.getForumTag();	
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		this.createTime=sdf.format(forum.getCreateTime());
		if(forum.getUpdateTime()!=null){
			this.updateTime=sdf.format(forum.getUpdateTime());
		}
		this.authorName=forum.getAuthorName();
		this.authorPortrait=forum.getAuthorPortrait();
		this.isPraised=forum.getIsPraised();
		this.forumType=forum.getForumType();
		this.forumTagName=forum.getForumTagName();
	}
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


	public String getForumCover() {
		return forumCover;
	}


	public void setForumCover(String forumCover) {
		this.forumCover = forumCover;
	}


	public String getIsPraised() {
		return isPraised;
	}


	public void setIsPraised(String isPraised) {
		this.isPraised = isPraised;
	}


	public String getForumReaderNum() {
		return forumReaderNum;
	}


	public void setForumReaderNum(String forumReaderNum) {
		this.forumReaderNum = forumReaderNum;
	}


	public String getForumPraiserNum() {
		return forumPraiserNum;
	}


	public void setForumPraiserNum(String forumPraiserNum) {
		this.forumPraiserNum = forumPraiserNum;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getForumTagName() {
		return forumTagName;
	}
	public void setForumTagName(String forumTagName) {
		this.forumTagName = forumTagName;
	}


}
