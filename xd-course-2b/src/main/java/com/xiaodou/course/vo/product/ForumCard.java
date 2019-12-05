package com.xiaodou.course.vo.product;

import com.xiaodou.common.util.ConfigProp;

import lombok.Data;

/**
 * @name ForumCard CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月9日
 * @description 知识分享
 * @version 1.0
 */
@Data
public class ForumCard {
	/** cover 封面 */
	private String cover = ConfigProp.getParams("forumcard.cover");
	/** titel 标题 */
	private String titel = ConfigProp.getParams("forumcard.title");
	/** desc 描述 */
	private String desc = ConfigProp.getParams("forumcard.desc.default");

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
