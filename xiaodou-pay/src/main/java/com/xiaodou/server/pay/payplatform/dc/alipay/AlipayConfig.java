package com.xiaodou.server.pay.payplatform.dc.alipay;

import com.xiaodou.server.pay.prop.AliPayProp;

/**
 * 
 * @name AlipayConfig
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年6月8日
 * @description 这里是对账号，Key,回调连接地址等一些设置
 * @version 1.0
 */
public class AlipayConfig {

  private static AlipayConfig alconfig = null;

  private AlipayConfig() {

  }

  public static AlipayConfig getInstance() {
    if (alconfig == null) {
      alconfig = new AlipayConfig();
    }
    return alconfig;
  }

  // 如何获取安全校验码和合作身份者ID
  // 1.访问支付宝商户服务中心(b.alipay.com)，然后用您的签约支付宝账号登陆.
  // 2.访问“技术服务”→“下载技术集成文档”（https://b.alipay.com/support/helperApply.htm?action=selfIntegration）
  // 3.在“自助集成帮助”中，点击“合作者身份(Partner ID)查询”、“安全校验码(Key)查询”

  // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
  /* 不可空参数 */
  // 未付款交易的超时时间
  public static String it_b_pay = "30m";

  // 合作身份者ID，以2088开头由16位纯数字组成的字符串
  public static String partner = "2088121991217467";

  public static String service = "mobile.securitypay.pay";// create_direct_pay_by_user

  // 交易安全检验码，由数字和字母组成的32位字符串
  public static String key = "eiweyugq8a47pjdh1bbf4dctjew54gfg";

  //--------------------------------------------------------------------------------------
  // 商户的私钥
  public static String private_key =
      "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDF4i55aOEqa6xH"
          + "UghcVPuSbtHUsJtF3d1Zxv69iL1VHWfum15H2inDflUzF7eh/3CEo8WfKruNzVEc"
          + "yYkhGeNlIm+OUekn9Oq5xPXk2pEPpPXGOF29Rv5hRNSJCV4fmDuYP9wfHYu+ZMWA"
          + "Ltup/6Hl/rMzxtQHpBVbaSKwMT6dAZ/8oR3r9hFd1bytEOtff3c+/FXApD+a0z0H"
          + "JlGWumt8XKKoM5aRE3W/itXipBtkLIroVlTjgJ3xrzj60TrVFiM30k2ALOaMB3L9"
          + "yxD4xKsj4aTAbfX5pUorq+DXmOataIc9v4PStWjbVHQ6AvpLaG0iytbwuZey27FL"
          + "MVigmt6XAgMBAAECggEAX6YqjKjzXYvaJEc0n1y8lVVXu5T6GNVbLxAPBhJYYhHq"
          + "sN0Mv3JmiYZBLIckTfuoqZR3662+Jpa9xEpuLD/VOIdioMpbBuNNqinYKZy0TXmR"
          + "CxUnylvMjbu1d9+9TUxKUhdcEPY5G/idto3vw8Y6vW9Vs4tB5cecQykYiU3kVAMg"
          + "VMB+WpjwOFby+XmLbjEaSxnBpJpvR5gtMEvUBUamXPWXy8bmH+rQptaR2Ph9m2Sw"
          + "Ix/zk8I2fT2CZI0c+/HEh6iMzvv8p8+lRXnpMgGZ9uoi5mjPavIJXvjaDYmWibdS"
          + "wz5UDdRBJtwGcebV0nKbNx9Ro28cQKHsf4CIq+B3gQKBgQD5X7jI07tpMY852KZm"
          + "nJnanIzMZToGBoa7zNpj/PGaVMgucX9in4ITeSDG3oQWl121iFPl4tpSEzBesVlE"
          + "+v9GClU9c4MExUQcTXEw6v4BfDlFaMoAazJVbZktvCk8SCWPIYBPO+5Ck9bsCdHZ"
          + "ivDRYrsRkgMz9OjTz53rnwvAYQKBgQDLJDbn/O+n2wQziE1SUeIqUIXmTjWd0dm+"
          + "ZkRUQcHet902SJcgirerJ7pM9H454QqKxRLhqaG0RJxu6wjIBbTJBwuIVef/c+Ds"
          + "dX5M5Mf43SOZDgF+aE9EMJmFIzJ5J1PWz4ve2Ex7vP/XPYQHQMNEt3VxqFfnxtIP"
          + "4ejLoo/h9wKBgHf6aOIelH1w/bLcHHsj/xXh2hAA5+C8RRiX377ZlLgm8EUAEES3"
          + "75JE5dMofa6M1uYfGBZzhYyVdOEqPIPmqujc0dQF/lROQI3JaJsbhGfch4VwfDel"
          + "W4skdt09OS2qnH93o877S4+2p2Ha+57JrUFgWhaDBI172IUHz7fsRnAhAoGASOHI"
          + "VTk5XpbEjhyT6Tn5k8bqE3Px5pp4rcgRMc2Z2edLlYM7vXAnb1/EhoGdm/RyjNEh"
          + "8hajLoMDQQRQVTs+3nUJnTN9FfikNRH+a2YVnOLyN6J/xLN15PguzEaRgEgs20AB"
          + "ZcViw25OuBGZAeUpackMeOe2CjEaAprBo/XB+4cCgYEAzHRKWjbJp+BPXirozTO7"
          + "Y1nYxQvgHp4nrcAuMvDmfSOo6NAjAPhDRD7ubzilpaKTBS68BHLu3DBi5+VK/r/s"
          + "8kQIK26twJcZJnXAYojZJHVuffu2MPTPu38wLxQ2dXW54afpkugO8TNlEX76NyU6"
          + "joBm3EZZY1DxLOj8839Nh/o=";

