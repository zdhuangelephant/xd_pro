package com.xiaodou.crontmonitor.model;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.autotest.core.vo.ApiResult;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.crontmonitor.model.MonitorApiLog.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月18日
 * @description 监控项日式
 * @version 1.0
 */
@Data
@Xml(tableName = "xd_cront_monitor_api_log", outputDir = "")
public class MonitorApiLog {
  public MonitorApiLog() {
    this.id = UUID.randomUUID().toString();
  }

  @Column(isMajor = true)
  /** id 主键ID */
  private String id;
  /** apiId apiId */
  private String apiId;
  /** result 执行结果 */
  private String result;
  /** message 异常消息 */
  private String message;
  @Column(betweenScope = true)
  /** createTime 创建时间 */
  private Timestamp createTime;

  public void init(ApiResult apiResult) {
    if (null == apiResult) {
      return;
    }
    if (apiResult.getHasError()) {
      setResult(String.format("[Code:%s][Mes%s]", ResultType.SYSFAIL.getCode(),
          ResultType.SYSFAIL.name()));
      setMessage(apiResult.getErrorMessage());
    } else {
      setResult(String.format("[Code:%s][Mes%s]", ResultType.SUCCESS.getCode(),
          ResultType.SUCCESS.name()));
    }
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(MonitorApiLog.class).buildXml();
  }
}
