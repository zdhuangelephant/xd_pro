package com.xiaodou.resources.request.forum;

import java.util.List;

import com.xiaodou.resources.request.BaseRequest;
/**
 * 话题发布
 * @author hualong.li
 *
 */
public class PostRequest extends BaseRequest {
//	String 话题类型，String 标题 ，String 内容，String[] 图片，String 用户id
	/**
	 * 话题类型
	 */
	private String forumType;
	/**
	 * 话题标题
	 */
	private String forumTitle;
	/**
	 * 话题内容
	 */
	private String forumContent;
	/**
	 * 话题图片路径
	 */
	private List<String> imgUrl;
	/**
	 * 发布人id
	 */
	private String uid;
	public String getForumType() {
		return forumType;
	}
	public void setForumType(String forumType) {
		this.forumType = forumType;
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
	public List<String> getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	

}
