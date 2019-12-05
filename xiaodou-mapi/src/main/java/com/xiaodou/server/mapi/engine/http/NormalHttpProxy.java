package com.xiaodou.server.mapi.engine.http;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.aopagent.util.TraceWrapper;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.engine.ActionConstant;
import com.xiaodou.server.mapi.engine.model.Param;

/**
 * @name @see com.xiaodou.server.mapi.engine.http.NormalHttpProxy.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description HTTP请求代理类-封装了日志操作
 * @version 1.0
 */
public class NormalHttpProxy extends AbstractHttpProxy {

  /** jsonStr json参数 */
  private LinkedList<NameValuePair> paramList = Lists.newLinkedList();

  public NormalHttpProxy(String url, Integer timeOut, Integer retry) throws MalformedURLException {
    super(url, timeOut, retry);
  }

  public NormalHttpProxy(String protocol, String url, Integer timeOut, Integer retry)
      throws MalformedURLException {
    super(protocol, url, timeOut, retry);
  }

  public NormalHttpProxy(String url) throws MalformedURLException {
    this(url, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  public NormalHttpProxy(String host, Integer port, String baseurl, Integer timeOut, Integer retry) {
    super(host, port, baseurl, timeOut, retry);
  }

  public NormalHttpProxy(String host, Integer port, String baseurl) {
    this(host, port, baseurl, ActionConstant.DEFAULT_TIMEOUT, ActionConstant.DEFAULT_RETRY);
  }

  @Override
  public HttpMethod initMethod(String baseUrl) throws Exception {
    NameValuePair[] params = new NameValuePair[this.paramList.size()];
    return HttpMethodUtil.getPostMethod(baseUrl, this.paramList.toArray(params));
  }

  @Override
  public String getParams() {
    for(NameValuePair param :paramList){
      String name = param.getName();
      if(name.indexOf("pwd") != -1 || name.indexOf("password") !=-1 || 
          name.indexOf("passWord") !=-1 || name.indexOf("OldPwd") !=-1
          ||name.indexOf("NewPwd") !=-1 ||name.indexOf("confirmNewPwd") !=-1)
        param.setValue("******");
    }
    return FastJsonUtil.toJson(paramList);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void setParams(Object paramPojo, List<Param> paramList) {
    Map paramMap = Maps.newHashMap();
    if (paramPojo instanceof Map) {
      paramMap = (Map) paramPojo;
    } else {
      CommUtil.transferFromVO2Map(paramMap, paramPojo);
    }
    for (Param param : paramList) {
      Object object = paramMap.get(param.getName());
      if (null != object)
        this.paramList.add(new NameValuePair(param.getName(), object.toString()));
    }
    this.paramList.add(new NameValuePair("traceId",TraceWrapper.getWrapper().getTraceParam().getTraceId()));
    this.paramList.add(new NameValuePair("myId",TraceWrapper.getWrapper().getTraceParam().getMyId()));
  }

}
