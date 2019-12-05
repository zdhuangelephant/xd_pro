package com.xiaodou.webfetch.plugin.phantom;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.webfetch.plugin.PhantomResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月12日
 * @description Phantom插件响应
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PhantomResponse extends ResultInfo {

  /** 无参构造 */
  public PhantomResponse() {}

  /** 枚举构造 */
  public PhantomResponse(ResultType type) {
    super(type);
  }

  /** message 异常消息 */
  private String message;
  /** cost 耗时 */
  private Long cost;
  /** content 内容 */
  private String content;
}
