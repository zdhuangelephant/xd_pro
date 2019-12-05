package com.xiaodou.webfetch.plugin.jsoup;

import org.jsoup.Connection.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.webfetch.plugin.jsoup.JsoupResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月14日
 * @description Jsoup插件响应
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JsoupResponse extends ResultInfo {

  /** 无参构造 */
  public JsoupResponse() {}

  /** 枚举构造 */
  public JsoupResponse(ResultType type) {
    super(type);
  }

  /** message 异常消息 */
  private String message;
  /** cost 耗时 */
  private Long cost;
  /** response 响应 */
  private Response response;
}
