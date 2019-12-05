package com.xiaodou.mission.engine.initialize;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.MissionModel;
import com.xiaodou.mission.domain.ProductRelation;
import com.xiaodou.mission.engine.EventListenerHolder;
import com.xiaodou.mission.engine.ProRelationCatHolder;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.listener.IEventListener;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.service.facade.MissionOperationFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.mission.engine.initialize.MissionInitFactory.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月12日
 * @description 任务初始化工厂
 * @version 1.0
 */
public class MissionInitFactory {

  MissionOperationFacade missionOperationFacade = SpringWebContextHolder
      .getBean(MissionOperationFacade.class);

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** quesTypeDelay 刷新延迟 */
  private Integer refreshDelay = 5;

  public void init() {

    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {

      @Override
      public void doMain() {
        refreshProductCatRelationHolder();
        refreshMissionHolder();
      }

      /** 刷新产品关联专业关系容器 */
      private void refreshProductCatRelationHolder() {
        IQueryParam param = new QueryParam();
        param.addInput("relationState", MissionConstant.PRODUCT_RELATION_INUSE);
        param.addOutputs(ProductRelation.class);
        Page<ProductRelation> relationPage = missionOperationFacade.queryProductRelationPage(param);
        if (null == relationPage || null == relationPage.getResult()
            || relationPage.getResult().isEmpty()) {
          return;
        }
        for (ProductRelation relation : relationPage.getResult()) {
          ProRelationCatHolder.registRelation(relation.getProductId(),
              relation.getProductCategoryId());
        }

      }

      /** 刷新任务容器 */
      private void refreshMissionHolder() {
        MissionModel cond = new MissionModel();
        cond.setMissionState(MissionConstant.MISSON_STATE_INUSE);
        List<MissionModel> missionList = missionOperationFacade.queryMissionModelList(cond);
        if (null == missionList || missionList.isEmpty()) {
          return;
        }
        EventListenerHolder.switchSnapshot();
        for (MissionModel model : missionList) {
          MissionInstance instance = null;
          try {
            instance = new MissionInstance(model);
          } catch (Exception e) {
            LoggerUtil.error("任务模型数据异常", e);
            continue;
          }
          if (null == instance || null == instance.getCondition()) {
            continue;
          }
          List<Class<? extends BaseEvent>> eventList = instance.getCondition().getEventList();
          IEventListener listener = instance.getCondition().getEventListener();
          listener.setMissionInstance(instance);
          for (Class<? extends BaseEvent> eventType : eventList) {
            EventListenerHolder.registListener(eventType, listener);
          }
        }
        EventListenerHolder.switchNewest();
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("刷新任务模型数据异常.", t);
      }
    }, initialDelay, refreshDelay, TimeUnit.SECONDS);

  }

}
