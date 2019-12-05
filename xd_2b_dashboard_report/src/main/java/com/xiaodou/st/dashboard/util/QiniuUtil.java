package com.xiaodou.st.dashboard.util;

import com.qiniu.util.Auth;
import com.xiaodou.st.dashboard.domain.qiniu.QiniuPutPolicy;

public class QiniuUtil {

  /**
   * 获取上传凭证
   * @param accessKey
   * @param secretKey
   * @return
   */
  public static String getUptoken(String accessKey,String secretKey,QiniuPutPolicy putPolicy){
    Auth auth = Auth.create(accessKey, secretKey);
    return auth.uploadToken(putPolicy.getScope());
  }

}
