package com.xiaodou.ms.web.response.selftaught;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * @name SearchTitle4ForumResponse CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月22日
 * @description 知识分享标题搜索响应类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchTitle4ForumResponse extends BaseResponse {

  public SearchTitle4ForumResponse(ResultType resultType) {
    super(resultType);
  }

  public List<String> getForumTitleList() {
	return forumTitleList;
}

public void setForumTitleList(List<String> forumTitleList) {
	this.forumTitleList = forumTitleList;
}

private List<String> forumTitleList = Lists.newArrayList();

}
