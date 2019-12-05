package com.xiaodou.sms.utils.http;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.util.Assert;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.sms.model.AlarmEntity;
import com.xiaodou.sms.model.AlarmEntity.FailTypeEnum;
import com.xiaodou.sms.utils.SmsMerchantInfoProp;
import com.xiaodou.sms.vo.GetNumResult;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;

public class SendSmsByChuangrui implements ISendSms {

  @Override
  public MessageResult sendSms(MessageInfo messageInput) {
    String[] affirmPar =
        {messageInput.getContent(), messageInput.getTelephone(), messageInput.getProductLine()};
    Assert.notEmpty(affirmPar);
    MessageResult result = new MessageResult();
    String host = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.host");
    Integer port = SmsMerchantInfoProp.getInteger("merchant.sms.chuangrui.port");
    String protocol = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.protocol");
    String url = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.sendSmsUrl");
    HttpUtil http = HttpUtil.getInstance(host, port, protocol);
    http.getClient().getParams().setContentCharset("UTF-8");
    NameValuePair[] data =
        this.buildSmsSendData(messageInput.getTelephone(), messageInput.getContent());
    PostMethod method = HttpMethodUtil.getPostMethod(url, data);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    http.setMethod(method);
    HttpResult res = http.getHttpResult();
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl(String.format("%s://%s:%s/%s", protocol, host, port, url));
    entity.setResult(res);
    System.out.println(entity);
    LoggerUtil.inOutInfo(entity);
    boolean flag = false;
    String msg = StringUtils.EMPTY;
    if (res.isResultOk() && StringUtils.isNotBlank(res.getContent())) {
      try {
        String results[] = res.getContent().split(",");
        if ("0".equals(results[0])) {
          result.setStatus("0");
          flag = true;
        } else {
          result.setStatus("-1");
          result.setFailReason(results[results.length - 1]);
        }
      } catch (Exception e) {
        result.setStatus("-1");
        msg = e.getMessage();
        LoggerUtil.error("发送短信返回值解析异常.", e);
      }
    } else {
      result.setStatus("-1");
      if (null != res.getErr()) {
        msg = res.getStatusDesc();
        LoggerUtil.error(res.getStatusDesc(), res.getErr());
      }
    }
    if (!flag) {
      AlarmEntityImpl alarm = new AlarmEntity(FailTypeEnum.SEND_SMS_FAIL, msg);
      LoggerUtil.alarmInfo(alarm);
    }
    return result;
  }

  @Override
  public GetNumResult getNumber() {
    GetNumResult result = new GetNumResult();
    String host = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.host");
    Integer port = SmsMerchantInfoProp.getInteger("merchant.sms.chuangrui.port");
    String protocol = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.protocol");
    String url = SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.getNumber");
    HttpUtil http = HttpUtil.getInstance(host, port, protocol);
    http.getClient().getParams().setContentCharset("UTF-8");
    NameValuePair[] data =
        {
            new NameValuePair("name",
                SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.account")),
            new NameValuePair("pwd",
                SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.password")),
            new NameValuePair("type", "balance")};
    PostMethod method = HttpMethodUtil.getPostMethod(url, data);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    http.setMethod(method);
    HttpResult res = http.getHttpResult();
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl(String.format("%s://%s:%s/%s", protocol, host, port, url));
    entity.setResult(res);
    LoggerUtil.inOutInfo(entity);
    if (res.isResultOk() && StringUtils.isNotBlank(res.getContent())) {
      try {
        String results[] = res.getContent().split(",");
        if ("0".equals(results[0]))
          result.setStatus("0");
        else {
          result.setStatus("-1");
          result.setFailReason(results[results.length - 1]);
        }
      } catch (Exception e) {
        result.setStatus("-1");
        LoggerUtil.error("发送余额返回值解析异常.", e);
      }
    } else {
      result.setStatus("-1");
      if (null != res.getErr()) LoggerUtil.error(res.getStatusDesc(), res.getErr());
    }
    return result;
  }

  /**
   * 
   * 构建发送数据
   * 
   * @param mobilePhone
   * @param checkCode
   * @return
   */
  private NameValuePair[] buildSmsSendData(String mobilePhone, String message) {
    NameValuePair[] data =
        {
            new NameValuePair("name",
                SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.account")),
            new NameValuePair("pwd",
                SmsMerchantInfoProp.getParams("merchant.sms.chuangrui.password")),
            new NameValuePair("mobile", mobilePhone),
            new NameValuePair("content", String.format("【小逗网络】%s", message)),
            new NameValuePair("type", "pt")};
    return data;
  }

}