  // 商户私钥，pkcs8格式
  public static final String RSA_PRIVATE =
      "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDF4i55aOEqa6xH"
          + "UghcVPuSbtHUsJtF3d1Zxv69iL1VHWfum15H2inDflUzF7eh/3CEo8WfKruNzVEc"
          + "yYkhGeNlIm+OUekn9Oq5xPXk2pEPpPXGOF29Rv5hRNSJCV4fmDuYP9wfHYu+ZMWA"
          + "Ltup/6Hl/rMzxtQHpBVbaSKwMT6dAZ/8oR3r9hFd1bytEOtff3c+/FXApD+a0z0H"
          + "JlGWumt8XKKoM5aRE3W/itXipBtkLIroVlTjgJ3xrzj60TrVFiM30k2ALOaMB3L9"
          + "yxD4xKsj4aTAbfX5pUorq+DXmOataIc9v4PStWjbVHQ6AvpLaG0iytbwuZey27FL"
          + "MVigmt6XAgMBAAECggEAX6YqjKjzXYvaJEc0n1y8lVVXu5T6GNVbLxAPBhJYYhHq"
          + "sN0Mv3JmiYZBLIckTfuoqZR3662+Jpa9xEpuLD/VOIdioMpbBuNNqinYKZy0TXmR"
          + "CxUnylvMjbu1d9+9TUxKUhdcEPY5G/idto3vw8Y6vW9Vs4tB5cecQykYiU3kVAMg"
          + "VMB+WpjwOFby+XmLbjEaSxnBpJpvR5gtMEvUBUamXPWXy8bmH+rQptaR2Ph9m2Sw"
          + "Ix/zk8I2fT2CZI0c+/HEh6iMzvv8p8+lRXnpMgGZ9uoi5mjPavIJXvjaDYmWibdS"
          + "wz5UDdRBJtwGcebV0nKbNx9Ro28cQKHsf4CIq+B3gQKBgQD5X7jI07tpMY852KZm"
          + "nJnanIzMZToGBoa7zNpj/PGaVMgucX9in4ITeSDG3oQWl121iFPl4tpSEzBesVlE"
          + "+v9GClU9c4MExUQcTXEw6v4BfDlFaMoAazJVbZktvCk8SCWPIYBPO+5Ck9bsCdHZ"
          + "ivDRYrsRkgMz9OjTz53rnwvAYQKBgQDLJDbn/O+n2wQziE1SUeIqUIXmTjWd0dm+"
          + "ZkRUQcHet902SJcgirerJ7pM9H454QqKxRLhqaG0RJxu6wjIBbTJBwuIVef/c+Ds"
          + "dX5M5Mf43SOZDgF+aE9EMJmFIzJ5J1PWz4ve2Ex7vP/XPYQHQMNEt3VxqFfnxtIP"
          + "4ejLoo/h9wKBgHf6aOIelH1w/bLcHHsj/xXh2hAA5+C8RRiX377ZlLgm8EUAEES3"
          + "75JE5dMofa6M1uYfGBZzhYyVdOEqPIPmqujc0dQF/lROQI3JaJsbhGfch4VwfDel"
          + "W4skdt09OS2qnH93o877S4+2p2Ha+57JrUFgWhaDBI172IUHz7fsRnAhAoGASOHI"
          + "VTk5XpbEjhyT6Tn5k8bqE3Px5pp4rcgRMc2Z2edLlYM7vXAnb1/EhoGdm/RyjNEh"
          + "8hajLoMDQQRQVTs+3nUJnTN9FfikNRH+a2YVnOLyN6J/xLN15PguzEaRgEgs20AB"
          + "ZcViw25OuBGZAeUpackMeOe2CjEaAprBo/XB+4cCgYEAzHRKWjbJp+BPXirozTO7"
          + "Y1nYxQvgHp4nrcAuMvDmfSOo6NAjAPhDRD7ubzilpaKTBS68BHLu3DBi5+VK/r/s"
          + "8kQIK26twJcZJnXAYojZJHVuffu2MPTPu38wLxQ2dXW54afpkugO8TNlEX76NyU6"
          + "joBm3EZZY1DxLOj8839Nh/o=";
  // 支付宝公钥
  public static final String RSA_PUBLIC =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

  //------------------------------------------------------------------------------------------

