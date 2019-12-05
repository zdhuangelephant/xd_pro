package com.xiaodou.course.task;

import java.util.List;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.message.StudyCourseWareScore;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.user.UserCourseHourProgress;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.vo.message.StudyCourseWareScoreMessage;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("CaculateCourseWareScore")
public class CaculateCourseWareScoreWork extends AbstractDefaultWorker {

  private static final long serialVersionUID = -485452328350601733L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    UserCourseHourProgress info =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserCourseHourProgress.class);
    IQueryParam param = new QueryParam();
    param.addInput("module", info.getModule());
    param.addInput("courseId", info.getCourseId());
    param.addInput("status", CourseConstant.FINISHED);
    param.addOutputs(CommUtil.getGeneralField(UserCourseHourProgress.class));
    Page<UserCourseHourProgress> pageTaskInfo =
        productServiceFacade.queryUserCourseHourProgress(param, null);
    param = new QueryParam();
    param.addInput("productId", info.getCourseId());
    param.addInput("resourceType", info.getResourceType());
    Page<ProductItemModel> itemPage = productServiceFacade.queryProductItemPage(param);
    if (null != pageTaskInfo && null != itemPage && itemPage.getTotalCount() > 0) {
      Double dScore =
          new Double(pageTaskInfo.getTotalCount()) / new Double(itemPage.getTotalCount());
      StudyCourseWareScore score = new StudyCourseWareScore();
      score.setCourseId(info.getCourseId());
      score.setModule(info.getModule());
      score.setUserId(info.getUserId());
      score.setCourseWareScore(dScore);
      RabbitMQSender.getInstance().send(new StudyCourseWareScoreMessage(score));
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable e, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("记录每天学习时长 失败", e);
  }

}
