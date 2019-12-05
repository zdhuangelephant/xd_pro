package com.xiaodou.autotest.core;

import java.util.UUID;

import com.xiaodou.autotest.core.ActionEnum.ConditionScope;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.ActionEnum.DataSource;
import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.ActionEnum.VarScope;
import com.xiaodou.autotest.core.datasource.mysql.ConnectionFactory;
import com.xiaodou.autotest.core.datasource.mysql.DataSourceConfig;
import com.xiaodou.autotest.core.model.Api;

public class ApiTest {

  public static Api api_a_1 = new Api();
  public static Api api_b_1 = new Api();
  public static Api api_c_1 = new Api();
  public static Api api_d_1 = new Api();
  public static Api api_e_1 = new Api();
  public static Api api_f_1 = new Api();
  public static Api api_g_1 = new Api();

  public static void initTestAction() {
    // api_a_1.setUniqueId(UUID.randomUUID().toString());
    // api_a_1.setName("api_a_1");
    // api_a_1.setUrl("http://119.61.66.57:8102/quesbk/exam_result");
    // api_a_1.setTimeOut(5000);
    // api_a_1.setResultDataFormat(DataFormat.jSon);
    // // 设置参数
    // api_a_1.registParam("uid", "用户ID");
    // api_a_1.registParam("courseId", "产品ID");
    // api_a_1.registParam("typeCode", "专业码值");
    // api_a_1.registParam("module", "所属模块");
    // api_a_1.registParam("paperId", "试卷ID");
    // api_a_1.registParam("examStatus", "提交状态");
    // api_a_1.registParam("examTime", "做题耗时");
    // api_a_1.registParam("examDetail", "答题详情");
    // api_a_1.registParam("userCredit", "用户积分");
    // // 设置预设值
    // api_a_1.pushPreSet("uid", "1421");
    // api_a_1.pushPreSet("courseId", "205767267");
    // api_a_1.pushPreSet("typeCode", "01A0820");
    // api_a_1.pushPreSet("module", "2");
    // api_a_1.pushPreSet("paperId", "6bde5024-6bc6-478e-9abe-adc60ea5e603");
    // api_a_1.pushPreSet("examStatus", "1");
    // api_a_1.pushPreSet("examTime", "2");
    // api_a_1
    // .pushPreSet(
    // "examDetail",
    // "[{\"answerIdList\":[\"26915_2\"],\"quesId\":26915},{\"answerIdList\":[\"26964_2\"],\"quesId\":26964},{\"answerIdList\":[\"26887_1\"],\"quesId\":26887},{\"answerIdList\":[\"27061_2\"],\"quesId\":27061},{\"answerIdList\":[\"26882_1\"],\"quesId\":26882}]");
    // api_a_1.pushPreSet("userCredit", "156");
    // api_a_1.pushPreSet("targetRetcode", "00202");
    // // 设置用例
    // api_a_1.pushTestCase("tc1", "retcode", "${targetRetcode}", DataType.sTring.name());
    // // 设置后置变量
    // api_a_1.pushAfterSet("pkExam", "api_a_1_pkExam");

    api_a_1.setUniqueId(UUID.randomUUID().toString());
    api_a_1.setName("api_a_1");
    api_a_1.setUrl("http://119.61.66.57:8002/quesbk/exam_result");
    api_a_1.setTimeOut(5000);
    api_a_1.setResultDataFormat(DataFormat.jSon);
  }

