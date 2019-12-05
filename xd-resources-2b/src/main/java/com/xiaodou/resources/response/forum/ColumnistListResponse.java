package com.xiaodou.resources.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.model.ColumnistMajorModel;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ColumnistListResponse 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年9月1日
 * @description 专栏列表响应
 * @version 1.0
 */
public class ColumnistListResponse extends BaseResponse {
  public ColumnistListResponse(ResultType type) {
    super(type);
  }

  public ColumnistListResponse(ForumResType type) {
    super(type);
  }
  /** columnistList 专栏列表 */
  private List<ColumnistMajorModel> columnistList = Lists.newArrayList();
  public List<ColumnistMajorModel> getColumnistList() {
    return columnistList;
  }

  public void setColumnistList(List<ColumnistMajorModel> columnistList) {
    this.columnistList = columnistList;
  }

}
