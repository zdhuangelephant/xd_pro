package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.util.OrderLoggerUtil;


/**
 * <p>
 * HTTPApiProxy组件
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
@SuppressWarnings("unchecked")
public abstract class HttpApiBase extends AbstractApiProxy<RemoteAPI> {
  
  protected String params = StringUtils.EMPTY;

  public HttpApiBase(RemoteAPI endpoint) {
    super(endpoint);
  }

  @Override
  public Object execute(Context context) throws Exception {
    InOutEntity value = new InOutEntity();
    value.setTargetUrl(api.getUrl());
    HttpResult content = sendRequest(context);
    value.setParams(params);
    value.setResult(content);
    OrderLoggerUtil.inOutInfo(value);
    if(content.isResultOk()){
      if (StringUtils.isNotBlank(api.getReturnValueName())) {
        // 设置返回值
        context.getShares().put(api.getReturnValueName(), content.getContent());
      }
      return content.getContent();
    }
    else
      throw content.getErr();
  }

  protected abstract HttpResult sendRequest(Context context) throws Exception;
}
