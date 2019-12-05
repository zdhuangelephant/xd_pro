package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.HomeResponse.StCourseInfo;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @name UserExpCourseResponse CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年11月10日
 * @description 用戶过期课程
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotValidCourseResponse extends BaseResponse {
  
  public UserNotValidCourseResponse() {}

  public UserNotValidCourseResponse(ResultType type) {
    super(type);
  }

  public UserNotValidCourseResponse(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
  }

  private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表
}
