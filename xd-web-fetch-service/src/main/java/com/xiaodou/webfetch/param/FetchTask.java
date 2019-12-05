package com.xiaodou.webfetch.param;

import lombok.Data;

import com.xiaodou.webfetch.model.FetchTaskModel;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.param.FetchTask.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月8日
 * @description 抓取任务执行模型
 * @version 1.0
 */
@Data
public class FetchTask {
  /** id 主键*/
  private String id;
  /** url url地址 */
  private String url;
  /** dependAction 依赖动作 */
  private Target dependAction;
  /** contentTarget 目标操作板 */
  private Target contentTarget;
  /** defaultAction 默认动作 */
  private Target defaultAction;
  public FetchTask() {};
  public FetchTask(FetchTaskModel model) {
    // modify by zdhuang 
    this.id = model.getId();
    
    this.url = model.getUrl();
    this.dependAction = new Target(model.getDependActionId());
    this.contentTarget = new Target(model.getTargetFieldId());
    this.defaultAction = new Target(model.getStartActionId());
  }

}
