package com.xiaodou.payment.util;

import com.xiaodou.payment.vo.ClientType;




public class ClientUtil {
  public static ClientType getClinetType(String key) {
    ClientType clientType = ClientType.UNKNOWN;
    if (null == key) {
      return clientType;
    }
    switch (key.toUpperCase()) {
      case "WAP":
        clientType = ClientType.WAP;
        break;
      case "IPHONE":
        clientType = ClientType.IPHONEAPP;
        break;
      case "IOS":
        clientType = ClientType.IPHONEAPP;
        break;
      case "J2ME":
        clientType = ClientType.UNKNOWN;
        break;
      case "ANDROID":
        clientType = ClientType.ANDROIDAPP;
        break;
      case "IPAD":
        clientType = ClientType.IPADAPP;
        break;
      case "ANDROIDPAD":
        clientType = ClientType.ANDROIDAPP;
        break;
      case "WINDOWSPHONE":
        clientType = ClientType.WINPHONE;
        break;
      case "HTML5WAP":
        clientType = ClientType.H5APP;
        break;
      case "WINDOWSPCPAD":
        clientType = ClientType.WINPHONE;
        break;
      default:
        clientType = ClientType.UNKNOWN;
    }
    return clientType;
  }
}
