package com.xiaodou.server.pay.task;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.HttpMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.server.pay.service.queue.QueueService.Message;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;

@HandlerMessage("CallBackBusiness")
public class CallBackBusinessTask extends AbstractDefaultWorker {

  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  /** serialVersionUID */
  private static final long serialVersionUID = -1864014853850965753L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    String messageBody = message.getMessageBodyJson();
    if (StringUtils.isJsonBlank(messageBody)) return;
    CallbackBusinessPojo pojo = FastJsonUtil.fromJson(messageBody, CallbackBusinessPojo.class);
    String reqJson = FastJsonUtil.toJson(pojo.getParam());
    HttpUtil httpUtil = HttpUtil.getInstance();
    httpUtil.setConnectionTimeout(5000);
    HttpMethod postMethod = null;
    try {
      postMethod =
          HttpMethodUtil.getPostMethod(pojo.getNotifyUrl(), "application/json", "utf-8", reqJson);
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.error("不支持编码格式", e);
    }
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    String res = dealHttpResult(pojo.getNotifyUrl(), reqJson, result);
    if (!SUCCESS.equals(res)) {
      message.setMessageName(Message.RetryCallBackBusiness.name());
      callback.onFail(CallBackStatus.RESET, message);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}

  private static String dealHttpResult(String url, String params, HttpResult result) {
    // inOut日志
    InOutEntity msg = new InOutEntity();
    msg.setTargetUrl(url);
    msg.setParams(params);
    msg.setResult(result);
    LoggerUtil.inOutInfo(msg);
    // 处理http状态码200
    if (!result.isResultOk() || result.getContent() == null) {
      Exception httpException = result.getErr();
      if (httpException == null) {
        httpException = new Exception("发送http请求异常");
      }
      LoggerUtil.error("发送http请求异常,url:" + url, httpException);
      // 报警
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.HTTP_SERVICE_UNVALID.getType());
      // entity.setUrl(
      // CommUtil.getServerName(),
      // url,
      // Integer.toString(result.getStatusCode()),
      // HttpStatusSwitch.getStatus(result.getStatusCode()).getMessage(),
      // result.getErr() == null ? null : result.getErr().getMessage()
      // );
      // LoggerUtil.alarmInfo(entity);
      // 生成http错误的response
      return FAIL;
    }
    return result.getContent();
  }
}
