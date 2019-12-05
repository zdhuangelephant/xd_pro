package com.xiaodou.wallet.agent.util;

import com.xiaodou.wallet.agent.constants.AccountClassify;

/**
 * @name @see com.xiaodou.wallet.agent.util.AccountClassifyUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月4日
 * @description 账户类型工具类
 * @version 1.0
 */
public class AccountClassifyUtil {

  public static AccountClassify getAccountClassifyByDesc(String desc) {
    AccountClassify accountClassify = AccountClassify.ACCOUNT_CLASSIFY_LOCAL;
    if (null == desc) {
      return accountClassify;
    }
    switch (desc.toUpperCase()) {
      case "IPHONE":
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_IOS;
        break;
      case "IOS":
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_IOS;
        break;
      case "ANDROID":
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_ANDROID;
        break;
      case "IPAD":
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_IOS;
        break;
      case "ANDROIDPAD":
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_ANDROID;
        break;
      default:
        accountClassify = AccountClassify.ACCOUNT_CLASSIFY_LOCAL;
    }
    return accountClassify;
  }

}
