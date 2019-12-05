package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
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
public class SumResponse extends BaseResponse {
  public SumResponse(ResultType type) {
    super(type);
  }

  public SumResponse(ForumResType type) {
    super(type);
  }

public Integer getSum() {
	return sum;
}

public void setSum(Integer sum) {
	this.sum = sum;
}

private Integer sum;

}
