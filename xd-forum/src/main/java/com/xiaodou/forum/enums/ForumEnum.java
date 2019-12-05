package com.xiaodou.forum.enums;

/**
 * 话题枚举
 * 
 * @author bing.cheng
 *
 */
public enum ForumEnum {

	IsRecommendForum(1, "推荐话题"),
	IsTopForum(1, "置顶话题");

	private Integer code;
	private String name;

	ForumEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
