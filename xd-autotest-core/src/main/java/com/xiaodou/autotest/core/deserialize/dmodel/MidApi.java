package com.xiaodou.autotest.core.deserialize.dmodel;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.autotest.core.ActionEnum.ApiStatus;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.ActionEnum.Format;
import com.xiaodou.autotest.core.ActionEnum.Method;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.core.model.AfterSet;
import com.xiaodou.autotest.core.model.PreCondition;
import com.xiaodou.autotest.core.model.PreSet;
import com.xiaodou.autotest.core.model.TestCase;
import com.xiaodou.autotest.core.vo.ApiResult;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Api.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 反序列化API组件模型
 * @version 1.0
 */
public class MidApi {

  /** uniqueId 唯一ID */
  private String uniqueId;
  /** name 名称 */
  private String name;
  /** protocol 协议类型 */
  private Protocol protocol;
  /** format 数据传输格式 */
  private Format format;
  /** host 主机域名 */
  private String url;
  /** method 方法类型 */
  private Method method;
  /** params 参数列表 */
  private List<MidParam> params = Lists.newArrayList();
  /** timeOut 超时时间 */
  private Integer timeOut;
  /** retryTime 重试次数 */
  private Integer retryTime;
  /** preConds 前置校验条件 */
  private LinkedList<PreCondition> preConds = Lists.newLinkedList();
  /** preSets 前置设置列表 */
  private LinkedList<PreSet> preSets = Lists.newLinkedList();
  /** tastCases 测试条目列表 */
  private LinkedList<TestCase> testCases = Lists.newLinkedList();
  /** afterSets 后置设置列表 */
  private LinkedList<AfterSet> afterSets = Lists.newLinkedList();
  /** resultDataType 返回值类型 */
  private DataFormat resultDataFormat;
  /** apiResult api执行结果 */
  private ApiResult apiResult;
  /** version 版本号 */
  private String version;
  /** special 临时版本 */
  private Boolean special;
  /** status 执行状态 */
  private ApiStatus status;

  public MidApi() {}

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public Format getFormat() {
    return format;
  }

  public void setFormat(Format format) {
    this.format = format;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public List<MidParam> getParams() {
    return params;
  }

  public void setParams(List<MidParam> params) {
    this.params = params;
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

  public LinkedList<PreCondition> getPreConds() {
    return preConds;
  }

  public void setPreConds(LinkedList<PreCondition> preConds) {
    this.preConds = preConds;
  }

  public LinkedList<PreSet> getPreSets() {
    return preSets;
  }

  public void setPreSets(LinkedList<PreSet> preSets) {
    this.preSets = preSets;
  }

  public LinkedList<TestCase> getTestCases() {
    return testCases;
  }

  public void setTestCases(LinkedList<TestCase> testCases) {
    this.testCases = testCases;
  }

  public LinkedList<AfterSet> getAfterSets() {
    return afterSets;
  }

  public void setAfterSets(LinkedList<AfterSet> afterSets) {
    this.afterSets = afterSets;
  }

  public DataFormat getResultDataFormat() {
    return resultDataFormat;
  }

  public void setResultDataFormat(DataFormat resultDataFormat) {
    this.resultDataFormat = resultDataFormat;
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

  public ApiStatus getStatus() {
    return status;
  }

  public void setStatus(ApiStatus status) {
    this.status = status;
  }

}
