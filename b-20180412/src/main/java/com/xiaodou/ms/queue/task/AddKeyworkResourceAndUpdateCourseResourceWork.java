package com.xiaodou.ms.queue.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.course.CourseKeywordResourceModel;
import com.xiaodou.ms.service.course.CourseKeywordResourceService;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("AddKeyworkResourceAndUpdateCourseResource")
public class AddKeyworkResourceAndUpdateCourseResourceWork extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 681700950367469783L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    CourseKeywordResourceModel ckrm =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), CourseKeywordResourceModel.class);
    CourseKeywordResourceService courseKeywordResourceService =
        SpringWebContextHolder.getBean("courseKeywordResourceService");
    courseKeywordResourceService.addKeywordResource(ckrm);
    courseKeywordResourceService.updateResourceKeyPoint(ckrm.getResourceId(),
        ckrm.getResourceType());
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("批量导入试题更新考点中间表时出错", t.getCause());
  }

}
