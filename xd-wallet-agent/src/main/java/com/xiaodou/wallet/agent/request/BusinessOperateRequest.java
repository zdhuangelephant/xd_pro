package com.xiaodou.wallet.agent.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.wallet.agent.constants.WalletConstant;

/**
 * @name BusinessOperateRequest CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月4日
 * @description 业务操作基础请求类
 * @version 1.0
 */
public class BusinessOperateRequest extends BaseRequest {

  /** businessType 业务类型 */
  @NotEmpty
  private Integer businessType;
  /** accountClassify 账户分类 */
  @LegalValueList({WalletConstant.ACCOUNT_CLASSIFY_YUAN, WalletConstant.ACCOUNT_CLASSIFY_IOS,
      WalletConstant.ACCOUNT_CLASSIFY_ANDROID})
  private Short accountClassify = Short.valueOf(WalletConstant.ACCOUNT_CLASSIFY_YUAN);

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public Short getAccountClassify() {
    return accountClassify;
  }

  public void setAccountClassify(Short accountClassify) {
    this.accountClassify = accountClassify;
  }

}
