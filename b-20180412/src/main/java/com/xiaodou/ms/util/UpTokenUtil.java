package com.xiaodou.ms.util;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * @name @see com.xiaodou.ms.util.UpTokenUtil.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月5日
 * @description 七牛token工具类
 * @version 1.0
 */
public class UpTokenUtil {

  public static String getUpToken(String accessKey, String secretKey, String scope,
      String prefixPath) {
    Auth auth = Auth.create(accessKey, secretKey);
    StringMap stringMap = new StringMap();
    stringMap.put("returnBody", "{\"success\": true,\"file_path\": \"" + prefixPath + "$(key)\"}");
    return auth.uploadToken(scope, null, 3600, stringMap);
  }

}
