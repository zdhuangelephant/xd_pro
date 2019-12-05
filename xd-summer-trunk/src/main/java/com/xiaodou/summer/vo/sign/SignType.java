package com.xiaodou.summer.vo.sign;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;

/**
 * @name @see com.xiaodou.summer.vo.sign.SignType.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月25日
 * @description 签名类型枚举
 * @version 1.0
 */
public enum SignType {
  MD5 {
    @Override
    public String getSign(String srcData) throws Exception {
      return CommUtil.HEXAndMd5(srcData);
    }
  },
  RSA {
    @Override
    public String getSign(String srcData) throws Exception {
      byte[] reqData = Base64Utils.decode(srcData);
      String privateKey = ConfigProp.getParams("req.data.private.key");
      return RSAUtils.sign(reqData, privateKey);
    }
  };
  public abstract String getSign(String srcData) throws Exception;

  public static SignType getSingType(String singType) {
    try {
      return Enum.valueOf(SignType.class, singType);
    } catch (Exception e) {
      return MD5;
    }
  }
}
