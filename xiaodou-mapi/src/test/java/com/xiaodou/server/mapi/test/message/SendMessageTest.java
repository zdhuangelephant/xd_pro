package com.xiaodou.server.mapi.test.message;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;

public class SendMessageTest {
  public static void main(String[] args) throws Exception {
    String param = "{\"type\":\"1\",\"nickName\":\"AutoTest_000\", \"gender\":\"2\"}";
    String publicKey = ConfigProp.getParams("req.data.public.key");
    byte[] res = RSAUtils.encryptByPublicKey(param.getBytes(), publicKey);
    System.out.println(Base64Utils.encode(res));
  }
}