  // 商户私钥，PKCS8处理后的私钥文件示例（Java使用）
  public static final String PRIVATE_LEY =
      "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANlL8F7SR3J34QFb32M2Xl/8jkFTZm3e1PHCqn8byVVf9eaf1UxTr4CTnRbBkzaqhBwJJUw3eqk7YeHb3LaX+il5URGHJqb2okmBXJ8eib2DaB1Of06BrW0ZZ7eHBxl0oJLe7Otad8VVcOucOZLl3xUFaMcjtyq1Ysx1K4qhnbudAgMBAAECgYEAhkGxJiTuF/+RefF1uCrjbJLyodIRu1iuYSFRjm9wmdtMZ03hz+L00JBjwqefeWspbNkJvmmY0+7or0/Ussx7tvjSGi/skH2WW6orUr3wRB+eIqFPJP5rU5NpL2bKXdrtV1jlEANEFlUVnp8+lfWHtnil24qu42Zw3+TS+8sJ5FECQQDuaApOeM3hC/ObNiWh+8Br1srS7F9qwOE0+AwkDbcuN9vLpqDhoAo7S8qLNzC3o5F0U0IikppaeHaPnSAU2d+DAkEA6VUYGgLxUqRc7Q+BpZrZAWZ38GxHIsHYmggEgUjP+GXRXJWqy6nSQmfEc+6ZloN2AnPLqIpQPT/sgcXyY6juXwJAVRnqY+yXgTfk84i2kkXtgBEiQXyC6vaC7YeFvfDvbJ4f5WhLkX6xaE07tcozp7nJmD6IIxM16gmKOOWbqBXFxQJBAKEN71jk9xWIBexLa72XKHpi9B6rXl6LVqrbJeDnFUXrYZt6Yy24xjy3y04KP1E45FfE/aoT7laYrPLWYvyPOhkCQQDcrmolqieefSv1ei3eyqGYSzsa6++JyDhtvgZszVoU7GRsAI5q90AMQTLBmIwFTeDd97D6PGcQAsrfvPfBrcPA";
  
  //开发者(商户)公钥(SHA1withRSA)
  //使用SHA1withRsa，支付宝会用SHA1withRsa算法进行接口调用时的验签（不限制密钥长度）
  public static final String PUBLIC_KEY = 
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZS/Be0kdyd+EBW99jNl5f/I5BU2Zt3tTxwqp/G8lVX/Xmn9VMU6+Ak50WwZM2qoQcCSVMN3qpO2Hh29y2l/opeVERhyam9qJJgVyfHom9g2gdTn9Oga1tGWe3hwcZdKCS3uzrWnfFVXDrnDmS5d8VBWjHI7cqtWLMdSuKoZ27nQIDAQAB";
  
  public static final String OLD_PUBLIC_KEY = 
      "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxeIueWjhKmusR1IIXFT7km7R1LCbRd3dWcb+vYi9VR1n7pteR9opw35VMxe3of9whKPFnyq7jc1RHMmJIRnjZSJvjlHpJ/TqucT15NqRD6T1xjhdvUb+YUTUiQleH5g7mD/cHx2LvmTFgC7bqf+h5f6zM8bUB6QVW2kisDE+nQGf/KEd6/YRXdW8rRDrX393PvxVwKQ/mtM9ByZRlrprfFyiqDOWkRN1v4rV4qQbZCyK6FZU44Cd8a84+tE61RYjN9JNgCzmjAdy/csQ+MSrI+GkwG31+aVKK6vg15jmrWiHPb+D0rVo21R0OgL6S2htIsrW8LmXstuxSzFYoJrelwIDAQAB";
  
  //支付宝公钥(SHA1withRsa)，无需修改该值
  //使用SHA1withRsa，支付宝会在接口返回信息时使用SHA1withRsa进行加密（密钥长度为1024）
  public static final String ALI_PUBLIC_KEY = 
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";


  // 卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）。
  public static String seller_id = "xdzkpayment@51xiaodou.com";

  // 读配置文件
  // notify_url 交易过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
  public static String notify_url = AliPayProp.getParams("alipay_notify_url");

  // 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
  // return_url的域名不能写成http://localhost/js_jsp_utf8/return_url.jsp，否则会导致return_url执行无效
  public static String return_url = "http://www.51xiaodou.com";

  // 网站商品的展示地址，不允许加?id=123这类自定义参数
  public static String show_url = "http://www.51xiaodou.com";

  // 收款方名称，如：公司名称、网站名称、收款人姓名等
  public static String mainname = "北京小逗网络科技有限公司";
  // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

  // 字符编码格式 目前支持 gbk 或 utf-8
  public static String _input_charset = "utf-8";

  // 签名方式 不需修改
  public static String sign_type = "RSA";

  // 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
  public static String transport = "http";

  // 支付类型。默认值为：1（商品购买）
  public static String payment_type = "1";

  public static String subject = "北京小逗网络科技有限公司";

  /* 可空参数 */
  // 客户端号
  public static String app_id = "";

  // 客户端来源
  public static String appenv = "";

  public static boolean verifySign = false;
  // ...还有一堆。。
}
