package com.xiaodou.server.mapi.domain;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.constant.QuesBaseConstant;

/**
 * Date: 2015/4/19 Time: 15:55
 * 
 * @author Tian.Dong
 */
public class BaseEntity {

  /** operation 操作类型 */
  private Integer operation = QuesBaseConstant.QUES_DOMAIN_OPERATION_NOTHING;

  public void markCreate() {
    operation = QuesBaseConstant.QUES_DOMAIN_OPERATION_CREATE;
  }

  public void markDelete() {
    operation = QuesBaseConstant.QUES_DOMAIN_OPERATION_DELETE;
  }

  public void markModify() {
    operation = QuesBaseConstant.QUES_DOMAIN_OPERATION_MODIFY;
  }

  public Integer getOperation() {
    return operation;
  }

  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
