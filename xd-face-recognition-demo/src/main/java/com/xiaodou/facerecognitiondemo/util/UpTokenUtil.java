package com.xiaodou.facerecognitiondemo.util;

import com.qiniu.util.Auth;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.xiaodou.common.util.HmacSHA1;
import com.xiaodou.common.util.UtfUtil;
import com.xiaodou.userCenter.request.UpTokenPojo;

public class UpTokenUtil {

	public static String getUpToken(String accessKey, String secretKey,
			String putPolicy) throws Exception {
		String encoded = new Base64Encoder().encode(UtfUtil.utf16To8(putPolicy)
				.getBytes("utf-8"));
		String encoded_signed = HmacSHA1.getSignature(secretKey, encoded,
				"utf-8");
		return accessKey + ":" + encoded_signed + ":" + encoded;
	}
	
	public static String getUpToken(String accessKey, String secretKey,
			UpTokenPojo pojo) throws Exception {
		Auth auth = Auth.create(accessKey, secretKey);
		return auth.uploadToken(pojo.getScope());
	}

}
