package com.xiaodou.wallet.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.wallet.constant.WalletConstant;

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
  @LegalValueList({WalletConstant.BUSINESS_TYPE_ZKJ, WalletConstant.BUSINESS_TYPE_MOSHARE})
  private Integer businessType;
  /** accountClassify 账户分类 */
  @LegalValueList({WalletConstant.ACCOUNT_CLASSIFY_LOCAL, WalletConstant.ACCOUNT_CLASSIFY_IOS,
      WalletConstant.ACCOUNT_CLASSIFY_ANDROID})
  private String accountClassify = WalletConstant.ACCOUNT_CLASSIFY_LOCAL;

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  public String getAccountClassify() {
    return accountClassify;
  }

  public void setAccountClassify(String accountClassify) {
    this.accountClassify = accountClassify;
  }
}
