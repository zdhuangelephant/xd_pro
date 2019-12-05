package com.xiaodou.crontab.engine.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.crontab.engine.protocol.CrontResult.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 定时任务执行结果
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrontResult extends ResultInfo {
  public CrontResult(ResultType type) {
    super(type);
  }

  /** cost 执行耗时 */
  private Long cost;
}