  public static void initTestOrderApiAction() {
    {
      api_b_1.setUniqueId(UUID.randomUUID().toString());
      api_b_1.setName("api_a_1");
      api_b_1.setUrl("http://119.61.66.57:8102/quesbk/exam_result");
      api_b_1.setTimeOut(5000);
      api_b_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_b_1.registParam("uid", "用户ID");
      api_b_1.registParam("courseId", "产品ID");
      api_b_1.registParam("typeCode", "专业码值");
      api_b_1.registParam("module", "所属模块");
      api_b_1.registParam("paperId", "试卷ID");
      api_b_1.registParam("examStatus", "提交状态");
      api_b_1.registParam("examTime", "做题耗时");
      api_b_1.registParam("examDetail", "答题详情");
      api_b_1.registParam("userCredit", "用户积分");
      // 设置预设值
      api_b_1.pushPreSet("courseId", "205767267");
      api_b_1.pushPreSet("typeCode", "01A0820");
      api_b_1.pushPreSet("paperId", "6bde5024-6bc6-478e-9abe-adc60ea5e603");
      api_b_1.pushPreSet("examStatus", "1");
      api_b_1.pushPreSet("examTime", "2");
      api_b_1
          .pushPreSet(
              "examDetail",
              "[{\"answerIdList\":[\"26915_2\"],\"quesId\":26915},{\"answerIdList\":[\"26964_2\"],\"quesId\":26964},{\"answerIdList\":[\"26887_1\"],\"quesId\":26887},{\"answerIdList\":[\"27061_2\"],\"quesId\":27061},{\"answerIdList\":[\"26882_1\"],\"quesId\":26882}]");
      api_b_1.pushPreSet("userCredit", "156");
      // 设置用例
      api_b_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_b_1.pushAfterSet("pkExam", "api_a_1_pkExam");
    }

    {
      api_c_1.setUniqueId(UUID.randomUUID().toString());
      api_c_1.setName("api_b_1");
      api_c_1.setUrl("http://119.61.66.57:8020/selftaught/login");
      api_c_1.setTimeOut(5000);
      api_c_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_c_1.registParam("req", "登录参数");
      api_c_1.registParam("clientIp", ParamType.HeaderParam, "客户端IP");
      api_c_1.registParam("deviceId", ParamType.HeaderParam, "设备ID");
      api_c_1.registParam("clientType", ParamType.HeaderParam, "客户端类型");
      api_c_1.registParam("module", ParamType.HeaderParam, "所属模块");
      api_c_1.registParam("version", ParamType.HeaderParam, "版本号");
      // 设置预设值
      api_c_1.pushPreSet("clientIp", "192.168.10.243", VarScope.global);
      api_c_1.pushPreSet("deviceId", "80:71:7a:18:6a:63A000004F5FB862", VarScope.global);
      api_c_1.pushPreSet("clientType", "ios", VarScope.global);
      api_c_1.pushPreSet("module", "2", VarScope.global);
      api_c_1.pushPreSet("version", "1.3.0", VarScope.global);
      api_c_1
          .pushPreSet(
              "req",
              "{\"platform\":\"local\",\"phoneNum\":\"17700001199\",\"pwd\":\"123456\",\"lat\":\"126.123123\",\"lng\":\"33.8237745\"}");
      // 设置用例
      api_c_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_c_1.pushAfterSet("token", "sessionToken");
      api_c_1.pushAfterSet("user.id", "uid");
    }
  }

  public static void initTestMultiApiAction() {
    DataSourceConfig config = new DataSourceConfig();
    config.setAlias("dev");
    config
        .setDriverUrl("jdbc:mysql://mysql.dev.51xiaodou.com:3306/2business?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf8");
    config.setUserName("xddev");
    config.setPassword("xddev@xiaodou");
    ConnectionFactory.registMysqlDataSource(config);
    {
      api_d_1.setUniqueId(UUID.randomUUID().toString());
      api_d_1.setName("api_a_1");
      api_d_1.setUrl("http://119.61.66.57:8102/quesbk/exam_result");
      api_d_1.setTimeOut(5000);
      api_d_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_d_1.registParam("uid", "用户ID");
      api_d_1.registParam("courseId", "产品ID");
      api_d_1.registParam("typeCode", "专业码值");
      api_d_1.registParam("module", "所属模块");
      api_d_1.registParam("paperId", "试卷ID");
      api_d_1.registParam("examStatus", "提交状态");
      api_d_1.registParam("examTime", "做题耗时");
      api_d_1.registParam("examDetail", "答题详情");
      api_d_1.registParam("userCredit", "用户积分");
      // 设置预设值
      api_d_1.pushPreSet("courseId", "205767267");
      api_d_1.pushPreSet("typeCode", "01A0820");
      api_d_1.pushPreSet("paperId", "6bde5024-6bc6-478e-9abe-adc60ea5e603");
      api_d_1.pushPreSet("examStatus", "1");
      api_d_1.pushPreSet("examTime", "2");
      api_d_1
          .pushPreSet(
              "examDetail",
              "[{\"answerIdList\":[\"26915_2\"],\"quesId\":26915},{\"answerIdList\":[\"26964_2\"],\"quesId\":26964},{\"answerIdList\":[\"26887_1\"],\"quesId\":26887},{\"answerIdList\":[\"27061_2\"],\"quesId\":27061},{\"answerIdList\":[\"26882_1\"],\"quesId\":26882}]");
      api_d_1.pushPreSet("userCredit", "156");
      // 设置用例
      api_d_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_d_1.pushAfterSet("pkExam", "api_a_1_pkExam");
    }

    {
      api_e_1.setUniqueId(UUID.randomUUID().toString());
      api_e_1.setName("api_b_1");
      api_e_1.setUrl("http://119.61.66.57:8020/selftaught/login");
      api_e_1.setTimeOut(5000);
      api_e_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_e_1.registParam("req", "登录参数");
      api_e_1.registParam("clientIp", ParamType.HeaderParam, "客户端IP");
      api_e_1.registParam("deviceId", ParamType.HeaderParam, "设备ID");
      api_e_1.registParam("clientType", ParamType.HeaderParam, "客户端类型");
      api_e_1.registParam("module", ParamType.HeaderParam, "所属模块");
      api_e_1.registParam("version", ParamType.HeaderParam, "版本号");
      // 设置预设值
      api_e_1
          .pushPreSet(
              "headinfo",
              DataSource.Mysql,
              "dev#ds#select module $module$, token $sessionToken$, used_device_id $deviceId$, latest_device_ip $clientIp$, 'ios' $clientType$, '1.3.0' $version$ from xd_user where user_name = '13718037895';",
              VarScope.global, DataType.sTring);
      api_e_1
          .pushPreSet(
              "req",
              "{\"platform\":\"local\",\"phoneNum\":\"17700001199\",\"pwd\":\"123456\",\"lat\":\"126.123123\",\"lng\":\"33.8237745\"}");
      // 设置用例
      api_e_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_e_1.pushAfterSet("token", "sessionToken");
      api_e_1.pushAfterSet("user.id", "uid");
    }
  }

