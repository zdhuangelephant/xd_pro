package com.xiaodou.resources.util;

import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * http消息工具类
 * 
 * @author bing.cheng
 *
 */
public class HttpUtils {
	/**
	 * 
	 * @param host
	 * @param port
	 * @param path
	 * @param retryTime
	 * @param timeout
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String send(String host, int port, String path, int retryTime,int timeout, String content,Map<String, String> headers) throws Exception {
		
		HttpUtil http = HttpUtil.getInstance(host, port, "http", timeout, retryTime);
		HttpMethod httpMethod = HttpMethodUtil.getPostMethod(path,"application/x-www-form-urlencoded", "utf-8", content);
		for(String headerName:headers.keySet()){
		  httpMethod.setRequestHeader(headerName, headers.get(headerName));
		}
		
		http.setMethod(httpMethod);
		HttpResult httpResult = http.getHttpResult();

		if (!httpResult.isResultOk()) {
			LoggerUtil.error("[通信异常][" + httpResult.toString() + "]", new RuntimeException());
			return null;
		} else {
			return httpResult.getContent();
		}
	}
}
