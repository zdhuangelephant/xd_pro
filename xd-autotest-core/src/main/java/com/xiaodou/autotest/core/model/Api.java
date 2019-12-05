package com.xiaodou.autotest.core.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.Assert;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.ApiStatus;
import com.xiaodou.autotest.core.ActionEnum.ConditionScope;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.ActionEnum.DataSource;
import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.Format;
import com.xiaodou.autotest.core.ActionEnum.Method;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.core.ActionEnum.VarScope;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.vo.ApiRequest;
import com.xiaodou.autotest.core.vo.ApiResult;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Api.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description API组件模型, 代表了业务系统注册的一个服务
 * @version 1.0
 */
public class Api implements Comparable<Api> {

  /** uniqueId 唯一ID */
  private String uniqueId;
  /** name 名称 */
  private String name;
  /** protocol 协议类型 */
  private Protocol protocol = ActionConstant.DEFAULT_PROTOCOL;
  /** format 数据传输格式 */
  private Format format = ActionConstant.DEFAULT_FORMAT;
  /** host 主机域名 */
  private String url;
  /** method 方法类型 */
  private Method method = ActionConstant.DEFAULT_METHOD;
  /** params 参数列表 */
  private Map<String, Param> params = Maps.newHashMap();
  /** timeOut 超时时间 */
  private Integer timeOut = ActionConstant.DEFAULT_TIMEOUT;
  /** retryTime 重试次数 */
  private Integer retryTime = ActionConstant.DEFAULT_RETRY;
  /** preConds 前置校验条件 */
  private LinkedList<PreCondition> preConds = Lists.newLinkedList();
  /** preSets 前置设置列表 */
  private LinkedList<PreSet> preSets = Lists.newLinkedList();
  /** tastCases 测试条目列表 */
  private LinkedList<TestCase> testCases = Lists.newLinkedList();
  /** afterSets 后置设置列表 */
  private LinkedList<AfterSet> afterSets = Lists.newLinkedList();
  /** resultDataType 返回值类型 */
  private DataFormat resultDataFormat = ActionConstant.DEFAULT_DATA_TYPE;
  /** apiRequest api实际请求头 */
  private ApiRequest apiRequest = new ApiRequest();
  /** apiResult api执行结果 */
  private ApiResult apiResult = new ApiResult();
  /** version 版本号 */
  private String version;
  /** special 临时版本 */
  private Boolean special = Boolean.FALSE;
  /** status 执行状态 */
  private ApiStatus status = ActionConstant.DEFAULT_API_STATUS;
  /** previous 行为链,指向前一版本 */
  @JSONField(serialize = false, deserialize = false)
  private Api previous;

  public Api(String name) {
    this.name = name;
  }

  public Api() {
    this(ActionConstant.DEFAULT_API_NAME);
  }

