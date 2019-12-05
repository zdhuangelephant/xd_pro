package com.xiaodou.ms.vo.resources;

import lombok.Data;

@Data
public class WechatOfficialAccountVO {
	/** officialAccount 微信号 */
	private String officialAccount;
	/** officialAccountNO 微信号的ID */
	private String officialAccountNO;
	/** countsOfMonths 月发量 */
	private String repostsOfMonths;
	/** profile 功能介绍 */
	private String profile;
	/** authenticate 微信认证 */
	private String authenticate;
	/** recentArticle 最近文章 */
	private String recentArticle;
	private String recentArticleLinkAddr;
	/** publishTime 发布时间 */
	private String publishTime;
	/** linkAddr 改文章的地址 */
	private String linkAddr;

	private String isSubscribe = "0";

	public String getOfficialAccount() {
		return officialAccount;
	}

	public void setOfficialAccount(String officialAccount) {
		this.officialAccount = officialAccount;
	}

	public String getOfficialAccountNO() {
		return officialAccountNO;
	}

	public void setOfficialAccountNO(String officialAccountNO) {
		this.officialAccountNO = officialAccountNO;
	}

	public String getRepostsOfMonths() {
		return repostsOfMonths;
	}

	public void setRepostsOfMonths(String repostsOfMonths) {
		this.repostsOfMonths = repostsOfMonths;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(String authenticate) {
		this.authenticate = authenticate;
	}

	public String getRecentArticle() {
		return recentArticle;
	}

	public void setRecentArticle(String recentArticle) {
		this.recentArticle = recentArticle;
	}

	public String getRecentArticleLinkAddr() {
		return recentArticleLinkAddr;
	}

	public void setRecentArticleLinkAddr(String recentArticleLinkAddr) {
		this.recentArticleLinkAddr = recentArticleLinkAddr;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

}
