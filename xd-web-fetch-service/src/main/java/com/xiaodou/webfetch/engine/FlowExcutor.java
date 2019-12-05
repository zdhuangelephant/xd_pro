package com.xiaodou.webfetch.engine;

import com.xiaodou.webfetch.action.IncisePoint;
import com.xiaodou.webfetch.model.FetchTacticModel;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.service.QueueService;
import com.xiaodou.webfetch.service.QueueService.NotifyTaskCommand;

/**
 * @name @see com.xiaodou.webfetch.engine.FlowExcutor.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月7日
 * @description 内容抓取流程执行引擎
 * @version 1.0
 */
public class FlowExcutor {

  /**
   * 执行抓取操作
   */
  public static void start(FetchTacticModel model) {
    SandBoxContext context = SandBoxContext.newInstance();
    context.init(model);
    if (null != context.getStartAction()) {
      QueueService.notifyTask(new NotifyTaskCommand(context.unique(), context.getStartAction().unique()));
    }
  }

  /**
   * 执行抓取操作
   */
  public static void flowAction(FetchTask task, IncisePoint action, SandBoxContext sandBox) {
    action.doMain(task, sandBox);
  }

}
