package com.xiaodou.autotest.core;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class ActionTest {

  /**
   * 单接口用例
   */
  @Test
  public void testAction() {
    Action a = new Action();
    a.setBusinessLine("0102");
    a.setName("测试用例1");
    ApiTest.initTestAction();
    a.registApi(ApiTest.api_a_1);
    a.setVersion("1.3.0");
    ActionScheduler.getInstance().schedule(a);
    System.out.println(a);
  }

  /**
   * 多接口用例
   */
  @Test
  public void testMultiApiAction() {
    Action a = new Action();
    a.setBusinessLine("0102");
    a.setName("测试用例-多接口用例");
    ApiTest.initTestMultiApiAction();
    a.registApi(ApiTest.api_e_1);
    a.registApi(ApiTest.api_d_1);
    a.setVersion("1.3.0");
    ActionScheduler.getInstance().schedule(a);
    System.out.println(a);
  }

  /**
   * 顺序用例
   */
  @Test
  public void testOrderApiAction() {
    Action a = new Action();
    a.setBusinessLine("0102");
    a.setName("测试用例-顺序用例");
    ApiTest.initTestOrderApiAction();
    a.registApi(ApiTest.api_c_1);
    a.registApi(ApiTest.api_b_1);
    a.setVersion("1.3.0");
    ActionScheduler.getInstance().schedule(a);
    System.out.println(a);
  }

  /**
   * 逻辑分支用例
   */
  @Test
  public void testLogicBranchApiAction() {
    Action a = new Action();
    a.setBusinessLine("0102");
    a.setName("测试用例-逻辑分支用例");
    ApiTest.initTestLogicBranchApiAction();
    a.registApi(ApiTest.api_g_1);
    a.registApi(ApiTest.api_f_1);
    a.setVersion("1.3.0");
    ActionScheduler.getInstance().schedule(a);
    System.out.println(a);
  }

  /**
   * 文本读取用例
   */
  @Test
  public void testFromTextApiAction() {
    String actionInfo =
        "[{\"apiList\":[{\"afterSets\":[{\"dataType\":\"sTring\",\"mappingKey\":\"api_a_1_pkExam\",\"resultkey\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad.pkExam\"}],\"format\":\"normal\",\"method\":\"get\",\"name\":\"api_a_1\",\"params\":[{\"dataType\":\"sTring\",\"desc\":\"答题详情\",\"name\":\"examDetail\",\"paramType\":\"QueryParam\",\"value\":\"${examDetail}\"},{\"dataType\":\"sTring\",\"desc\":\"做题耗时\",\"name\":\"examTime\",\"paramType\":\"QueryParam\",\"value\":\"${examTime}\"},{\"dataType\":\"sTring\",\"desc\":\"试卷ID\",\"name\":\"paperId\",\"paramType\":\"QueryParam\",\"value\":\"${paperId}\"},{\"dataType\":\"sTring\",\"desc\":\"用户积分\",\"name\":\"userCredit\",\"paramType\":\"QueryParam\",\"value\":\"${userCredit}\"},{\"dataType\":\"sTring\",\"desc\":\"用户ID\",\"name\":\"uid\",\"paramType\":\"QueryParam\",\"value\":\"${uid}\"},{\"dataType\":\"sTring\",\"desc\":\"所属模块\",\"name\":\"module\",\"paramType\":\"QueryParam\",\"value\":\"${module}\"},{\"dataType\":\"sTring\",\"desc\":\"提交状态\",\"name\":\"examStatus\",\"paramType\":\"QueryParam\",\"value\":\"${examStatus}\"},{\"dataType\":\"sTring\",\"desc\":\"产品ID\",\"name\":\"courseId\",\"paramType\":\"QueryParam\",\"value\":\"${courseId}\"},{\"dataType\":\"sTring\",\"desc\":\"专业码值\",\"name\":\"typeCode\",\"paramType\":\"QueryParam\",\"value\":\"${typeCode}\"}],\"preConds\":[],\"preSets\":[{\"assignment\":\"1421\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"uid\",\"scope\":\"part\"},{\"assignment\":\"205767267\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"courseId\",\"scope\":\"part\"},{\"assignment\":\"01A0820\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"typeCode\",\"scope\":\"part\"},{\"assignment\":\"2\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"module\",\"scope\":\"part\"},{\"assignment\":\"6bde5024-6bc6-478e-9abe-adc60ea5e603\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"paperId\",\"scope\":\"part\"},{\"assignment\":\"1\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examStatus\",\"scope\":\"part\"},{\"assignment\":\"2\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examTime\",\"scope\":\"part\"},{\"assignment\":\"[{\\\"answerIdList\\\":[\\\"26915_2\\\"],\\\"quesId\\\":26915},{\\\"answerIdList\\\":[\\\"26964_2\\\"],\\\"quesId\\\":26964},{\\\"answerIdList\\\":[\\\"26887_1\\\"],\\\"quesId\\\":26887},{\\\"answerIdList\\\":[\\\"27061_2\\\"],\\\"quesId\\\":27061},{\\\"answerIdList\\\":[\\\"26882_1\\\"],\\\"quesId\\\":26882}]\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examDetail\",\"scope\":\"part\"},{\"assignment\":\"156\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"userCredit\",\"scope\":\"part\"},{\"assignment\":\"00202\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"targetRetcode\",\"scope\":\"part\"}],\"protocol\":\"http\",\"resultDataFormat\":\"jSon\",\"retryTime\":2,\"special\":false,\"status\":\"UnExcute\",\"testCases\":[{\"dataType\":\"sTring\",\"name\":\"tc1\",\"testKey\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad.retcode\",\"testResult\":\"Unknown\",\"testValue\":\"${targetRetcode}\"}],\"timeOut\":5000,\"uniqueId\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad\",\"url\":\"http://119.61.66.57:8102/quesbk/exam_result\"}],\"businessLine\":\"0102\",\"name\":\"测试用例-文本读取\",\"version\":\"1.3.0\"}]";
    List<Action> actionList = ActionScheduler.getInstance().schedule(actionInfo);
    System.out.println(FastJsonUtil.toJson(actionList));
  }

  /**
   * 文件读取用例
   */
  @Test
  public void testFromFileApiAction() {
    String path =
        ActionTest.class.getClassLoader().getResource("conf/custom/action/test.a").getPath();
    List<Action> actionList = ActionScheduler.getInstance().scheduleFromFile(path);
    System.out.println(FastJsonUtil.toJson(actionList));
  }

  @After
  public void clear() {
    ApiTest.clear();
  }

}
