package com.xiaodou.common.http.method;

import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 重写DELETE方法
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-19
 */
public class DeleteMethod extends PostMethod {

  public DeleteMethod(String serUrl) {
    super(serUrl);
  }

  @Override
  public String getName() {
    return "DELETE";
  }
}
