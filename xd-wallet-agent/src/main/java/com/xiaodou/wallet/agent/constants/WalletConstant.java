package com.xiaodou.wallet.agent.constants;

/**
 * @name @see com.xiaodou.wallet.constant.WalletConstant.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月6日
 * @description 用户支付平台常量池
 * @version 1.0
 */
public interface WalletConstant {

  /** PAYMENT_BUSINESSTYPE_WALLET 用户账户平台-支付系统业务类型 */
  public static final String PAYMENT_PRODUCTLINE_WALLET = "91";
  /** GOOD_STATUS_NEW 新品 */
  public static final Short GOOD_STATUS_NEW = 0;
  /** GOOD_STATUS_UP 上架 */
  public static final Short GOOD_STATUS_UP = 1;
  /** GOOD_STATUS_SUPPLY 断货 */
  public static final Short GOOD_STATUS_SUPPLY = 2;
  /** GOOD_STATUS_DOWN 下架 */
  public static final Short GOOD_STATUS_DOWN = 3;

  public static final String PAGESIZE = "20";
  /********************************** 账户分类 *******************************/
  /** ACCOUNT_CLASSIFY_LOCAL 账户分类:实体货币(人命币)账户 */
  public static final String ACCOUNT_CLASSIFY_YUAN = "4601";
  /** ACCOUNT_CLASSIFY_ANDROID 账户分类:虚拟货币安卓账户 */
  public static final String ACCOUNT_CLASSIFY_ANDROID = "4702";
  /** ACCOUNT_CLASSIFY_IOS 账户分类:虚拟货币苹果账户 */
  public static final String ACCOUNT_CLASSIFY_IOS = "4701";
}
