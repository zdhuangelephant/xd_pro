//package com.xiaodou.sms.web.request.sms;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.alibaba.fastjson.JSON;
//import com.xiaodou.sms.web.request.BaseRequest;
//import com.xiaodou.summer.validator.annotion.NotEmpty;
//
//public class MessageRequest extends BaseRequest {
//  /**
//   * TODO 短信请求 1. telephone 2. map-变量 3. templateId
//   */
//  @NotEmpty
//  private List<String> telephone;
//  @NotEmpty
//  private Map<String, Object> variables;
//  @NotEmpty
//  private String templateId;
//
//  public List<String> getTelephone() {
//    return telephone;
//  }
//
//  public void setTelephone(List<String> telephone) {
//    this.telephone = telephone;
//  }
//
//  public Map<String, Object> getVariables() {
//    return variables;
//  }
//
//  public void setVariables(Map<String, Object> variables) {
//    this.variables = variables;
//  }
//
//  public String getTemplateId() {
//    return templateId;
//  }
//
//  public void setTemplateId(String templateId) {
//    this.templateId = templateId;
//  }
//
//  public static void main(String[] args) {
//    MessageRequest re = new MessageRequest();
//    re.setDeviceId("test");
//    List<String> list = new ArrayList<String>();
//    list.add("15311479569");
//    re.setTelephone(list);
//    re.setTemplateId("1");
//    Map<String, Object> variables = new HashMap<String, Object>();
//    variables.put("checkcode", "53424");
//    re.setVariables(variables);
//
//    System.out.println(JSON.toJSONString(re));
//  }
//
//}