  public synchronized String getUniqueId() {
    if (null == uniqueId) {
      uniqueId = UUID.randomUUID().toString();
    }
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getName() {
    Assert.hasText(name, "Api's name can't be bull.");
    return name;
  }

  public void setName(String name) {
    if (StringUtils.isBlank(this.name) || ActionConstant.DEFAULT_API_NAME.equals(this.name)) {
      this.name = name;
    }
  }

  public List<Param> getParams() {
    if (null == params) {
      return null;
    }
    return Lists.newArrayList(params.values());
  }

  public Param getParam(String name, ParamType paramType) {
    if (null == params) {
      return null;
    }
    return params.get(String.format(ActionConstant.DEFAULT_CONTACT_KEY, paramType, name));
  }

  public Param getParam(String name) {
    if (null == params) {
      return null;
    }
    return params
        .get(String.format(ActionConstant.DEFAULT_CONTACT_KEY, ParamType.QueryParam, name));
  }

  public void setParams(List<Param> params) {
    if (null == params || params.isEmpty()) {
      return;
    }
    for (Param par : params) {
      if (null == par || StringUtils.isBlank(par.getName())) {
        continue;
      }
      registParam(par.getName(), par.getDataType(), par.getParamType(), par.getDesc(),
          par.getParamValue());
    }
  }

  public void setParamValue(String name, String value) {
    if (null != params) {
      Param param = getParam(name);
      if (null != param) {
        param.setParamValue(value);
      }
    }
  }

  public void setParamValue(String name, ParamType paramType, String value) {
    if (null != params) {
      Param param = getParam(name, paramType);
      if (null != param) {
        param.setParamValue(value);
      }
    }
  }

  public void registParam(String name, String desc) {
    registParam(name, null, null, desc, null);
  }

  public void registParam(String name, DataType dataType, String desc) {
    registParam(name, dataType, null, desc, null);
  }

  public void registParam(String name, ParamType paramType, String desc) {
    registParam(name, null, paramType, desc, null);
  }

  public void registParam(String name, DataType dataType, ParamType paramType, String desc,
      String paramValue) {
    if (StringUtils.isBlank(name)) {
      return;
    }
    if (null == dataType) {
      dataType = DataType.sTring;
    }
    if (null == paramType) {
      paramType = ParamType.QueryParam;
    }
    Param param = new Param(name, dataType.name(), desc);
    if (null == paramValue) {
      param.setParamValue(String.format(ActionConstant.DEFAULT_PARAM_TMP, name));
    } else {
      param.setParamValue(paramValue);
    }
    param.setParamType(paramType.name());
    params.put(String.format(ActionConstant.DEFAULT_CONTACT_KEY, paramType, name), param);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Format getFormat() {
    return format;
  }

  public void setFormat(String formatName) {
    Format format = ActionTool.getEnumValue(Format.class, formatName);
    if (null != format) {
      this.format = format;
    }
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocolName) {
    Protocol protocol = ActionTool.getEnumValue(Protocol.class, protocolName);
    if (null != protocol) {
      this.protocol = protocol;
    }
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(String methodName) {
    Method method = ActionTool.getEnumValue(Method.class, methodName);
    if (null != method) {
      this.method = method;
    }
  }

  @Override
  public int compareTo(Api other) {
    return this.version.compareTo(other.version);
  }

  public Api getSuitable(String version) {
    int compareTo = this.version.compareTo(version);
    if (compareTo == 0) {
      return this;
    }
    if (!special && compareTo < 0) {
      return this;
    }
    if (null != previous) {
      return previous.getSuitable(version);
    }
    return null;
  }

  public Integer getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
  }

  public Integer getRetryTime() {
    return retryTime;
  }

  public void setRetryTime(Integer retryTime) {
    this.retryTime = retryTime;
  }

  public void setPrevious(Api previous) {
    if (null != previous) {
      this.previous = previous;
    }
  }

  public Api getPrevious() {
    return previous;
  }

  public DataFormat getResultDataFormat() {
    return resultDataFormat;
  }

  public void setResultDataFormat(DataFormat resultDataFormat) {
    this.resultDataFormat = resultDataFormat;
  }

  public void setResultDataFormat(String resultDataFormat) {
    DataFormat dataFormat = ActionTool.getEnumValue(DataFormat.class, resultDataFormat);
    if (null != dataFormat) {
      this.resultDataFormat = dataFormat;
    }
  }

  public LinkedList<PreCondition> getPreConds() {
    return preConds;
  }

  public LinkedList<PreSet> getPreSets() {
    return preSets;
  }

  public LinkedList<TestCase> getTestCases() {
    return testCases;
  }

  public LinkedList<AfterSet> getAfterSets() {
    return afterSets;
  }

  public void pushPreCond(String key, String value) {
    pushPreCond(key, value, ConditionScope.eq.name());
  }

  public void pushPreCond(String key, ConditionScope condition) {
    pushPreCond(key, StringUtils.EMPTY, condition.name());
  }

  public void pushPreCond(String key, String value, ConditionScope condition) {
    pushPreCond(key, value, condition.name());
  }

  public void pushPreCond(String key, String value, String condition) {
    PreCondition cond = new PreCondition();
    cond.setKey(key);
    cond.setTargetValue(value);
    cond.setCondition(condition);
    this.preConds.add(cond);
  }

  public void pushPreSet(String key, String assignment) {
    pushPreSet(key, DataSource.Local.name(), assignment, VarScope.part.name(),
        DataType.sTring.name());
  }

  public void pushPreSet(String key, String assignment, VarScope varScope) {
    pushPreSet(key, DataSource.Local.name(), assignment, varScope.name(), DataType.sTring.name());
  }

  public void pushPreSet(String key, String assignment, DataType dataType) {
    pushPreSet(key, DataSource.Local.name(), assignment, VarScope.part.name(), dataType.name());
  }

  public void pushPreSet(String key, String dataSource, String assignment) {
    pushPreSet(key, dataSource, assignment, VarScope.part.name(), DataType.sTring.name());
  }

  public void pushPreSet(String key, DataSource dataSource, String assignment) {
    pushPreSet(key, dataSource.name(), assignment, VarScope.part.name(), DataType.sTring.name());
  }

  public void pushPreSet(String key, DataSource dataSource, String assignment, VarScope varScope,
      DataType dataType) {
    pushPreSet(key, dataSource.name(), assignment, varScope.name(), dataType.name());
  }

  public void pushPreSet(String key, String dataSource, String assignment, String varScope,
      String dataType) {
    PreSet preSet = new PreSet();
    preSet.setKey(key);
    preSet.setDataSource(dataSource);
    preSet.setAssignment(assignment);
    preSet.setScope(varScope);
    preSet.setDataType(dataType);
    this.preSets.add(preSet);
  }

  public void pushTestCase(String name, String key, String value, String dataType) {
    TestCase testCase = new TestCase();
    testCase.setName(name);
    testCase.setTestKey(String.format(ActionConstant.DEFAULT_CONTACT_KEY, getUniqueId(), key));
    testCase.setTestValue(value);
    testCase.setDataType(dataType);
    this.testCases.add(testCase);
  }

  public void pushTestCase(String name, String key, String value, DataType dataType) {
    pushTestCase(name, key, value, dataType.name());
  }

  public void pushTestCase(String name, String key, String value) {
    pushTestCase(name, key, value, DataType.sTring.name());
  }

  public void pushTestCase(String key, String value) {
    pushTestCase(String.format(ActionConstant.DEFAULT_TEST_CASE_NAME, getName()), key, value,
        DataType.sTring.name());
  }

  public void pushAfterSet(String resultKey, String mappingKey, String dataType) {
    AfterSet afterSet = new AfterSet();
    afterSet.setResultkey(String.format(ActionConstant.DEFAULT_CONTACT_KEY, getUniqueId(),
        resultKey));
    afterSet.setDataType(dataType);
    afterSet.setMappingKey(mappingKey);
    this.afterSets.add(afterSet);
  }

  public void pushAfterSet(String resultKey, String mappingKey, DataType dataType) {
    pushAfterSet(resultKey, mappingKey, dataType.name());
  }

  public void pushAfterSet(String resultKey, String mappingKey) {
    pushAfterSet(resultKey, mappingKey, DataType.sTring.name());
  }

  public void pushAfterSet(String resultKey) {
    pushAfterSet(resultKey, resultKey, DataType.sTring.name());
  }

  public void setStatus(ApiStatus status) {
    this.status = status;
  }

  public ApiRequest getApiRequest() {
    return apiRequest;
  }

  public void setApiRequest(ApiRequest apiRequest) {
    this.apiRequest = apiRequest;
  }

  public ApiResult getApiResult() {
    return apiResult;
  }

  public void setApiResult(ApiResult apiResult) {
    this.apiResult = apiResult;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Boolean getSpecial() {
    return special;
  }

  public void setSpecial(Boolean special) {
    this.special = special;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public void setFormat(Format format) {
    this.format = format;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public void markExcute() {
    this.status = ApiStatus.Excuted;
  }

  public ApiStatus getStatus() {
    return status;
  }

}
