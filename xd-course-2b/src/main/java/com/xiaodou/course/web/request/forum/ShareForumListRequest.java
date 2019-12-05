package com.xiaodou.course.web.request.forum;

import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.course.web.request.forum.ShareForumListRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月10日
 * @description 知识分享列表查询请求
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@OverComeField(field = "typeCode", annotiation = AnnotationType.NotEmpty)
public class ShareForumListRequest extends BaseRequest {
	/** tagList tag列表 */
	private List<String> tagList = Lists.newArrayList();
	/** createTimeUpper 时间上限 */
	private Timestamp createTimeUpper;

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

	public Timestamp getCreateTimeUpper() {
		return createTimeUpper;
	}

	public void setCreateTimeUpper(Timestamp createTimeUpper) {
		this.createTimeUpper = createTimeUpper;
	}

}
