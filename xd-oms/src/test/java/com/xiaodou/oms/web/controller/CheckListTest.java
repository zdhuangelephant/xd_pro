package com.xiaodou.oms.web.controller;
//package com.elong.oms.web.controller;
//
//import com.elong.common.http.HttpMethodUtil;
//import com.elong.common.http.HttpUtil;
//import com.elong.common.http.model.HttpResult;
//import org.apache.commons.httpclient.HttpMethod;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.lang.reflect.Method;
//
///**
// * 加checkList
// * <p/>
// * Date: 2014/8/15
// * Time: 13:48
// *
// * @author Tian.Dong
// */
//public class CheckListTest {
//  /*
//  exist:1
//  alertchange:0
//  department:elongorder
//  servicename:com.elong.oms.web.controller.OmsNotifyController
//  methodname:twicePayNotify
//  methodalias:elongorder_oms_twicePayNotify
//  templateName:methodstandard
//  monitorcycle:1
//  alertitem_0:exceptionrate
//  alertstrategy_0:greaterthan
//  threshold_0:0.05
//  alertcolor_0:yellow
//  alertnotice_0:email
//  alerttype_0:checklist_elongorder
//  alertinstance_0:
//  counter:1
//  */
//
//
//  //@Test
//  public void checkList() {
//    for (Method method : OmsTaskController.class.getMethods()) {
//      if (method.getAnnotation(RequestMapping.class) != null) {
//        send(OmsOrderController.class.getName(), method.getName());
//      }
//    }
//  }
//
//  public void send(String servicename, String methodname) {
//    String url = "http://dashboard2.mis.elong.com:8080/checklist-web/method/setting/save.do";
//    url +=
//        "?exist=1&alertchange=0&department=elongorder&servicename=%s&methodname=%s&methodalias=%s&"
//            +
//            "templateName=methodstandard&monitorcycle=1&alertitem_0=exceptionrate&alertstrategy_0=greaterthan&"
//            +
//            "threshold_0=0.05&alertcolor_0=yellow&alertnotice_0=email&alerttype_0=checklist_elongorder&alertinstance_0=&counter=1";
//
//    String methodalias = "elongorder_oms_" + methodname;
//    url = String.format(url, servicename, methodname, methodalias);
//    System.out.println(url);
//
//    HttpUtil httpUtil = HttpUtil.getInstance();
//    HttpMethod method = HttpMethodUtil.getGetMethod(url);
//    httpUtil.setMethod(method);
//    HttpResult result = httpUtil.getHttpResult();
//    System.out.println(result.getStatusCode() + result.getContent());
//  }
//
//
//  //@Test
//  public void test(){
//
//    System.out.println("\u7968\u9762\u4E58\u8F66\u7AD9\u5F00\u8F66\u524D15\u5929\uFF08\u4E0D\u542B\uFF09\u4EE5\u4E0A\uFF0C\u65E0\u9000\u7968\u8D39\uFF1B15\u5929\uFF08\u542B\uFF09\u4EE5\u5185\u300148\u5C0F\u65F6\u4EE5\u4E0A\u7684\u6309\u7968\u4EF75%\u8BA1\uFF1B24\u5C0F\u65F6\u4EE5\u4E0A\u3001\u4E0D\u8DB348\u5C0F\u65F6\u7684\u6309\u7968\u4EF710%\u8BA1\uFF1B\u4E0D\u8DB324\u5C0F\u65F6\u7684\u6309\u7968\u4EF720%\u8BA1\uFF1B\u6539\u7B7E\u540E\u4E58\u8F66\u65E5\u671F\u57282015\u5E742\u67084\u65E5-3\u670815\u65E5\u95F4\u7684\uFF0C\u6309\u7968\u4EF720%\u8BA1\u3002\u624B\u7EED\u8D39\u4E0D\u8DB32\u5143\u7684\uFF0C\u63092\u5143/\u5F20\u8BA1\u3002");
//  }
//}
