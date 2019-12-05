package com.xiaodou.summer.vo.converter.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.codehaus.jackson.JsonParseException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.in.BaseSignPojo;

/**
 * @name @see com.xiaodou.summer.vo.converter.json.SummerMD5SignMessageConverter.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月25日
 * @description MD5签名pojo转换
 * @version 1.0
 */
public class SummerMD5SignMessageConverter extends MappingJacksonHttpMessageConverter {

  @Override
  public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return readInternal((Class<?>) type, inputMessage);
  }

  @Override
  protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    try {
      String jsonStr = getContent(inputMessage.getBody());
      if (StringUtils.isJsonBlank(jsonStr)) return FastJsonUtil.fromJson("{}", clazz);
      if ("ON".equals(ConfigProp.getParams("req.data.need.decrypt"))) {
        try {
          byte[] reqData = Base64Utils.decode(jsonStr);
          String privateKey = ConfigProp.getParams("req.data.private.key");
          byte[] res = RSAUtils.decryptByPrivateKey(reqData, privateKey);
          jsonStr = new String(res, "utf-8");
        } catch (Exception e) {
          LoggerUtil.error("参数解密失败", e);
          // throw new IOException(e.getMessage(), e);
          jsonStr = "{}";
        }
      }
      Object target = FastJsonUtil.fromJson(jsonStr, clazz);
      if (target instanceof BaseSignPojo) {
        ((BaseSignPojo) target).setJsonStr(jsonStr);
      }
      // Object target = this.objectMapper.readValue(inputMessage.getBody(), javaType);
      return target;
    } catch (JsonParseException ex) {
      throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
    }
  }

  /**
   * 从流中读取字符串
   * 
   * @param input
   * @return
   */
  private String getContent(InputStream input) throws UnsupportedEncodingException {
    StringBuffer pojoStr = new StringBuffer(200);
    BufferedReader read = new BufferedReader(new InputStreamReader(input, "utf-8"));
    try {
      String info = null;
      while (null != (info = read.readLine())) {
        pojoStr.append(info.trim());
      }
      return pojoStr.toString();
    } catch (Exception e) {
      return StringUtils.EMPTY;
    } finally {
      try {
        if (null != read) read.close();
      } catch (IOException e) {
        return StringUtils.EMPTY;
      }
      read = null;
    }
  }

}
