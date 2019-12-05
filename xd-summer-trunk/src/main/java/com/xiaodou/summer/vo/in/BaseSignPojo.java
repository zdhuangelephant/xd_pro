package com.xiaodou.summer.vo.in;

import java.util.Map;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.sign.SignType;

/**
 * <p>
 * 验证Pojo基类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月7日
 */
public abstract class BaseSignPojo extends BaseValidatorPojo {

  @NotEmpty
  @LegalValueList({"MD5"})
  private String signType;
  @NotEmpty
  private String sign;
  @JSONField(deserialize = false, serialize = false)
  private String jsonStr;

  public abstract String getKey();

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String md5) {
    this.sign = md5;
  }

  public String getJsonStr() {
    return jsonStr;
  }

  public void setJsonStr(String jsonStr) {
    this.jsonStr = jsonStr;
  }

  public boolean validateSign() throws Exception {
    String valiSrc = getValiString();
    SignType eSignType = SignType.getSingType(this.signType);
    if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(valiSrc)) return false;
    return sign.equals(eSignType.getSign(valiSrc));
  }

  @Override
  public Errors validate() {
    Errors errors = new BeanPropertyBindingResult(this, this.getClass().getSimpleName());
    if ("ON".equals(ConfigProp.getParams("json.data.need.sign")) && StringUtils.isNotBlank(jsonStr)) {
      try {
        if (StringUtils.isBlank(getKey())) {
          errors.rejectValue("sign", null, null, "Sign check fail : unvalid base param.");
          return errors;
        }
        if (!validateSign()) {
          errors.rejectValue("sign", null, null, "Sign check fail : wrong sign.");
          return errors;
        }
      } catch (Exception e) {
        errors.rejectValue("sign", null, null, "Sign check fail : try sign fail.");
        return errors;
      }
    }
    return super.validate();
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  private String getValiString() {
    StringBuffer srcString = new StringBuffer(300);
    getValiString(srcString);
    return srcString.toString();
  }

  protected void getValiString(StringBuffer srcString) {
    Map<String, Object> valiSrc = getValiSrc();
    for (Object value : valiSrc.values().toArray()) {
      srcString.append(value);
    }
    if (StringUtils.isNotBlank(getKey())) srcString.append(getKey());
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> getValiSrc() {
    if (StringUtils.isBlank(jsonStr)) return null;
    Map<String, Object> fromJson = Maps.newTreeMap();
    fromJson.putAll(FastJsonUtil.fromJson(jsonStr, Map.class));
    fromJson.remove("sign");
    fromJson.remove("signType");
    return fromJson;
  }

}
