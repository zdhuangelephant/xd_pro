package com.xiaodou.course.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.web.request.product.LearnRecordRequestLT;
import com.xiaodou.course.web.request.product.PersonProductInfoRequest;
import com.xiaodou.course.web.response.user.PersonInfoResponse;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;


/**
 * @name UpdateUserCourseTaskInfoWorker CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年6月22日
 * @description 更新用户不同课件情况下学习的时长worker
 * @version 1.0
 */
@HandlerMessage("UpdatePersonProductInfo")
public class UpdatePersonProductInfoWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -7579776778501118850L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    LearnRecordRequestLT request =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), LearnRecordRequestLT.class);
    ProductService productService = SpringWebContextHolder.getBean("productService");
    PageManager pageManager = SpringWebContextHolder.getBean("pageManager");
    PersonProductInfoRequest pojo = new PersonProductInfoRequest();
    pojo.setUid(request.getUid());
    PersonInfoResponse response = productService.personProductInfo0(pojo);
    String key = pojo.getCacheKey();
    pageManager.addPage(key, response, CourseConstant.MONGO_CACHE_TIME_DAYS, true, TimeUnit.DAYS);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("计时接口、操作xd_user_course_hour_progress失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("计时接口、操作xd_user_course_hour_progress失败", t);
  }

}
