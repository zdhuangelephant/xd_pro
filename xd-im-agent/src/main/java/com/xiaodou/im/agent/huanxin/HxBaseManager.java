package com.xiaodou.im.agent.huanxin;

import org.apache.commons.httpclient.params.HttpMethodParams;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.IMConfigManager;
import com.xiaodou.im.agent.huanxin.constants.HuanxinConstant;
import com.xiaodou.im.agent.huanxin.param.TokenParam;
import com.xiaodou.im.agent.huanxin.result.TokenResult;

/**
 * @name @see com.xiaodou.im.agent.huanxin.HxBaseManager.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月1日
 * @description 环信管理基类
 * @version 1.0
 */
public class HxBaseManager implements HuanxinConstant {

  protected TokenResult getToken(String module) {
    try {
      HttpUtil util =
          HttpUtil.getInstance(IMConfigManager.getHost(PREFIX), IMConfigManager.getPort(PREFIX),
              IMConfigManager.getProtocol(PREFIX));
      util.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
      String url =
          IMConfigManager.getAppKey(PREFIX, module) + IMConfigManager.getUrl(PREFIX, TOKEN);
      TokenParam param = new TokenParam();
      param.setClient_id(IMConfigManager.getAppId(PREFIX, module));
      param.setClient_secret(IMConfigManager.getAppSecret(PREFIX, module));
      String jsonStr = FastJsonUtil.toJson(param);
      util.setMethod(HttpMethodUtil.getPostMethod(url, "application/json", "utf-8", jsonStr));
      HttpResult result = util.getHttpResult();
      InOutEntity inOut = new InOutEntity();
      inOut.setTargetUrl(url);
      inOut.setParams(jsonStr);
      inOut.setResult(result);
      LoggerUtil.inOutInfo(inOut);
      if (null != result && result.isResultOk() && StringUtils.isNotBlank(result.getContent()))
        return FastJsonUtil.fromJson(result.getContent(), TokenResult.class);
      return null;
    } catch (Exception e) {
      LoggerUtil.error("HUANXIN GET TOKEN FAIL", e);
      return null;
    }
  }

}
