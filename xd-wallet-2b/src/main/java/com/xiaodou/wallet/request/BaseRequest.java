package com.xiaodou.wallet.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseSignPojo;
import com.xiaodou.wallet.util.WalletConfig;

/**
 * @name @see com.xiaodou.wallet.request.BaseRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包模块基础请求Pojo
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest extends BaseSignPojo {

  /** productLine 产品线 */
  @NotEmpty
  private String productLine;
  /** userId 用户ID */
  private String userId;

  @Override
  public String getKey() {
    return WalletConfig.getKey(productLine);
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
