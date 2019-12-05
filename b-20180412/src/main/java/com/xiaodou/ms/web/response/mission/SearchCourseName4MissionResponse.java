package com.xiaodou.ms.web.response.mission;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * @name SearchAuthor4ForumResponse CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年3月28日
 * @description TODO
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCourseName4MissionResponse extends BaseResponse {

  public SearchCourseName4MissionResponse(ResultType resultType) {
    super(resultType);
  }

  public List<String> getCourseNameList() {
    return courseNameList;
  }

  public void setCourseNameList(List<String> courseNameList) {
    this.courseNameList = courseNameList;
  }

  private List<String> courseNameList = Lists.newArrayList();

}
