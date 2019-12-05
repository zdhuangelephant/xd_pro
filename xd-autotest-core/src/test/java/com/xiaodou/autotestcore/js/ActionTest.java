package com.xiaodou.autotestcore.js;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaodou.autotest.core.ActionScheduler;
import com.xiaodou.autotest.core.datasource.mysql.ConnectionFactory;
import com.xiaodou.autotest.core.datasource.mysql.DataSourceConfig;
import com.xiaodou.autotest.core.funclib.FuncLibFactory;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotestcore.js.ActionTest.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description Action测试类
 * @version 1.0
 */
public class ActionTest {

  @Before
  public void before() {
    // regist JS
    {
      FuncLibFactory.registScript("function getCourseId(srcCourseId) {return srcCourseId;}");
    }
    // regist DS
    {
      DataSourceConfig config = new DataSourceConfig();
      config.setAlias("2business_temp");
      config
          .setDriverUrl("jdbc:mysql://mysql.dev.51xiaodou.com:3306/2business_temp?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf8");
      config.setUserName("xddev");
      config.setPassword("xddev@xiaodou");
      ConnectionFactory.registMysqlDataSource(config);
    }
  }

  // @Test
  /**
   * 单接口用例
   */
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
   * 单接口用例-数据源测试
   */
  @Test
  public void testDataSourceApiAction() {
    Action a = new Action();
    a.setBusinessLine("0102");
    a.setName("测试用例-数据源测试用例");
    ApiTest.initDataSourceApiAction();
    a.registApi(ApiTest.api_h_1);
    a.setVersion("1.3.0");
    ActionScheduler.getInstance().schedule(a);
    System.out.println(a);
  }

  // @Test
  /**
   * 多接口用例
   */
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

  // @Test
  /**
   * 顺序用例
   */
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

  // @Test
  /**
   * 逻辑分支用例
   */
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

  // @Test
  /**
   * 文本读取用例
   */
  public void testFromTextApiAction() {
    String actionInfo =
        "[{\"apiList\":[{\"afterSets\":[{\"dataType\":\"sTring\",\"mappingKey\":\"api_a_1_pkExam\",\"resultkey\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad.pkExam\"}],\"format\":\"normal\",\"method\":\"get\",\"name\":\"api_a_1\",\"params\":[{\"dataType\":\"sTring\",\"desc\":\"答题详情\",\"name\":\"examDetail\",\"paramType\":\"QueryParam\",\"value\":\"${examDetail}\"},{\"dataType\":\"sTring\",\"desc\":\"做题耗时\",\"name\":\"examTime\",\"paramType\":\"QueryParam\",\"value\":\"${examTime}\"},{\"dataType\":\"sTring\",\"desc\":\"试卷ID\",\"name\":\"paperId\",\"paramType\":\"QueryParam\",\"value\":\"${paperId}\"},{\"dataType\":\"sTring\",\"desc\":\"用户积分\",\"name\":\"userCredit\",\"paramType\":\"QueryParam\",\"value\":\"${userCredit}\"},{\"dataType\":\"sTring\",\"desc\":\"用户ID\",\"name\":\"uid\",\"paramType\":\"QueryParam\",\"value\":\"${uid}\"},{\"dataType\":\"sTring\",\"desc\":\"所属模块\",\"name\":\"module\",\"paramType\":\"QueryParam\",\"value\":\"${module}\"},{\"dataType\":\"sTring\",\"desc\":\"提交状态\",\"name\":\"examStatus\",\"paramType\":\"QueryParam\",\"value\":\"${examStatus}\"},{\"dataType\":\"sTring\",\"desc\":\"产品ID\",\"name\":\"courseId\",\"paramType\":\"QueryParam\",\"value\":\"${courseId}\"},{\"dataType\":\"sTring\",\"desc\":\"专业码值\",\"name\":\"typeCode\",\"paramType\":\"QueryParam\",\"value\":\"${typeCode}\"}],\"preConds\":[],\"preSets\":[{\"assignment\":\"1421\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"uid\",\"scope\":\"part\"},{\"assignment\":\"205767267\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"courseId\",\"scope\":\"part\"},{\"assignment\":\"01A0820\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"typeCode\",\"scope\":\"part\"},{\"assignment\":\"2\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"module\",\"scope\":\"part\"},{\"assignment\":\"6bde5024-6bc6-478e-9abe-adc60ea5e603\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"paperId\",\"scope\":\"part\"},{\"assignment\":\"1\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examStatus\",\"scope\":\"part\"},{\"assignment\":\"2\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examTime\",\"scope\":\"part\"},{\"assignment\":\"[{\\\"answerIdList\\\":[\\\"26915_2\\\"],\\\"quesId\\\":26915},{\\\"answerIdList\\\":[\\\"26964_2\\\"],\\\"quesId\\\":26964},{\\\"answerIdList\\\":[\\\"26887_1\\\"],\\\"quesId\\\":26887},{\\\"answerIdList\\\":[\\\"27061_2\\\"],\\\"quesId\\\":27061},{\\\"answerIdList\\\":[\\\"26882_1\\\"],\\\"quesId\\\":26882}]\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"examDetail\",\"scope\":\"part\"},{\"assignment\":\"156\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"userCredit\",\"scope\":\"part\"},{\"assignment\":\"00202\",\"dataSource\":\"Local\",\"dataType\":\"sTring\",\"key\":\"targetRetcode\",\"scope\":\"part\"}],\"protocol\":\"http\",\"resultDataFormat\":\"jSon\",\"retryTime\":2,\"special\":false,\"status\":\"UnExcute\",\"testCases\":[{\"dataType\":\"sTring\",\"name\":\"tc1\",\"testKey\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad.retcode\",\"testResult\":\"Unknown\",\"testValue\":\"${targetRetcode}\"}],\"timeOut\":5000,\"uniqueId\":\"107e0a60-07ec-490c-b3d6-8b4958db85ad\",\"url\":\"http://119.61.66.57:8102/quesbk/exam_result\"}],\"businessLine\":\"0102\",\"name\":\"测试用例-文本读取\",\"version\":\"1.3.0\"}]";
    List<Action> actionList = ActionScheduler.getInstance().schedule(actionInfo);
    System.out.println(FastJsonUtil.toJson(actionList));
  }

  // @Test
  /**
   * 文件读取用例
   */
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
