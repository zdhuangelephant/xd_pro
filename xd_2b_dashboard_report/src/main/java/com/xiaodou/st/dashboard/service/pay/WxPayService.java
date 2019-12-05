package com.xiaodou.st.dashboard.service.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.goeasy.GoEasy;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.order.OrderDO;
import com.xiaodou.st.dashboard.service.order.OrderService;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.wxwebpay.CommonUtil;
import com.xiaodou.st.dashboard.util.wxwebpay.PayCommonUtil;
import com.xiaodou.st.dashboard.util.wxwebpay.WxPayConfigUtil;
import com.xiaodou.st.dashboard.util.wxwebpay.XMLUtil;

@Service
public class WxPayService {

  @Resource
  OrderService orderService;

  /**
   * 微信支付统一下单接口
   * 
   * @param out_trade_no
   * @return
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  public String wxPay(HttpServletRequest request, Long orderNumber) throws Exception {
    // 账号信息
    String appid = WxPayConfigUtil.APP_ID; // appid
    // String appsecret = WxPayConfigUtil.APP_SECRET; // appsecret
    // 商业号
    String mch_id = WxPayConfigUtil.MCH_ID;
    // 随机字符串
    // 价格 注意：价格的单位是分
    // String order_price = "1";
    // 商品名称
    // String body = "企嘉科技商品";
    // 查询订单数据表获取订单信息
    OrderDO orderDO = orderService.getOrder(orderNumber);
    // 获取发起电脑 ip
    String spbill_create_ip = CommonUtil.getIpAddress(request);
    // 回调接口
    String notify_url = WxPayConfigUtil.NOTIFY_URL;
    String trade_type = "NATIVE";
    String time_start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    Calendar ca = Calendar.getInstance();
    ca.setTime(new Date());
    ca.add(Calendar.DATE, 1);
    String time_expire = new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());
    SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mch_id);
    packageParams.put("nonce_str", PayCommonUtil.CreateNoncestr());
    packageParams.put("body", orderDO.getPilotUnitName() + "-批量购课");
    packageParams.put("out_trade_no", orderNumber.toString());
    packageParams.put("total_fee", getMoney(orderDO.getTotalAmount().toString()));
    packageParams.put("spbill_create_ip", spbill_create_ip);
    packageParams.put("notify_url", notify_url);
    packageParams.put("trade_type", trade_type);
    packageParams.put("time_start", time_start);
    packageParams.put("time_expire", time_expire);
    String sign = PayCommonUtil.createSign(packageParams);
    packageParams.put("sign", sign);
    String requestXML = PayCommonUtil.getRequestXml(packageParams);
    System.out.println("请求xml：：：：" + requestXML);
    String resXml = CommonUtil.httpsRequest(WxPayConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
    System.out.println("返回xml：：：：" + resXml);
    Map map = XMLUtil.doXMLParse(resXml);
    // String return_code = (String) map.get("return_code");
    // String prepay_id = (String) map.get("prepay_id");
    String urlCode = (String) map.get("code_url");
    System.out.println("打印调用统一下单接口生成二维码url:::::" + urlCode);
    return FastJsonUtil.toJson(map);
  }


  /**
   * 元转换成分
   * 
   * @param money
   * @return
   */
  public static String getMoney(String amount) {
    if (StringUtils.isBlank(amount)) {
      return StringUtils.EMPTY;
    }
    // 金额转化为分为单位
    String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥ 或者$的金额
    int index = currency.indexOf(".");
    int length = currency.length();
    Long amLong = 0l;
    if (index == -1) {
      amLong = Long.valueOf(currency + "00");
    } else if (length - index >= 3) {
      amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
    } else if (length - index == 2) {
      amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
    } else {
      amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
    }
    return amLong.toString();
  }



