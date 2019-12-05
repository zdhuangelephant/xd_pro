package com.xiaodou.sms.utils.http;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.Assert;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.sms.model.AlarmEntity;
import com.xiaodou.sms.model.AlarmEntity.FailTypeEnum;
import com.xiaodou.sms.utils.SmsMerchantInfoProp;
import com.xiaodou.sms.vo.GetNumResult;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;

public class SendSmsByHuyi implements ISendSms {

  /**
   * 
   * 短信发送方法 //02161557216 彭 互亿技术联系人
   * 
   * @param mobilePhone
   * @param checkCode
   * @return
   */
  public MessageResult sendSms(MessageInfo messageInput) {
    String[] affirmPar =
        {messageInput.getContent(), messageInput.getTelephone(), messageInput.getProductLine()};
    Assert.notEmpty(affirmPar);
    MessageResult result = new MessageResult();
    String host = SmsMerchantInfoProp.getParams("merchant.sms.huyi.host");
    Integer port = SmsMerchantInfoProp.getInteger("merchant.sms.huyi.port");
    String protocol = SmsMerchantInfoProp.getParams("merchant.sms.huyi.protocol");
    String url = SmsMerchantInfoProp.getParams("merchant.sms.huyi.sendSmsUrl");
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
    LoggerUtil.inOutInfo(entity);
    boolean flag = false;
    String msg = StringUtils.EMPTY;
    if (res.isResultOk()) {
      try {
        Document doc = DocumentHelper.parseText(res.getContent());
        Element root = doc.getRootElement();
        String code = root.elementText("code");
        msg = root.elementText("msg");
        if (code.equals("2")) {
          result.setStatus("0");
          flag = true;
        } else {
          result.setStatus("-1");
          result.setFailReason(msg);
        }
      } catch (DocumentException e) {
        msg = e.getMessage();
        result.setStatus("-1");
        LoggerUtil.error("发送短信返回值解析异常.", e);
      }
    } else {
      result.setStatus("-1");
      if (null != res.getErr()) LoggerUtil.error(res.getStatusDesc(), res.getErr());
    }
    if (!flag) {
      AlarmEntityImpl alarm =
          new AlarmEntity(FailTypeEnum.SEND_SMS_FAIL,msg);
      LoggerUtil.alarmInfo(alarm);
    }
    return result;
  }

  public static void main(String[] args) {
    try {
      throw new Exception();
    } catch (Exception e) {
      System.out.println("error");
    }
    System.out.println("finish");
  }

  /**
   * 
   * 获取余额方法
   * 
   * @return
   */
  public GetNumResult getNumber() {
    GetNumResult result = new GetNumResult();
    String host = SmsMerchantInfoProp.getParams("merchant.sms.huyi.host");
    Integer port = SmsMerchantInfoProp.getInteger("merchant.sms.huyi.port");
    String protocol = SmsMerchantInfoProp.getParams("merchant.sms.huyi.protocol");
    String url = SmsMerchantInfoProp.getParams("merchant.sms.huyi.getNumber");
    HttpUtil http = HttpUtil.getInstance(host, port, protocol);
    http.getClient().getParams().setContentCharset("UTF-8");
    NameValuePair[] data =
        {
            new NameValuePair("account", SmsMerchantInfoProp.getParams("merchant.sms.huyi.account")),
            new NameValuePair("password",
                SmsMerchantInfoProp.getParams("merchant.sms.huyi.password"))};
    PostMethod method = HttpMethodUtil.getPostMethod(url, data);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    http.setMethod(method);
    HttpResult res = http.getHttpResult();
    InOutEntity entity = new InOutEntity();
    entity.setTargetUrl(String.format("%s://%s:%s/%s", protocol, host, port, url));
    entity.setResult(res);
    LoggerUtil.inOutInfo(entity);
    if (res.isResultOk()) {
      try {
        Document doc = DocumentHelper.parseText(res.getContent());
        Element root = doc.getRootElement();
        String code = root.elementText("code");
        String msg = root.elementText("msg");
        String num = root.elementText("num");
        if (code.equals("2")) {
          result.setStatus("0");
          result.setNumber(num);
        } else {
          result.setStatus("1");
          result.setFailReason(msg);
        }
      } catch (DocumentException e) {
        result.setStatus("-1");
        LoggerUtil.error("获取余额返回值解析异常.", e);
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
            new NameValuePair("account", SmsMerchantInfoProp.getParams("merchant.sms.huyi.account")),
            new NameValuePair("password",
                SmsMerchantInfoProp.getParams("merchant.sms.huyi.password")),
            new NameValuePair("mobile", mobilePhone), new NameValuePair("content", message),};
    return data;
  }
}
