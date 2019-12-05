package com.xiaodou.crontab.engine.protocol.http.proxy.param;

import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;

import com.google.common.collect.Lists;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;

public class NormalHttpParam extends HttpProxyParam {

  private LinkedList<NameValuePair> paramList = Lists.newLinkedList();

  public NormalHttpParam(CrontHttpProtocolConfig config) {
    super(config);
    for (Entry<String, String> nameValuePair : config.getParamMap().entrySet())
      paramList.add(new NameValuePair(nameValuePair.getKey(), nameValuePair.getValue()));
  }

  public LinkedList<NameValuePair> getParamList() {
    return paramList;
  }

}
