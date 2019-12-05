package com.xiaodou.ms.util;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xiaodou.ms.vo.QiniuPutPolicy;

/**
 * Created by zyp on 15/7/18.
 */
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
