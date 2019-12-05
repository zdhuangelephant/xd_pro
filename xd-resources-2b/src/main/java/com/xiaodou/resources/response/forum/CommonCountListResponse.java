package com.xiaodou.resources.response.forum;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.model.ColumnistMajorModel;
import com.xiaodou.resources.vo.forum.CommonCount;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ColumnistListResponse 
 * CopyRright (c) 2016 by zhouhuan
 *
 * @author zhouhuan
 * @date 2016年9月1日
 * @description 通用count接口
 * @version 1.0
 */
public class CommonCountListResponse extends BaseResponse {
  public CommonCountListResponse(ResultType type) {
    super(type);
  }

  public CommonCountListResponse(ForumResType type) {
    super(type);
  }
  public List<CommonCount> getList() {
	return list;
}

public void setList(List<CommonCount> list) {
	this.list = list;
}
private List<CommonCount> list=new ArrayList<CommonCount>();

}
