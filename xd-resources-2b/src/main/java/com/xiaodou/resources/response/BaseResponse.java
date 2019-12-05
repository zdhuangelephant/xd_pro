package com.xiaodou.resources.response;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:52:13
 */
public class BaseResponse extends ResultInfo {

  public BaseResponse() {}

  public BaseResponse(ResultType type) {
    super(type);
  }

  public BaseResponse(com.xiaodou.resources.constant.quesbk.ResultType resultType) {
    super(resultType.getCode(), resultType.getMsg(), resultType.getServerIp());
  }

  public BaseResponse(ForumResType type) {
    super(type.getCode(), type.getMsg(), type.getServerIp());
  }

  public BaseResponse(ProductResType productResType) {
    super(productResType.getCode(), productResType.getMsg(), productResType.getServerIp());
  }

  public void appendErrorMessage(String errorMsg) {
    this.appendRetdesc(errorMsg);
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
