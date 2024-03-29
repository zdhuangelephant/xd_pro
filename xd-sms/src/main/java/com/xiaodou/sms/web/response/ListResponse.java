package com.xiaodou.sms.web.response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.sms.common.enums.ResultType;

public class ListResponse<T> extends BaseResponse {
  /**
   * 结果list
   */
  private List<T> list;
  /**
   * 唯一序列号
   */
  private String sign;

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;

    if (this.list == null || this.list.size() == 0) {
      return;
    }

    Set<T> sortSet = null;
    T first = this.list.get(0);
    if (first instanceof Comparable) {
      sortSet = new TreeSet<T>();
    } else {
      sortSet = new HashSet<T>();
    }

    for (T t : this.list) {
      sortSet.add(t);
    }
    // MD5
    try {
      this.sign = CommUtil.HEXAndMd5(FastJsonUtil.toJson(sortSet));
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
  }

  public String getSign() {
    return sign;
  }

  public ListResponse(ResultType type) {
    super(type);
  }
}
