package com.xiaodou.course.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.util.CollectionUtils;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.product.CourseResourceModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.user.UserCourseHourProgress;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.ProductItemService;
import com.xiaodou.course.web.request.product.LearnRecordRequest.PointVo;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;


/**
 * @name UpdateUserCourseTaskInfoWorker CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年6月22日
 * @description 更新用户不同课件情况下学习的时长worker
 * @version 1.0
 */
@HandlerMessage("UpdateUserCourseHourProgress")
public class UpdateUserCourseHourProgressWorker extends AbstractDefaultWorker {
  // TODO 在ms该该节下添加完成视频或者音频资源的时候即时长发生变化的时候， 需要发送异步消息进行xd_user_course_task_info该表数据的更新！！切记

  /** serialVersionUID */
  private static final long serialVersionUID = -7579776778501118850L;
  ProductServiceFacade productServiceFacade = null;
  ProductItemService productItemService = null;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    productServiceFacade = SpringWebContextHolder.getBean("productServiceFacade");
    productItemService = SpringWebContextHolder.getBean("productItemService");
    UserCourseHourProgress userCourseTaskInfo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserCourseHourProgress.class);
    
    Map<String, UserCourseHourProgress> progressMap = Maps.newHashMap();
    
    IQueryParam param = new QueryParam();
    param.addInput("module", userCourseTaskInfo.getModule());
    param.addInput("courseId", userCourseTaskInfo.getCourseId());
    param.addInput("chapterId", userCourseTaskInfo.getChapterId());
    param.addInput("itemId", userCourseTaskInfo.getItemId());
    param.addInput("userId", userCourseTaskInfo.getUserId());
    // 资源类型(1 题库，2 视频，3 文档，4 知识点 ,5 章，6节,7音频)这个字段主要的作用是用来区分不同的资源表,取自item表
    param.addInput("resourceType", userCourseTaskInfo.getResourceType());
    for (PointVo vo : userCourseTaskInfo.getPoints()) {
      param.addInput("keyPointId", vo.getPointId());
      param.addOutputs(CommUtil.getGeneralField(UserCourseHourProgress.class));
      Page<UserCourseHourProgress> pageTaskInfo =
          productServiceFacade.queryUserCourseHourProgress(param, null);
      if (null != pageTaskInfo && !CollectionUtils.isEmpty(pageTaskInfo.getResult())) {
        // 有记录用户学习的行为， 则进行更新操作
        for (UserCourseHourProgress pro : pageTaskInfo.getResult()) {
          if (vo.getPointId() != null && pro.getKeyPointId().equals(vo.getPointId())
              && null != vo.getLastLearnTime()
              && (Long.parseLong(vo.getLastLearnTime()) > pro.getLearnTime())) {
            pro.setLearnTime(Long.parseLong(vo.getLastLearnTime()));
            if (Long.parseLong(vo.getLastLearnTime()) >= pro.getDuration()) {
              pro.setLearnTime(pro.getDuration());
              pro.setStatus(CourseConstant.FINISHED);
            }
            // 更新
            productServiceFacade.updateUserCourseHourProgress(pro);
            break;
          }
        }
      } else {
        // 找不到任何用户关于该考点的学习记录 则进行插入操作
        insertIntoUserCourseHourProgress(progressMap, userCourseTaskInfo, vo);
      }
    }
  }

  private void initProgressMap(Map<String, UserCourseHourProgress> progressMap,
      UserCourseHourProgress userCourseTaskInfo) {
    // 获取该节下的该考点下的音频时长
    if (userCourseTaskInfo.getResourceType().longValue() == CourseConstant.RESOURCE_TYPE_AUDIO) {
      progressMap.putAll(getResourcesByItemIdAndResType(userCourseTaskInfo,
          CourseConstant.RESOURCE_TYPE_AUDIO));
    } else if (userCourseTaskInfo.getResourceType().longValue() == CourseConstant.RESOURCE_TYPE_VIDEO) {
      progressMap.putAll(getResourcesByItemIdAndResType(userCourseTaskInfo,
          CourseConstant.RESOURCE_TYPE_VIDEO));
    } else if (userCourseTaskInfo.getResourceType().longValue() == CourseConstant.RESOURCE_TYPE_MICRO) {
      progressMap.putAll(getResourcesByItemIdAndResType(userCourseTaskInfo,
          CourseConstant.RESOURCE_TYPE_MICRO));
    }
  }

  private void insertIntoUserCourseHourProgress(Map<String, UserCourseHourProgress> progressMap,
      UserCourseHourProgress userCourseTaskInfo, PointVo vo) {
    if (null == progressMap || progressMap.isEmpty()) {
      initProgressMap(progressMap, userCourseTaskInfo);
    }
    UserCourseHourProgress pro = progressMap.get(vo.getPointId());
    if (null == pro) {
      return;
    }
    pro.setModule(userCourseTaskInfo.getModule());
    pro.setCourseId(userCourseTaskInfo.getCourseId());
    pro.setChapterId(userCourseTaskInfo.getChapterId());
    pro.setItemId(userCourseTaskInfo.getItemId());
    pro.setUserId(userCourseTaskInfo.getUserId());
    pro.setResourceType(userCourseTaskInfo.getResourceType());
    pro.setLearnTime(Long.parseLong(vo.getLastLearnTime()));
    pro.setStatus(CourseConstant.UNFINISHED);
    if (pro.getLearnTime() >= pro.getDuration()) {
      pro.setLearnTime(pro.getDuration());
      pro.setStatus(CourseConstant.FINISHED);
    }
    productServiceFacade.insertUserCourseHourProgress(pro);
  }



  /**
   * 填充资源时长、和考点、并且返回填充完毕后的数据集合
   * 
   * @param info
   * @param resourceType
   * @return
   */
  private Map<String, UserCourseHourProgress> getResourcesByItemIdAndResType(
      UserCourseHourProgress info, Long resourceType) {
    // 获取某节下某个资源类型的所有资源数据
    List<ProductItemModel> resourcesOfItem =
        productItemService.queryItemList(info.getCourseId(), info.getItemId(), null, resourceType);
    Map<String, UserCourseHourProgress> progressMap = Maps.newHashMap();
    if (null != resourceType && !CollectionUtils.isEmpty(resourcesOfItem)) {
      for (ProductItemModel res : resourcesOfItem) {
        CourseResourceModel cresources =
            FastJsonUtil.fromJson(res.getResource(), CourseResourceModel.class);
        AtomicLong sumsVideoDurs = new AtomicLong(0);
        if (null == cresources.getTimeLengthMinute() && null == cresources.getTimeLengthSecond())
          continue;
        if (null != cresources.getTimeLengthMinute())
          sumsVideoDurs.addAndGet(cresources.getTimeLengthMinute() * 60);
        if (null != cresources.getTimeLengthSecond())
          sumsVideoDurs.addAndGet(cresources.getTimeLengthSecond());
        UserCourseHourProgress ele = new UserCourseHourProgress();
        ele.setDuration(sumsVideoDurs.get());
        // 将Item表的Id设置为考点Id
        ele.setKeyPointId(res.getId() + "");
        progressMap.put(ele.getKeyPointId(), ele);
      }
    }
    return progressMap;
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
