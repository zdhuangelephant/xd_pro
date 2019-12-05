package com.xiaodou.course.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.user.UserLearnRecordDayModel;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.UserLearnRecordDayService;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("RecordLearnTimeForDay")
public class RecordLearnTimeForDayWork extends AbstractDefaultWorker {

  /**
   * 
   */
  private static final long serialVersionUID = -485452328350601733L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserLearnRecordModel learnRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserLearnRecordModel.class);
    recordLearnTimeForDay(learnRecord);
  }

  public void recordLearnTimeForDay(UserLearnRecordModel learnRecord) {
    UserLearnRecordDayService userLearnRecordDayService =
        SpringWebContextHolder.getBean("userLearnRecordDayService");
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", learnRecord.getUserId());
    cond.put("productId", learnRecord.getProductId());
    if (null != learnRecord.getChapterId()) cond.put("chapterId", learnRecord.getChapterId());
    if (null != learnRecord.getItemId()) cond.put("itemId", learnRecord.getItemId());
    cond.put("moduleId", learnRecord.getModuleId());
    cond.put("learnType", learnRecord.getLearnType());
    String today = DateUtil.SDF_YMD.format(new Date());
    UserLearnRecordDayModel learnRecordDay = new UserLearnRecordDayModel();
    learnRecordDay.setUserId(learnRecord.getUserId());
    learnRecordDay.setProductId(learnRecord.getProductId());
    learnRecordDay.setChapterId(learnRecord.getChapterId());
    learnRecordDay.setItemId(learnRecord.getItemId());
    learnRecordDay.setModuleId(learnRecord.getModuleId());
    learnRecordDay.setLearnType(learnRecord.getLearnType());
    cond.put("recordTimeLower", today);
    cond.put("recordTimeUpper", DateUtil.getDateForDateAndDays(new Date(), 0, 0, 1));
    learnRecordDay.setLearnTime(productServiceFacade.sumLearnTimeByCond(cond));
    learnRecordDay.setRecordTime(new Date());
    cond.put("recordTime", today);
    List<UserLearnRecordDayModel> recordDayList =
        userLearnRecordDayService.queryLearnRecordDay(cond,
            CommUtil.getAllField(UserLearnRecordDayModel.class));
    if (null == recordDayList || recordDayList.size() < 1) {
      learnRecordDay.setCreateTime(new Timestamp(System.currentTimeMillis()));
      userLearnRecordDayService.addlearnRecordDay(learnRecordDay);
    } else {
      for (UserLearnRecordDayModel recordDay : recordDayList) {
        if (null == recordDay.getId()) continue;
        Map<String, Object> updateCond = Maps.newHashMap();
        updateCond.put("id", recordDay.getId());
        userLearnRecordDayService.updateLearnRecordDay(updateCond, learnRecordDay);
      }
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
