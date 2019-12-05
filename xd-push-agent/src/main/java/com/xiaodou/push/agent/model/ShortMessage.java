package com.xiaodou.push.agent.model;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.push.agent.annotation.NamePair;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see cpm.xiaodou.push.agent.model.ShortMessage.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 推送系统接收推送短信模型
 * @version 1.0
 */
public class ShortMessage extends AbstractMessage {

  /** telephone 电话号码 */
  @NotEmpty
  @NamePair
  private List<String> telephone = Lists.newArrayList();
  
  /** variables 变量参数 */
  @NotEmpty
  @NamePair
  private Map<String, Object> variables = Maps.newHashMap();
  
  /** templateId 模板ID */
  @NotEmpty
  @NamePair
  private String templateId;
  
  /** appMessageId 应用消息ID */
  @NotEmpty
  @NamePair
  private String appMessageId;
  
  /** productLine 产品线 */
  @NotEmpty
  @NamePair
  private String productLine;

  public List<String> getTelephone() {
    return telephone;
  }

  public void setTelephone(String... telephone) {
    this.telephone.addAll(Lists.newArrayList(telephone));
  }

  public Map<String, Object> getVariables() {
    return variables;
  }

  public void setVariables(String key, Object value) {
    this.variables.put(key, value);
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getAppMessageId() {
    return appMessageId;
  }

  public void setAppMessageId(String appMessageId) {
    this.appMessageId = appMessageId;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }
}
