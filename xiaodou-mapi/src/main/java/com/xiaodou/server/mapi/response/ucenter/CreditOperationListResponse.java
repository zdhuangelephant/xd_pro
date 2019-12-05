package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;

/**
 * @name @see com.xiaodou.userCenter.response.CreditOperationListResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年11月13日
 * @description 积分列表响应类
 * @version 1.0
 */
public class CreditOperationListResponse extends BaseResponse {
  public CreditOperationListResponse() {}

  public CreditOperationListResponse(ResultType type) {
    super(type);
  }

  /** creditOperationList 积分操作列表 */
  private List<CreditOperation> creditOperationList = Lists.newArrayList();

  public List<CreditOperation> getCreditOperationList() {
    return creditOperationList;
  }

  public void setCreditOperationList(List<CreditOperation> creditOperationList) {
    this.creditOperationList = creditOperationList;
  }

  /**
   * @name @see com.xiaodou.userCenter.response.CreditOperationListResponse.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年11月13日
   * @description 积分操作记录
   * @version 1.0
   */
  @Data
  public static class CreditOperation {
    public CreditOperation() {}

    /** operationId 记录ID */
    private String operationId;
    /** operationDesc 操作描述 */
    private String operationDesc;
    /** operationCount 操作数量 */
    private String operationCount;
    /** operationTime 操作时间 */
    private String operationTime;
  }
}