  /**
   * 微信支付回调方法
   * 
   * @param request
   * @param response
   * @throws Exception
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 读取参数
    InputStream inputStream;
    StringBuffer sb = new StringBuffer();
    inputStream = request.getInputStream();
    String s;
    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
    while ((s = in.readLine()) != null) {
      sb.append(s);
    }
    in.close();
    inputStream.close();
    // 解析xml成map
    Map<String, String> m = new HashMap<String, String>();
    m = XMLUtil.doXMLParse(sb.toString());
    // 过滤空 设置 TreeMap
    SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
    Iterator it = m.keySet().iterator();
    while (it.hasNext()) {
      String parameter = (String) it.next();
      String parameterValue = m.get(parameter);
      String v = "";
      if (null != parameterValue) {
        v = parameterValue.trim();
      }
      packageParams.put(parameter, v);
    }
    // 账号信息
    String out_trade_no = (String) packageParams.get("out_trade_no");
    // logger.info(packageParams);
    // 判断签名是否正确
    if (PayCommonUtil.isTenpaySign(packageParams)) {
      // ------------------------------
      // 处理业务开始
      // ------------------------------
      String resXml = "";
      if ("SUCCESS".equals((String) packageParams.get("trade_state"))) {
        // 这里是支付成功
        // ////////执行自己的业务逻辑////////////////
        String mch_id = (String) packageParams.get("mch_id");
        String openid = (String) packageParams.get("openid");
        String is_subscribe = (String) packageParams.get("is_subscribe");
        String bank_type = (String) packageParams.get("bank_type");
        String total_fee = (String) packageParams.get("total_fee");
        String transaction_id = (String) packageParams.get("transaction_id");
        System.out.println("mch_id:" + mch_id);
        System.out.println("openid:" + openid);
        System.out.println("is_subscribe:" + is_subscribe);
        System.out.println("out_trade_no:" + out_trade_no);
        System.out.println("total_fee:" + total_fee);
        System.out.println("bank_type:" + bank_type);
        System.out.println("transaction_id:" + transaction_id);
        // 成功回调后需要处理预生成订单的状态和一些支付信息
        // TODO
        // 查询数据库中订单，首先判定订单中金额与返回的金额是否相等，不等金额被纂改
        OrderDO orderDO = orderService.getOrder(Long.valueOf(out_trade_no));
        if (!orderDO.getTotalAmount().equals(Double.valueOf(total_fee))) {
          // 日志
          // TODO
          return;
        }
        // 判定订单是否已经被支付，不可重复支付
        // TODO
        // 正常处理相关业务逻辑
        orderDO.setStatus(Constants.ALREADYPAYMENT);
        orderDO.setPayTime(new Timestamp(System.currentTimeMillis()));
        orderService.updateOrder(orderDO);
      } else {
        System.out.println("支付失败,错误信息：" + packageParams.get("err_code") + "-----订单号：：："
            + out_trade_no + "*******支付失败时间：：：："
            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String err_code = (String) packageParams.get("err_code");

        resXml =
            "<xml>" + "<return_code><![CDATA[" + err_code + "]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

      }
      GoEasy goEasy = new GoEasy(StaticInfoProp.goEasyCommonkey());
      goEasy.publish(String.format("wx_pay_channel_%s", out_trade_no), FastJsonUtil.toJson(packageParams));
      // ------------------------------
      // 处理业务完毕
      // ------------------------------
      BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
      out.write(resXml.getBytes());
      out.flush();
      out.close();
    } else {
      System.out.println("通知签名验证失败---时间::::"
          + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
  }


  /**
   * 订单查询接口
   * 
   * @param orderNumber
   * @return
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  public String queryPay(Long orderNumber) throws Exception {
    // 账号信息
    String appid = WxPayConfigUtil.APP_ID; // appid
    // 商业号
    String mch_id = WxPayConfigUtil.MCH_ID;
    SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mch_id);
    packageParams.put("nonce_str", PayCommonUtil.CreateNoncestr());
    packageParams.put("out_trade_no", orderNumber.toString());
    String sign = PayCommonUtil.createSign(packageParams);
    packageParams.put("sign", sign);
    String requestXML = PayCommonUtil.getRequestXml(packageParams);
    String resXml = CommonUtil.httpsRequest(WxPayConfigUtil.CHECK_ORDER_URL, "POST", requestXML);
    Map map = XMLUtil.doXMLParse(resXml);
    if ("SUCCESS".equals((String) map.get("trade_state"))) {
      String total_fee = (String) map.get("total_fee");
      // 成功回调后需要处理预生成订单的状态和一些支付信息
      // TODO
      // 查询数据库中订单，首先判定订单中金额与返回的金额是否相等，不等金额被纂改
      OrderDO orderDO = orderService.getOrder(orderNumber);
      if (!getMoney(orderDO.getTotalAmount().toString()).equals(Double.valueOf(total_fee))) {
        // 日志
        // TODO
        return "";
      }
      // 判定订单是否已经被支付，不可重复支付
      // TODO
      // 正常处理相关业务逻辑
      orderDO.setStatus(Constants.ALREADYPAYMENT);
      orderService.updateOrder(orderDO);
    } else {}
    // (String) map.get("return_msg");
    return FastJsonUtil.toJson(map);
  }

}
