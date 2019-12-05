package com.xiaodou.control.util;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.control.prop.DingDingProp;
public class DingDingMessageUtil {
	    public static String WEBHOOK_TOKEN = DingDingProp.getParams("url");
	 	    
		public static void sendMessage(String json) {
			HttpMethod httpMethod;
			try {
				HttpUtil http = HttpUtil.getInstance();
		        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\":\"" +json+"\"}}";
				httpMethod = HttpMethodUtil.getPostMethod(WEBHOOK_TOKEN, "application/json", "utf-8", textMsg);
				http.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			    http.setMethod(httpMethod);
			    http.getHttpResult();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
