package com.xiaodou.userCenter.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.CreditChangeLog;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

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
public class CreditOperationListResponse extends BaseResultInfo {
  public CreditOperationListResponse(ResultType type) {
    super(type);
  }

  public CreditOperationListResponse(UcenterResType type) {
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

    public CreditOperation(CreditChangeLog log) {
      if (null == log || null == log.getId()) return;
      this.operationId = log.getId().toString();
      this.operationDesc = log.getOperateDesc();
      this.operationCount = log.getCount();
      this.operationTime = DateUtil.relativeDateFormat(log.getCreateTime());
    }

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
