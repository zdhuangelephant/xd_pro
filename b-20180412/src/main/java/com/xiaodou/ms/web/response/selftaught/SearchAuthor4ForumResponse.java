package com.xiaodou.ms.web.response.selftaught;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * @name SearchAuthor4ForumResponse 
 * CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月9日
 * @description 知识分享搜索作者响应类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchAuthor4ForumResponse extends BaseResponse {

  public SearchAuthor4ForumResponse(ResultType resultType) {
    super(resultType);
  }

  public List<String> getAuthorList() {
	return authorList;
}

public void setAuthorList(List<String> authorList) {
	this.authorList = authorList;
}

private List<String> authorList = Lists.newArrayList();
  
}
