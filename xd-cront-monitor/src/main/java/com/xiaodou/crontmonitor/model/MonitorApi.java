package com.xiaodou.crontmonitor.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.crontmonitor.model.MonitorApi.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 监控API数据模型
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_cront_monitor_api", outputDir = "")
public class MonitorApi {
  /** uniqueId 唯一ID */
  @Column(isMajor = true)
  private String id;
  /** name 名称 */
  private String name;
  /** protocol 协议类型 */
  private String protocol;
  /** format 数据传输格式 */
  private String format;
  /** host 主机域名 */
  private String url;
  /** method 方法类型 */
  private String method;
  /** params 参数列表 */
  private String params;
  /** timeOut 超时时间 */
  private Integer timeOut;
  /** retryTime 重试次数 */
  private Integer retryTime;
  /** createTime 创建时间 */
  @Column(betweenScope = true)
  private Timestamp createTime;
  /** updateTime 更新时间 */
  @Column(betweenScope = true)
  private Timestamp updateTime;

  public Api getApi() {
    Api api = new Api(name);
    api.setUniqueId(id);
    api.setProtocol(protocol);
    api.setFormat(format);
    api.setUrl(url);
    api.setMethod(method);
    api.setVersion(Long.toString(updateTime.getTime()));
    if (StringUtils.isJsonNotBlank(params)) {
      List<Param> paramList = FastJsonUtil.fromJsons(params, new TypeReference<List<Param>>() {});
      api.setParams(paramList);
    }
    if (null != timeOut) {
      api.setTimeOut(timeOut);
    }
    if (null != retryTime) {
      api.setRetryTime(retryTime);
    }
    return api;
  }

}
