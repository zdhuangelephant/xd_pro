package com.xiaodou.course.vo.forum;

import com.xiaodou.course.model.forum.AuthorModel;

public class Author {
	private String authorId;
	private String authorName;
	private String authorPortrait;
	private String authorCover;
	private String authorInfo;
	private String forumCount;
	public Author(AuthorModel author){
		this.authorId=author.getId();
		this.authorCover=author.getCover();
		this.authorName=author.getName();
		this.authorInfo=author.getInfo();
		this.authorPortrait=author.getPortrait();
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
	public String getAuthorCover() {
		return authorCover;
	}
	public void setAuthorCover(String authorCover) {
		this.authorCover = authorCover;
	}
	public String getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	public String getForumCount() {
		return forumCount;
	}
	public void setForumCount(String forumCount) {
		this.forumCount = forumCount;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	

}
