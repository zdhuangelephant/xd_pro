package com.xiaodou.server.mapi.response.selftaught;

import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotValidCourseCountResponse extends BaseResponse {

  public UserNotValidCourseCountResponse() {}

  public UserNotValidCourseCountResponse(ResultType type) {
    super(type);
  }

  public UserNotValidCourseCountResponse(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
  }

  public String getOtherExamCourseCount() {
	return otherExamCourseCount;
  }

  public void setOtherExamCourseCount(String otherExamCourseCount) {
	  this.otherExamCourseCount = otherExamCourseCount;
  }

  private String otherExamCourseCount = String.valueOf(0);// 其它考期课程数目
	  
  }
