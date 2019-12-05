package com.xiaodou.ms.web.response.product;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * @name @see com.xiaodou.ms.web.response.product.SearchSubjectResponse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月5日
 * @description 资源课程搜索响应类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchSubjectResponse extends BaseResponse {

  public SearchSubjectResponse(ResultType resultType) {
    super(resultType);
  }

  public List<String> getSubjectNameList() {
	return subjectNameList;
}

public void setSubjectNameList(List<String> subjectNameList) {
	this.subjectNameList = subjectNameList;
}

private List<String> subjectNameList = Lists.newArrayList();

}
