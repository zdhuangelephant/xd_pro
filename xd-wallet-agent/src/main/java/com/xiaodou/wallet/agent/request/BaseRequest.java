package com.xiaodou.wallet.agent.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;


/**
 * @name @see com.xiaodou.wallet.request.BaseRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包模块基础请求Pojo
 * @version 1.0
 */
public class BaseRequest extends BaseValidatorPojo {

  /** productLine 产品线 */
  @NotEmpty
  private String productLine;
  /** userId 用户ID */
  private String userId;
  /** signType 签名类型 */
  @NotEmpty
  @LegalValueList({"MD5"})
  private String signType = "MD5";
  /** sign 签名 */
  @NotEmpty
  private String sign;

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

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

}
