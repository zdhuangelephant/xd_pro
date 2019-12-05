package com.xiaodou.autotest.core.model;

import org.springframework.util.Assert;

import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.TestResult;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.vo.SandBoxContext;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.vo.TestCase.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月11日
 * @description 测试条目
 * @version 1.0
 */
public class TestCase {

  /** name 条目名 */
  private String name = ActionConstant.DEFAULT_TEST_CASE_NAME;
  /** testKey 测试键 */
  private String testKey;
  /** dataType 数据类型 */
  private DataType dataType = DataType.sTring;
  /** testValue 测试值 */
  private String testValue;
  /** testResult 测试结果 */
  private TestResult testResult = TestResult.Unknown;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTestKey() {
    return testKey;
  }

  public void setTestKey(String testKey) {
    this.testKey = testKey;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    Assert.notNull(dataType, "TestCase's dataType can't be null.");
    this.dataType = dataType;
  }

  public void setDataType(String dataType) {
    DataType eDataType = ActionTool.getEnumValue(DataType.class, dataType);
    setDataType(eDataType);
  }

  public String getTestValue() {
    return testValue;
  }

  public void setTestValue(String testValue) {
    this.testValue = testValue;
  }

  public TestResult getTestResult() {
    return testResult;
  }

  public void setTestResult(TestResult testResult) {
    this.testResult = testResult;
  }

  public void checkVar(SandBoxContext sandBox) {
    if (StringUtils.isOrBlank(name, testValue)) {
      testResult = TestResult.Success;
      return;
    }
    if (null == sandBox) {
      testResult = TestResult.Fail;
      return;
    }
    Object varValue = sandBox.getParam(testKey);
    if (ActionTool.checkRegex(testValue)) {
      Object oTestValue = ActionTool.getValue(sandBox, testValue);
      if (null != oTestValue) {
        testValue = oTestValue.toString();
      } else {
        testValue = StringUtils.EMPTY;
      }
    }
    Object expectValue = dataType.getData(testValue);
    if (null != expectValue && expectValue.equals(varValue)) {
      testResult = TestResult.Success;
    } else {
      testResult = TestResult.Fail;
    }
  }
}
