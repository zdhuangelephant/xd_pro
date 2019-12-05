package com.xiaodou.autotest.core.vo;

import java.sql.Timestamp;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.DataFormat;
import com.xiaodou.autotest.core.interfaces.SetVar;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.vo.ApiResult.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description Api结果模型
 * @version 1.0
 */
public class ApiResult implements SetVar {

  /** apiUniqueId 所属API-ID */
  private String apiUniqueId;
  /** format 返回值格式 */
  private DataFormat format = ActionConstant.DEFAULT_DATA_TYPE;
  /** hasError 行正常 */
  private Boolean hasError = Boolean.FALSE;
  /** httpStatus Http状态码 */
  private Integer httpStatus = 0;
  /** header 响应头 */
  private Map<String, String> header = Maps.newHashMap();
  /** srcResult 源返回值 */
  private String srcResult;
  /** errorMessage 异常信息 */
  private String errorMessage;
  /** startTime 请求开始时间 */
  private Timestamp startTime;
  /** endTime 请求结束时间 */
  private Timestamp endTime;
  /** costTime 请求耗时 */
  private Long costTime;

  public String getApiUniqueId() {
    return apiUniqueId;
  }

  public void setApiUniqueId(String apiUniqueId) {
    this.apiUniqueId = apiUniqueId;
  }

  public DataFormat getFormat() {
    return format;
  }

  public void setFormat(DataFormat format) {
    this.format = format;
  }

  public Boolean getHasError() {
    return hasError;
  }

  public void setHasError(Boolean hasError) {
    this.hasError = hasError;
  }

  public Integer getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public Map<String, String> getHeader() {
    return header;
  }

  public void setHeader(Map<String, String> header) {
    this.header = header;
  }

  public String getSrcResult() {
    return srcResult;
  }

  public void setSrcResult(String srcResult) {
    this.srcResult = srcResult;
  }

  @Override
  public void setVar(SandBoxContext sandBox) {
    if (StringUtils.isBlank(srcResult)) {
      return;
    }
    sandBox.setParam(apiUniqueId, format.format(srcResult));
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public Long getCostTime() {
    return costTime;
  }

  public void setCostTime(Long costTime) {
    this.costTime = costTime;
  }

}