  public static void initTestLogicBranchApiAction() {
    {
      api_f_1.setUniqueId(UUID.randomUUID().toString());
      api_f_1.setName("api_a_1");
      api_f_1.setUrl("http://119.61.66.57:8102/quesbk/exam_result");
      api_f_1.setTimeOut(5000);
      api_f_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_f_1.registParam("uid", "用户ID");
      api_f_1.registParam("courseId", "产品ID");
      api_f_1.registParam("typeCode", "专业码值");
      api_f_1.registParam("module", "所属模块");
      api_f_1.registParam("paperId", "试卷ID");
      api_f_1.registParam("examStatus", "提交状态");
      api_f_1.registParam("examTime", "做题耗时");
      api_f_1.registParam("examDetail", "答题详情");
      api_f_1.registParam("userCredit", "用户积分");
      // 设置预设值
      api_f_1.pushPreSet("courseId", "205767267");
      api_f_1.pushPreSet("typeCode", "01A0820");
      api_f_1.pushPreSet("paperId", "6bde5024-6bc6-478e-9abe-adc60ea5e603");
      api_f_1.pushPreSet("examStatus", "1");
      api_f_1.pushPreSet("examTime", "2");
      api_f_1
          .pushPreSet(
              "examDetail",
              "[{\"answerIdList\":[\"26915_2\"],\"quesId\":26915},{\"answerIdList\":[\"26964_2\"],\"quesId\":26964},{\"answerIdList\":[\"26887_1\"],\"quesId\":26887},{\"answerIdList\":[\"27061_2\"],\"quesId\":27061},{\"answerIdList\":[\"26882_1\"],\"quesId\":26882}]");
      api_f_1.pushPreSet("userCredit", "156");
      // 前置校验
      api_f_1.pushPreCond("uid", ConditionScope.isNotNull);
      api_f_1.pushPreCond("module", "2");
      // 设置用例
      api_f_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_f_1.pushAfterSet("pkExam", "api_a_1_pkExam");
    }

    {
      api_g_1.setUniqueId(UUID.randomUUID().toString());
      api_g_1.setName("api_b_1");
      api_g_1.setUrl("http://119.61.66.57:8020/selftaught/login");
      api_g_1.setTimeOut(5000);
      api_g_1.setResultDataFormat(DataFormat.jSon);
      // 设置参数
      api_g_1.registParam("req", "登录参数");
      api_g_1.registParam("clientIp", ParamType.HeaderParam, "客户端IP");
      api_g_1.registParam("deviceId", ParamType.HeaderParam, "设备ID");
      api_g_1.registParam("clientType", ParamType.HeaderParam, "客户端类型");
      api_g_1.registParam("module", ParamType.HeaderParam, "所属模块");
      api_g_1.registParam("version", ParamType.HeaderParam, "版本号");
      // 设置预设值
      api_g_1
          .pushPreSet(
              "headinfo",
              DataSource.Mysql,
              "dev#ds#select module $module$, token $sessionToken$, used_device_id $deviceId$, latest_device_ip $clientIp$, 'ios' $clientType$, '1.3.0' $version$ from xd_user where user_name = '13718037895';",
              VarScope.global, DataType.sTring);
      api_g_1
          .pushPreSet(
              "req",
              "{\"platform\":\"local\",\"phoneNum\":\"17700001199\",\"pwd\":\"123456\",\"lat\":\"126.123123\",\"lng\":\"33.8237745\"}");
      // 设置用例
      api_g_1.pushTestCase("tc1", "retcode", "0", DataType.sTring.name());
      // 设置后置变量
      api_g_1.pushAfterSet("token", "sessionToken");
      api_g_1.pushAfterSet("user.id", "uid");
    }
  }

  public static void clear() {
    api_a_1 = new Api();
    api_b_1 = new Api();
    api_c_1 = new Api();
    api_d_1 = new Api();
    api_e_1 = new Api();
    api_f_1 = new Api();
    api_g_1 = new Api();
  }

}
