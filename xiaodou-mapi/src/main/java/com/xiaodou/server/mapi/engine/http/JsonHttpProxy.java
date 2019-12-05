package com.xiaodou.server.mapi.engine.http;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.engine.ActionConstant;
import com.xiaodou.server.mapi.engine.model.Param;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.JsonHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP-JSON请求代理类-封装了日志操作
 * @version 1.0
 */
public class JsonHttpProxy extends AbstractHttpProxy {

  /** jsonStr json参数 */
  private String jsonStr;
  private List<Param> paramList;
  private Object paramPojo;
  public String getJsonStr() {
	return jsonStr;
}

public void setJsonStr(String jsonStr) {
	this.jsonStr = jsonStr;
}

public List<Param> getParamList() {
	return paramList;
}

public void setParamList(List<Param> paramList) {
	this.paramList = paramList;
}

public Object getParamPojo() {
	return paramPojo;
}

public void setParamPojo(Object paramPojo) {
	this.paramPojo = paramPojo;
}

public JsonHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public JsonHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public JsonHttpProxy(String url) throws MalformedURLException {
    this(url, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    super(host, port, baseurl, timeOut, retry);
  }

  public JsonHttpProxy(String host, Integer port, String baseurl) {
    this(host, port, baseurl, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  @Override
  public HttpMethod initMethod(String baseUrl) throws Exception {
    return HttpMethodUtil.getPostMethod(baseUrl, "application/x-www-form-urlencoded", "utf-8", jsonStr);
  }
//  public HttpMethod initMethod(String baseUrl){
//	  PostMethod post = new PostMethod(baseUrl);
//	  NameValuePair[] values = {
//			  new NameValuePair("req",FastJsonUtil.toJson(paramPojo)),
//			  };//设置请求参数
//	  post.setRequestBody(values);
//	  //都是通过key和value的关系设置值
//	  post.setRequestHeader("Host", "");
//	  post.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E)");
//	  post.setRequestHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//	  post.setRequestHeader("Accept-Language", "zh-CN");
//	  post.setRequestHeader("Accept-Encoding", "gzip,deflate");
//	  post.setRequestHeader("Referer","");
//	  post.setRequestHeader("Cookie", "");
//	  post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//	  return post;
//	  }
  @Override
  public String getParams() {
    return jsonStr;
  }

  @Override
  public void setParams(Object paramPojo, List<Param> paramList) {
    this.jsonStr = FastJsonUtil.toJson(paramPojo);
    this.paramList = paramList;
    this.paramPojo = paramPojo;
  }

}
