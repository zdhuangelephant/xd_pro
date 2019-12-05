package com.xiaodou.im.agent.huanxin;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.IMConfigManager;
import com.xiaodou.im.IMSelfManager;
import com.xiaodou.im.agent.huanxin.constants.HuanxinConstant;
import com.xiaodou.im.agent.huanxin.param.RegistUserParam;
import com.xiaodou.im.agent.huanxin.result.TokenResult;
import com.xiaodou.im.agent.huanxin.result.UserResult;
import com.xiaodou.im.request.DeleteUserPojo;
import com.xiaodou.im.request.LoginPojo;
import com.xiaodou.im.request.LogoffPojo;
import com.xiaodou.im.request.ModifyInfoPojo;
import com.xiaodou.im.request.ModifyPasswdPojo;
import com.xiaodou.im.request.RegistUserPojo;
import com.xiaodou.im.response.IMResponse;
import com.xiaodou.im.response.IMResponse.IMResponseType;

/**
 * @name @see com.xiaodou.im.agent.huanxin.HxSelfManager.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月1日
 * @description 环信个人账号管理Manager
 * @version 1.0
 */
public class HxSelfManager extends HxBaseManager implements IMSelfManager {

  @Override
  public IMResponse registUser(RegistUserPojo pojo) {
    TokenResult token = getToken(pojo.getModule());
    if (null == token) {
      return new IMResponse(IMResponseType.FAIL);
    }
    try {
      HttpUtil util =
          HttpUtil.getInstance(IMConfigManager.getHost(PREFIX), IMConfigManager.getPort(PREFIX),
              IMConfigManager.getProtocol(PREFIX));
      util.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
      String url =
          IMConfigManager.getAppKey(PREFIX, pojo.getModule())
              + IMConfigManager.getUrl(PREFIX, REGIST);
      RegistUserParam param = new RegistUserParam();
      param.setUsername(pojo.getAccount());
      param.setPassword(pojo.getPassword());
      String jsonParam = FastJsonUtil.toJson(param);
      PostMethod postMethod =
          HttpMethodUtil.getPostMethod(url, "application/json", "utf-8", jsonParam);
      postMethod.setRequestHeader("Authorization", token.getAccess_token());
      util.setMethod(postMethod);
      HttpResult result = util.getHttpResult();
      InOutEntity inOut = new InOutEntity();
      inOut.setTargetUrl(url);
      inOut.setParams(jsonParam);
      inOut.setResult(result);
      LoggerUtil.inOutInfo(inOut);
      if (null != result && result.isResultOk() && StringUtils.isNotBlank(result.getContent())) {
        UserResult userResult = FastJsonUtil.fromJson(result.getContent(), UserResult.class);
        if (StringUtils.isBlank(userResult.getError())
            || DUPLICATE_UNIQUE_PROPERTY_EXISTS.equals(userResult.getError())) {
          return new IMResponse(IMResponseType.SUCCESS);
        } else if (HuanxinConstant.DUPLICATE_UNIQUE_PROPERTY_EXISTS.equals(userResult.getError())) {
          return new IMResponse(IMResponseType.DUPLICATE_USER);
        }
      }
      return new IMResponse(IMResponseType.FAIL);
    } catch (Exception e) {
      LoggerUtil.error("HUANXIN REGIST USER FAIL", e);
      return null;
    }
  }

  @Override
  public IMResponse modifyInfo(ModifyInfoPojo pojo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMResponse modifyPasswd(ModifyPasswdPojo pojo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMResponse deleteUser(DeleteUserPojo pojo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMResponse login(LoginPojo pojo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMResponse logoff(LogoffPojo pojo) {
    // TODO Auto-generated method stub
    return null;
  }

}
